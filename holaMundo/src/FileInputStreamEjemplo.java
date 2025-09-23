import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class FileInputStreamEjemplo {
    public static void main(String[] args) {
        try {
            // Abre un FileInputStream para el archivo especificado
            InputStreamReader fileInputStream = new InputStreamReader(new FileInputStream("texto.txt"), "UTF-8");

            int byteLeido;

            // Lee cada byte del archivo
            while ((byteLeido = fileInputStream.read()) != -1) {
                // Haz algo con el byte leído, como procesarlo o almacenarlo en otro lugar
                System.out.print((char) byteLeido); // Convierte el byte a un carácter para imprimirlo
            }

            // Cierra el FileInputStream cuando ya no se necesita
            fileInputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}