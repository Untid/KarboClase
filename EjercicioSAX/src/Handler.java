import org.xml.sax.Attributes;
import org.xml.sax.helpers.DefaultHandler;
import java.util.*;

/**
 * Handler personalizado para procesar un archivo RSS con SAX
 * Recolecta los datos de cada <item> del feed.
 * Construye objetos Imagen (titulo, enlace, url, categoria)
 * Agrupa las imágenes en un Map (categoría -> lista de imágenes)
 */

public class Handler extends DefaultHandler {

    // Estructura donde guardamos las imágenes clasificadas por categoría
    private Map<String, List<Imagen>> datos = new HashMap<>();

    // Buffer para ir almacenando el contenido de las etiquetas de texto
    private StringBuilder buffer = new StringBuilder();

    // Variables temporales para construir cada imagen
    private String titulo, enlace, url, categoria;

    // Flag para saber si estamos dentro de un <item>
    private boolean dentroItem = false;

    // Permite acceder a los datos procesados desde fuera
    public Map<String, List<Imagen>> getDatos() {
        return datos;
    }

    // Se ejecuta al inicio de cada etiqueta (<tag>)
    @Override
    public void startElement(String uri, String localName, String qName, Attributes attributes) {
        buffer.setLength(0); // Reiniciamos el buffer para el nuevo contenido de texto

        // Detectamos el inicio de un <item>, que representa una entrada en el feed
        if (qName.equalsIgnoreCase("item")) {
            dentroItem = true;
            // Inicializamos las variables para evitar arrastrar datos del item anterior
            titulo = enlace = url = categoria = null;
        }
        // En RSS tipo media, la imagen suele venir como atributo "url" en <media:thumbnail>
        if (qName.equalsIgnoreCase("media:thumbnail")) {
            url = attributes.getValue("url");
        }
    }

    // Se ejecuta cuando se leen los caracteres dentro de una etiqueta
    @Override
    public void characters(char[] ch, int start, int length) {
        // Vamos acumulando el texto en el buffer
        buffer.append(ch, start, length);
    }

    // Se ejecuta al cerrar una etiqueta (</tag>)
    @Override
    public void endElement(String uri, String localName, String qName) {
        // Si no estamos dentro de un <item>, ignoramos
        if (!dentroItem) return;

        switch (qName.toLowerCase()) {
            case "title":
                titulo = buffer.toString().trim();
                break;

            case "link":
                enlace = buffer.toString().trim();
                break;

            case "category":
                categoria = buffer.toString().trim();
                break;

            case "item":
                // Si un item no tiene categoría, le asignamos "Sin categoría"
                if (categoria == null) categoria = "Sin categoría";
                // Creamos un objeto Imagen con los datos recolectados
                Imagen img = new Imagen(titulo, url, enlace, categoria);
                // Insertamos la imagen en el mapa según su categoría
                datos.computeIfAbsent(categoria, k -> new ArrayList<>()).add(img);

                //Marcamos que hemos terminado el item
                dentroItem = false;
                break;
        }
    }
}
