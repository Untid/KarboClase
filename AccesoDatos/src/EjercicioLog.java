import java.io.*;
import java.sql.SQLOutput;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Encontrar qué Sistemas Operativos estan registrados en el Log.
 * Podemos encontrar cierros patrones, como que siempre estan entre "(" y ")" y que cada final de log pone "server: www.colexio-karbo.com"
 * Después contar cuantas veces coincide el mismo texto y mostrar la cuenta
 */
public class EjercicioLog {
    public static void main(String[] args) {
        String rutaArchivo = "accesos.log";
        
        Pattern patron = Pattern.compile("\\(([^)]+)\\)");

        try(BufferedReader br = new BufferedReader(new FileReader(rutaArchivo))){
            String linea;

            while((linea = br.readLine()) != null){
                Matcher matcher = patron.matcher(linea);

                if (matcher.find()){
                    String contenido = matcher.group(1);
                    System.out.println(contenido);
                }
            }

        } catch (IOException e) {
            throw new RuntimeException(e);
        }

    }
}