package org.example.dao;

import org.example.ConexionBD;
import org.example.modelo.Socio;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SocioDAO {
    // Dar de alta un socio
    public void insertar(Socio socio) throws SQLException {
        String sql = "INSERT INTO Socios (nombre, apellidos, edad, direccion, telefono) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setInt(3, socio.getEdad());
            ps.setString(4, socio.getDireccion());
            ps.setString(5, socio.getTelefono());
            ps.executeUpdate();

            ResultSet rs = ps.getGeneratedKeys();
            if (rs.next()) {
                socio.setId(rs.getInt(1));
            }
        }
    }

    // Modificar un socio
    public void actualizar(Socio socio) throws SQLException {
        String sql = "UPDATE Socios SET nombre = ?, apellidos = ?, edad = ?, direccion = ?, telefono = ? WHERE id_socio = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, socio.getNombre());
            ps.setString(2, socio.getApellidos());
            ps.setInt(3, socio.getEdad());
            ps.setString(4, socio.getDireccion());
            ps.setString(5, socio.getTelefono());
            ps.setInt(6, socio.getId());
            ps.executeUpdate();
        }
    }

    // Dar de baja un socio
    public void eliminar(int id) throws SQLException {
        String sql = "DELETE FROM Socios WHERE id_socio = ?";
        try (Connection conn = ConexionBD.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        }
    }

    // Buscar socios por nombre (búsqueda parcial, insensible a mayúsculas)
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

    // Buscar socios por apellidos (búsqueda parcial)
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

    // Listar todos los socios (útil para cargar JComboBox en préstamos)
    public List<Socio> listarTodos() throws SQLException {
        List<Socio> socios = new ArrayList<>();
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

    // Obtener un socio por ID (útil para mostrar detalles)
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
        return null;
    }
}
