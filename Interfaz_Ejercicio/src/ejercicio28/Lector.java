package ejercicio28;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Lector {
    public static List<String> leer(){
        String Path = "C:\\Users\\Usuario\\IdeaProjects\\Interfaz_Ejercicio\\src\\contactos.txt";
            List<String> contactos = new ArrayList<>();

            try {
                FileReader entrada = new FileReader(Path);
                BufferedReader mibuffer = new BufferedReader(entrada);
                String linea = "";

                while (linea != null) {
                    linea = mibuffer.readLine();

                    if (linea != null) {
                        contactos.add(linea);
                    }
                }
            } catch (IOException e) {
                System.out.println("No se ha encontrado el archivo");
            }
            return contactos;
        }
    }
