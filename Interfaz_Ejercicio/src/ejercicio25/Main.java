package ejercicio25;

public class Main {
    public static void main(String[] args) {
        Concesionario miConcesionario = new Concesionario();

        Autos coche = new Autos("Seat","Ibiza",2022,20000,4,true);
        Motos moto = new Motos("Scouter","Mas Scouter",2023,3000,500, false);

        miConcesionario.agregarVehiculo(coche);
        miConcesionario.agregarVehiculo(moto);

        System.out.println(" Lista de vehiculos" );
        miConcesionario.mostrarVehiculos();


        System.out.println(" Precios finales de los vehículos ");
        System.out.println(coche.getMarca() + " " + coche.getModelo()+" "+coche.calcularPrecioFinal());
        System.out.println(moto.getMarca() + " " + moto.getModelo()+" "+moto.calcularPrecioFinal());

        System.out.println(" Vendiendo un vehículo ");
        miConcesionario.venderVehiculo(coche);

        System.out.println(" Lista de vehículos después de la venta");
        miConcesionario.mostrarVehiculos();


    }
}
