package org.example;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;
import java.io.File;

/**
 *
 * @author Moncho
 */
public class AccesoJAXB {

    public static void main(String[] args) {
        try {
            // Crear el contexto JAXB para las clases Libros y Libro
            // Este contexto proporciona el entorno necesario para convertir entre XML y objetos Java
            JAXBContext context = JAXBContext.newInstance(Libros.class);

            // Unmarshalling: Convertir XML a objetos Java
            // El Unmarshaller convierte el contenido de un archivo XML en instancias de las clases Java correspondientes
            Unmarshaller unmarshaller = context.createUnmarshaller();
            // Leemos el archivo XML y lo convertimos en un objeto de la clase Libros
            Libros libros = (Libros) unmarshaller.unmarshal(new File("libros.xml"));
            System.out.println("Unmarshalled XML a objetos Java:");
            System.out.println(libros);

            // Modificar el atributo 'publicadoEn' del segundo libro
            // Verificamos que la lista de libros no esté vacía
            if (!libros.getLibros().isEmpty()) {
                // Obtenemos el segundo libro de la lista (índice 1) y modificamos su año de publicación
                Libro segundoLibro = libros.getLibros().get(1);
                segundoLibro.setPublicadoEn(1960);
                System.out.println("Modificado el año de publicación en el segundo libro:");
                System.out.println(segundoLibro);
            }

            // Modificar el título del segundo libro
            // Establecemos un nuevo título para el segundo libro de la lista
            libros.getLibros().get(1).setTitulo("Nuevo título");

            // Crear un nuevo libro
            Libro nuevoLibro = new Libro();
            nuevoLibro.setPublicadoEn(1968);
            nuevoLibro.setTitulo("2001: Odisea en el espacio");
            nuevoLibro.setAutor("Arthur C. Clarke");

            // Añadir el nuevo libro a la lista existente
            libros.getLibros().add(nuevoLibro);

            System.out.println("\nAñadido un nuevo libro "+libros.getLibros().get(libros.getLibros().size()-1)+"\n");

            // Marshalling: Convertir objetos Java a XML
            // El Marshaller convierte los objetos Java de nuevo en XML
            Marshaller marshaller = context.createMarshaller();
            // Configuramos el marshaller para que formatee el XML con saltos de línea y sangría
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            // Convertimos los objetos Java a XML y los escribimos en un nuevo archivo
            marshaller.marshal(libros, new File("librosActualizado.xml"));

            System.out.println("Marshalled objetos Java a XML:");
            // También mostramos el XML resultante en la consola
            marshaller.marshal(libros, System.out);

        } catch (JAXBException e) {
            // Capturamos cualquier excepción que ocurra durante el proceso de unmarshalling o marshalling
            e.printStackTrace();
        }
    }
}