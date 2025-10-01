package ejercicio26;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.*;
import java.lang.management.BufferPoolMXBean;
import java.util.ArrayList;
import java.util.List;

public class Ventana extends JFrame {
    private JPanel miPanel;
    private JPanel botonPanel;
    private JButton botonAbrir;
    private JButton botonGuardar;
    private JTextArea campoTexto;
    private JFileChooser seleccionador;

    public Ventana() {

        setTitle("Ejercicio 26");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);

        miPanel = new JPanel();
        miPanel.setLayout(new BorderLayout());
        add(miPanel);

        Componentes();

        setVisible(true);
    }

    public void Componentes() {

        botonPanel = new JPanel(new FlowLayout());
        campoTexto = new JTextArea();
        botonAbrir = new JButton("Abrir");
        botonGuardar = new JButton("Guardar");
        seleccionador = new JFileChooser();


        miPanel.add(campoTexto, BorderLayout.CENTER);


        botonAbrir.setPreferredSize(new Dimension(100, 30));
        botonAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionador = new JFileChooser();

                int seleccion = seleccionador.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = seleccionador.getSelectedFile();

                    try {
                        List<String> miLista = new ArrayList<>();
                        miLista = leer(file);
                        for (String i : miLista) {
                            campoTexto.append(i + "\n");
                        }
                    } catch (Exception a) {
                        System.out.println("Error: " + a.getMessage());
                    }
                }
            }
        });
        botonPanel.add(botonAbrir);

        botonGuardar.setPreferredSize(new Dimension(100, 30));
        botonGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                seleccionador = new JFileChooser();

                int seleccion = seleccionador.showOpenDialog(null);
                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = seleccionador.getSelectedFile();
                    String texto = campoTexto.getText();

                    escribir(texto,file);
                }
            }
        });
        botonPanel.add(botonGuardar);
        miPanel.add(botonPanel, BorderLayout.SOUTH);
    }

    public List<String> leer(File Path) {
        List<String> words = new ArrayList<>();

        try {
            FileReader entrada = new FileReader(Path);
            BufferedReader mibuffer = new BufferedReader(entrada);
            String linea = "";

            while (linea != null) {
                linea = mibuffer.readLine();

                if (linea != null) {
                    words.add(linea);
                    System.out.println(linea);
                }
            }
        } catch (IOException e) {
            System.out.println("No se ha encontrado el archivo");
        }
        return words;
    }

    public void escribir(String texto, File Path) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Path, true))) {
            bw.write("\n"+texto);
            bw.newLine();
            JOptionPane.showMessageDialog(null,"Texto escrito con éxito");
        } catch (IOException a) {
            System.out.println("Error: " + a.getMessage());
        }


    }
}