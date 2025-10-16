package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    // Local
    private static final String CONNECTION_STRING = "mongodb://localhost:27017";

    // Nombre de la base de datos
    private static final String DATABASE_NAME = "ToDo";

    // Objeto que gestiona la conexión al servidor
    private static MongoClient mongoClient;

    // Referencia a la base de datos
    private static MongoDatabase database;


    /**
     * Bloque estático
     * Este bloque se ejecuta una sola vez cuando la clase es cargada por primera vez.
     * Crea el cliente MongoDB y obtiene la base de datos especificada.
     */
    static { // Evita que se tenga que instanciar la clase manualmente
        mongoClient = MongoClients.create(CONNECTION_STRING); // Crea el cliente con la URI
        database = mongoClient.getDatabase(DATABASE_NAME); // Obtiene la base de datos
    }

    /**
     * Devuelve la instancia de la base de datos MongoDB
     *
     * @return objeto MongoDataBase para realizar operaciones sobre la base de datos.
     */
    public static MongoDatabase getDatabase() {
        return database;
    }

    /**
     * Cierra la conexión con el servidor MongoDB
     *
     * Es importante llamarlo al finalizar el uso de la base de datos para liberar recursos.
     */
    public static void close() {
        if (mongoClient != null) {
            mongoClient.close();
        }
    }
}