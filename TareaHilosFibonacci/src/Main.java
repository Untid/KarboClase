public class Main {
    public static void main(String[] args) {
            MiHilo miHilo = new MiHilo();
            Thread miThread = new Thread(miHilo);

            miThread.start();
    }
}
