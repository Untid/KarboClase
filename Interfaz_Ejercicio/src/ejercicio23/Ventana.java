package ejercicio23;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Calendar;

public class Ventana extends JFrame {
    private JLabel etiqueta;
    private JTextField campoTexto;
    private JButton boton;
    private JPanel miPanel;

    public Ventana() {
        setTitle("Ejercicio 23");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        miPanel = new JPanel();
        miPanel.setLayout(null);
        add(miPanel);

        Componentes();

        setVisible(true);
    }

    public void Componentes() {

        // ---------------------------------------Etiqueta------------------------------
        etiqueta = new JLabel("Adivina un número entre el 1 y el 100");
        etiqueta.setBounds(30, 20, 700, 30);
        miPanel.add(etiqueta);


        // ---------------------------------------Campo Texto---------------------------
        campoTexto = new JTextField();
        campoTexto.setBounds(250, 20, 50, 30);
        // tring texto = campoTexto.getText();
        miPanel.add(campoTexto);


        // ---------------------------------------Boton--------------------------------
        boton = new JButton("Adivina !");
        boton.setBounds(320, 20, 80, 30);
        int misterio = Random();
        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numUsuario = Integer.parseInt(campoTexto.getText());
                int intentos = 0;

                if (numUsuario == misterio) {
                    etiqueta.setText("Correcto! Has adivinado!");
                    JOptionPane.showMessageDialog(null, "¡Correcto! Lo lograste en " + intentos + " intentos");
                } else if (numUsuario > misterio) {
                    etiqueta.setText("Demasiado alto. Intenta de nuevo.");
                    intentos += intentos;
                } else if (numUsuario < misterio) {
                    etiqueta.setText("Demasiado bajo. Intenta de nuevo.");
                    intentos += intentos;
                }
            }
        });
        miPanel.add(boton);
    }

    public int Random() {

        double aleatorioD = ((Math.random() * 100) + 1);
        int aleatorio = (int) aleatorioD;
        System.out.println(aleatorio);

        return aleatorio;
    }
}
