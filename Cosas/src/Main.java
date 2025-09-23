//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.io.FileOutputStream;
import java.io.IOException;
public class Main {
    public static void main(String[] args) {
        try {
            // Abre un FileOutputStream para el archivo especificado
            FileOutputStream fileOutputStream = new FileOutputStream("archivo.txt");

            // Datos que se escribirán en el archivo
            byte[] datos = "Hola, mundo".getBytes();

            // Escribe los datos en el archivo
            fileOutputStream.write(datos);

            // Cierra el FileOutputStream cuando ya no se necesita
            fileOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}