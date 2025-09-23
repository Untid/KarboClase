import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Realizar un programa que llame al proceso tasklist y a partir de los datos recibidos mediante la llamada a un
 * proceso que me devuelva cada línea como una tupla de datos con la que crear un objeto de los mismos y a partir de ellos realizar
 * una lista o mapa ordenada por la cantidad de memoria usada.
 * * Pista: hacer un override del metodo compareTo
 */

/**
 * @author Javier
 */
public class Main {
    public static void main(String[] args) {

        Process process;
        BufferedReader reader;

        try {
            process = Runtime.getRuntime().exec("tasklist"); // Este comando solo funciona en windows
            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

            // Para saltar la cabecera que son:
            reader.readLine(); // Los Títulos
            reader.readLine(); // Los separadores

            String linea; // Cada línea leída

            List<Proceso> procesos = new ArrayList<>(); // Lista de procesos recogidos

            while ((linea = reader.readLine()) != null) { // Bucle para leer cada línea hasta que de nulo
                if (linea.trim().isEmpty()) continue;  // Descarto las lineas vacias
                String[] parts = linea.trim().split("\\s+"); // Divido la linea en trozos separados por espacios
                if (parts.length < 5) continue;  // Si no tiene datos suficientes, la ignoro

                //-------------------------------Buscar el PID---------------------------------------------------

                int pidIndex = -1; // Posición de la columna PID
                for (int i = 1; i < parts.length; i++) {
                    if (parts[i].matches("\\d+")) { // Primer número encontrado = PID
                        pidIndex = i;
                        break;
                    }
                }
                if (pidIndex == -1) continue; // Si no hay PID, no sirve la línea
                int pid = Integer.parseInt(parts[pidIndex]); // Convertimos el PID de texto a número para poder manipularlo.

                //------------------------Sacar la memoria----------------------------------------------------------

                String memoriaStr = parts[parts.length - 2]
                        .replace(".", "") // Quita puntos de miles
                        .replace(",", ""); // Quita comas si las hubiera
                long memoria = Long.parseLong(memoriaStr); // Convertimos a número
                //--------------------------------Reconstruir el nombre--------------------------------------------------

                // El nombre es todo lo que está antes del PID
                StringBuilder nombreBuilder = new StringBuilder();
                for (int i = 0; i < pidIndex; i++) {
                    nombreBuilder.append(parts[i]).append(" ");
                }
                String nombre = nombreBuilder.toString().trim();

                //-----------------------Crear objeto y añadirlo a la lista-----------------------------------------------------------

                procesos.add(new Proceso(nombre, pid, memoria));
            }

            //-------------------------Ordenar e imprimir---------------------------------------------------------------
            Collections.sort(procesos); // Usa el orden natural definido por el método sobrescrito compareTo

            for (Proceso p : procesos) { // Recorremos la lista
                System.out.println(p); // Imprimimos la lista, gracias a la llamada de toString implícito en Proceso
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}