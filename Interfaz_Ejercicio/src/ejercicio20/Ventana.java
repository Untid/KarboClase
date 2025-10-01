package ejercicio20;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {

    private JTextField campoNumero1;
    private JTextField campoNumero2;
    private JLabel areaResultado;
    private JLabel etiquetaNum1;
    private JLabel etiquetaNum2;
    private JLabel etiquetaResultado;
    private JButton botonSumar;
    private JButton botonRestar;
    private JButton botonMultiplicar;
    private JButton botonDividir;
    private JPanel miPanel;

    public Ventana() {
        setTitle("Calculadora");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        miPanel = new JPanel();

        miPanel.setLayout(null);
        add(miPanel);

        etiquetas();
        textField();
        Botones();
    }

    public void etiquetas() {
        etiquetaNum1 = new JLabel("Número 1");
        etiquetaNum1.setBounds(20, 20, 80, 30);
        miPanel.add(etiquetaNum1);
//------------------------------------------------------------------
        etiquetaNum2 = new JLabel("Número 2");
        etiquetaNum2.setBounds(20, 60, 80, 30);
        miPanel.add(etiquetaNum2);
//------------------------------------------------------------------
        etiquetaResultado = new JLabel("Resultado: ");
        etiquetaResultado.setBounds(20, 140, 80, 30);
        miPanel.add(etiquetaResultado);
//------------------------------------------------------------------
        areaResultado = new JLabel();
        areaResultado.setBounds(100, 140, 100, 30);
        miPanel.add(areaResultado);

    }

    public void textField() {
        campoNumero1 = new JTextField();
        campoNumero1.setBounds(100, 20, 100, 30);
        miPanel.add(campoNumero1);
//------------------------------------------------------------------
        campoNumero2 = new JTextField();
        campoNumero2.setBounds(100, 60, 100, 30);
        miPanel.add(campoNumero2);
    }

    public void Botones() {
//--------------------------SUMAR----------------------------------------
        botonSumar = new JButton("Sumar");
        botonSumar.setBounds(220, 20, 100, 30);
        miPanel.add(botonSumar);

        botonSumar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                sumar(e);
            }
        });
//-----------------------------RESTAR-------------------------------------
        botonRestar = new JButton("Restar");
        botonRestar.setBounds(220, 60, 100, 30);
        miPanel.add(botonRestar);

        botonRestar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                restar(e);
            }
        });
//------------------------------MULTIPLICAR------------------------------------
        botonMultiplicar = new JButton("Multiplicar");
        botonMultiplicar.setBounds(220, 100, 100, 30);
        miPanel.add(botonMultiplicar);

        botonMultiplicar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                multiplicar(e);
            }
        });
//---------------------------------DIVIDIR---------------------------------
        botonDividir = new JButton("Dividir");
        botonDividir.setBounds(220, 140, 100, 30);
        miPanel.add(botonDividir);

        botonDividir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dividir(e);
            }
        });
    }
    //------------------------Acción Sumar------------------------------------------
    public void sumar(ActionEvent e) {
        try {

            double num1 = Double.parseDouble(campoNumero1.getText());
            double num2 = Double.parseDouble(campoNumero2.getText());

            System.out.println(num1 + " " + num2);

            double suma = num1 + num2;

            areaResultado.setText(String.valueOf(suma));

        } catch (Exception x) {
            System.out.println("Ha saltado un error " + x.getCause().getMessage());
        }
    }
    //-----------------------------Acción Restar-------------------------------------
    public void restar(ActionEvent e) {
        try {

            double num1 = Double.parseDouble(campoNumero1.getText());
            double num2 = Double.parseDouble(campoNumero2.getText());

            System.out.println(num1 + " " + num2);

            double resta = num1 - num2;

            areaResultado.setText(String.valueOf(resta));

        } catch (Exception x) {
            System.out.println("Ha saltado un error " + x.getCause().getMessage());
        }
    }
    //----------------------------Acción Multiplicar--------------------------------------
        public void multiplicar(ActionEvent e) {
            try {

                double num1 = Double.parseDouble(campoNumero1.getText());
                double num2 = Double.parseDouble(campoNumero2.getText());

                System.out.println(num1 + " " + num2);

                double multiplicacion = num1 * num2;

                areaResultado.setText(String.valueOf(multiplicacion));

            } catch (Exception x) {
                System.out.println("Ha saltado un error " + x.getCause().getMessage());
            }
        }
    //-------------------------------Acción Dividir-----------------------------------
    public void dividir(ActionEvent e) {
        try {

            double num1 = Double.parseDouble(campoNumero1.getText());
            double num2 = Double.parseDouble(campoNumero2.getText());

            System.out.println(num1 + " " + num2);

            double division = num1 / num2;

            areaResultado.setText(String.valueOf(division));

        } catch (Exception x) {
            System.out.println("Ha saltado un error " + x.getCause().getMessage());
        }
    }
}
