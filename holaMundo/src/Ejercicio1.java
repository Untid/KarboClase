import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class Ejercicio1 {
    public static void main(String[] args) throws IOException {
        int contador = 0;

        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "tasklist");
        Process process = pb.start();
        // Process process = new ProcessBuilder(args).start();

        InputStream is = process.getInputStream();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);

        String line;

       // System.out.println("Salida del proceso " + Arrays.toString(args) + ":");

        while ((line = br.readLine()) != null) {


            if (line.contains(".exe")) {
                System.out.println(line);
                    contador++;
            }
            System.out.println(contador);
        }
    }
}
