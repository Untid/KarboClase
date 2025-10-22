import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    private static int CAPACIDAD_PARKING = 30;
    private static final int TIEMPO_ENTRADA_SALIDA= 1000;

    private static final Semaphore parking = new Semaphore(CAPACIDAD_PARKING,true);
    private static final Semaphore barrera = new Semaphore(1,true); // Solo un coche puede pasar por la barrera

    private static final Random randon = new Random();

    public static void main(String[] args) {
        int numCoches = randon.nextInt(100) + 40;
        System.out.println("\u200B\uD83C\uDD7F\uFE0F\u200BBienvenidos al parking de Miño!\u200B\uD83C\uDD7F\uFE0F\u200B");
        System.out.println("\u200B\uD83C\uDD7F\uFE0F\u200BHan llegado "+numCoches+" coches al parking!\u200B\uD83C\uDD7F\uFE0F\u200B");

        for (int i = 1;i<=numCoches;i++){
            new Coche(i).start();
            try {
                Thread.sleep(randon.nextInt(500));

            }catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    static class Coche extends Thread{
        private int id;

        public Coche(int id){
            this.id = id;
        }


        @Override
        public void run(){
            try {
                System.out.println("\uD83D\uDE97Coche "+id+" llega al parking y espera en la barrera \uD83D\uDEA7\u200B");
                barrera.acquire();

                if (parking.availablePermits()==0){
                    System.out.println("\uD83D\uDE97Coche "+id+" no puede entrar(parking completo)⛔\u200B");
                    barrera.release();
                    return;
                }

                parking.acquire();
                System.out.println("\uD83E\uDD51\u200BBarrera abre para coche "+id+". Entra y aparca...\uD83D\uDFE2\u200B");
                Thread.sleep(TIEMPO_ENTRADA_SALIDA);

                barrera.release();

                int plazasOcupadas = CAPACIDAD_PARKING - parking.availablePermits();
                System.out.printf("Display: %d/%d plazas ocupadas.%n", plazasOcupadas, CAPACIDAD_PARKING);


                int tiempoPlaya = randon.nextInt(10000)+5000;
                double tiempoMin = tiempoPlaya/1000.0/60.0;
                System.out.println();
                System.out.printf("\uD83C\uDFD6\uFE0F\u200B\uD83E\uDE73Coche %d estará en la playa durante %.2f min.%n \uD83C\uDFD6\uFE0F\u200B\uD83E\uDE73",
                        id, tiempoMin);
                Thread.sleep(tiempoPlaya);


                barrera.acquire();
                System.out.println("\uD83D\uDE97Coche "+id+" sale del parking...");
                Thread.sleep(TIEMPO_ENTRADA_SALIDA);
                parking.release();
                barrera.release();

                plazasOcupadas = CAPACIDAD_PARKING - parking.availablePermits();
                System.out.printf("Display: %d plazas ocupadas.%n",plazasOcupadas);
                System.out.println("\uD83D\uDE97Coche "+id+" ha salido del parking.\uD83D\uDD1A\u200B \n");



            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}