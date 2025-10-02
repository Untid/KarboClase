import java.util.Random;
import java.util.concurrent.Semaphore;

public class EjemploSemaforo {
    private static Semaphore semaphore = new Semaphore(1);

    public static void main(String[] args) {
        for (int i = 0; i < 5; i++) {
            new Thread(() -> {
//                boolean dentro = false;
                try {
//                    if (semaphore.tryAcquire()) {
                    semaphore.acquire();
//                        dentro = true;
                        System.out.println("Hilo " + Thread.currentThread().getId() + " está en la sección crítica");
                        Thread.sleep((new Random()).nextInt(1000)+1000);
//                    } else {
//                        System.out.println("Hilo " + Thread.currentThread().getId() + " no está en la sección crítica");
//                        Thread.sleep(1000);
//                    }
                } catch (InterruptedException e) {
                    e.getMessage();
                } finally {
//                    if (dentro) {
                        semaphore.release();
//                        dentro = false;
                        System.out.println("Hilo " + Thread.currentThread().getId() + " ha salido de la sección crítica");
//                    }
                }
            }).start();
        }
    }
}