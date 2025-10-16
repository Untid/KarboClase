package org.example.model;
import org.bson.codecs.pojo.annotations.BsonId;
import org.bson.types.ObjectId;


public class Task {
    @BsonId // Anotación que utiliza MongoDB para reconocer el campo que actúa como clave primaria
    private ObjectId id;
    private String title;
    private String description;
    private boolean completed;

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
     * @param title
     * @param description
     * @param completed
     */
    public Task(String title, String description, boolean completed) {
        this.title = title;
        this.description = description;
        this.completed = completed;
    }

    // ------------------------- Getters y Setters -------------------------
    public ObjectId getId() { return id; } // ObjectId es el tipo de dato usado por Mongo para generar ID únicos
    public void setId(ObjectId id) { this.id = id; } // ObjectId es el tipo de dato usado por Mongo para generar ID únicos

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public boolean isCompleted() { return completed; }
    public void setCompleted(boolean completed) { this.completed = completed; }

    // ------------------------- toString() -------------------------
    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", completed=" + completed +
                '}';
    }
}