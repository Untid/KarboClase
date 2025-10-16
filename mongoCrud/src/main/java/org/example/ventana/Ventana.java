package org.example.ventana;

import org.example.dao.TaskDAO;
import org.example.model.Task;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.util.*;
import java.util.List;

public class Ventana extends JFrame {

    private TaskDAO taskDAO = new TaskDAO();

    private DefaultTableModel tableModel;
    private JTable taskTable;
    private JTextField titleField, descriptionField;
    private JCheckBox completedCheckBox;
    private JButton createButton, updateButton, deleteButton, refreshButton;
    private String selectedTaskId = null;

    public Ventana() {
        setTitle("Gestor de Tareas - MongoDB");
        setSize(700, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initComponents(); // Inicializa los elementos de la interfaz
        loadTasks(); // Carga las tareas desde MongoDB
    }

    /**
     * Inicializa y configura todos los componentes de la interfaz
     */
    private void initComponents() {
        setLayout(new BorderLayout());

        // ------------------- Tabla de tareas ------------------------
        String[] columns = {"ID", "Título", "Descripción", "Completada"};

        // Modelo de la tabla con columnas no editables
        tableModel = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };
        taskTable = new JTable(tableModel);
        taskTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        // Detectar selección de fila
        taskTable.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                int selectedRow = taskTable.getSelectedRow();
                if (selectedRow >= 0) {
                    // Guardar ID y mostrar datos en los campos
                    selectedTaskId = (String) tableModel.getValueAt(selectedRow, 0);
                    titleField.setText((String) tableModel.getValueAt(selectedRow, 1));
                    descriptionField.setText((String) tableModel.getValueAt(selectedRow, 2));
                    completedCheckBox.setSelected(Boolean.parseBoolean(tableModel.getValueAt(selectedRow, 3).toString()));
                    updateButton.setEnabled(true);
                    deleteButton.setEnabled(true);
                } else {
                    clearForm();
                }
            }
        });

        JScrollPane scrollPane = new JScrollPane(taskTable);
        add(scrollPane, BorderLayout.CENTER);

        // -----------------------Formulario-------------------------------
        JPanel formPanel = new JPanel(new GridLayout(4, 2, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Detalles de la Tarea"));

        formPanel.add(new JLabel("Título:"));
        titleField = new JTextField();
        formPanel.add(titleField);

        formPanel.add(new JLabel("Descripción:"));
        descriptionField = new JTextField();
        formPanel.add(descriptionField);

        formPanel.add(new JLabel("Completada:"));
        completedCheckBox = new JCheckBox();
        formPanel.add(completedCheckBox);

        add(formPanel, BorderLayout.NORTH);

        // ---------------------------Botones----------------------------------
        JPanel buttonPanel = new JPanel(new FlowLayout());
        createButton = new JButton("Crear");
        updateButton = new JButton("Actualizar");
        deleteButton = new JButton("Eliminar");
        refreshButton = new JButton("Refrescar");

        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);


        // Asignar acciones a los botones
        createButton.addActionListener(this::onCreate);
        updateButton.addActionListener(this::onUpdate);
        deleteButton.addActionListener(this::onDelete);
        refreshButton.addActionListener(e -> loadTasks());

        // Agregar botones al panel
        buttonPanel.add(createButton);
        buttonPanel.add(updateButton);
        buttonPanel.add(deleteButton);
        buttonPanel.add(refreshButton);

        add(buttonPanel, BorderLayout.SOUTH);
    }

    // ------------------------ Métodos de funcionalidad ---------------------

    /**
     * Carga las tareas desde MongoDB y actualiza la tabla.
     */
    private void loadTasks() {
        tableModel.setRowCount(0); // Limpiar tabla
        List<Task> tasks = taskDAO.getTareas();
        for (Task t : tasks) {
            tableModel.addRow(new Object[]{
                    t.getId().toHexString(),
                    t.getTitle(),
                    t.getDescription(),
                    t.isCompleted()
            });
        }
        clearForm();
    }

    /**
     * Acción del botón "Crear".
     * Inserta una nueva tarea en MongoDB.
     * @param e
     */
    private void onCreate(ActionEvent e) {
        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        boolean completed = completedCheckBox.isSelected();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Task task = new Task(title, description, completed);
        taskDAO.insertarTask(task);
        JOptionPane.showMessageDialog(this, "Tarea creada con éxito.");
        loadTasks();
    }

    /**
     * Acción del botón "Actualizar"
     * Reemplaza los datos de una tarea existente en la base de de datos
     * @param e
     */
    private void onUpdate(ActionEvent e) {
        if (selectedTaskId == null) return;

        String title = titleField.getText().trim();
        String description = descriptionField.getText().trim();
        boolean completed = completedCheckBox.isSelected();

        if (title.isEmpty()) {
            JOptionPane.showMessageDialog(this, "El título no puede estar vacío.", "Error", JOptionPane.WARNING_MESSAGE);
            return;
        }

        Task updatedTask = new Task(title, description, completed);
        updatedTask.setId(new org.bson.types.ObjectId(selectedTaskId));

        if (taskDAO.updateTarea(selectedTaskId, updatedTask)) {
            JOptionPane.showMessageDialog(this, "Tarea actualizada.");
            loadTasks();
        } else {
            JOptionPane.showMessageDialog(this, "No se pudo actualizar la tarea.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Acción del botoón "Eliminar".
     * Elimina la tarea seleccionada tras confirmar
     * @param e
     */
    private void onDelete(ActionEvent e) {
        if (selectedTaskId == null) return;

        int confirm = JOptionPane.showConfirmDialog(this,
                "¿Seguro que deseas eliminar esta tarea?", "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            if (taskDAO.deleteTarea(selectedTaskId)) {
                JOptionPane.showMessageDialog(this, "Tarea eliminada.");
                loadTasks();
            } else {
                JOptionPane.showMessageDialog(this, "Error al eliminar la tarea.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * Limpia el formulario y desactiva los botones de edición.
     */
    private void clearForm() {
        titleField.setText("");
        descriptionField.setText("");
        completedCheckBox.setSelected(false);
        selectedTaskId = null;
        updateButton.setEnabled(false);
        deleteButton.setEnabled(false);
    }

}
