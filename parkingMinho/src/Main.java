import java.util.Random;
import java.util.concurrent.Semaphore;

public class Main{

    private static final int CAPACIDAD_PARKING = 30;
    private static final int TIEMPO_ENTRADA_SALIDA = 1000;

    private static final Semaphore[] plazas = new Semaphore[CAPACIDAD_PARKING]; // Cada plaza como semáforo
    private static final Semaphore barrera = new Semaphore(1, true); // Solo un coche a la vez
    private static final Random random = new Random();

    public static void main(String[] args) {
        // Inicializamos plazas
        for (int i = 0; i < CAPACIDAD_PARKING; i++) {
            plazas[i] = new Semaphore(1, true);
        }

        int numCoches = random.nextInt(100) + 40;
        System.out.println("🚦 Bienvenidos al parking de Miño! 🚦");
        System.out.println("🚗 Han llegado " + numCoches + " coches al parking 🚗\n");

        for (int i = 1; i <= numCoches; i++) {
            new Coche(i).start();
            try {
                Thread.sleep(random.nextInt(500)); // Intervalo aleatorio entre coches
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    static class Coche extends Thread {
        private int id;

        public Coche(int id) {
            this.id = id;
        }

        @Override
        public void run() {
            try {
                System.out.println("🚗 Coche " + id + " llega al parking y busca plaza...");

                int plazaAsignada = -1;

                // Buscar plaza libre
                while (plazaAsignada == -1) {
                    for (int i = 0; i < CAPACIDAD_PARKING; i++) {
                        if (plazas[i].tryAcquire()) {
                            plazaAsignada = i;
                            break;
                        }
                    }
                    if (plazaAsignada == -1) {
                        System.out.println("🔴 Parking lleno para coche " + id + ". Esperando...");
                        Thread.sleep(500);
                    }
                }

                // Pasar por la barrera
                barrera.acquire();
                System.out.println("🟢 Barrera abre para coche " + id + ". Entra y aparca en plaza " + (plazaAsignada + 1));
                Thread.sleep(TIEMPO_ENTRADA_SALIDA);
                barrera.release();

                // Mostrar estado del parking
                mostrarEstado();

                // Tiempo en la playa
                int tiempoPlaya = random.nextInt(10000) + 5000;
                double tiempoMin = tiempoPlaya / 1000.0 / 60.0;
                System.out.printf("🏖️ Coche %d estará en la playa durante %.2f min.%n", id, tiempoMin);
                Thread.sleep(tiempoPlaya);

                // Salida
                barrera.acquire();
                System.out.println("🚗 Coche " + id + " sale del parking de la plaza " + (plazaAsignada + 1));
                Thread.sleep(TIEMPO_ENTRADA_SALIDA);
                barrera.release();

                // Liberar plaza
                plazas[plazaAsignada].release();
                mostrarEstado();

            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

        private void mostrarEstado() {
            int ocupadas = 0;
            for (Semaphore s : plazas) {
                if (s.availablePermits() == 0) ocupadas++;
            }
            System.out.printf("📟 Display: %d/%d plazas ocupadas.%n", ocupadas, CAPACIDAD_PARKING);
        }
    }
}
