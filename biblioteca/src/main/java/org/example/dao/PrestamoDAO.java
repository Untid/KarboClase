package org.example.dao;

import org.example.ConexionBD;
import org.example.modelo.Libro;
import org.example.modelo.Prestamo;
import org.example.modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase de Acceso a Datos (DAO) para la entidad {@link Prestamo}.
 * Proporciona operaciones relacionadas con el registro, consulta y actualización
 * de préstamos de libros a socios en la base de datos.
 *
 * <p>Esta clase interactúa con las tablas {@code Prestamos}, {@code Libros} y {@code Socios}
 * para ofrecer funcionalidades como listar préstamos activos, detectar vencimientos,
 * contar préstamos por socio y marcar devoluciones.</p>
 *
 * @author Javier
 */
public class PrestamoDAO {

    /**
     * Registra un nuevo préstamo en la base de datos.
     *
     * <p>Los campos de fecha ({@code fecha_inicio} y {@code fecha_fin}) se convierten
     * de {@link java.time.LocalDate} a {@link java.sql.Date} para ser compatibles con JDBC.</p>
     *
     * <p>Tras la inserción, se obtiene el ID autogenerado del préstamo y se asigna
     * al objeto {@code prestamo} para mantenerlo sincronizado con la base de datos.</p>
     *
     * @param prestamo el objeto {@link Prestamo} a registrar. No debe ser {@code null}.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void registrarPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO Prestamos (id_libro, id_socio, fecha_inicio, fecha_fin, devuelto) VALUES (?, ?, ?, ?, ?)";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            // Establecer los valores de los parámetros del PreparedStatement
            ps.setInt(1, prestamo.getIdLibro());
            ps.setInt(2, prestamo.getIdSocio());
            // Conversión de LocalDate a java.sql.Date
            ps.setDate(3, Date.valueOf(prestamo.getFechaInicio()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaFin()));
            ps.setBoolean(5, prestamo.isDevuelto());

            // Ejecutar la inserción
            ps.executeUpdate();

            // Recuperar el ID generado por la base de datos
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                prestamo.setIdPrestamo(rs.getInt(1));
            }
        }
    }

    /**
     * Obtiene la lista de préstamos actualmente activos.
     *
     * <p>Se consideran "activos" aquellos préstamos que:
     * <ul>
     *   <li>No han sido devueltos ({@code devuelto = FALSE})</li>
     *   <li>La fecha de fin es mayor o igual a la fecha actual ({@code fecha_fin >= CURDATE()})</li>
     * </ul>
     * </p>
     *
     * <p>Nota: {@code CURDATE()} es una función de MySQL. Si cambias de SGBD,
     * deberás adaptar esta consulta.</p>
     *
     * @return una lista de objetos {@link Prestamo} que cumplen las condiciones. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Prestamo> librosPrestadosActualmente() throws SQLException {
        List<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM Prestamos WHERE devuelto = FALSE AND fecha_fin >= CURDATE()";

        try (Connection conn = ConexionBD.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Prestamo p = new Prestamo();
                p.setIdPrestamo(rs.getInt("id_prestamo"));
                p.setIdLibro(rs.getInt("id_libro"));
                p.setIdSocio(rs.getInt("id_socio"));
                p.setFechaInicio(rs.getDate("fecha_inicio").toLocalDate());
                p.setFechaFin(rs.getDate("fecha_fin").toLocalDate());
                p.setDevuelto(rs.getBoolean("devuelto"));
                prestamos.add(p);
            }
        }
        return prestamos;
    }

    /**
     * Cuenta la cantidad de préstamos activos (no devueltos) asociados a un socio específico.
     *
     * @param idSocio el identificador único del socio.
     * @return el número de préstamos activos del socio. Retorna 0 si no tiene ninguno.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public int numeroLibrosPrestadosPorSocio(int idSocio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Prestamos WHERE id_socio = ? AND devuelto = FALSE";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return rs.getInt(1); // COUNT(*) devuelve un entero en la primera columna
            }
        }
        return 0; // Por seguridad, aunque el COUNT nunca debería ser null
    }

    /**
     * Obtiene la lista de libros cuyos préstamos están vencidos.
     *
     * <p>Un préstamo se considera vencido si:
     * <ul>
     *   <li>No ha sido devuelto ({@code devuelto = FALSE})</li>
     *   <li>La fecha de fin es anterior a la fecha actual ({@code fecha_fin < CURDATE()})</li>
     * </ul>
     * </p>
     *
     * <p>Esta consulta realiza un JOIN entre las tablas {@code Libros} y {@code Prestamos}
     * para devolver únicamente los datos de los libros (no del préstamo).</p>
     *
     * @return una lista de objetos {@link Libro} con préstamos vencidos. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Libro> librosVencidos() throws SQLException {
        List<Libro> libros = new ArrayList<>();
        String sql = "SELECT l.* FROM Libros l " +
                "INNER JOIN Prestamos p ON l.id_libro = p.id_libro " +
                "WHERE p.devuelto = FALSE AND p.fecha_fin < CURDATE()";

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
     * Obtiene la lista de socios que tienen al menos un préstamo vencido.
     *
     * <p>Un préstamo se considera vencido si no ha sido devuelto y su fecha de fin
     * es anterior a la fecha actual.</p>
     *
     * <p>Esta consulta realiza un JOIN entre {@code Socios} y {@code Prestamos}
     * para devolver los datos de los socios (no del préstamo).</p>
     *
     * @return una lista de objetos {@link Socio} con préstamos vencidos. Puede estar vacía.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public List<Socio> sociosConLibrosVencidos() throws SQLException {
        List<Socio> socios = new ArrayList<>();
        String sql = "SELECT s.* FROM Socios s " +
                "INNER JOIN Prestamos p ON s.id_socio = p.id_socio " +
                "WHERE p.devuelto = FALSE AND p.fecha_fin < CURDATE()";

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
     * Marca un préstamo específico como devuelto en la base de datos.
     *
     * <p>Actualiza el campo {@code devuelto} a {@code TRUE} para el préstamo
     * identificado por {@code idPrestamo}.</p>
     *
     * @param idPrestamo el identificador único del préstamo a marcar como devuelto.
     * @throws SQLException si ocurre un error al acceder a la base de datos.
     */
    public void marcarComoDevuelto(int idPrestamo) throws SQLException {
        String sql = "UPDATE Prestamos SET devuelto = TRUE WHERE id_prestamo = ?";

        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
        }
    }
}