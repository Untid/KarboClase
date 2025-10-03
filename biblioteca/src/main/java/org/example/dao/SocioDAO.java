package org.example.dao;

import org.example.ConexionBD;
import org.example.modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Acceso a Datos (DAO) para la entidad {@link Socio}.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la tabla "Socios" en la base de datos.
 *
 * <p>Además, incluye métodos de búsqueda específicos (por nombre o apellidos)
 * y listado completo, útiles para interfaces de usuario como formularios
 * de préstamo o gestión de socios.</p>
 *
 * @author Javier
 */
public class SocioDAO {

    /**
     * Inserta un nuevo socio en la base de datos.
     *
     * <p>Tras la inserción, se recupera el ID autogenerado por la base de datos
     * y se asigna al objeto {@code socio} para mantenerlo sincronizado.</p>
     *
     * @param socio el objeto {@link Socio} a insertar. No debe ser {@code null}.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void insertar(Socio socio) throws SQLException {
        String sql = "INSERT INTO Socios (nombre, apellidos, edad, direccion, telefono) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            // Establecer los valores de los parámetros
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setInt(3, socio.getEdad());
            ps.setString(4, socio.getDireccion());
            ps.setString(5, socio.getTelefono());

            // Ejecutar la inserción
            ps.executeUpdate();

            // Recuperar el ID generado por la base de datos
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                socio.setId(rs.getInt(1)); // Actualiza el ID del objeto
            }
        }
    }

    /**
     * Actualiza los datos de un socio existente en la base de datos.
     *
     * <p>El socio debe tener un ID válido (previamente guardado en la base de datos).</p>
     *
     * @param socio el objeto {@link Socio} con los datos actualizados. No debe ser {@code null}.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void actualizar(Socio socio) throws SQLException {
        String sql = "UPDATE Socios SET nombre = ?, apellidos = ?, edad = ?, direccion = ?, telefono = ? WHERE id_socio = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setInt(3, socio.getEdad());
            ps.setString(4, socio.getDireccion());
            ps.setString(5, socio.getTelefono());
            ps.setInt(6, socio.getId()); // Condición WHERE

            ps.executeUpdate();
        }
    }

    /**
     * Elimina un socio de la base de datos por su ID.
     *
     * <p>Nota: En una aplicación real, se podría querer verificar primero
     * que el socio no tenga préstamos activos antes de eliminarlo,
     * para mantener la integridad referencial.</p>
     *
     * @param id el identificador único del socio a eliminar.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Socios WHERE id_socio = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    /**
     * Busca socios cuyo nombre contenga la cadena especificada (búsqueda parcial).
     *
     * <p>Utiliza el operador SQL {@code LIKE} con comodines ({@code %}) al inicio y al final,
     * lo que permite coincidencias en cualquier parte del nombre.</p>
     *
     * <p>La sensibilidad a mayúsculas/minúsculas depende de la configuración
     * de la base de datos (en MySQL con collation predeterminado, suele ser insensible).</p>
     *
     * @param nombre fragmento del nombre a buscar.
     * @return una lista de {@link Socio} que coinciden con la búsqueda. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Socio> buscarPorNombre(String nombre) throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM Socios WHERE nombre LIKE ?";

        try (Connection conn = ConexionBD.getConnection() ;
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + nombre + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Socio s = new Socio();
                s.setId(rs.getInt("id_socio"));
                s.setNombre(rs.getString("nombre"));
                s.setApellidos(rs.getString("apellidos"));
                s.setEdad(rs.getInt("edad"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getString("telefono"));
                socios.add(s);
            }
        }
        return socios;
    }

    /**
     * Busca socios cuyos apellidos contengan la cadena especificada (búsqueda parcial).
     *
     * <p>Funciona de forma análoga a {@link #buscarPorNombre(String)}, pero sobre el campo {@code apellidos}.</p>
     *
     * @param apellidos fragmento de los apellidos a buscar.
     * @return una lista de {@link Socio} que coinciden con la búsqueda. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Socio> buscarPorApellidos(String apellidos) throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT * FROM Socios WHERE apellidos LIKE ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, "%" + apellidos + "%");

            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Socio s = new Socio();
                s.setId(rs.getInt("id_socio"));
                s.setNombre(rs.getString("nombre"));
                s.setApellidos(rs.getString("apellidos"));
                s.setEdad(rs.getInt("edad"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getString("telefono"));
                socios.add(s);
            }
        }
        return socios;
    }

    /**
     * Obtiene todos los socios almacenados en la base de datos, ordenados por apellidos y nombre.
     *
     * <p>Este método es especialmente útil para cargar listas desplegables (como {@code JComboBox})
     * en formularios de préstamo, donde se necesita una lista completa y ordenada de socios.</p>
     *
     * @return una lista con todos los {@link Socio}, ordenada alfabéticamente. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Socio> listarTodos() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        // Ordenar por apellidos y luego por nombre mejora la experiencia de usuario
        String sql = "SELECT * FROM Socios ORDER BY apellidos, nombre";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Socio s = new Socio();
                s.setId(rs.getInt("id_socio"));
                s.setNombre(rs.getString("nombre"));
                s.setApellidos(rs.getString("apellidos"));
                s.setEdad(rs.getInt("edad"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getString("telefono"));
                socios.add(s);
            }
        }
        return socios;
    }

    /**
     * Obtiene un socio específico por su ID.
     *
     * @param id el identificador único del socio.
     * @return el objeto {@link Socio} si se encuentra, o {@code null} si no existe.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public Socio obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Socios WHERE id_socio = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Socio s = new Socio();
                s.setId(rs.getInt("id_socio"));
                s.setNombre(rs.getString("nombre"));
                s.setApellidos(rs.getString("apellidos"));
                s.setEdad(rs.getInt("edad"));
                s.setDireccion(rs.getString("direccion"));
                s.setTelefono(rs.getString("telefono"));
                return s;
            }
        }
        return null; // No se encontró ningún socio con ese ID
    }
}
