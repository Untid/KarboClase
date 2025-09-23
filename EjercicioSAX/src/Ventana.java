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
 * Ventana
 */

public class Ventana extends JFrame {

    // Componentes
    private JComboBox comboBox;
    private JLabel lblImagen;
    private JList<Imagen> lista;
    private JScrollPane scroll;
    private JButton btnCargar;


    // Lista de imágenes
    private Map<String, List<Imagen>> datos = new HashMap<>();

    public Ventana() {

        setTitle("Ejercicio SAX");
        setSize(600,300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(null);
        setLocationRelativeTo(null);

        // Panel principal
        JPanel miPanel = new JPanel();
        miPanel.setLayout(null);
        add(miPanel);


        // Crear y colocar los componentes gráficos
        colocarComponentes(miPanel);

        // Cargar archivos iniciales desde el archivo RSS
        cargar();

        // Mostrar la primera categoría si hay disponibles
        if (comboBox.getItemCount() > 0) {
            comboBox.setSelectedIndex(0);
            mostrarLista();
        }

        // Cuando cambie la seleccionada, refrescamos la lista
        comboBox.addActionListener(e -> mostrarLista());
    }

    private void colocarComponentes(JPanel mipanel){

        // -------------------------Selector comboBOX-----------------------------------------
        comboBox = new JComboBox<>();
        comboBox.setBounds(20,20,200,25);
        comboBox.addActionListener(e -> mostrarLista());
        add(comboBox);

        //-------------------------Área para mostrar la imagen---------------------------------
        lblImagen = new JLabel();
        lblImagen.setBounds(250,20,300,250);
        lblImagen.setBorder(BorderFactory.createLineBorder(Color.gray));
        add(lblImagen);

        //-------------------------Lista de títulos de la imagen-----------------------------
        lista = new JList<>();
        scroll = new JScrollPane(lista);

        scroll.setViewportView(lista);
        scroll.setBounds(20, 60, 200, 200);

        // Cuando se selecciona un título, se carga y muestra la imagen en lblImagen.
        lista.addListSelectionListener(e ->{
            Imagen img = lista.getSelectedValue();
            if (img != null){
                try{
                    ImageIcon icon = new ImageIcon(new URL(img.getUrl()));
                    Image scaled = icon.getImage().getScaledInstance(400,300,Image.SCALE_SMOOTH);
                    lblImagen.setIcon(new ImageIcon(scaled));
                }catch (Exception ex){
                    ex.printStackTrace();
                }
            }
        });
        add(scroll);

        //-----------------------------Botón para recargar datos------------------------------
        btnCargar = new JButton("Cargar foto al azar");
        btnCargar.setBounds(250, 200, 150, 30);
        add(btnCargar);

        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
            cargar();    // Llama al método cargar para leer el archivo RSS
            mostrarLista(); // Refresca la lista con los nuevos datos
            }
        });
    }

    /**
     * Método cargar que carga los datos desde el archivo rss
     *  Utiliza un parser SAX para procesar el XML.
     *  Extrae las categorías e imágenes a través de la clase Handler de este programa.
     *  Guarda la información en el map llamado 'datos'.
     *  Actualiza el ComboBox con las categorías disponibles.
     * Errores:
     * -Error al leer o parsear el archivo.
     */
    private void cargar() {
        try{
            // Archivo RSS con los datos de las imágenes
            File inputFile = new File("image-of-the-day.rss");

            // Configuración del parser SAX
           SAXParserFactory factory = SAXParserFactory.newInstance();
           factory.setNamespaceAware(true);
           SAXParser parser = factory.newSAXParser();


           // Usamos la clase Handler de este programa para procesar el xml de forma personalizada
           Handler handler = new Handler();
            parser.parse(inputFile,handler);

            // Guardamos en el Map los datos parseados (categoría -> imágenes)
           datos = handler.getDatos();


           // Actualizamos las opciones del comboBox
           comboBox.removeAllItems();
            for (String categoria : datos.keySet()){
                comboBox.addItem(categoria);
            }

        }catch (Exception ex){
            ex.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error");
        }
    }

    /**
     *  Muestra en la lista las imágenes de la categoría seleccionada en el ComboBox
     *      Recupera la categoría actualmente seleccionada.
     *      Si existe en el Map 'datos' , actualiza la lista con las imágenes seleccionadas
     *      Cada elemento de la lista es un objeto 'Imagen' (titulo, url).
     *  Errores:
     *      Si no hay categoría seleccionada o si no existen datos para ella no muestra nada.
     */

    private void mostrarLista(){

        // Recuperamos la categoría seleccionada en el comboBox
        String categoria = (String) comboBox.getSelectedItem();


        // Si hay una categoría válida y existen datos, mostramos su lista de imágenes
        if (categoria !=null && datos.containsKey(categoria)) {
            lista.setListData(datos.get(categoria).toArray(new Imagen[0]));
        }
    }
}