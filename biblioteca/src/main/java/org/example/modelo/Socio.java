package org.example.modelo;

/**
 * Representa a un socio (usuario) de la biblioteca en el sistema de gestión.
 *
 * <p>Esta clase encapsula la información personal de un socio, como nombre, apellidos,
 * edad, dirección y teléfono. Se utiliza en conjunto con {@link org.example.dao.SocioDAO}
 * para persistir y recuperar datos de la base de datos.</p>
 *
 * <p>Al igual que otros modelos del sistema, sigue el patrón JavaBean: constructor por defecto,
 * getters, setters y un método {@link #toString()} útil para interfaces de usuario.</p>
 *
 * @author Javier
 */
public class Socio {
    private int id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String direccion;
    private String telefono;

    // ---------------------------------Constructores------------------------------------------------------------------
    /**
     * Constructor por defecto (sin argumentos).
     *
     * <p>Requerido por tecnologías como JDBC (al mapear filas de ResultSet),
     * frameworks de serialización (JSON, XML) y herramientas de reflexión.
     * Permite crear una instancia vacía que se completa posteriormente con setters.</p>
     */
    public Socio() {
    }

    /**
     * Constructor con parámetros para inicializar un socio sin ID.
     *
     * <p>Útil al registrar un nuevo socio que aún no ha sido persistido en la base de datos
     * (el ID se generará automáticamente al insertarlo).</p>
     *
     * @param nombre el nombre de pila del socio.
     * @param apellidos los apellidos del socio.
     * @param edad la edad en años.
     * @param direccion la dirección postal completa.
     * @param telefono el número de teléfono de contacto.
     */
    public Socio(String nombre, String apellidos, int edad, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    //------------------------------------------ Getters y setters-----------------------------------------------------
    /**
     * Obtiene el identificador único del socio.
     *
     * @return el ID asignado por la base de datos.
     */
    public int getId() {
        return id;
    }

    /**
     * Establece el identificador único del socio.
     *
     * <p>Este valor normalmente es asignado por la base de datos tras la inserción.</p>
     *
     * @param id el valor del ID a asignar.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre de pila del socio.
     *
     * @return el nombre (puede estar vacío si no se valida).
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre de pila del socio.
     *
     * @param nombre el nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene los apellidos del socio.
     *
     * @return los apellidos completos.
     */
    public String getApellidos() {
        return apellidos;
    }

    /**
     * Establece los apellidos del socio.
     *
     * @param apellidos los nuevos apellidos.
     */
    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    /**
     * Obtiene la edad del socio en años.
     *
     * @return la edad (debe ser >= 0 en un sistema bien validado).
     */
    public int getEdad() {
        return edad;
    }

    /**
     * Establece la edad del socio.
     *
     * @param edad la nueva edad en años.
     */
    public void setEdad(int edad) {
        this.edad = edad;
    }

    /**
     * Obtiene la dirección postal del socio.
     *
     * @return la dirección completa (calle, número, ciudad, etc.).
     */
    public String getDireccion() {
        return direccion;
    }

    /**
     * Establece la dirección postal del socio.
     *
     * @param direccion la nueva dirección.
     */
    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    /**
     * Obtiene el número de teléfono de contacto del socio.
     *
     * @return el teléfono (formato libre: puede incluir espacios, guiones, +34, etc.).
     */
    public String getTelefono() {
        return telefono;
    }

    /**
     * Establece el número de teléfono de contacto del socio.
     *
     * @param telefono el nuevo número de teléfono.
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * Devuelve una representación en cadena del socio, ideal para mostrar en interfaces de usuario.
     *
     * <p>Formato: {@code "ID - Nombre Apellidos"} (ej. {@code "3 - María García López"}).</p>
     *
     * <p>Este formato es especialmente útil en componentes como {@code JComboBox} o listas
     * desplegables en formularios de préstamo, donde se necesita identificar rápidamente a un socio.</p>
     *
     * @return una cadena con el ID, nombre y apellidos del socio.
     */
    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellidos;
    }
}
