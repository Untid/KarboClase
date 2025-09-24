package Pregunta2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String linea;

        System.out.println("Escribe 'fin' para terminar");

        while (true) {
            linea = sc.nextLine();

            if (linea.equalsIgnoreCase("fin")) {
                System.out.println("Hasta la vista!");

                break;
            }

            try {

                // Llamo al proceso padre
                ProcessBuilder miPb = new ProcessBuilder(
                        "java",
                        "-cp",
                        "C:\\Users\\Javier\\IdeaProjects\\BoletinProcesos1\\out\\production\\BoletinProcesos1",
                        "Pregunta2.Aleatorio"
                );

                Process miProceso = miPb.start();

                // Leo la salida del proceso hijo
                BufferedReader reader = new BufferedReader(new InputStreamReader(miProceso.getInputStream()));
                String numero = reader.readLine();
                System.out.println(numero);


                miProceso.waitFor();
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }

        }
        sc.close();
    }
}

