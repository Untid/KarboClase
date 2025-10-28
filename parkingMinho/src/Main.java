import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main {

    // Arreglar que no entren más coches una vez completado el parking.

    private static final int CAPACIDAD_PARKING = 30;
    private static final int TIEMPO_ENTRADA_SALIDA= 1000;

    private static final Semaphore parking = new Semaphore(CAPACIDAD_PARKING,true);
    private static final Semaphore barrera = new Semaphore(1,true); // Solo un coche puede pasar por la barrera

    private static final Random random = new Random();

    public static void main(String[] args) {
        int numCoches = random.nextInt(100) + 40;
        System.out.println("\u200B\uD83C\uDD7F\uFE0F\u200BBienvenidos al parking de Miño!\u200B\uD83C\uDD7F\uFE0F\u200B");
        System.out.println("\u200B\uD83C\uDD7F\uFE0F\u200BHan llegado "+numCoches+" coches al parking!\u200B\uD83C\uDD7F\uFE0F\u200B");

        for (int i = 1;i<=numCoches;i++){
            new Coche(i).start();
            try {
                Thread.sleep(random.nextInt(500));

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
        public void run() {
            try {
                System.out.println("🚗 Coche " + id + " llega al parking y espera una plaza...");

                // Espera hasta que haya una plaza libre (sin bloquear la barrera)
                parking.acquire();

                // Una vez hay plaza libre, ahora pasa por la barrera (uno a la vez)
                barrera.acquire();
                System.out.println("🟢 Barrera abre para coche " + id + ". Entra y aparca...");
                Thread.sleep(TIEMPO_ENTRADA_SALIDA);
                barrera.release();

                int plazasOcupadas = CAPACIDAD_PARKING - parking.availablePermits();
                System.out.printf("Display: %d/%d plazas ocupadas.%n", plazasOcupadas, CAPACIDAD_PARKING);

                int tiempoPlaya = random.nextInt(10000) + 5000;
                double tiempoMin = tiempoPlaya / 1000.0 / 60.0;
                System.out.printf("🏖️ Coche %d estará en la playa durante %.2f min.%n", id, tiempoMin);
                Thread.sleep(tiempoPlaya);

                // Salida: vuelve a pasar por la barrera
                barrera.acquire();
                System.out.println("🚗 Coche " + id + " sale del parking...");
                Thread.sleep(TIEMPO_ENTRADA_SALIDA);
                barrera.release();

                // Libera plaza
                parking.release();

                plazasOcupadas = CAPACIDAD_PARKING - parking.availablePermits();
                System.out.printf("Display: %d plazas ocupadas.%n", plazasOcupadas);
                System.out.println("🚗 Coche " + id + " ha salido del parking.\n");

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }


    }
}