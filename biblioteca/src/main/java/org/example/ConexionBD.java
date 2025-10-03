package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Clase utilitaria para gestionar la conexión con la base de datos de la biblioteca.
 *
 * <p>Proporciona un método estático {@link #getConnection()} que devuelve una nueva
 * conexión a la base de datos MySQL configurada para este sistema.</p>
 *
 * <h3>Configuración actual</h3>
 * <ul>
 *   <li><b>Motor de base de datos:</b> MySQL</li>
 *   <li><b>URL:</b> {@code jdbc:mysql://localhost:3306/biblioteca}</li>
 *   <li><b>Usuario:</b> {@code root}</li>
 *   <li><b>Contraseña:</b> vacía (por defecto)</li>
 * </ul>
 *
 * @author Javier
 */
public class ConexionBD {
    private static final String URL = "jdbc:mysql://localhost:3306/biblioteca";
    private static final String USER = "root";
    private static final String PASSWORD = "";

    /**
     * Obtiene una nueva conexión a la base de datos.
     *
     * <p>Este método utiliza el driver JDBC de MySQL para establecer la conexión.</p>
     *
     * @return una nueva instancia de {@link Connection} lista para usarse.
     * @throws SQLException si ocurre un error al conectar (ej. base de datos caída,
     *                      credenciales incorrectas, driver no encontrado, etc.).
     */
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }
}
