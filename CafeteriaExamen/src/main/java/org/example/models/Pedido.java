package org.example.models;

import java.util.List;

public class Pedido {
    private String id;
    private String clienteNombre;
    private String clienteIdLocal;
    private List<Producto> productos;
    private String estado;
    private double total;
    private int tiempoEstimadoMin;

    // Constructor, getters y setters
    public Pedido(String clienteNombre, String clienteIdLocal, List<Producto> productos) {
        this.clienteNombre = clienteNombre;
        this.clienteIdLocal = clienteIdLocal;
        this.productos = productos;
        this.estado = "Pedido";
    }

    // Getters y Setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getClienteNombre() { return clienteNombre; }
    public void setClienteNombre(String clienteNombre) { this.clienteNombre = clienteNombre; }

    public String getClienteIdLocal() { return clienteIdLocal; }
    public void setClienteIdLocal(String clienteIdLocal) { this.clienteIdLocal = clienteIdLocal; }

    public List<Producto> getProductos() { return productos; }
    public void setProductos(List<Producto> productos) { this.productos = productos; }

    public String getEstado() { return estado; }
    public void setEstado(String estado) { this.estado = estado; }

    public double getTotal() { return total; }
    public void setTotal(double total) { this.total = total; }

    public int getTiempoEstimadoMin() { return tiempoEstimadoMin; }
    public void setTiempoEstimadoMin(int tiempoEstimadoMin) { this.tiempoEstimadoMin = tiempoEstimadoMin; }
}