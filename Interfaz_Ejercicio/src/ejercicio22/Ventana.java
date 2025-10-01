package ejercicio22;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Ventana extends JFrame {
    JPanel miPanel;
    JLabel etiqueta;
    JLabel resultado;
    JTextField campoTexto;
    JButton miBoton;

    public Ventana(){
        setTitle("Ejercicio 22");
        setSize(500,500);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        miPanel = new JPanel();

        miPanel.setLayout(null);
        add(miPanel);

        Componentes();

        setVisible(true);
    }

    public void Componentes(){

        etiqueta = new JLabel();
        resultado = new JLabel();
        etiqueta.setText("Introduzca una ruta (Incluyendo el nombre del archivo)");

        resultado.setBounds(100,120,200,30);
        etiqueta.setBounds(100,20,200,30);
        miPanel.add(etiqueta);
        miPanel.add(resultado);


        campoTexto = new JTextField();
        campoTexto.setBounds(100,50,200,30);
        miPanel.add(campoTexto);

        miBoton = new JButton("Crear archivo");
        miBoton.setBounds(100,80,200,30);
        miBoton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                File miFile = new File(campoTexto.getText());
                try{
                    miFile.createNewFile();
                    resultado.setText("Su archivo ha sido creado");
                }catch (Exception x){
                    resultado.setText("Error: "+ x.getMessage());
                    System.out.println("Error: "+ x.getMessage());
                }
            }
        });
        miPanel.add(miBoton);
    }
}