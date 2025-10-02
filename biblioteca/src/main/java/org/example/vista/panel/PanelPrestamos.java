package org.example.vista.panel;

import org.example.dao.LibroDAO;
import org.example.dao.PrestamoDAO;
import org.example.dao.SocioDAO;
import org.example.modelo.Libro;
import org.example.modelo.Socio;
import org.example.modelo.Prestamo;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class PanelPrestamos extends JPanel {
    private JComboBox<Libro> comboLibros;
    private JComboBox<Socio> comboSocios;
    private JTextField txtFechaInicio, txtFechaFin;
    private JTable tablaPrestamos;
    private DefaultTableModel modeloTabla;
    private LibroDAO libroDAO = new LibroDAO();
    private SocioDAO socioDAO = new SocioDAO();
    private PrestamoDAO prestamoDAO = new PrestamoDAO();

    public PanelPrestamos() {
        setLayout(new BorderLayout());
        initComponents();
        cargarCombos();
        cargarPrestamosActivos();
    }

    private void initComponents() {
        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(3, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Registrar Préstamo"));

        panelForm.add(new JLabel("Libro:"));
        comboLibros = new JComboBox<>();
        panelForm.add(comboLibros);

        panelForm.add(new JLabel("Socio:"));
        comboSocios = new JComboBox<>();
        panelForm.add(comboSocios);

        panelForm.add(new JLabel("Fecha inicio (yyyy-MM-dd):"));
        txtFechaInicio = new JTextField();
        txtFechaInicio.setText(LocalDate.now().toString());
        panelForm.add(txtFechaInicio);

        panelForm.add(new JLabel("Fecha fin (yyyy-MM-dd):"));
        txtFechaFin = new JTextField();
        txtFechaFin.setText(LocalDate.now().plusDays(15).toString());
        panelForm.add(txtFechaFin);

        // Botón
        JButton btnRegistrar = new JButton("Registrar Préstamo");
        btnRegistrar.addActionListener(e -> registrarPrestamo());

        // Tabla de préstamos activos
        String[] columnas = {"ID", "Libro", "Socio", "Inicio", "Fin"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaPrestamos = new JTable(modeloTabla);
        JScrollPane scroll = new JScrollPane(tablaPrestamos);

        // Layout
        add(panelForm, BorderLayout.NORTH);
        add(btnRegistrar, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private void cargarCombos() {
        try {
            comboLibros.removeAllItems();
            List<Libro> libros = libroDAO.listarTodos();
            for (Libro l : libros) {
                comboLibros.addItem(l);
            }

            comboSocios.removeAllItems();
            List<Socio> socios = socioDAO.listarTodos();
            for (Socio s : socios) {
                comboSocios.addItem(s);
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar listas: " + ex.getMessage());
        }
    }

    private void registrarPrestamo() {
        try {
            Libro libro = (Libro) comboLibros.getSelectedItem();
            Socio socio = (Socio) comboSocios.getSelectedItem();
            LocalDate inicio = LocalDate.parse(txtFechaInicio.getText().trim());
            LocalDate fin = LocalDate.parse(txtFechaFin.getText().trim());

            if (libro == null || socio == null) {
                JOptionPane.showMessageDialog(this, "Seleccione libro y socio");
                return;
            }

            Prestamo p = new Prestamo(libro.getId(), socio.getId(), inicio, fin);
            prestamoDAO.registrarPrestamo(p);
            JOptionPane.showMessageDialog(this, "Préstamo registrado con ID: " + p.getIdPrestamo());
            cargarPrestamosActivos();
        } catch (DateTimeParseException ex) {
            JOptionPane.showMessageDialog(this, "Formato de fecha inválido. Use yyyy-MM-dd");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al registrar préstamo: " + ex.getMessage());
        }
    }

    private void cargarPrestamosActivos() {
        try {
            modeloTabla.setRowCount(0);
            // Aquí solo mostramos ID, libro y socio (para simplicidad)
            // En una app real, podrías hacer JOINs o cargar nombres
            List<Prestamo> prestamos = prestamoDAO.librosPrestadosActualmente();
            for (Prestamo p : prestamos) {
                // Obtener nombres para mostrar (opcional, mejora UX)
                String tituloLibro = "ID: " + p.getIdLibro();
                String nombreSocio = "ID: " + p.getIdSocio();
                try {
                    Libro l = libroDAO.obtenerPorId(p.getIdLibro());
                    Socio s = socioDAO.obtenerPorId(p.getIdSocio());
                    if (l != null) tituloLibro = l.getTitulo();
                    if (s != null) nombreSocio = s.getNombre() + " " + s.getApellidos();
                } catch (SQLException ignored) {}

                modeloTabla.addRow(new Object[]{
                        p.getIdPrestamo(),
                        tituloLibro,
                        nombreSocio,
                        p.getFechaInicio(),
                        p.getFechaFin()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar préstamos: " + ex.getMessage());
        }
    }
}
