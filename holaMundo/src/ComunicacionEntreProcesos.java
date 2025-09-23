import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;

public class ComunicacionEntreProcesos {
    public static void main(String[] args) throws IOException {

        ProcessBuilder pb = new ProcessBuilder("cmd.exe","/c","netstat");
        Process process = pb.start();
        // Process process = new ProcessBuilder(args).start();

        InputStream is = process.getInputStream();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);

        String line;

        System.out.println("Salida del proceso " + Arrays.toString(args) + ":");

        while ((line = br.readLine()) != null) {

            String[] parts = line.split("\\s+");

            if (parts.length ==5){
//                System.out.println(line);
//                System.out.println(parts.length);
                System.out.println(parts [2]);
                String puerto = parts[2].split(":")[1];
                System.out.println(puerto);

            }

            try {
                System.out.println(parts[1]);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println(e.getMessage());
            }

        }
    }
}
