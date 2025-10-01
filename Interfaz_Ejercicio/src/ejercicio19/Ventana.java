package ejercicio19;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLOutput;

public class Ventana extends JFrame {
    private JTextField campoNumero1;
    private JLabel areaResultado;
    private JLabel etiquetaNum1;
    private JButton botonSaludo;
    private JPanel miPanel;

    public Ventana(){
        setTitle("Calculadora");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        miPanel = new JPanel();

        //Crear y configurar el panel
        miPanel.setLayout(null);
        add(miPanel);

        Pherb();
    }

    private void Pherb(){
        etiquetaNum1 = new JLabel("Introduce tu nombre: ");
        etiquetaNum1.setBounds(50,30,200,25);
        miPanel.add(etiquetaNum1);

        campoNumero1 = new JTextField();
        campoNumero1.setBounds(190,30,150,20);
        miPanel.add(campoNumero1);

        botonSaludo = new JButton("Saludar");
        botonSaludo.setBounds(50,80,100,25);
        miPanel.add(botonSaludo);
        botonSaludo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                saludar(e);
            }
        });

        areaResultado = new JLabel();
        areaResultado.setBounds(50,125,200,25);
        miPanel.add(areaResultado);
    }
    public void saludar(ActionEvent e){
        try{
            String cosa = "Saludos ";
            areaResultado.setText(cosa+campoNumero1.getText());

        }catch (Exception x){
            System.out.println("Ha saltado un error "+x.getMessage());

        }
    }
}
