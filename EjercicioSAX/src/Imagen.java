/**
 * Representa una imagen obtenida desde un archivo RSS.
 * Contiene información relevante como el título, la URL de la imagen,
 * el enlace original y la categoría a la que pertenece.
 */
public class Imagen {
    private String titulo;
    private String url;
    private String enlace;
    private String categoria;

    /**
     * Constructor de la clase Imagen. Inicializa un objeto Imagen con los datos extraídos del RSS.
     * @param titulo Título de la imagen.
     * @param url URL de la imagen.
     * @param enlace Enlace original asociado a la imagen.
     * @param categoria Categoría a la que pertenece la imagen.
     */
    public Imagen(String titulo, String url, String enlace, String categoria) {
        this.titulo = titulo;
        this.url = url;
        this.enlace = enlace;
        this.categoria = categoria;
    }
    // Getters
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

    /**
     * Es lo que se mostrará en un JList o componentes similares.
     * @return titulo Titulo de la imagen.
     */
    @Override
    public String toString(){
        return titulo;
    }
}