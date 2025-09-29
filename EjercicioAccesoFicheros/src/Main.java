import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        // Ruta en la cual el programa empezará a analizar los archivos del directorio.
        String ruta = "C:\\Users\\Javier\\Downloads";
        File carpeta = new File(ruta);

        // Verificamos que esta ruta exista y que realmente sea un directorio
        if (carpeta.exists() && carpeta.isDirectory()) {
        // Si es correcto, iniciamos el recorrido con nivel 0 (sin sangría) llamándo al método mostrar
            mostrar(carpeta, 0);
        } else {
            System.out.println("La ruta no es válida");
        }
    }

    /**
     * Método recursivo para recorrer los directorios
     * @param file  archivo o carpeta a mostrar
     * @param nivel  profundidad en el árbol (para calcular la identación visual)
     */
    // Indentacion es el espacio en blanco que se introduce al principio de una línea
    private static void mostrar(File file, int nivel) {
        // Calculamos la sangría en función del nivel (2 espacios por nivel)
        String indentacion = " ".repeat(nivel * 2);

        if (file.isDirectory()) {
            // Si es carpeta, la mostramos con el prefijo [DIR]
            System.out.println(indentacion + "[DIR] " + file.getName());

            // Obtenemos todos los elementos que contiene
            File[] archivos = file.listFiles();

            // Si no es nulo (puede dar null si no hay permisos de lectura)
            if (archivos != null) {
                // Recorremos cada archivo/carpeta dentro y aplicamos recursividad
                for (File f : archivos) {
                    mostrar(f, nivel + 1); // Recursividad, el método mostrar se llama a sí mismo
                }
            }
        } else {
            // Si es un archivo, primero detectamos su tipo según "cabecera mágica"
            String tipo = detectarTipo(file);

            // Lo mostramos con indentación, nombre y tipo detectado
            System.out.println(indentacion + "- " + file.getName() + " (" + tipo + ")");
        }
    }

    /**
     * Método que sirve para detectar el tipo de archivo leyendo los primeros bytes (cabecera mágica)
     * Si no coincide con los tipos conocidos, devuelve "desconocido"
     * @param file
     * @return String
     */
    private static String detectarTipo(File file) {

        // Asegura que el stream se cierre automáticamente
        try (FileInputStream fis = new FileInputStream(file)) {
            byte[] cabecera = new byte[8]; // Buffer para los primeros 8 bytes
            int leidos = fis.read(cabecera); // Guardamos cuántos bytes se leyeron

            // Verificamos las firmas mágicas más comunes:
            if (cabecera[0] == (byte) 0x89 && cabecera[1] == 0x50 && cabecera[2] == 0x4E && cabecera[3] == 0x47) {
                return "PNG";
            }
            if (cabecera[0] == (byte) 0xFF && cabecera[1] == (byte) 0xD8 && cabecera[2] == (byte) 0xFF) {
                return "JPEG";
            }
            if (cabecera[0] == 0x25 && cabecera[1] == 0x50 && cabecera[2] == 0x44 && cabecera[3] == 0x46) {
                return "PDF";
            }
            if (cabecera[0] == 0x50 && cabecera[1] == 0x4B && cabecera[2] == 0x03 && cabecera[3] == 0x04) {
                return "ZIP/JAR/DOCX";
            }
            if (cabecera[0] == 0x47 && cabecera[1] == 0x49 && cabecera[2] == 0x46 && cabecera[3] == 0x38) {
                return "GIF";
            }
        } catch (IOException e) {
            // Si ocurre un error al leer el archivo (p. ej., falta de permisos)
            return "Error de lectura";
        }
        // Si no coincide con ninguna cabecera conocida, lo marcaremos como "desconocido"
        return "desconocido";
    }
}