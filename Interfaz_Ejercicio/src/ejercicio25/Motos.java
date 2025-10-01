package ejercicio25;

public class Motos extends Vehiculos{
    private int cilindrada;
    private boolean esElectrica;

    public Motos(String marca, String modelo, int anio, double precioBase, int cilindrada, boolean esElectrica) {
        super(marca, modelo, anio, precioBase);
        this.cilindrada = cilindrada;
        this.esElectrica = esElectrica;
    }

    @Override
    public double calcularPrecioFinal() {
        double impuesto =  esElectrica ? 0.08 : 0.12;
        return getPrecioBase() + (getPrecioBase() * impuesto);

    }

    @Override
    public void mostrarInformacion(){
        super.mostrarInformacion();
        System.out.println("Cilindrada: "+cilindrada+ " cc");
        System.out.println("Es eléctrica: " + (esElectrica ? "Sí" : "No"));
        System.out.println("Precio final: "+calcularPrecioFinal());
    }


    public int getCilindrada() {
        return cilindrada;
    }

    public void setCilindrada(int cilindrada) {
        this.cilindrada = cilindrada;
    }

    public boolean isEsElectrica() {
        return esElectrica;
    }

    public void setEsElectrica(boolean esElectrica) {
        this.esElectrica = esElectrica;
    }
}
