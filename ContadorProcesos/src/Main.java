import java.io.IOException;
import java.util.Scanner;



public class Main {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.println("Escriba uno para ver los exes o 2 para ver los dll");
        int opcion = sc.nextInt();

        if (opcion == 1){
            try {
                ContadorEXE exe = new ContadorEXE();
                exe.contar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }


        } else if (opcion == 2) {

            try {
                ContadorDLL dll = new ContadorDLL();
                dll.contar();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }

    }
}