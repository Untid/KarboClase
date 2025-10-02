package org.example.modelo;


import java.time.LocalDate;

public class Prestamo {
    // Libros, Socio, Fecha inicio préstamo y Fecha fin de préstamo
    private int idPrestamo;
    private int idLibro;
    private int idSocio;
    private LocalDate fechaInicio;
    private LocalDate fechaFin;
    private boolean devuelto;

    // ---------------------------------Constructores------------------------------------------------------

    public Prestamo() {
    }


    public Prestamo(int idLibro, int idSocio, LocalDate fechaInicio, LocalDate fechaFin) {
        this.idLibro = idLibro;
        this.idSocio = idSocio;
        this.fechaInicio = fechaInicio;
        this.fechaFin = fechaFin;
        this.devuelto = false;
    }

    //------------------------------------------ Getters y setters-----------------------------------------------------
    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdLibro() {
        return idLibro;
    }

    public void setIdLibro(int idLibro) {
        this.idLibro = idLibro;
    }

    public int getIdSocio() {
        return idSocio;
    }

    public void setIdSocio(int idSocio) {
        this.idSocio = idSocio;
    }

    public LocalDate getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(LocalDate fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public LocalDate getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(LocalDate fechaFin) {
        this.fechaFin = fechaFin;
    }

    public boolean isDevuelto() {
        return devuelto;
    }

    public void setDevuelto(boolean devuelto) {
        this.devuelto = devuelto;
    }

    @Override
    public String toString() {
        return "Préstamo[ID=" + idPrestamo + ", Libro=" + idLibro + ", Socio=" + idSocio +
                ", Inicio=" + fechaInicio + ", Fin=" + fechaFin + ", Devuelto=" + devuelto + "]";
    }


}
