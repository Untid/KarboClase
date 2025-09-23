/**
 * Clase Imagen para convertir las imágenes en objetos
 * y extraer la información deseada.
 */
public class Imagen {
    private String titulo;
    private String url;
    private String enlace;
    private String categoria;

    public Imagen(String titulo, String url, String enlace, String categoria) {
        this.titulo = titulo;
        this.url = url;
        this.enlace = enlace;
        this.categoria = categoria;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getUrl() {
        return url;
    }

    public String getEnlace() {
        return enlace;
    }

    public String getCategoria() {
        return categoria;
    }

    @Override
    public String toString(){
        return titulo; // Esto es lo que se mostrará en el JList
    }
}