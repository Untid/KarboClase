import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {
    private static final Random randon = new Random();
    private static int CAPACIDAD_PARKING = 30;

    private static final Semaphore parking = new Semaphore(CAPACIDAD_PARKING, true);
    private static final Semaphore barrera = new Semaphore(randon.nextInt(1,1000),true);

    public static void main(String[] args) {

    }

    static class Coche extends Thread{
        private int id;

        public Coche(int id){
            this.id = id;
        }


        @Override
        public void run() {
            try {
            System.out.println("Ha llegado un coche "+id+" a la barrera");


                barrera.acquire();
                System.out.println("Coche "+id+" ha traspasdo la barrera");

                synchronized (parking){


                }










            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }


        }


    }
}
