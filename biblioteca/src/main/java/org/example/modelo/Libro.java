package org.example.modelo;


/**
 * Representa un libro en el sistema de gestión de biblioteca.
 *
 * <p>Esta clase es un POJO (Plain Old Java Object) que encapsula los datos
 * de un libro, como título, editorial, número de páginas, etc. Se utiliza
 * en conjunto con {@link org.example.dao.LibroDAO} para persistir y recuperar
 * información de la base de datos.</p>
 *
 * <p>Implementa los métodos estándar de JavaBean: constructor por defecto,
 * getters, setters y {@link #toString()} para facilitar su uso en interfaces
 * gráficas (como {@code JComboBox} o tablas).</p>
 *
 * @author Javier
 */
public class Libro {
    private int id;
    private String titulo;
    private int numeroEjemplares;
    private String editorial;
    private int numeroPaginas;
    private int anioEdicion;

    // ---------------------------------Constructores------------------------------------------------------

    /**
     * Constructor por defecto (sin argumentos).
     *
     * <p>Requerido por frameworks como JDBC, JPA o librerías de serialización.
     * Permite crear una instancia vacía que luego se completa con setters.</p>
     */
    public Libro() {
    }

    /**
     * Constructor con parámetros para inicializar un libro sin ID.
     *
     * <p>Útil al crear un nuevo libro que aún no ha sido persistido en la base de datos
     * (el ID se generará al insertarlo).</p>
     *
     * @param titulo           el título del libro.
     * @param numeroEjemplares la cantidad de copias disponibles.
     * @param editorial        la editorial del libro.
     * @param numeroPaginas    el número total de páginas.
     * @param anioEdicion      el año de edición/publicación.
     */
    public Libro(String titulo, int numeroEjemplares, String editorial, int numeroPaginas, int anioEdicion) {
        this.titulo = titulo;
        this.numeroEjemplares = numeroEjemplares;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.anioEdicion = anioEdicion;
    }

    //------------------------------------------ Getters y setters-----------------------------------------------------

    /**
     * Obtiene el identificador único del libro.
     *
     * @return el ID del libro asignado por la base de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del libro.
     *
     * <p>Normalmente asignado por la base de datos tras una inserción.</p>
     *
     * @param id el valor del ID a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el título del libro.
     *
     * @return el título (no {@code null}, aunque puede estar vacío si no se valida).
     */
    public String getTitulo() {
        return titulo;
    }

    /**
     * Establece el título del libro.
     *
     * @param titulo el nuevo título.
     */
    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    /**
     * Obtiene el número de ejemplares disponibles del libro en la biblioteca.
     *
     * @return la cantidad de copias (debe ser >= 0 en un sistema bien gestionado).
     */
    public int getNumeroEjemplares() {
        return numeroEjemplares;
    }

    /**
     * Establece el número de ejemplares disponibles.
     *
     * @param numeroEjemplares la nueva cantidad de copias.
     */
    public void setNumeroEjemplares(int numeroEjemplares) {
        this.numeroEjemplares = numeroEjemplares;
    }

    /**
     * Obtiene la editorial del libro.
     *
     * @return el nombre de la editorial.
     */
    public String getEditorial() {
        return editorial;
    }

    /**
     * Establece la editorial del libro.
     *
     * @param editorial el nombre de la editorial.
     */
    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    /**
     * Obtiene el número total de páginas del libro.
     *
     * @return la cantidad de páginas (debe ser > 0).
     */
    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    /**
     * Establece el número total de páginas del libro.
     *
     * @param numeroPaginas la nueva cantidad de páginas.
     */
    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    /**
     * Obtiene el año de edición del libro.
     *
     * @return el año (ej. 1967). Debe ser un valor razonable (ej. entre 1400 y el año actual + 1).
     */
    public int getAnioEdicion() {
        return anioEdicion;
    }

    /**
     * Establece el año de edición del libro.
     *
     * @param anioEdicion el año de publicación de esta edición.
     */
    public void setAnioEdicion(int anioEdicion) {
        this.anioEdicion = anioEdicion;
    }

    /**
     * Devuelve una representación en cadena del libro, útil para mostrar en interfaces de usuario.
     *
     * <p>Formato: {@code "ID - Título"} (ej. {@code "5 - Cien años de soledad"}).</p>
     *
     * <p>Este método es especialmente útil en componentes como {@code JComboBox},
     * donde se necesita una etiqueta legible para cada objeto.</p>
     *
     * @return una cadena con el ID y el título del libro.
     */
    @Override
    public String toString() {
        return id + " - " + titulo;
    }
}