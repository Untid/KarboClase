package Pregunta1;

public class Main {
    public static void main(String[] args) {
        String comando = "ipconfig";
        String[] opciones = {"/all"};

        Ejecuta miProceso = new Ejecuta(comando, opciones);
        miProceso.ejecutar();
        //----------------------------------------------------------------------------------------------
        String comando2 = "cmd";
        String[] opciones2 = {"/c", "dir", "/w"};

        Ejecuta miProceso2 = new Ejecuta(comando2, opciones2);
        miProceso2.ejecutar();
    }
}