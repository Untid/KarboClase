package org.example.vista.panel;

import org.example.dao.LibroDAO;
import org.example.modelo.Libro;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class PanelGestionLibros extends JPanel {
    private JTextField txtTitulo, txtEjemplares, txtEditorial, txtPaginas, txtAnio;
    private JTable tablaLibros;
    private DefaultTableModel modeloTabla;
    private LibroDAO libroDAO = new LibroDAO();
    private int libroSeleccionadoId = -1;

    public PanelGestionLibros() {
        setLayout(new BorderLayout());
        initComponents();
        cargarLibros();
    }

    private void initComponents() {
        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Libro"));

        panelForm.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        panelForm.add(txtTitulo);

        panelForm.add(new JLabel("Ejemplares:"));
        txtEjemplares = new JTextField();
        panelForm.add(txtEjemplares);

        panelForm.add(new JLabel("Editorial:"));
        txtEditorial = new JTextField();
        panelForm.add(txtEditorial);

        panelForm.add(new JLabel("Páginas:"));
        txtPaginas = new JTextField();
        panelForm.add(txtPaginas);

        panelForm.add(new JLabel("Año edición:"));
        txtAnio = new JTextField();
        panelForm.add(txtAnio);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscar = new JButton("Buscar por título");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscar);

        // Tabla
        String[] columnas = {"ID", "Título", "Ejemplares", "Editorial", "Págs", "Año"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaLibros = new JTable(modeloTabla);
        tablaLibros.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaLibros.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaLibros.getSelectedRow();
                if (row >= 0) {
                    libroSeleccionadoId = (int) modeloTabla.getValueAt(row, 0);
                    cargarDatosDesdeTabla(row);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaLibros);

        // Eventos
        btnGuardar.addActionListener(e -> guardarLibro());
        btnActualizar.addActionListener(e -> actualizarLibro());
        btnEliminar.addActionListener(e -> eliminarLibro());
        btnBuscar.addActionListener(e -> buscarPorTitulo());

        add(panelForm, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private void cargarLibros() {
        try {
            modeloTabla.setRowCount(0);
            List<Libro> libros = libroDAO.listarTodos();
            for (Libro l : libros) {
                modeloTabla.addRow(new Object[]{
                        l.getId(), l.getTitulo(), l.getNumeroEjemplares(),
                        l.getEditorial(), l.getNumeroPaginas(), l.getAnioEdicion()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar libros: " + ex.getMessage());
        }
    }

    private void guardarLibro() {
        try {
            String titulo = txtTitulo.getText().trim();
            String ejemplares = txtEjemplares.getText().trim();
            String paginas = txtPaginas.getText().trim();
            String anio = txtAnio.getText().trim();

            if (titulo.isEmpty() || ejemplares.isEmpty() || paginas.isEmpty() || anio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            Libro l = new Libro(
                    titulo,
                    Integer.parseInt(ejemplares),
                    txtEditorial.getText().trim(),
                    Integer.parseInt(paginas),
                    Integer.parseInt(anio)
            );
            libroDAO.insertar(l);
            JOptionPane.showMessageDialog(this, "Libro guardado con ID: " + l.getId());
            limpiarCampos();
            cargarLibros();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ejemplares, páginas y año deben ser números");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void cargarDatosDesdeTabla(int row) {
        txtTitulo.setText(modeloTabla.getValueAt(row, 1).toString());
        txtEjemplares.setText(modeloTabla.getValueAt(row, 2).toString());
        txtEditorial.setText(modeloTabla.getValueAt(row, 3).toString());
        txtPaginas.setText(modeloTabla.getValueAt(row, 4).toString());
        txtAnio.setText(modeloTabla.getValueAt(row, 5).toString());
    }

    private void actualizarLibro() {
        if (libroSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla");
            return;
        }
        try {
            String titulo = txtTitulo.getText().trim();
            String ejemplares = txtEjemplares.getText().trim();
            String paginas = txtPaginas.getText().trim();
            String anio = txtAnio.getText().trim();

            if (titulo.isEmpty() || ejemplares.isEmpty() || paginas.isEmpty() || anio.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Todos los campos son obligatorios");
                return;
            }

            Libro l = new Libro(
                    titulo,
                    Integer.parseInt(ejemplares),
                    txtEditorial.getText().trim(),
                    Integer.parseInt(paginas),
                    Integer.parseInt(anio)
            );
            l.setId(libroSeleccionadoId);
            libroDAO.actualizar(l);
            JOptionPane.showMessageDialog(this, "Libro actualizado");
            limpiarCampos();
            cargarLibros();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "Ejemplares, páginas y año deben ser números");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarLibro() {
        if (libroSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un libro de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar libro?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                libroDAO.eliminar(libroSeleccionadoId);
                JOptionPane.showMessageDialog(this, "Libro eliminado");
                limpiarCampos();
                cargarLibros();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private void buscarPorTitulo() {
        String titulo = JOptionPane.showInputDialog(this, "Ingrese título a buscar:");
        if (titulo != null && !titulo.trim().isEmpty()) {
            try {
                modeloTabla.setRowCount(0);
                List<Libro> libros = libroDAO.buscarPorTitulo(titulo.trim());
                for (Libro l : libros) {
                    modeloTabla.addRow(new Object[]{
                            l.getId(), l.getTitulo(), l.getNumeroEjemplares(),
                            l.getEditorial(), l.getNumeroPaginas(), l.getAnioEdicion()
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error en búsqueda: " + ex.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        txtTitulo.setText("");
        txtEjemplares.setText("");
        txtEditorial.setText("");
        txtPaginas.setText("");
        txtAnio.setText("");
        libroSeleccionadoId = -1;
    }
}
