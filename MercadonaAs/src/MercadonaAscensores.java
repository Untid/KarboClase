import java.util.Random;
import java.util.concurrent.Semaphore;

public class MercadonaAscensores {

    private static final int CAPACIDAD_ASCENSOR = 5;   // 5 personas por ascensor
    private static final int CAPACIDAD_TIENDA = 1133;  // Máximo posible de personas en tienda
    private static final int TIEMPO_VIAJE = 2000;      // Subida/bajada en ms

    // Semáforos
    private static final Semaphore ascensor1 = new Semaphore(CAPACIDAD_ASCENSOR, true);
    private static final Semaphore ascensor2 = new Semaphore(CAPACIDAD_ASCENSOR, true);
    private static final Semaphore miMercadona = new Semaphore(CAPACIDAD_TIENDA, true);

    private static final Random random = new Random();

    public static void main(String[] args) {
        int numClientes = random.nextInt(2000) + 200; // llegan más de los que caben
        int aforoAleatorio = random.nextInt(CAPACIDAD_TIENDA) + 1; // número aleatorio de 1 a 1133
        System.out.println("¡Han llegado " + numClientes + " clientes, felicidades!");
        System.out.println("Hoy se estiman que entren " + aforoAleatorio + " clientes, ¡Enhorabuena!");

        // Creamos y lanzamos todos los clientes como hilos
        for (int i = 1; i <= numClientes; i++) {
            new Cliente(i, aforoAleatorio).start();
        }
    }

    static class Cliente extends Thread {
        private int id;
        private int aforoObjetivo;

        public Cliente(int id, int aforoAleatorio) {
            this.id = id;
            this.aforoObjetivo = aforoAleatorio;
        }

        @Override
        public void run() {
            try {
                // Espera su turno para subir
                System.out.println("Cliente " + id + " llega y espera un ascensor...");

                Semaphore ascensor;
                if (random.nextBoolean()) {
                    ascensor = ascensor1;
                } else {
                    ascensor = ascensor2;
                }

                ascensor.acquire(); // entra al ascensor
                System.out.println("Cliente " + id + " entra en el ascensor.");

                Thread.sleep(TIEMPO_VIAJE); // sube

                // Comprobamos si el aforo máximo del día no se ha alcanzado
                synchronized (miMercadona) {
                    int ocupacionActual = CAPACIDAD_TIENDA - miMercadona.availablePermits();
                    if (ocupacionActual >= aforoObjetivo) {
                        // Aforo completo, no puede entrar
                        System.out.println("Cliente " + id + " no puede entrar (aforo completo). Baja de nuevo.");
                        ascensor.release();
                        Thread.sleep(TIEMPO_VIAJE); // baja otra vez
                        return;
                    } else {
                        // Puede entrar
                        miMercadona.acquire();
                    }
                }

                // Tiempo de compra entre 10 y 50 minutos
                int minCompra = 10 * 60 * 1000;
                int maxCompra = 50 * 60 * 1000;
                int tiempoCompra = random.nextInt(maxCompra - minCompra + 1) + minCompra;
                double tiempoMinutos = tiempoCompra / 1000.0 / 60.0;


                ascensor.release(); // libera el ascensor una vez dentro
                System.out.println("Cliente " + id + " entra al Mercadona.");
                System.out.printf("Cliente %d tardará en comprar en %.2f min.%n",id,tiempoMinutos);


                Thread.sleep(tiempoCompra);
                System.out.printf("Cliente %d ha terminado de comprar en %.2f min.%n",id,tiempoMinutos);

                // Baja del supermercado
                if (random.nextBoolean()) {
                    ascensor = ascensor1;
                } else {
                    ascensor = ascensor2;
                }

                ascensor.acquire(); // Espera ascensor libre
                System.out.println("Cliente " + id + " entra al ascensor para bajar.");
                Thread.sleep(TIEMPO_VIAJE); // baja
                ascensor.release();

                // Sale del supermercado y libera su plaza en el aforo
                miMercadona.release();
                System.out.println("Cliente " + id + " ha salido del Mercadona.");

            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }
}
