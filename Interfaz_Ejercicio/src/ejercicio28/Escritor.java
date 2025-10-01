package ejercicio28;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Escritor {
    public static void escribir(String nombre, String tlf, String email) {

        String ruta = "C:\\Users\\Usuario\\IdeaProjects\\Interfaz_Ejercicio\\src\\contactos.txt";

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(ruta, true))) {
            bw.write(nombre);
            bw.write(" - ");

            bw.write(tlf);
            bw.write(" - ");

            bw.write(email);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}
