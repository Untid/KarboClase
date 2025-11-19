package org.example.view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import org.json.JSONArray;
import org.json.JSONObject;
import org.example.ApiClient;

public class CamareroView extends JPanel {
    private JTextField txtNombreCliente;
    private JTextField txtIdLocal;
    private JTextArea txtProductos;
    private JTextArea txtResultado;
    private JButton btnCargarMenu;
    private JButton btnCrearPedido;
    private JTextArea txtMenu; // Nuevo área específica para el menú

    public CamareroView() {
        setLayout(new BorderLayout(10, 10));

        // Panel superior - Datos del cliente
        JPanel panelCliente = new JPanel(new GridLayout(2, 2, 5, 5));
        panelCliente.setBorder(BorderFactory.createTitledBorder("Datos del Cliente"));
        panelCliente.setPreferredSize(new Dimension(600, 80));

        panelCliente.add(new JLabel("Nombre:"));
        txtNombreCliente = new JTextField();
        panelCliente.add(txtNombreCliente);

        panelCliente.add(new JLabel("ID Local (opcional):"));
        txtIdLocal = new JTextField();
        panelCliente.add(txtIdLocal);

        // Panel central dividido en dos - Menú y Productos
        JPanel panelCentral = new JPanel(new GridLayout(1, 2, 10, 10));

        // Panel izquierdo - Menú
        JPanel panelMenu = new JPanel(new BorderLayout());
        panelMenu.setBorder(BorderFactory.createTitledBorder("Menú Disponible"));

        txtMenu = new JTextArea(15, 25);
        txtMenu.setEditable(false);
        txtMenu.setFont(new Font("Consolas", Font.PLAIN, 11));
        txtMenu.setText("Haz clic en 'Cargar Menú' para ver los productos disponibles");
        JScrollPane scrollMenu = new JScrollPane(txtMenu);
        panelMenu.add(scrollMenu, BorderLayout.CENTER);

        JPanel panelBotonesMenu = new JPanel(new FlowLayout());
        btnCargarMenu = new JButton("Cargar Menú");
        panelBotonesMenu.add(btnCargarMenu);
        panelMenu.add(panelBotonesMenu, BorderLayout.SOUTH);

        // Panel derecho - Productos del pedido
        JPanel panelProductos = new JPanel(new BorderLayout());
        panelProductos.setBorder(BorderFactory.createTitledBorder("Productos del Pedido"));

        txtProductos = new JTextArea(15, 25);
        txtProductos.setText("Formato: id,cantidad (uno por línea)\nEjemplo:\n1,2\n2,1\n3,1");
        JScrollPane scrollProductos = new JScrollPane(txtProductos);
        panelProductos.add(scrollProductos, BorderLayout.CENTER);

        panelCentral.add(panelMenu);
        panelCentral.add(panelProductos);

        // Panel inferior - Botones y resultado
        JPanel panelBotones = new JPanel(new FlowLayout());
        btnCrearPedido = new JButton("Crear Pedido");
        panelBotones.add(btnCrearPedido);

        txtResultado = new JTextArea(6, 60);
        txtResultado.setEditable(false);
        txtResultado.setFont(new Font("Consolas", Font.PLAIN, 12));
        JScrollPane scrollResultado = new JScrollPane(txtResultado);

        // Layout principal
        add(panelCliente, BorderLayout.NORTH);
        add(panelCentral, BorderLayout.CENTER);

        JPanel panelSur = new JPanel(new BorderLayout());
        panelSur.add(panelBotones, BorderLayout.NORTH);
        panelSur.add(scrollResultado, BorderLayout.CENTER);
        add(panelSur, BorderLayout.SOUTH);

        // Event listeners
        btnCargarMenu.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarMenu();
            }
        });

        btnCrearPedido.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                crearPedido();
            }
        });
    }

    private void cargarMenu() {
        new Thread(() -> {
            String resultado = ApiClient.obtenerMenu();
            SwingUtilities.invokeLater(() -> {
                try {
                    JSONArray menu = new JSONArray(resultado);
                    StringBuilder sb = new StringBuilder();

                    sb.append("╔══════════════════════════════════════╗\n");
                    sb.append("║           MENÚ DISPONIBLE            ║\n");
                    sb.append("╚══════════════════════════════════════╝\n\n");

                    sb.append("┌────┬──────────────────────┬────────┬──────┐\n");
                    sb.append("│ ID │ PRODUCTO             │ TIPO   │ PREC │\n");
                    sb.append("├────┼──────────────────────┼────────┼──────┤\n");

                    for (int i = 0; i < menu.length(); i++) {
                        JSONObject producto = menu.getJSONObject(i);
                        String nombre = producto.getString("nombre");
                        // Nombres más cortos para caber mejor
                        if (nombre.length() > 18) {
                            nombre = nombre.substring(0, 15) + "...";
                        }

                        sb.append("│ ")
                                .append(padRight(String.valueOf(producto.getInt("id")), 2))
                                .append(" │ ")
                                .append(padRight(nombre, 20))
                                .append(" │ ")
                                .append(padRight(producto.getString("tipo"), 6))
                                .append(" │ ")
                                .append(padRight(producto.getString("precio") + "€", 4))
                                .append(" │\n");
                    }

                    sb.append("└────┴──────────────────────┴────────┴──────┘\n");
                    sb.append("\n💡 Usa: id,cantidad\n");
                    sb.append("   Ej: 1,2 = 2 Café Latte\n");

                    txtMenu.setText(sb.toString());

                } catch (Exception e) {
                    txtMenu.setText("❌ Error cargando menú:\n" + resultado);
                }
            });
        }).start();
    }

    private void crearPedido() {
        String nombre = txtNombreCliente.getText().trim();
        String idLocal = txtIdLocal.getText().trim();
        String productosText = txtProductos.getText().trim();

        if (nombre.isEmpty() || productosText.isEmpty()) {
            JOptionPane.showMessageDialog(this,
                    "⚠️ Datos incompletos\n\n" +
                            "• Nombre del cliente: Obligatorio\n" +
                            "• Productos: Al menos un producto requerido",
                    "Datos incompletos", JOptionPane.WARNING_MESSAGE);
            return;
        }

        new Thread(() -> {
            try {
                // Parsear productos
                JSONArray productosArray = new JSONArray();
                String[] lineas = productosText.split("\n");

                boolean tieneErrores = false;
                StringBuilder errores = new StringBuilder();

                for (String linea : lineas) {
                    linea = linea.trim();
                    if (linea.isEmpty() || linea.startsWith("Formato") || linea.startsWith("Ejemplo")) {
                        continue;
                    }

                    String[] partes = linea.split(",");
                    if (partes.length == 2) {
                        try {
                            int id = Integer.parseInt(partes[0].trim());
                            int cantidad = Integer.parseInt(partes[1].trim());

                            // ✅ VALIDACIÓN EN JAVA - Cantidad debe ser > 0
                            if (cantidad < 1) {
                                tieneErrores = true;
                                errores.append("❌ Línea '").append(linea).append("': Cantidad debe ser 1 o más\n");
                                continue;
                            }

                            // ✅ VALIDACIÓN EN JAVA - ID debe estar entre 1-6
                            if (id < 1 || id > 6) {
                                tieneErrores = true;
                                errores.append("❌ Línea '").append(linea).append("': ID debe estar entre 1-6\n");
                                continue;
                            }

                            JSONObject producto = new JSONObject();
                            producto.put("id", id);
                            producto.put("cantidad", cantidad);
                            productosArray.put(producto);

                        } catch (NumberFormatException e) {
                            tieneErrores = true;
                            errores.append("❌ Línea '").append(linea).append("': Formato incorrecto. Usa: número,número\n");
                        }
                    } else {
                        tieneErrores = true;
                        errores.append("❌ Línea '").append(linea).append("': Formato incorrecto. Usa: id,cantidad\n");
                    }
                }

                // Si hay errores de validación, mostrarlos y salir
                if (tieneErrores) {
                    SwingUtilities.invokeLater(() -> {
                        txtResultado.setText("⚠️ ERRORES EN LOS PRODUCTOS:\n\n" + errores.toString() +
                                "\n📝 Formato correcto: id,cantidad\n" +
                                "   Ejemplo: 1,2 (2 Café Latte)\n" +
                                "   IDs válidos: 1 al 6");
                    });
                    return;
                }

                // Validar que hay al menos un producto válido
                if (productosArray.length() == 0) {
                    SwingUtilities.invokeLater(() -> {
                        txtResultado.setText("❌ Error: No se encontraron productos válidos\n" +
                                "Formato correcto: id,cantidad (ej: 1,2)\n" +
                                "IDs válidos: 1 al 6");
                    });
                    return;
                }

                // Crear JSON del pedido
                JSONObject pedido = new JSONObject();
                JSONObject cliente = new JSONObject();
                cliente.put("nombre", nombre);
                cliente.put("id_local", idLocal.isEmpty() ? "anonimo" : idLocal);

                pedido.put("cliente", cliente);
                pedido.put("productos", productosArray);

                // Enviar al servidor
                String resultado = ApiClient.crearPedido(pedido.toString());

                SwingUtilities.invokeLater(() -> {
                    try {
                        JSONObject pedidoCreado = new JSONObject(resultado);
                        StringBuilder sb = new StringBuilder();

                        sb.append("╔══════════════════════════════════════╗\n");
                        sb.append("║         PEDIDO CREADO ✅             ║\n");
                        sb.append("╚══════════════════════════════════════╝\n\n");

                        sb.append("┌────────────────────────────────────┐\n");
                        sb.append("│ 👤 CLIENTE: ").append(padRight(pedidoCreado.getJSONObject("cliente").getString("nombre"), 25)).append("│\n");
                        String idCorto = pedidoCreado.getString("_id");
                        if (idCorto.length() > 20) {
                            idCorto = idCorto.substring(0, 17) + "...";
                        }
                        sb.append("│ 🆔 ID: ").append(padRight(idCorto, 31)).append("│\n");
                        sb.append("│ 📊 ESTADO: ").append(padRight(pedidoCreado.getString("estado"), 26)).append("│\n");
                        sb.append("│ ⏱️  TIEMPO: ").append(padRight(pedidoCreado.getInt("tiempo_estimado_min") + " min", 25)).append("│\n");
                        sb.append("├────────────────────────────────────┤\n");

                        JSONArray productos = pedidoCreado.getJSONArray("productos");
                        double total = 0;
                        for (int i = 0; i < productos.length(); i++) {
                            JSONObject producto = productos.getJSONObject(i);
                            int cantidad = producto.getInt("cantidad");
                            double precio = producto.getDouble("precio");
                            double subtotal = cantidad * precio;
                            total += subtotal;

                            String nombreProducto = producto.getString("nombre");
                            if (nombreProducto.length() > 18) {
                                nombreProducto = nombreProducto.substring(0, 15) + "...";
                            }

                            sb.append("│ • ").append(padRight(nombreProducto, 18))
                                    .append(" x").append(cantidad)
                                    .append(" ").append(String.format("%5.2f", subtotal)).append("€ │\n");
                        }

                        sb.append("├────────────────────────────────────┤\n");
                        sb.append("│ 💰 TOTAL: ").append(padRight(String.format("%.2f€", total), 27)).append("│\n");
                        sb.append("└────────────────────────────────────┘\n\n");

                        sb.append("📢 Pedido enviado a cocina ✓\n");
                        sb.append("📱 Cliente puede ver estado en app móvil\n");

                        txtResultado.setText(sb.toString());

                        // ✅ LIMPIAR CAMPOS - AHORA SÍ FUNCIONA
                        txtNombreCliente.setText("");
                        txtIdLocal.setText("");
                        txtProductos.setText("Formato: id,cantidad (uno por línea)\nEjemplo:\n1,2\n2,1\n3,1");

                    } catch (Exception ex) {
                        txtResultado.setText("=== RESPUESTA DEL SERVIDOR ===\n" + resultado);

                        // Limpiar campos si parece exitoso
                        if (resultado.contains("_id") && resultado.contains("productos")) {
                            txtNombreCliente.setText("");
                            txtIdLocal.setText("");
                            txtProductos.setText("Formato: id,cantidad (uno por línea)\nEjemplo:\n1,2\n2,1\n3,1");
                        }
                    }
                });

            } catch (Exception ex) {
                SwingUtilities.invokeLater(() -> {
                    txtResultado.setText("❌ Error inesperado:\n" + ex.getMessage());
                });
            }
        }).start();
    }

    // Método auxiliar para alinear texto
    private String padRight(String s, int n) {
        if (s == null) s = "";
        return String.format("%-" + n + "s", s);
    }
}