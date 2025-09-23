package Pregunta1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;


public class Ejecuta {
    private ProcessBuilder construirProceso;
    private Process miProceso;
    private String comando;
    private String[] opciones;


    public Ejecuta(String comando, String[] opciones) {
        this.comando = comando;
        this.opciones = opciones;
    }

    public void ejecutar() {
        try {
            String[] comandoCompleto = new String[1 + opciones.length];
            comandoCompleto[0] = comando;
            System.arraycopy(opciones, 0, comandoCompleto, 1, opciones.length);

            construirProceso = new ProcessBuilder(comandoCompleto);
            miProceso = construirProceso.start();

            // -------- Para poder leer lo que nos daría por consola el proceso ---------------
            BufferedReader salida = new BufferedReader(new InputStreamReader(miProceso.getInputStream()));
            BufferedReader errores = new BufferedReader(new InputStreamReader(miProceso.getErrorStream()));

            String linea;
            while ((linea = salida.readLine()) != null) {
                System.out.println(linea);
            }
            while ((linea = errores.readLine()) != null) {
                System.out.println(linea);
            }

            //------------------------------------------------------------------------------------------------

            int exitProceso = miProceso.waitFor(); // Bloqueo al proceso padre hasta que el proceso hijo termina

            if (exitProceso == 0) {

                System.out.println("Ejecución correcta. ExitProceso : " + exitProceso);
            } else {

                System.out.println("Error en la ejecución. ExitProceso : " + exitProceso);
            }

        } catch (IOException | InterruptedException e) {
            System.err.println("Error al ejecutar el comando: " + e.getMessage());
        }
    }
}