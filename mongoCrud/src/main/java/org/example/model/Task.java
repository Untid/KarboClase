package org.example.model;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;


public class Task {
    @BsonId // Anotación que utiliza MongoDB para reconocer el campo que actúa como clave primaria
    private ObjectId id;
    private String titulo;
    private String descripcion;
    private boolean completada;

    /**
     *  Constructor vacío (requerido por MongoDB y el POJO codec).
     *  MongoDB necesita un constructor sin argumentos para poder
     *  deserializar los documentos BSON a objetos Java.
     */
    public Task() {}

    /**
     * Constructor con parámetros.
     * Permite crear tareas fácilmente desde el código.
     *
     * @param titulo
     * @param descripcion
     * @param completada
     */
    public Task(String titulo, String descripcion, boolean completada) {
        this.titulo = titulo;
        this.descripcion = descripcion;
        this.completada = completada;
    }

    // ------------------------- Getters y Setters -------------------------
    public ObjectId getId() { return id; } // ObjectId es el tipo de dato usado por Mongo para generar ID únicos
    public void setId(ObjectId id) { this.id = id; } // ObjectId es el tipo de dato usado por Mongo para generar ID únicos

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getDescripcion() { return descripcion; }
    public void setDescripcion(String descripcion) { this.descripcion = descripcion; }

    public boolean isCompletada() { return completada; }
    public void setCompletada(boolean completada) { this.completada = completada; }

    // ------------------------- toString() -------------------------
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + titulo + '\'' +
                ", description='" + descripcion + '\'' +
                ", completed=" + completada +
                '}';
    }
}