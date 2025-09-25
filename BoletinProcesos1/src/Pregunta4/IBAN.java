package Pregunta4;

import java.util.Scanner;

public class IBAN {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        while (sc.hasNextLine()){
            String iban = sc.nextLine().trim().replaceAll("\\s","");

            if (!iban.matches("[A-Z]{2}\\d{22}")){ // Es + 22 dígitos
                System.out.println("-1"); // formato incorrecto
            }else {
                // Validación simplificada: dígito control (posiciones 2-3) entre 00 y 99
                String digitoControl = iban.substring(2,4);

                try {
                    int dc = Integer.parseInt(digitoControl);

                    if (dc >=0 && dc <=99){
                        System.out.println("OK");
                    }else{
                        System.out.println("-1" + iban.substring(0,2)+"00"+iban.substring(4));
                    }
                }catch (NumberFormatException e){
                    System.out.println("-1 "+ iban.substring(0,2)+"00"+iban.substring(4) );
                }
            }
        }
    }
}
