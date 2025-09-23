import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class ContadorEXE {
    int contador = 0;

    public void contar() throws IOException {
        ProcessBuilder pb = new ProcessBuilder("cmd.exe", "/c", "tasklist");
        Process process = pb.start();

        InputStream is = process.getInputStream();

        InputStreamReader isr = new InputStreamReader(is);

        BufferedReader br = new BufferedReader(isr);
        String line;

        while ((line = br.readLine()) != null) {
            if (line.contains(".exe")) {
                System.out.println(line);
                contador++;
            }
            System.out.println(contador);
        }

    }

    public ContadorEXE() throws IOException {
    }

}
