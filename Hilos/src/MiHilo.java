import java.io.IOException;

public class MiHilo extends Thread {

    static int numeroHilos;
    static int hilosParados;
    private int numeroHilo;


    public MiHilo() {
        this.numeroHilo = ++this.numeroHilos;
    }

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {

            System.out.println("Hilos: " + this.numeroHilos + " Hilo: "+this.numeroHilo+" Iteración " + i + " TS: "+ this.hilosParados);

            try {
                this.hilosParados ++;
                Thread.sleep(10); // Agrega una pausa de 1 segundo
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            this.hilosParados --;
        }
    }
}
