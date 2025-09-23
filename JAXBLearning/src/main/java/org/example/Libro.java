package org.example;
import jakarta.xml.bind.annotation.XmlAttribute;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;


/**
 * Clase de Mapeo del XML
 * @author Moncho
 */

// Anotación que indica que esta clase es el elemento raíz del XML
@XmlRootElement
public class Libro {
    // Atributo 'publicado_en' que se almacenará como un atributo XML
    private int publicado_en;
    // Elementos XML para el título y el autor
    private String titulo;
    private String autor;

    // Método getter para 'publicado_en'
    public int getPublicadoEn() {
        return publicado_en;
    }

    // Método setter para 'publicado_en'
    // La anotación @XmlAttribute indica que este campo será un atributo XML del elemento Libro
    @XmlAttribute(name = "publicado_en")
    public void setPublicadoEn(int publicado_en) {
        this.publicado_en = publicado_en;
    }

    // Método getter para 'titulo'
    public String getTitulo() {
        return titulo;
    }

    // Método setter para 'titulo'
    // La anotación @XmlElement indica que este campo será un subelemento XML del elemento Libro
    @XmlElement
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    // Método getter para 'autor'
    public String getAutor() {
        return autor;
    }

    // Método setter para 'autor'
    // La anotación @XmlElement indica que este campo será un subelemento XML del elemento Libro
    @XmlElement
    public void setAutor(String autor) {
        this.autor = autor;
    }




    // Método toString sobrescrito para una representación en cadena del objeto Libro
    @Override
    public String toString() {
        return "Libro{" + "publicado_en=" + publicado_en + ", titulo=" + titulo + ", autor=" + autor + '}';
    }
}