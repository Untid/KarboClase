package Pregunta3;

import java.util.Scanner;

public class Mayusculas {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()){
            String linea = sc.nextLine();
            System.out.println(linea.toUpperCase());
        }
        sc.close();
    }
}
