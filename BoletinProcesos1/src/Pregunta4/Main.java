package Pregunta4;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);

        System.out.println("Introduce DNI: ");
        String dni = sc.nextLine().trim();

        System.out.println("Introduce IBAN: ");
        String iban = sc.nextLine().trim();



        //-------------------------------------- DNI ---------------------------------------------------------------


        ProcessBuilder pbDNI = new ProcessBuilder("java",
                "-cp",
                "C:\\Users\\Javier\\IdeaProjects\\BoletinProcesos1\\out\\production\\BoletinProcesos1",
                "Pregunta4.DNI");

        Process miProcesoDNI = pbDNI.start();

        PrintWriter escritorDNI = new PrintWriter(miProcesoDNI.getOutputStream(),true);
        BufferedReader lectorDNI = new BufferedReader(new InputStreamReader(miProcesoDNI.getInputStream()));



        escritorDNI.println(dni);
        String resultadoDNI = lectorDNI.readLine();
        if (resultadoDNI.startsWith("-1")){
            System.out.println("DNI incorrecto. Valor correcto: "+resultadoDNI.replace("-1",""));
            miProcesoDNI.destroy();
            System.exit(-1);
        }
        miProcesoDNI.destroy();



        //-------------------------------------- IBAN ---------------------------------------------------------------
        ProcessBuilder pbIBAN = new ProcessBuilder("java",
                "-cp",
                "C:\\Users\\Javier\\IdeaProjects\\BoletinProcesos1\\out\\production\\BoletinProcesos1",
                "Pregunta4.IBAN");

        Process miProcesoIBAN = pbIBAN.start();

        PrintWriter escritorIBAN = new PrintWriter(miProcesoIBAN.getOutputStream(),true);
        BufferedReader lectorIBAN = new BufferedReader(new InputStreamReader(miProcesoIBAN.getInputStream()));


        escritorIBAN.println(iban);
        String resultadoIBAN = lectorIBAN.readLine();

        if (resultadoIBAN.startsWith("-1")){
            System.out.println("IBAN Incorrecto. Valor correcto: "+resultadoIBAN.replace("-1",""));
            miProcesoIBAN.destroy();
            System.exit(-1);
        }
        miProcesoIBAN.destroy();

        System.out.println("DNI e IBAN correctos.");
        sc.close();
    }
}