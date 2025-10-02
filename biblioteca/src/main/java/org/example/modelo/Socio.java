package org.example.modelo;

public class Socio {
    private int id;
    private String nombre;
    private String apellidos;
    private int edad;
    private String direccion;
    private String telefono;

    // ---------------------------------Constructores------------------------------------------------------
    public Socio() {
    }

    public Socio(String nombre, String apellidos, int edad, String direccion, String telefono) {
        this.nombre = nombre;
        this.apellidos = apellidos;
        this.edad = edad;
        this.direccion = direccion;
        this.telefono = telefono;
    }

    //------------------------------------------ Getters y setters-----------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }

    public int getEdad() {
        return edad;
    }

    public void setEdad(int edad) {
        this.edad = edad;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public String toString() {
        return id + " - " + nombre + " " + apellidos;
    }
}
