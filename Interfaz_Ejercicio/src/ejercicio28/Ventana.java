package ejercicio28;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;

public class Ventana extends JFrame {
    private JPanel miPanel;

    private JLabel etiquetaNombre;
    private JLabel etiquetaTlf;
    private JLabel etiquetaEmail;

    private JTextField campoNombre;
    private JTextField campoTlf;
    private JTextField campoEmail;

    private JButton btnGuardar;
    private JButton btnCargar;

    private JTextArea areaTexto;


    public Ventana() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Ejercicio 28");
        setSize(800, 700);
        setLocationRelativeTo(null);


        miPanel = new JPanel();
        miPanel.setLayout(null);

        cargarLabel();
        cargarTextField();
        cargarBoton();
        cargarAreaTexto();

        add(miPanel);
        setVisible(true);
    }

    public void cargarLabel() {
        etiquetaNombre = new JLabel("Nombre");
        etiquetaTlf = new JLabel("Teléfono");
        etiquetaEmail = new JLabel("Email");

        etiquetaNombre.setBounds(10, 10, 50, 25);
        etiquetaTlf.setBounds(10, 50, 50, 25);
        etiquetaEmail.setBounds(10, 90, 50, 25);

        miPanel.add(etiquetaNombre);
        miPanel.add(etiquetaTlf);
        miPanel.add(etiquetaEmail);
    }

    public void cargarTextField() {
        campoNombre = new JTextField();
        campoTlf = new JTextField();
        campoEmail = new JTextField();

        campoNombre.setBounds(70, 10, 200, 25);
        campoTlf.setBounds(70, 50, 200, 25);
        campoEmail.setBounds(70, 90, 200, 25);

        miPanel.add(campoNombre);
        miPanel.add(campoTlf);
        miPanel.add(campoEmail);
    }

    public void cargarBoton() {
        btnGuardar = new JButton("Guardar contacto");
        btnCargar = new JButton("Cargar contacto");

        btnGuardar.setBounds(300, 10, 200, 25);
        btnCargar.setBounds(300, 50, 200, 25);

        btnGuardar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                Escritor.escribir(campoNombre.getText(),campoTlf.getText(),campoEmail.getText());
            }
        });

        btnCargar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                List<String> contactos = new ArrayList<>();
                contactos = Lector.leer();

                for (String i : contactos){
                    areaTexto.append(i+"\n");
                }

            }
        });
        miPanel.add(btnGuardar);
        miPanel.add(btnCargar);

    }

    public void cargarAreaTexto() {
        areaTexto = new JTextArea();

        areaTexto.setBounds(5, 150, 500, 500);

        miPanel.add(areaTexto);
    }
}
