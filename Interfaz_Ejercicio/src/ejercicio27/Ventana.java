package ejercicio27;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

public class Ventana extends JFrame {
    private JPanel miPanel;
    private JPanel botones;
    private JButton btnAbrir;
    private JButton btnEliminar;
    private JLabel miEtiqueta;
    private JFileChooser escogedor;

    public Ventana() {
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setTitle("Ejercicio 27");
        setSize(800, 700);
        setLocationRelativeTo(null);


        miPanel = new JPanel();
        miPanel.setLayout(new BorderLayout());
        add(miPanel);


        Componentes();

        setVisible(true);
    }

    public void Componentes() {
        // ------------------------------------------------------------------------------------------------------------
        miEtiqueta = new JLabel("No se ha seleccionado ningún archivo.");

        miPanel.add(miEtiqueta, BorderLayout.CENTER);

// -------------------------------------------------------------------------------------------------------------------
        botones = new JPanel();
        btnAbrir = new JButton("Seleccionar archivo");


        escogedor = new JFileChooser();

        btnAbrir.setSize(300, 300);

// ----------------------------------------------BOTON ABRIR-----------------------------------------------------------
        btnAbrir.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {


                int seleccion = escogedor.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = escogedor.getSelectedFile();

                    try {
                        if (file.isFile()){
                            miEtiqueta.setText("Archivo seleccionado: "+file.getName());
                        }
                    } catch (Exception a) {
                        System.out.println("Error: " + a.getMessage());
                    }
                }
            }
        });
        botones.add(btnAbrir);
// --------------------------------------------------------------------------------------------------------------------

        btnEliminar = new JButton("Eliminar archivo");

        btnEliminar.setSize(300, 300);
// ---------------------------------------------BOTON ELIMINAR---------------------------------------------------------
        btnEliminar.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int seleccion = escogedor.showOpenDialog(null);

                if (seleccion == JFileChooser.APPROVE_OPTION) {
                    File file = escogedor.getSelectedFile();

                    try {
                        if (file.isFile()){
                           int cosa = JOptionPane.showConfirmDialog(null,"¿Estas seguro de eliminar el archivo "+file.getName()+" ?");
                            if (cosa==0){
                                file.delete();
                                miEtiqueta.setText("Archivo eliminado: "+file.getName());
                            }
                            else {
                                miEtiqueta.setText("El archivo " + file.getName()+" no ha sido eliminado");
                            }
                        }
                    } catch (Exception a) {
                        System.out.println("Error: " + a.getMessage());
                    }
                }
            }
        });
// -------------------------------------------------------------------------------------------------------------------
        botones.add(btnEliminar);

        miPanel.add(botones, BorderLayout.NORTH);
    }
}
