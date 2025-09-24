package Pregunta2;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.Scanner;

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        String linea;

        System.out.println("Escribe 'fin' para terminar");

        while (true){
            linea = sc.nextLine();

            if (linea.equalsIgnoreCase("fin")){
                System.out.println("Hasta la vista!");

                break;
            }

            try{

                // Llamo al proceso padre
                ProcessBuilder miPb = new ProcessBuilder("C:\\Users\\Javier\\.jdks\\openjdk-24.0.2+12-54","-cp",".","Aleatorio");
                Process miProceso = miPb.start();

                // Leo la salida del proceso hijo
                BufferedReader reader = new BufferedReader(new InputStreamReader(miProceso.getInputStream()));
                String numero = reader.readLine();
                System.out.println(numero);


                miProceso.waitFor();
            }catch (Exception e ){
                System.out.println(e.getMessage());
            }
            sc.close();
        }
    }
}

