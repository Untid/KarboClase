package org.example.dao;

import org.example.ConexionBD;
import org.example.modelo.Libro;
import org.example.modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class LibroDAO {

    public void insertar(Libro libro) throws SQLException {
        String sql = "INSERT INTO Libros (titulo, numero_ejemplares, editorial, numero_paginas, anio_edicion) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getNumeroEjemplares());
            ps.setString(3, libro.getEditorial());
            ps.setInt(4, libro.getNumeroPaginas());
            ps.setInt(5, libro.getAnioEdicion());
            ps.executeUpdate();
            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) libro.setId(rs.getInt(1));
        }
    }

    public void actualizar(Libro libro) throws SQLException {
        String sql = "UPDATE Libros SET titulo = ?, numero_ejemplares = ?, editorial = ?, numero_paginas = ?, anio_edicion = ? WHERE id_libro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, libro.getTitulo());
            ps.setInt(2, libro.getNumeroEjemplares());
            ps.setString(3, libro.getEditorial());
            ps.setInt(4, libro.getNumeroPaginas());
            ps.setInt(5, libro.getAnioEdicion());
            ps.setInt(6, libro.getId());
            ps.executeUpdate();
        }
    }

    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Libros WHERE id_libro = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

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
    // Obtener un socio por ID (útil para mostrar detalles)
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
        return null;
    }
}
