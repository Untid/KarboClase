package org.example.modelo;

public class Libro {
    private int id;
    private String titulo;
    private int numeroEjemplares;
    private String editorial;
    private int numeroPaginas;
    private int anioEdicion;

    // ---------------------------------Constructores------------------------------------------------------
    public Libro() {
    }

    public Libro(String titulo, int numeroEjemplares, String editorial, int numeroPaginas, int anioEdicion) {
        this.titulo = titulo;
        this.numeroEjemplares = numeroEjemplares;
        this.editorial = editorial;
        this.numeroPaginas = numeroPaginas;
        this.anioEdicion = anioEdicion;
    }

    //------------------------------------------ Getters y setters-----------------------------------------------------
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getNumeroEjemplares() {
        return numeroEjemplares;
    }

    public void setNumeroEjemplares(int numeroEjemplares) {
        this.numeroEjemplares = numeroEjemplares;
    }

    public String getEditorial() {
        return editorial;
    }

    public void setEditorial(String editorial) {
        this.editorial = editorial;
    }

    public int getNumeroPaginas() {
        return numeroPaginas;
    }

    public void setNumeroPaginas(int numeroPaginas) {
        this.numeroPaginas = numeroPaginas;
    }

    public int getAnioEdicion() {
        return anioEdicion;
    }

    public void setAnioEdicion(int anioEdicion) {
        this.anioEdicion = anioEdicion;
    }

    @Override
    public String toString() {
        return id + " - " + titulo;
    }
}