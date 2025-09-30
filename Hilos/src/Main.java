import java.util.ArrayList;

public class Main {
    public static void main(String[] args) {
        ArrayList<MiHilo> misHilos = new ArrayList<MiHilo>();


        for (int i = 0 ; i <1000; i++){
            misHilos.add(new MiHilo());
            misHilos.getLast().start();
        }

    }
}
