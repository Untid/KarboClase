package ejercicio24;

import javax.swing.*;
import javax.swing.plaf.ColorUIResource;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {
    private JPanel miPanel;
    private JTextArea mostrarNumero;
    private JButton boton;

    public Ventana() {

        setTitle("Ejercicio 24");
        setSize(350, 200);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        miPanel = new JPanel();
        miPanel.setLayout(new BorderLayout());
        add(miPanel);


        Componentes();

        setVisible(true);
    }

    public void Componentes() {

        mostrarNumero = new JTextArea("0");
        boton = new JButton("Número Aleatorio");


        mostrarNumero.setFont(new Font("Arial",Font.BOLD,32));
        mostrarNumero.setForeground(ColorUIResource.BLACK);


        boton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int numero = Random();
                String numTexto = String.valueOf(numero);
                mostrarNumero.setText(numTexto);
            }
        });


        miPanel.add(mostrarNumero, BorderLayout.CENTER);
        miPanel.add(boton, BorderLayout.SOUTH);
    }

    public int Random() {

        double aleatorioD = ((Math.random() * 100) + 1);
        int aleatorio = (int) aleatorioD;
        System.out.println(aleatorio);

        return aleatorio;
    }
}

