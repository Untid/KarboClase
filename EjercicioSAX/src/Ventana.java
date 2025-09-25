import javax.swing.*;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Ventana principal de la aplicación.
 * Permite visualizar imágenes categorizadas extraídas de un archivo RSS usando SAX.
 * Contiene un JComboBox para seleccionar la categoría, un JList para mostrar títulos
 * de imágenes y un JLabel para mostrar la imagen seleccionada.
 */
public class Ventana extends JFrame {

    // Componentes
    private JComboBox comboBox;
    private JLabel lblImagen;
    private JList<Imagen> lista;
    private JScrollPane scroll;
    private JButton btnCargar;

    // Map que asocia cada categoria con su lista de imágenes.
    private Map<String, List<Imagen>> datos = new HashMap<>();

    /**
     * Constructor de la Ventana.
     * Inicializa los componentes, carga los archivos desde el RSS y configura eventos.
     */
    public Ventana() {
        // Configuración básica de la Ventana.
        setTitle("Ejercicio SAX");
        setSize(600, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel miPanel = new JPanel();
        miPanel.setLayout(new BorderLayout(10, 10));
        add(miPanel);


        // Crear y colocar los componentes gráficos.
        colocarComponentes(miPanel);

        // Cargar archivos iniciales desde el archivo RSS.
        cargar();

        // Mostrar la primera categoría si hay disponibles.
        if (comboBox.getItemCount() > 0) {
            comboBox.setSelectedIndex(0);
            mostrarLista();
        }
        // Cuando cambie la seleccionada, refrescamos la lista.
        comboBox.addActionListener(e -> mostrarLista());
    }
    /**
     * Método que inserta los componentes en el panel pasado como parámetro.
     *
     * @param mipanel panel donde se añadirán los componentes.
     *      Inserta componentes como JComboBox, JLabel, JList<Imagen>, JScrollPane, JButton.
     *      Gestiona eventos como el de cargar una imagen aleatoria con el botón,
     *      o como el de mostrar la imagen al clickar una imagen.
     */
    private void colocarComponentes(JPanel mipanel) {
        // -------------------------Selector comboBOX-----------------------------------------
        comboBox = new JComboBox<>();
        comboBox.addActionListener(e -> mostrarLista());
        add(comboBox, BorderLayout.NORTH);

        //-------------------------Área para mostrar la imagen---------------------------------
        lblImagen = new JLabel();
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.gray));
        add(lblImagen, BorderLayout.CENTER);

        //-------------------------Lista de títulos de la imagen-----------------------------
        lista = new JList<>();
        scroll = new JScrollPane(lista);
        scroll.setViewportView(lista);

        // Cuando se selecciona un título, se carga y muestra la imagen en lblImagen.
        lista.addListSelectionListener(e -> {
            Imagen img = lista.getSelectedValue();
            if (img != null) {
                try {
                    ImageIcon icon = new ImageIcon(new URL(img.getUrl()));
                    Image scaled = icon.getImage().getScaledInstance(400, 300, Image.SCALE_SMOOTH);
                    // Escala la imagen suavemente al tamaño del Jlabel con SCALE_SMOOTH.
                    lblImagen.setIcon(new ImageIcon(scaled));
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });
        add(scroll, BorderLayout.WEST);

        //-----------------------------Botón que carga una foto al azar------------------------------
        btnCargar = new JButton("Cargar foto al azar");
        add(btnCargar, BorderLayout.SOUTH);

        // Cargar una imagen aleatoria de cualquier categoría.
        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                cargarImagenAleatoria();
            }
        });
    }

    /**
     * Método cargar que carga los datos desde el archivo rss
     * Utiliza un parser SAX para procesar el XML.
     * Extrae las categorías e imágenes a través de la clase Handler de este programa.
     * Guarda la información en el map llamado 'datos'.
     * Actualiza el ComboBox con las categorías disponibles.
     * En caso de error, muestra un diálogo de error.
     */
    private void cargar() {
        try {
            // Archivo RSS con los datos de las imágenes.
            File inputFile = new File("image-of-the-day.rss");

            // Configuración del parser SAX.
            SAXParserFactory factory = SAXParserFactory.newInstance();
            factory.setNamespaceAware(true);
            SAXParser parser = factory.newSAXParser();

            // Usamos la clase Handler de este programa para procesar el xml de forma personalizada.
            Handler handler = new Handler();
            parser.parse(inputFile, handler);

            // Guardamos en el Map los datos parseados (categoría -> imágenes).
            datos = handler.getDatos();

            // Actualizamos las opciones del comboBox.
            comboBox.removeAllItems();
            for (String categoria : datos.keySet()) {
                comboBox.addItem(categoria);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    /**
     * Muestra en la lista las imágenes de la categoría seleccionada en el ComboBox.
     * Recupera la categoría actualmente seleccionada.
     * Si existe en el Map 'datos' , actualiza la lista con las imágenes seleccionadas.
     * Cada elemento de la lista es un objeto 'Imagen' (título, url).
     * Si no hay categoría seleccionada o si no existen datos para ella no muestra nada.
     */
    private void mostrarLista() {
        // Recuperamos la categoría seleccionada en el comboBox.
        String categoria = (String) comboBox.getSelectedItem();

        // Si hay una categoría válida y existen datos, mostramos su lista de imágenes.
        if (categoria != null && datos.containsKey(categoria)) {
            lista.setListData(datos.get(categoria).toArray(new Imagen[0]));
        }
    }

    /**
     *  Carga una imagen aleatoria almancenando los objetos cargados en un array de objetos,
     *  posteriormente utiliza el método random de la clase Math para generar un número aleatorio y al
     *  multiplicalo con el tamaño del array de objetos dar un número.
     *  Este número va a ser la posición dentro del array de objetos el cual se cogerá la imagen y se
     *  imprimirá en el lblImagen.
     */
    private void cargarImagenAleatoria(){
        if (datos.isEmpty()) {
            cargar();
        }
        if (!datos.isEmpty()) {
            Object[] categorias = datos.keySet().toArray();

            String categoria = (String) categorias[(int) (Math.random() * categorias.length)];

            List<Imagen> imagenes = datos.get(categoria);
            Imagen img = imagenes.get((int) (Math.random() * imagenes.size()));

            try {
                ImageIcon icon = new ImageIcon(new URL(img.getUrl()));
                Image scaled = icon.getImage().getScaledInstance(lblImagen.getWidth(), lblImagen.getHeight(), Image.SCALE_SMOOTH);
                // Escala la imagen suavemente al tamaño del Jlabel con SCALE_SMOOTH.
                lblImagen.setIcon(new ImageIcon(scaled));

            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

            comboBox.setSelectedItem(categoria);
            lista.setListData(imagenes.toArray(new Imagen[0]));
            lista.setSelectedValue(img, true);
        }
    }
}