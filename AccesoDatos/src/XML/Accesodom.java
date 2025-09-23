package XML;

import java.io.File;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * @author Moncho
 */
public class Accesodom {

    public static void main(String[] args) {
        try {
            // Leer el archivo XML
            File inputFile = new File("C:\\Users\\Javier\\IdeaProjects\\AccesoDatos\\src\\XML\\libros.xml");
            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(inputFile);
            doc.getDocumentElement().normalize();

            // Muestro el documento
            muestraDocumento(doc);

            // Modifico un elemento
            modificarNodo(doc, "Libro", 1, "Nuevo Título");
            muestraDocumento(doc);
            gardaDocumento(doc, "C:\\Users\\Javier\\IdeaProjects\\AccesoDatos\\src\\XML\\libros2.xml");

            // Añado un nuevo elemento
            anadirNodo(doc, "1846", "Los tres mosqueteros", "Alejandro Dumas");
            muestraDocumento(doc);
            gardaDocumento(doc, "C:\\Users\\Javier\\IdeaProjects\\AccesoDatos\\src\\XML\\libros3.xml");
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
        NodeList nList = doc.getElementsByTagName("Libro");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                System.out.println(eElement.getElementsByTagName("Titulo")
                        .item(0).getTextContent());
                System.out.println("\tAutor : " + eElement.
                        getElementsByTagName("Autor").item(0)
                        .getTextContent());
                System.out.println("\tFecha de publicación : " + eElement.
                        getAttribute("publicado_en"));
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
    public static void modificarNodo(Document doc, String elemento, int indice, String nuevoTitulo) {
        // Modificar el archivo XML
        NodeList nList = doc.getElementsByTagName(elemento);
        Node nodoIesimo = nList.item(indice);
        if (nodoIesimo.getNodeType() == Node.ELEMENT_NODE) {
            Element elementoIesimo = (Element) nodoIesimo;
            elementoIesimo.getElementsByTagName("Titulo").item(0)
                    .setTextContent("Nuevo título");
        }
        System.out.println("\n\nModificar: Fichero XML actualizado correctamente");
    }

    /**
     * Añadimos un nodo al documento
     *
     * @param doc         Documento
     * @param publicacion Año de publicación
     * @param titulo      Título del libro
     * @param autor       Autor del libro
     */
    public static void anadirNodo(Document doc, String publicacion, String titulo, String autor) {

        //Se crea un nodo tipo Element con nombre 'titulo ' (<titulo>)
        Node ntitulo = doc.createElement("Titulo");
        //Se crea un nodo tipo texto con el título del libro
        Node ntituloText = doc.createTextNode(titulo);
        //Se añade el nodo de texto con el título como hijo del elemento Titulo
        ntitulo.appendChild(ntituloText);
        //Se hace lo mismo que con título a autor (<autor>)
        Node nautor = doc.createElement("Autor");
        Node nautorText = doc.createTextNode(autor);
        nautor.appendChild(nautorText);

        //Se crea un nodo de tipo elemento (<libro>)
        Node nlibro = doc.createElement("Libro");
        //Al nuevo nodo libro se le añade un atributo publicado en
        ((Element) nlibro).setAttribute("publicado_en", publicacion);

        //Se añade a libro el nodo autor y titulo creados antes
        nlibro.appendChild(ntitulo);
        nlibro.appendChild(nautor);

        //Finalmente , se obtiene el primer nodo del documento y a él se le
        //añade como hijo el nodo libro que ya tiene colgando todos sus
        //hijos y atributos creados antes.
        Node raiz = doc.getChildNodes().item(0);
        raiz.appendChild(nlibro);

        System.out.println("\n\nAñadir: Fichero XML actualizado correctamente");
    }

    /**
     * Guardamos un fichero XML
     *
     * @param doc           Documento XML
     * @param nombreFichero Nombre del fichero que queremos darle
     */
    private static void gardaDocumento(Document doc, String nombreFichero) {
        try {
            TransformerFactory transformerFactory = TransformerFactory.newInstance();
            Transformer transformer = transformerFactory.newTransformer();
            DOMSource source = new DOMSource(doc);
            StreamResult result = new StreamResult(new File(nombreFichero));
            transformer.transform(source, result);
        } catch (TransformerException e) {
            e.printStackTrace();
        }
    }
}
