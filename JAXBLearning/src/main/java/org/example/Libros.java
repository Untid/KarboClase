package org.example;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import java.util.List;



/**
 *
 * @author Moncho
 */
// Anotación que indica que esta clase es el elemento raíz del XML
// El nombre del elemento raíz será "Libros"
@XmlRootElement(name = "Libros")
public class Libros {
    // Lista que contiene objetos de la clase Libro
    private List<Libro> libros;

    // Método getter para la lista de libros
    public List<Libro> getLibros() {
        return libros;
    }

    // Método setter para la lista de libros
    // La anotación @XmlElement indica que cada elemento de la lista será un subelemento XML del tipo "Libro"
    @XmlElement(name = "Libro")
    public void setLibros(List<Libro> libros) {
        this.libros = libros;
    }

    // Método toString sobrescrito para una representación en cadena del objeto Libros
    @Override
    public String toString() {
        return "Libros{" + "libros=" + libros + '}';
    }
}
