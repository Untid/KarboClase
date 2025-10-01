package Ejercicio21;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Ventana extends JFrame {
    private JPanel miPanel;
    private JTextField cajaTexto;
    private JButton BotonCombo;
    private JLabel resultado;
    private JComboBox<String> comboBox;


    public Ventana() {
        setTitle("Ejercicio 21");
        setSize(500, 500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setVisible(true);

        miPanel = new JPanel();
        miPanel.setLayout(null);

        CajaCombo();
        CajaTexto();
        CajaResultado();
        add(miPanel);
    }
/* ----------Caja texto + Caja resultado--------- */
    private void CajaTexto() {
        cajaTexto = new JTextField();
        cajaTexto.setBounds(150,250,200,30);
        miPanel.add(cajaTexto);

    }

    private void CajaResultado(){
        resultado = new JLabel();
        resultado.setBounds(150,300,200,30);
        miPanel.add(resultado);
    }
    /* ----------ComboBox Movidas--------- */
    private void CajaCombo(){
        String[] opciones = {"Raíz","Cubo"};
        comboBox = new JComboBox<>(opciones);
        Operacion miOpcion = new Operacion();
        BotonCombo = new JButton("Seleccionar operación");

        comboBox.setBounds(150,50,200,30);
        BotonCombo.setBounds(150,150,200,30);

        BotonCombo.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                double resultadoOperacion = 0;
                String selector = (String) comboBox.getSelectedItem();
                String input = cajaTexto.getText();

                try {
                    double numero = Double.parseDouble(input);

                    if (selector.equalsIgnoreCase("Raíz")) {
                        resultadoOperacion = miOpcion.raizCuadrada(numero);
                    } else if (selector.equalsIgnoreCase("Cubo")) {
                        resultadoOperacion = miOpcion.Cubo(numero);
                    }

                    resultado.setText("Resultado: " + resultadoOperacion);
                } catch (NumberFormatException ex) {
                    resultado.setText("Por favor, ingresa un número válido.");
                }
            }
        });

        miPanel.add(comboBox);
        miPanel.add(BotonCombo);
    }
}
