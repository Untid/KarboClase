package org.example.modelo;


import java.time.LocalDate;

/**
 * Representa un préstamo de un libro a un socio en el sistema de gestión de biblioteca.
 *
 * <p>Esta clase modela la relación entre un {@link Libro} y un {@link Socio} durante un
 * período de tiempo determinado. Incluye información sobre las fechas de préstamo,
 * el estado de devolución y las claves foráneas que enlazan con otras entidades.</p>
 *
 * <p>Utiliza {@link LocalDate} para manejar fechas, lo que proporciona una representación
 * inmutable, segura y moderna de fechas sin componente horario (ideal para préstamos).</p>
 *
 * @author Javier
 */
public class Prestamo {
    private int idPrestamo;
    // Clave foránea: ID del libro prestado (relación con la tabla Libros)
    private int idLibro;
    // Clave foránea: ID del socio que recibe el préstamo (relación con la tabla Socios)
    private int idSocio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    // Indica si el libro ha sido devuelto (true = devuelto, false = aún prestado)
    private boolean devuelto;

    // ---------------------------------Constructores------------------------------------------------------

    /**
     * Constructor por defecto (sin argumentos).
     *
     * <p>Necesario para frameworks de persistencia (como JDBC al mapear ResultSet)
     * y para serialización. Permite crear una instancia vacía que se completa luego
     * mediante los métodos setters.</p>
     */
    public Prestamo() {
    }

    /**
     * Constructor para crear un nuevo préstamo aún no devuelto.
     *
     * <p>Este constructor se usa al registrar un préstamo nuevo. Por defecto,
     * el estado {@code devuelto} se inicializa en {@code false}, ya que el libro
     * acaba de ser prestado.</p>
     *
     * @param idLibro el identificador del libro a prestar.
     * @param idSocio el identificador del socio que recibe el préstamo.
     * @param fechaInicio la fecha en que se entrega el libro.
     * @param fechaFin la fecha límite para la devolución.
     */
    public Prestamo(int idLibro, int idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idLibro = idLibro;
        this.idSocio = idSocio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.devuelto = false; // Un préstamo recién creado no está devuelto
    }

    //------------------------------------------ Getters y setters-----------------------------------------------------
    /**
     * Obtiene el identificador único del préstamo.
     *
     * @return el ID del préstamo asignado por la base de datos.
     */
    public int getIdPrestamo() {
        return idPrestamo;
    }

    /**
     * Establece el identificador único del préstamo.
     *
     * <p>Normalmente asignado por la base de datos tras insertar el préstamo.</p>
     *
     * @param idPrestamo el valor del ID a asignar.
     */
    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    /**
     * Obtiene el ID del libro asociado a este préstamo.
     *
     * @return el identificador del libro (clave foránea).
     */
    public int getIdLibro() {
        return idLibro;
    }

    /**
     * Establece el ID del libro asociado a este préstamo.
     *
     * @param idLibro el identificador del libro.
     */
    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    /**
     * Obtiene el ID del socio que ha recibido el préstamo.
     *
     * @return el identificador del socio (clave foránea).
     */
    public int getIdSocio() {
        return idSocio;
    }

    /**
     * Establece el ID del socio que ha recibido el préstamo.
     *
     * @param idSocio el identificador del socio.
     */
    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    /**
     * Obtiene la fecha en que se realizó el préstamo.
     *
     * @return la fecha de inicio como objeto {@link LocalDate}.
     */
    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    /**
     * Establece la fecha en que se realizó el préstamo.
     *
     * @param fechaInicio la fecha de inicio del préstamo.
     */
    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    /**
     * Obtiene la fecha límite para la devolución del libro.
     *
     * @return la fecha de fin como objeto {@link LocalDate}.
     */
    public LocalDate getFechaFin() {
        return fechaFin;
    }

    /**
     * Establece la fecha límite para la devolución del libro.
     *
     * @param fechaFin la fecha límite de devolución.
     */
    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    /**
     * Indica si el libro ha sido devuelto.
     *
     * @return {@code true} si el libro ya fue devuelto, {@code false} si sigue prestado.
     */
    public boolean isDevuelto() {
        return devuelto;
    }

    /**
     * Establece el estado de devolución del préstamo.
     *
     * @param devuelto {@code true} para marcar como devuelto, {@code false} si aún está prestado.
     */
    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    /**
     * Devuelve una representación en cadena detallada del préstamo.
     *
     * <p>Formato: {@code "Préstamo[ID=1, Libro=5, Socio=3, Inicio=2025-04-05, Fin=2025-04-19, Devuelto=false]"}.</p>
     *
     * <p>Útil para depuración, logging o mostrar información completa en consola.
     * Para interfaces de usuario (como listas), podrías considerar un formato más breve.</p>
     *
     * @return una cadena con todos los datos relevantes del préstamo.
     */
    @Override
    public String toString() {
        return "Préstamo[ID=" + idPrestamo + ", Libro=" + idLibro + ", Socio=" + idSocio +
                ", Inicio=" + fechaInicio + ", Fin=" + fechaFin + ", Devuelto=" + devuelto + "]";
    }
}