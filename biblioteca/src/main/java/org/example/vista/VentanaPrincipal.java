package org.example.vista;

import org.example.vista.panel.*;

import javax.swing.*;

public class VentanaPrincipal extends JFrame {
    public VentanaPrincipal() {
        configurarVentana();
        initComponents();
    }

    private void configurarVentana() {
        setTitle("Sistema de Gestión de Biblioteca");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }

    private void initComponents() {
        JTabbedPane tabbedPane = new JTabbedPane();

        // Cada panel se encarga de su propia lógica
        tabbedPane.addTab("📚 Libros", new PanelGestionLibros());
        tabbedPane.addTab("👥 Socios", new PanelGestionSocios());
        tabbedPane.addTab("📥 Préstamos", new PanelPrestamos());
        tabbedPane.addTab("🔍 Consultas", new PanelConsultas());

        add(tabbedPane);
    }
}

