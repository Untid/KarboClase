package Pregunta4;


import java.util.Scanner;

public class DNI {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while(sc.hasNextLine()){
            String dni = sc.nextLine().trim();
            if (dni.length()<2){
                System.out.println("-1"); // Error
                continue;
            }
            try{
                int numero = Integer.parseInt(dni.substring(0,dni.length()-1));
                char letra = dni.charAt(dni.length()-1);
                char letraCorrecta = "TRWAGMYFPDXBNJZSQVHLCKE".charAt(numero % 23);

                if (Character.toUpperCase(letra)==letraCorrecta){
                    System.out.println("OK");
                }else {
                    System.out.println("-1 "+numero+letraCorrecta);
                }
            }catch (Exception e){
                System.out.println("-1"); // Error de formato
            }
        }
        sc.close();
    }
}