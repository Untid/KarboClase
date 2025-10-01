package ejercicio25;

public class Autos extends  Vehiculos{
    private int numeroPuertas;
    private boolean aireAcondicionado;

    public Autos(String marca, String modelo, int anio, double precioBase, int numeroPuertas, boolean aireAcondicionado) {
        super(marca, modelo, anio, precioBase);
        this.numeroPuertas = numeroPuertas;
        this.aireAcondicionado = aireAcondicionado;
    }

    @Override
    public double calcularPrecioFinal() {

        double impuesto = aireAcondicionado ? 0.10 : 0.05;
        return getPrecioBase() + (getPrecioBase() * impuesto);
    }

    @Override
    public void mostrarInformacion(){
        super.mostrarInformacion();
        System.out.println("Nº de puertas: "+ numeroPuertas);
        System.out.println("Aire acondicionado: "+(aireAcondicionado ? "Sí" : "No"));
        System.out.println("Precio final: "+ calcularPrecioFinal());
    }
}
