package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import static com.mongodb.client.model.Filters.*;
import static com.mongodb.client.model.Updates.*;

import java.util.Arrays;
import java.util.List;


public class MongoConnection {
    public static void main(String[] args) {
        // Cadena de conexión
        String uri = "mongodb://localhost:27017";

        // Crear cliente
        MongoClient mongoClient = MongoClients.create(uri);

        // Acceder a una base de datos
        MongoDatabase database = mongoClient.getDatabase("miapp");


        System.out.println("Conectando a la bas ede datos: "+ database.getName());



        // Cerrar conexión al final
        // mongoClient.close();


        // ---------------------------Añadiendo un dato a la coleccion usuarios.---------------------------------------
        var collection = database.getCollection("usuarios");

        Document usuario = new Document("nombre", "Ana")
                .append("edad", 28)
                .append("email", "ana@colexio-karbo.com");

              collection.insertOne(usuario);
        System.out.println("Documento insertado con ID: " + usuario.getObjectId("_id"));

        // -----------------------------Añadiendo varios datos a la colección usuarios.--------------------------------
        List usuarios = Arrays.asList(
                new Document("nombre", "Luis").append("edad", 32),
                new Document("nombre", "Sofía").append("edad", 25)
        );
        collection.insertMany(usuarios);

        //--------------------------------Consultando documentos------------------------------------------------------

        // Encontrar todos
        for (Document doc : collection.find()) {
            System.out.println(doc.toJson());
        }

    // Filtrar por condición
        Document filtro = new Document("edad", new Document("$gt", 25));
        for (Document doc : collection.find(filtro)) {
            System.out.println(doc.getString("nombre"));
        }
        //-----------------------------------Actualizar un documento---------------------------------------------------
        // Actualizar el primer usuario con nombre "Ana"
        collection.updateOne(
                eq("nombre", "Ana"),
                set("edad", 29)
        );

        //-------------------------------------Eliminar un documento--------------------------------------------------
        // Eliminar el primer usuario con nombre "Luis"
        collection.deleteOne(eq("nombre", "Luis"));
    }
}
