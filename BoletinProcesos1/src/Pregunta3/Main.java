package Pregunta3;

import java.io.*;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws IOException {
        ProcessBuilder pb = new ProcessBuilder( "java",
                "-cp",
                "C:\\Users\\Javier\\IdeaProjects\\BoletinProcesos1\\out\\production\\BoletinProcesos1",
                "Pregunta3.Mayusculas");
        Process miProceso = pb.start();


        OutputStream outputStream = miProceso.getOutputStream();
        InputStream inputStream = miProceso.getInputStream();

        Scanner sc = new Scanner(System.in);

        BufferedReader lector = new BufferedReader(new InputStreamReader(inputStream));
        PrintWriter escritor = new PrintWriter(outputStream,true);


        System.out.println("Escribe algo: ");

        while(sc.hasNextLine()){
            String linea = sc.nextLine();
            escritor.println(linea);

            String respuesta = lector.readLine();
            System.out.println(respuesta);
        }

        miProceso.destroy();
        sc.close();
    }
}
