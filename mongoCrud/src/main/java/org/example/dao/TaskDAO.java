package org.example.dao;

import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.model.Filters;
import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.example.MongoDBConnection;
import org.example.model.Task;

import java.util.ArrayList;
import java.util.List;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;


/**
 * Clase con patrón DAO (Data Access Object) que gestiona las operaciones CRUD sobre
 * la colección "tareas" de la base de datos MongoDB.
 *
 * Emplea POJO Codec para mapear automáticamente documentos de MongoDB a
 * objetos Java del tipo Task.
 */
public class TaskDAO {

    // Colección MongoDB que almacena objetos Task
    private final MongoCollection<Task> collection;

  //--------------------------------------Constructor--------------------------------------
    public TaskDAO() {
        // Registro de codecs para convertir automáticamente entre BSON y POJOS
        CodecRegistry pojoCodecRegistry = fromRegistries(
                MongoClientSettings.getDefaultCodecRegistry(),
                fromProviders(PojoCodecProvider.builder().automatic(true).build())
        );

        // Obtiene la colección "tareas" desde la base de datos "ToDo"
        // y la configura con el codec personalizado
        this.collection = MongoDBConnection.getDatabase()
                .getCollection("tareas", Task.class)
                .withCodecRegistry(pojoCodecRegistry);
    }

    // --------------------------------------CRUD---------------------------------------

    // CREATE
    public void insertarTask (Task task){
        collection.insertOne(task);
    }

    // READ
    public List<Task> getTareas(){
        List<Task> tareas = new ArrayList<>();
        collection.find().into(tareas);
        return tareas;
    }

    // READ BY ID
    public Task getTareaID (String id){
        return collection.find(Filters.eq("_id", new org.bson.types.ObjectId(id))).first();
    }

    // UPDATE
    public  boolean updateTarea(String id, Task updatedTask){
        UpdateResult result = collection.replaceOne(
                Filters.eq("_id",new org.bson.types.ObjectId(id)),
                updatedTask
        );

        return result.getModifiedCount() > 0;
    }

    // DELETE
    public boolean deleteTarea(String id){
        DeleteResult result = collection.deleteOne(
                Filters.eq("_id", new org.bson.types.ObjectId(id))
        );
        return result.getDeletedCount() > 0;
    }
}
