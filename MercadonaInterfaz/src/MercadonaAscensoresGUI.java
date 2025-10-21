import javax.swing.*;
import java.awt.*;
import java.util.Random;
import java.util.concurrent.Semaphore;

public class MercadonaAscensoresGUI extends JFrame {

    private static final int CAPACIDAD_ASCENSOR = 5;
    private static final int CAPACIDAD_TIENDA = 1133;
    private static final int TIEMPO_VIAJE = 2000;

    private static final Semaphore ascensor1 = new Semaphore(CAPACIDAD_ASCENSOR, true);
    private static final Semaphore ascensor2 = new Semaphore(CAPACIDAD_ASCENSOR, true);
    private static final Semaphore miMercadona = new Semaphore(CAPACIDAD_TIENDA, true);

    private static final Random random = new Random();

    // Componentes de la interfaz
    private JTextArea areaEventos;
    private JProgressBar aforoBar;
    private JLabel lblAforoActual;
    private JLabel lblAsc1, lblAsc2;

    public MercadonaAscensoresGUI() {
        setTitle("Simulación Mercadona - Ascensores");
        setSize(700, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout());

        // Panel superior
        JPanel panelTop = new JPanel();
        panelTop.setLayout(new GridLayout(1, 2, 10, 10));
        lblAforoActual = new JLabel("Aforo actual: 0 / " + CAPACIDAD_TIENDA, SwingConstants.CENTER);
        aforoBar = new JProgressBar(0, CAPACIDAD_TIENDA);
        aforoBar.setStringPainted(true);
        panelTop.add(lblAforoActual);
        panelTop.add(aforoBar);
        add(panelTop, BorderLayout.NORTH);

        // Panel central (ascensores)
        JPanel panelAscensores = new JPanel(new GridLayout(1, 2, 10, 10));
        lblAsc1 = new JLabel("Ascensor 1: libre", SwingConstants.CENTER);
        lblAsc1.setOpaque(true);
        lblAsc1.setBackground(Color.GREEN);
        lblAsc2 = new JLabel("Ascensor 2: libre", SwingConstants.CENTER);
        lblAsc2.setOpaque(true);
        lblAsc2.setBackground(Color.GREEN);
        panelAscensores.add(lblAsc1);
        panelAscensores.add(lblAsc2);
        add(panelAscensores, BorderLayout.CENTER);

        // Panel inferior (eventos)
        areaEventos = new JTextArea();
        areaEventos.setEditable(false);
        JScrollPane scroll = new JScrollPane(areaEventos);
        scroll.setBorder(BorderFactory.createTitledBorder("Registro de eventos"));
        add(scroll, BorderLayout.SOUTH);
    }

    private void registrarEvento(String texto) {
        SwingUtilities.invokeLater(() -> {
            areaEventos.append(texto + "\n");
            areaEventos.setCaretPosition(areaEventos.getDocument().getLength());
        });
    }

    private void actualizarAforo() {
        SwingUtilities.invokeLater(() -> {
            int ocupacionActual = CAPACIDAD_TIENDA - miMercadona.availablePermits();
            aforoBar.setValue(ocupacionActual);
            lblAforoActual.setText("Aforo actual: " + ocupacionActual + " / " + CAPACIDAD_TIENDA);
        });
    }

    private void actualizarAscensor(JLabel label, boolean ocupado) {
        SwingUtilities.invokeLater(() -> {
            label.setBackground(ocupado ? Color.RED : Color.GREEN);
            label.setText(ocupado ? label.getText().split(":")[0] + ": ocupado" : label.getText().split(":")[0] + ": libre");
        });
    }

    public void iniciarSimulacion() {
        int numClientes = random.nextInt(2000) + 200;
        int aforoAleatorio = random.nextInt(CAPACIDAD_TIENDA) + 1;
        registrarEvento("Han llegado " + numClientes + " clientes.");
        registrarEvento("Aforo máximo permitido hoy: " + aforoAleatorio + " personas.");

        for (int i = 1; i <= numClientes; i++) {
            new Cliente(i, aforoAleatorio, this).start();
        }
    }

    // ================== CLASE CLIENTE ==================
    static class Cliente extends Thread {
        private final int id;
        private final int aforoObjetivo;
        private final MercadonaAscensoresGUI gui;

        public Cliente(int id, int aforoAleatorio, MercadonaAscensoresGUI gui) {
            this.id = id;
            this.aforoObjetivo = aforoAleatorio;
            this.gui = gui;
        }

        @Override
        public void run() {
            try {
                gui.registrarEvento("Cliente " + id + " llega y espera un ascensor...");

                Semaphore ascensor;
                JLabel lblAsc;

                if (random.nextBoolean()) {
                    ascensor = ascensor1;
                    lblAsc = gui.lblAsc1;
                } else {
                    ascensor = ascensor2;
                    lblAsc = gui.lblAsc2;
                }

                ascensor.acquire();
                gui.actualizarAscensor(lblAsc, true);
                gui.registrarEvento("Cliente " + id + " entra en el ascensor.");
                Thread.sleep(TIEMPO_VIAJE);

                synchronized (miMercadona) {
                    int ocupacionActual = CAPACIDAD_TIENDA - miMercadona.availablePermits();
                    if (ocupacionActual >= aforoObjetivo) {
                        gui.registrarEvento("Cliente " + id + " no puede entrar (aforo completo).");
                        Thread.sleep(TIEMPO_VIAJE);
                        ascensor.release();
                        gui.actualizarAscensor(lblAsc, false);
                        return;
                    } else {
                        miMercadona.acquire();
                        gui.actualizarAforo();
                    }
                }

                ascensor.release();
                gui.actualizarAscensor(lblAsc, false);
                gui.registrarEvento("Cliente " + id + " entra al Mercadona.");

                int minCompra = 10 * 60 * 1000;
                int maxCompra = 50 * 60 * 1000;
                int tiempoCompra = random.nextInt(maxCompra - minCompra + 1) + minCompra;
                double minutos = tiempoCompra / 60000.0;
                gui.registrarEvento("Cliente " + id + " comprará por " + String.format("%.2f", minutos) + " min.");

                Thread.sleep(tiempoCompra);

                gui.registrarEvento("Cliente " + id + " ha terminado de comprar.");

                if (random.nextBoolean()) {
                    ascensor = ascensor1;
                    lblAsc = gui.lblAsc1;
                } else {
                    ascensor = ascensor2;
                    lblAsc = gui.lblAsc2;
                }

                ascensor.acquire();
                gui.actualizarAscensor(lblAsc, true);
                gui.registrarEvento("Cliente " + id + " baja en el ascensor.");
                Thread.sleep(TIEMPO_VIAJE);
                ascensor.release();
                gui.actualizarAscensor(lblAsc, false);

                miMercadona.release();
                gui.actualizarAforo();
                gui.registrarEvento("Cliente " + id + " ha salido del Mercadona.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    // ================== MAIN ==================
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            MercadonaAscensoresGUI gui = new MercadonaAscensoresGUI();
            gui.setVisible(true);
            new Thread(gui::iniciarSimulacion).start();
        });
    }
}
