package org.example;

import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoClients;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

    // Local
    private static final String CONNECTION_LOCAL = "mongodb://localhost:27017";

    // Nombre de la base de datos
    private static final String DATABASE_NOMBRE = "ToDo";

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
        mongoClient = MongoClients.create(CONNECTION_LOCAL); // Crea el cliente con la URI
        database = mongoClient.getDatabase(DATABASE_NOMBRE); // Obtiene la base de datos
    }

    /**
     * Devuelve la instancia de la base de datos MongoDB
     *
     * @return objeto MongoDataBase para realizar operaciones sobre la base de datos.
     */
    public static MongoDatabase getDatabase() {
        return database;
    }

}