package org.example.models;


public class Cliente {
    private String nombre;
    private String idLocal;
    private String telefono;

    public Cliente() {}

    public Cliente(String nombre, String idLocal) {
        this.nombre = nombre;
        this.idLocal = idLocal;
    }

    // Getters y Setters
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }

    public String getIdLocal() { return idLocal; }
    public void setIdLocal(String idLocal) { this.idLocal = idLocal; }

    public String getTelefono() { return telefono; }
    public void setTelefono(String telefono) { this.telefono = telefono; }

    @Override
    public String toString() {
        return nombre + (idLocal != null ? " (" + idLocal + ")" : "");
    }
}
