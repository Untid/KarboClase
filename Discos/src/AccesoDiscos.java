import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;

/**
 * Primero por consola, decir un autor y que te ponga el nombre de los discos y la duración.
 * Después conseguir que modifique, añadir, borrar.
 * Darle interfaz gráfica
 */

public class AccesoDiscos {
    public static void main(String[] args) {
        try {
            // Leer el archivo XML
            File inputFile = new File("C:\\Users\\Javier\\IdeaProjects\\Discos\\src\\discos.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();  // Crear una fábrica de parsers DOM
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();  // Construye un parser DOM
            Document doc = dBuilder.parse(inputFile);  // Convierte el archivo XML en un objeto Document
            doc.getDocumentElement().normalize(); // Limpia los nodos vacíos

            // Llama al método muestraDocumento con el parámetro el documento a mostrar
            muestraDocumento(doc);

//            // Modifico un elemento
//            modificarNodo(doc, "Libro", 1, "Nuevo Título");
//            muestraDocumento(doc);
//            gardaDocumento(doc, "C:\\Users\\Javier\\IdeaProjects\\AccesoDatos\\src\\XML\\libros2.xml");
//
//            // Añado un nuevo elemento
//            anadirNodo(doc, "1846", "Los tres mosqueteros", "Alejandro Dumas");
//            muestraDocumento(doc);
//            gardaDocumento(doc, "C:\\Users\\Javier\\IdeaProjects\\AccesoDatos\\src\\XML\\libros3.xml");
        } catch (ParserConfigurationException | IOException | org.xml.sax.SAXException e) {
            e.printStackTrace();
        }
    }

    /**
     * Mostramos un documento
     *
     * @param doc Documento que queremos mostrar
     */
    public static void muestraDocumento(Document doc) {
        // Obtener y mostrar la lista de libros
        // Busco todos los elementos llamados <disco> y los guardo en NodeList (lista de nodos, parecido a un array)
        NodeList disco = doc.getElementsByTagName("disco");

        for (int i = 0; i < disco.getLength(); i++) {// Recorro todos los discos
            Node discoNode = disco.item(i); // Obtengo el <disco> en la posición i. Básicamente recorro todo el NodeList

            if (discoNode.getNodeType() == Node.ELEMENT_NODE) { // Me aseguro de que el nodo es un elemento XML (no un texto, comentario, etc)

                Element discoElement = (Element) discoNode;  // Lo convierto a Element, para poder acceder a etiquetas hijas fácilmente

                String id = discoElement.getAttribute("id"); // Leo el atributo id del <disco> y lo guardo en una variable
                // Leo el contenido del primer <titulo>(resto de etiquetas), y con el getTextContent() obtengo el texto que hay entre las etiquetas
                String titulo = discoElement.getElementsByTagName("titulo").item(0).getTextContent();
                String artista = discoElement.getElementsByTagName("artista").item(0).getTextContent();
                String anio = discoElement.getElementsByTagName("anio").item(0).getTextContent();

                // Imprimo los datos por consola
                System.out.println("Disco ID: " + id);
                System.out.println("Título: " + titulo);
                System.out.println("Artista: " + artista);
                System.out.println("Año: " + anio);
                System.out.println("Canciones:");

                // Busco todas las etiquetas <cancion> que estan dentro de <disco>
                NodeList canciones = discoElement.getElementsByTagName("cancion");


                // Ahora otro bucle, pero para recorrer cada <cancion> dentro de <disco>
                for (int j = 0; j < canciones.getLength(); j++) {
                    Node cancionNode = canciones.item(j);

                    // Me vuelvo a asegurar de que es una etiqueta <cancion> y no un texto vacio
                    if (cancionNode.getNodeType() == Node.ELEMENT_NODE) {
                        Element cancionElement = (Element) cancionNode; // Y lo convierto a Element

                        // Obtengo el contenido de <nombre> y duración y la guardo en una variable
                        String nombre = cancionElement.getElementsByTagName("nombre").item(0).getTextContent();
                        String duracion = cancionElement.getElementsByTagName("duracion").item(0).getTextContent();

                        // Imprimo las variables en las que guardé los datos por consola
                        System.out.println("\t- " + nombre + " (" + duracion + ")");
                    }
                }
                System.out.println("----------------------------------------");
            }
        }
    }
    /**
     * Modificamos un nodo
     *
     * @param doc         Documento a modificar
     * @param elemento    Tipo de elemento que queremos modificar
     * @param indice      Índice del elemento que queremos modificar
     * @param nuevoTitulo Nuevo título del libro
     */
//    public static void modificarNodo(Document doc, String elemento, int indice, String nuevoTitulo) {
//        // Modificar el archivo XML
//        NodeList nList = doc.getElementsByTagName(elemento);
//        Node nodoIesimo = nList.item(indice);
//        if (nodoIesimo.getNodeType() == Node.ELEMENT_NODE) {
//            Element elementoIesimo = (Element) nodoIesimo;
//            elementoIesimo.getElementsByTagName("Titulo").item(0)
//                    .setTextContent("Nuevo título");
//        }
//        System.out.println("\n\nModificar: Fichero XML actualizado correctamente");
//    }
//
//    /**
//     * Añadimos un nodo al documento
//     *
//     * @param doc         Documento
//     * @param publicacion Año de publicación
//     * @param titulo      Título del libro
//     * @param autor       Autor del libro
//     */
//    public static void anadirNodo(Document doc, String publicacion, String titulo, String autor) {
//
//        //Se crea un nodo tipo Element con nombre 'titulo ' (<titulo>)
//        Node ntitulo = doc.createElement("Titulo");
//        //Se crea un nodo tipo texto con el título del libro
//        Node ntituloText = doc.createTextNode(titulo);
//        //Se añade el nodo de texto con el título como hijo del elemento Titulo
//        ntitulo.appendChild(ntituloText);
//        //Se hace lo mismo que con título a autor (<autor>)
//        Node nautor = doc.createElement("Autor");
//        Node nautorText = doc.createTextNode(autor);
//        nautor.appendChild(nautorText);
//
//        //Se crea un nodo de tipo elemento (<libro>)
//        Node nlibro = doc.createElement("Libro");
//        //Al nuevo nodo libro se le añade un atributo publicado en
//        ((Element) nlibro).setAttribute("publicado_en", publicacion);
//
//        //Se añade a libro el nodo autor y titulo creados antes
//        nlibro.appendChild(ntitulo);
//        nlibro.appendChild(nautor);
//
//        //Finalmente , se obtiene el primer nodo del documento y a él se le
//        //añade como hijo el nodo libro que ya tiene colgando todos sus
//        //hijos y atributos creados antes.
//        Node raiz = doc.getChildNodes().item(0);
//        raiz.appendChild(nlibro);
//
//        System.out.println("\n\nAñadir: Fichero XML actualizado correctamente");
//    }
//
//    /**
//     * Guardamos un fichero XML
//     *
//     * @param doc           Documento XML
//     * @param nombreFichero Nombre del fichero que queremos darle
//     */
//    private static void gardaDocumento(Document doc, String nombreFichero) {
//        try {
//            TransformerFactory transformerFactory = TransformerFactory.newInstance();
//            Transformer transformer = transformerFactory.newTransformer();
//            DOMSource source = new DOMSource(doc);
//            StreamResult result = new StreamResult(new File(nombreFichero));
//            transformer.transform(source, result);
//        } catch (TransformerException e) {
//            e.printStackTrace();
//        }
//    }
}

