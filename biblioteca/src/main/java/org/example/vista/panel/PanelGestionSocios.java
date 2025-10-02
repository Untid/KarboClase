package org.example.vista.panel;

import org.example.dao.SocioDAO;
import org.example.modelo.Socio;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;

public class PanelGestionSocios extends JPanel {
    private JTextField txtNombre, txtApellidos, txtEdad, txtDireccion, txtTelefono;
    private JTable tablaSocios;
    private DefaultTableModel modeloTabla;
    private SocioDAO socioDAO = new SocioDAO();
    private int socioSeleccionadoId = -1;

    public PanelGestionSocios() {
        setLayout(new BorderLayout());
        initComponents();
        cargarSocios();
    }

    private void initComponents() {
        // Formulario
        JPanel panelForm = new JPanel(new GridLayout(6, 2, 5, 5));
        panelForm.setBorder(BorderFactory.createTitledBorder("Datos del Socio"));

        panelForm.add(new JLabel("Nombre:"));
        txtNombre = new JTextField();
        panelForm.add(txtNombre);

        panelForm.add(new JLabel("Apellidos:"));
        txtApellidos = new JTextField();
        panelForm.add(txtApellidos);

        panelForm.add(new JLabel("Edad:"));
        txtEdad = new JTextField();
        panelForm.add(txtEdad);

        panelForm.add(new JLabel("Dirección:"));
        txtDireccion = new JTextField();
        panelForm.add(txtDireccion);

        panelForm.add(new JLabel("Teléfono:"));
        txtTelefono = new JTextField();
        panelForm.add(txtTelefono);

        // Botones
        JPanel panelBotones = new JPanel(new FlowLayout());
        JButton btnGuardar = new JButton("Guardar");
        JButton btnActualizar = new JButton("Actualizar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnBuscarNombre = new JButton("Buscar por nombre");
        JButton btnBuscarApellidos = new JButton("Buscar por apellidos");

        panelBotones.add(btnGuardar);
        panelBotones.add(btnActualizar);
        panelBotones.add(btnEliminar);
        panelBotones.add(btnBuscarNombre);
        panelBotones.add(btnBuscarApellidos);

        // Tabla
        String[] columnas = {"ID", "Nombre", "Apellidos", "Edad", "Dirección", "Teléfono"};
        modeloTabla = new DefaultTableModel(columnas, 0);
        tablaSocios = new JTable(modeloTabla);
        tablaSocios.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        tablaSocios.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                int row = tablaSocios.getSelectedRow();
                if (row >= 0) {
                    socioSeleccionadoId = (int) modeloTabla.getValueAt(row, 0);
                    cargarDatosDesdeTabla(row);
                }
            }
        });

        JScrollPane scroll = new JScrollPane(tablaSocios);

        // Eventos

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                guardarSocio();
            }
        });
        btnActualizar.addActionListener(e -> actualizarSocio());
        btnEliminar.addActionListener(e -> eliminarSocio());
        btnBuscarNombre.addActionListener(e -> buscarPorNombre());
        btnBuscarApellidos.addActionListener(e -> buscarPorApellidos());

        add(panelForm, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
        add(scroll, BorderLayout.SOUTH);
    }

    private void cargarSocios() {
        try {
            modeloTabla.setRowCount(0);
            List<Socio> socios = socioDAO.listarTodos();
            for (Socio s : socios) {
                modeloTabla.addRow(new Object[]{
                        s.getId(), s.getNombre(), s.getApellidos(),
                        s.getEdad(), s.getDireccion(), s.getTelefono()
                });
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al cargar socios: " + ex.getMessage());
        }
    }

    private void guardarSocio() {
        try {
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String edad = txtEdad.getText().trim();

            if (nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, apellidos y edad son obligatorios");
                return;
            }

            Socio s = new Socio(
                    nombre,
                    apellidos,
                    Integer.parseInt(edad),
                    txtDireccion.getText().trim(),
                    txtTelefono.getText().trim()
            );
            socioDAO.insertar(s);
            JOptionPane.showMessageDialog(this, "Socio guardado con ID: " + s.getId());
            limpiarCampos();
            cargarSocios();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al guardar: " + ex.getMessage());
        }
    }

    private void cargarDatosDesdeTabla(int row) {
        txtNombre.setText(modeloTabla.getValueAt(row, 1).toString());
        txtApellidos.setText(modeloTabla.getValueAt(row, 2).toString());
        txtEdad.setText(modeloTabla.getValueAt(row, 3).toString());
        txtDireccion.setText(modeloTabla.getValueAt(row, 4).toString());
        txtTelefono.setText(modeloTabla.getValueAt(row, 5).toString());
    }

    private void actualizarSocio() {
        if (socioSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un socio de la tabla");
            return;
        }
        try {
            String nombre = txtNombre.getText().trim();
            String apellidos = txtApellidos.getText().trim();
            String edad = txtEdad.getText().trim();

            if (nombre.isEmpty() || apellidos.isEmpty() || edad.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Nombre, apellidos y edad son obligatorios");
                return;
            }

            Socio s = new Socio(
                    nombre,
                    apellidos,
                    Integer.parseInt(edad),
                    txtDireccion.getText().trim(),
                    txtTelefono.getText().trim()
            );
            s.setId(socioSeleccionadoId);
            socioDAO.actualizar(s);
            JOptionPane.showMessageDialog(this, "Socio actualizado");
            limpiarCampos();
            cargarSocios();
        } catch (NumberFormatException ex) {
            JOptionPane.showMessageDialog(this, "La edad debe ser un número");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error al actualizar: " + ex.getMessage());
        }
    }

    private void eliminarSocio() {
        if (socioSeleccionadoId == -1) {
            JOptionPane.showMessageDialog(this, "Seleccione un socio de la tabla");
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar socio?", "Confirmar", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                socioDAO.eliminar(socioSeleccionadoId);
                JOptionPane.showMessageDialog(this, "Socio eliminado");
                limpiarCampos();
                cargarSocios();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error al eliminar: " + ex.getMessage());
            }
        }
    }

    private void buscarPorNombre() {
        String nombre = JOptionPane.showInputDialog(this, "Ingrese nombre a buscar:");
        if (nombre != null && !nombre.trim().isEmpty()) {
            try {
                modeloTabla.setRowCount(0);
                List<Socio> socios = socioDAO.buscarPorNombre(nombre.trim());
                for (Socio s : socios) {
                    modeloTabla.addRow(new Object[]{
                            s.getId(), s.getNombre(), s.getApellidos(),
                            s.getEdad(), s.getDireccion(), s.getTelefono()
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error en búsqueda: " + ex.getMessage());
            }
        }
    }

    private void buscarPorApellidos() {
        String apellidos = JOptionPane.showInputDialog(this, "Ingrese apellidos a buscar:");
        if (apellidos != null && !apellidos.trim().isEmpty()) {
            try {
                modeloTabla.setRowCount(0);
                List<Socio> socios = socioDAO.buscarPorApellidos(apellidos.trim());
                for (Socio s : socios) {
                    modeloTabla.addRow(new Object[]{
                            s.getId(), s.getNombre(), s.getApellidos(),
                            s.getEdad(), s.getDireccion(), s.getTelefono()
                    });
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error en búsqueda: " + ex.getMessage());
            }
        }
    }

    private void limpiarCampos() {
        txtNombre.setText("");
        txtApellidos.setText("");
        txtEdad.setText("");
        txtDireccion.setText("");
        txtTelefono.setText("");
        socioSeleccionadoId = -1;
    }
}
