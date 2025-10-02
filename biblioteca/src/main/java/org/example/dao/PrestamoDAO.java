package org.example.dao;

import org.example.ConexionBD;
import org.example.modelo.Libro;
import org.example.modelo.Prestamo;
import org.example.modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PrestamoDAO {
    // Registrar un nuevo préstamo
    public void registrarPrestamo(Prestamo prestamo) throws SQLException {
        String sql = "INSERT INTO Prestamos (id_libro, id_socio, fecha_inicio, fecha_fin, devuelto) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setInt(1, prestamo.getIdLibro());
            ps.setInt(2, prestamo.getIdSocio());
            ps.setDate(3, Date.valueOf(prestamo.getFechaInicio()));
            ps.setDate(4, Date.valueOf(prestamo.getFechaFin()));
            ps.setBoolean(5, prestamo.isDevuelto());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                prestamo.setIdPrestamo(rs.getInt(1));
            }
        }
    }

    // Listar préstamos activos (no devueltos y fecha_fin >= hoy)
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

    // Contar préstamos activos de un socio
    public int numeroLibrosPrestadosPorSocio(int idSocio) throws SQLException {
        String sql = "SELECT COUNT(*) FROM Prestamos WHERE id_socio = ? AND devuelto = FALSE";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idSocio);
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                return rs.getInt(1);
            }
        }
        return 0;
    }

    // Libros con préstamos vencidos (no devueltos y fecha_fin < hoy)
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

    // Socios con préstamos vencidos
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

    // Opcional: marcar un préstamo como devuelto
    public void marcarComoDevuelto(int idPrestamo) throws SQLException {
        String sql = "UPDATE Prestamos SET devuelto = TRUE WHERE id_prestamo = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, idPrestamo);
            ps.executeUpdate();
        }
    }
}
