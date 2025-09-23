import java.io.*;

public class RandomAccessFileEjemplo {

    private static final String ARCHIVO = "empleados.dat";
    private static final int TAMANO_REGISTRO = 84; // 80 (nombre) + 4 (edad)
    private static final int MAX_NOMBRE = 40;

    public static void main(String[] args) {
        // Escribir algunos registros
        escribirEmpleado(0, "Ana López", 30);
        escribirEmpleado(1, "Carlos Ruiz", 25);
        escribirEmpleado(2, "Lucía Gómez", 35);

        // Leer los registros
        System.out.println("\n--- Lectura de empleados ---");
        leerEmpleado(0);
        leerEmpleado(1);
        leerEmpleado(2);

        // Modificar un empleado
        System.out.println("\n--- Modificando empleado en posición 1 ---");
        escribirEmpleado(1, "Carlos Ruiz Jr.", 26);

        // Volver a leer
        leerEmpleado(1);
    }

    /**
     * Escribe un empleado en una posición específica del archivo.
     */
    public static void escribirEmpleado(int posicion, String nombre, int edad) {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "rw")) {
            // Calcular la posición en bytes
            long posicionBytes = posicion * TAMANO_REGISTRO;
            raf.seek(posicionBytes); // Mover el puntero

            // Escribir nombre (ajustado a 40 caracteres)
            StringBuilder nombreAjustado = new StringBuilder(nombre);
            nombreAjustado.setLength(MAX_NOMBRE); // Rellenar con espacios si es necesario
            raf.writeChars(nombreAjustado.toString());

            // Escribir edad
            raf.writeInt(edad);

            System.out.println("Empleado '" + nombre + "' escrito en posición " + posicion);
        } catch (IOException e) {
            System.err.println("Error al escribir en el archivo: " + e.getMessage());
        }
    }

    /**
     * Lee un empleado desde una posición específica.
     */
    public static void leerEmpleado(int posicion) {
        try (RandomAccessFile raf = new RandomAccessFile(ARCHIVO, "r")) {
            long posicionBytes = posicion * TAMANO_REGISTRO;
            if (raf.length() < posicionBytes + TAMANO_REGISTRO) {
                System.out.println("No hay registro en la posición " + posicion);
                return;
            }

            raf.seek(posicionBytes);

            // Leer nombre (40 caracteres = 80 bytes)
            StringBuilder nombre = new StringBuilder();
            for (int i = 0; i < MAX_NOMBRE; i++) {
                char c = raf.readChar();
                if (c != ' ') { // Ignorar espacios iniciales
                    nombre.append(c);
                }
            }

            // Leer edad
            int edad = raf.readInt();

            System.out.println("Empleado[" + posicion + "]: Nombre = '" + nombre.toString().trim()
                    + "', Edad = " + edad);
        } catch (IOException e) {
            System.err.println("Error al leer el archivo: " + e.getMessage());
        }
    }
}
