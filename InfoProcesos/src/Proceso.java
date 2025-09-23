/**
 * Representa un proceso del sistema operativo.
 * Contiene el nombre, PID y la memoria usada en KB. (por como lo imprime tasklist)
 *
 */

class Proceso implements Comparable<Proceso> {

    private String nombre;
    private int pid;
    private long memoria;

    public Proceso(String nombre, int pid, long memoria) {
        this.nombre = nombre;
        this.pid = pid;
        this.memoria = memoria;
    }

    @Override
    public int compareTo(Proceso proceso) { // Sobrescribe el método "compareTo" para ordenar la memoría de mayor a menor

        return Long.compare(proceso.memoria, this.memoria);
    }

    @Override
    public String toString() {
        return nombre + " (PID: " + pid + ") - Memoria: " + memoria + " KB";
    }
}

