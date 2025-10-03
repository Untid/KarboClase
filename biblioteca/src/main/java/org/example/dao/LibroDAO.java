package org.example.dao;

import org.example.ConexionBD;
import org.example.modelo.Libro;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;


/**
 * Clase de Acceso a Datos (DAO) para la entidad {@link Libro}.
 * Proporciona operaciones CRUD (Crear, Leer, Actualizar, Eliminar)
 * sobre la tabla "Libros" en la base de datos biblioteca (En este caso local con XAMPP).
 *
 * <p>Esta clase utiliza el patrón DAO para desacoplar la lógica de negocio
 * de la lógica de acceso a datos, facilitando el mantenimiento y las pruebas.</p>
 *
 * @author Javier
 */
public class LibroDAO {
    /**
     * Inserta un nuevo libro en la base de datos.
     *
     * <p>Tras la inserción, se obtiene el ID autogenerado por la base de datos
     * y se asigna al objeto {@code libro} para mantenerlo sincronizado.</p>
     *
     * @param libro el objeto {@link Libro} a insertar. No debe ser {@code null}.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO Libros (titulo, numero_ejemplares, editorial, numero_paginas, anio_edicion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Establecer los parámetros del PreparedStatement
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getNumeroEjemplares());
            ps.setString(3, libro.getEditorial());
            ps.setInt(4, libro.getNumeroPaginas());
            ps.setInt(5, libro.getAnioEdicion());

            // Ejecutar la inserción
            ps.executeUpdate();

            // Recuperar la clave generada (ID del Libro)
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) libro.setId(rs.getInt(1)); // Actualiza el ID del objeto con el generado por la BD
        }
    }

    /**
     * Actualiza los datos de un libro existente en la base de datos.
     *
     * <p>El libro debe tener un ID válido (previamente guardado en la base de datos).</p>
     *
     * @param libro el objeto {@link Libro} con los datos actualizados. No debe ser {@code null}.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE Libros SET titulo = ?, numero_ejemplares = ?, editorial = ?, numero_paginas = ?, anio_edicion = ? WHERE id_libro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getNumeroEjemplares());
            ps.setString(3, libro.getEditorial());
            ps.setInt(4, libro.getNumeroPaginas());
            ps.setInt(5, libro.getAnioEdicion());
            ps.setInt(6, libro.getId()); // Condición WHERE
            ps.executeUpdate();
        }
    }

    /**
     * Elimina un libro de la base de datos por su ID.
     *
     *
     * @param id el identificador único del libro a eliminar.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Libros WHERE id_libro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }


    /**
     * Busca libros cuyo título contenga la cadena especificada (búsqueda parcial, insensible a mayúsculas/minúsculas según la BD)
     *
     * <p>Utiliza el operador SQL {@code LIKE} con comodines {@code %} al inicio y al final del término de la búsqueda.</p>
     *
     * @param titulo fragmento del título a buscar.
     * @return una lista de {@link Libro} que coinciden con la búsqueda. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Libro> buscarPorTitulo(String titulo) throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libros WHERE titulo LIKE ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, "%" + titulo + "%");
            ResultSet rs = ps.executeQuery();
            while (rs.next()) {
                Libro l = new Libro();
                l.setId(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
                l.setEditorial(rs.getString("editorial"));
                l.setNumeroPaginas(rs.getInt("numero_paginas"));
                l.setAnioEdicion(rs.getInt("anio_edicion"));
                libros.add(l);
            }
        }
        return libros;
    }

    /**
     * Obtiene todos los libros almacenados en la base de datos.
     *
     * @return una lista con todos los {@link Libro}. Puede estar vacía si no hay registros.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Libro> listarTodos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT * FROM Libros";
        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Libro l = new Libro();
                l.setId(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
                l.setEditorial(rs.getString("editorial"));
                l.setNumeroPaginas(rs.getInt("numero_paginas"));
                l.setAnioEdicion(rs.getInt("anio_edicion"));
                libros.add(l);
            }
        }
        return libros;
    }

    /**
     * Obtiene un libro específico por su ID.
     *
     * @param id el identificador único del libro.
     * @return el objeto {@link Libro} si se encuentra, o {@code null} si no existe.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public Libro obtenerPorId(int id) throws SQLException {
        String sql = "SELECT * FROM Libros WHERE id_libro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                Libro l = new Libro();
                l.setId(rs.getInt("id_libro"));
                l.setTitulo(rs.getString("titulo"));
                l.setNumeroEjemplares(rs.getInt("numero_ejemplares"));
                l.setEditorial(rs.getString("editorial"));
                l.setNumeroPaginas(rs.getInt("numero_paginas"));
                l.setAnioEdicion(rs.getInt("anio_edicion"));
                return l;
            }
        }
        return null; // No sé encontró ningún libro con ese ID.
    }
}
