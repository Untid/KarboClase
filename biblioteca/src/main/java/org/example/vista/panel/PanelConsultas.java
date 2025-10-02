package org.example.vista.panel;

import org.example.dao.PrestamoDAO;
import org.example.dao.SocioDAO;
import org.example.modelo.Libro;
import org.example.modelo.Socio;

import javax.swing.*;
import java.awt.*;
import java.util.List;
import java.sql.SQLException;

    public class PanelConsultas extends JPanel {
        private JTextArea txtResultados;
        private JTextField txtIdSocio;
        private PrestamoDAO prestamoDAO = new PrestamoDAO();
        private SocioDAO socioDAO = new SocioDAO();

        public PanelConsultas() {
            setLayout(new BorderLayout());
            initComponents();
        }

        private void initComponents() {
            // Botones de consultas
            JPanel panelBotones = new JPanel(new GridLayout(5, 1, 5, 5));
            panelBotones.setBorder(BorderFactory.createTitledBorder("Consultas Disponibles"));

            JButton btnPrestados = new JButton("1. Libros prestados actualmente");
            JButton btnVencidos = new JButton("2. Libros vencidos");
            JButton btnSociosVencidos = new JButton("3. Socios con libros vencidos");
            JButton btnNumPrestamos = new JButton("4. Número de préstamos de un socio");

            panelBotones.add(btnPrestados);
            panelBotones.add(btnVencidos);
            panelBotones.add(btnSociosVencidos);
            panelBotones.add(btnNumPrestamos);

            // Campo para ID de socio
            JPanel panelId = new JPanel(new FlowLayout());
            panelId.add(new JLabel("ID del socio:"));
            txtIdSocio = new JTextField(10);
            panelId.add(txtIdSocio);

            // Área de resultados
            txtResultados = new JTextArea();
            txtResultados.setEditable(false);
            JScrollPane scroll = new JScrollPane(txtResultados);

            // Eventos
            btnPrestados.addActionListener(e -> mostrarLibrosPrestados());
            btnVencidos.addActionListener(e -> mostrarLibrosVencidos());
            btnSociosVencidos.addActionListener(e -> mostrarSociosConLibrosVencidos());
            btnNumPrestamos.addActionListener(e -> mostrarNumeroPrestamosSocio());

            add(panelBotones, BorderLayout.NORTH);
            add(panelId, BorderLayout.CENTER);
            add(scroll, BorderLayout.SOUTH);
        }

        private void mostrarLibrosPrestados() {
            try {
                // Esta consulta devuelve préstamos, no libros directamente
                // Para simplicidad, mostramos mensaje
                var prestamos = prestamoDAO.librosPrestadosActualmente();
                StringBuilder sb = new StringBuilder("📚 Libros prestados actualmente:\n\n");
                if (prestamos.isEmpty()) {
                    sb.append("No hay préstamos activos.");
                } else {
                    sb.append("Total de préstamos activos: ").append(prestamos.size()).append("\n");
                    // Aquí podrías expandir con más detalles si lo deseas
                }
                txtResultados.setText(sb.toString());
            } catch (SQLException ex) {
                txtResultados.setText("Error: " + ex.getMessage());
            }
        }

        private void mostrarLibrosVencidos() {
            try {
                List<Libro> libros = prestamoDAO.librosVencidos();
                StringBuilder sb = new StringBuilder("⚠️ Libros con préstamos vencidos:\n\n");
                if (libros.isEmpty()) {
                    sb.append("No hay libros vencidos.");
                } else {
                    for (Libro l : libros) {
                        sb.append("• ").append(l.getTitulo()).append(" (ID: ").append(l.getId()).append(")\n");
                    }
                }
                txtResultados.setText(sb.toString());
            } catch (SQLException ex) {
                txtResultados.setText("Error: " + ex.getMessage());
            }
        }

        private void mostrarSociosConLibrosVencidos() {
            try {
                List<Socio> socios = prestamoDAO.sociosConLibrosVencidos();
                StringBuilder sb = new StringBuilder("⚠️ Socios con libros vencidos:\n\n");
                if (socios.isEmpty()) {
                    sb.append("No hay socios con libros vencidos.");
                } else {
                    for (Socio s : socios) {
                        sb.append("• ").append(s.getNombre()).append(" ").append(s.getApellidos())
                                .append(" (ID: ").append(s.getId()).append(")\n");
                    }
                }
                txtResultados.setText(sb.toString());
            } catch (SQLException ex) {
                txtResultados.setText("Error: " + ex.getMessage());
            }
        }

        private void mostrarNumeroPrestamosSocio() {
            try {
                String idStr = txtIdSocio.getText().trim();
                if (idStr.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Ingrese el ID del socio");
                    return;
                }
                int idSocio = Integer.parseInt(idStr);
                int num = prestamoDAO.numeroLibrosPrestadosPorSocio(idSocio);
                // Verificar si el socio existe
                Socio s = socioDAO.obtenerPorId(idSocio);
                if (s == null) {
                    txtResultados.setText("❌ Socio con ID " + idSocio + " no existe.");
                } else {
                    txtResultados.setText("📊 El socio " + s.getNombre() + " " + s.getApellidos() +
                            " tiene " + num + " libro(s) prestado(s) actualmente.");
                }
            } catch (NumberFormatException ex) {
                txtResultados.setText("❌ El ID debe ser un número entero.");
            } catch (SQLException ex) {
                txtResultados.setText("Error: " + ex.getMessage());
            }
        }
    }