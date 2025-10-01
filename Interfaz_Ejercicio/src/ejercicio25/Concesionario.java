package ejercicio25;

import java.util.ArrayList;
import java.util.List;

public class Concesionario {
    private List<Vehiculos> vehiculosList;

    public Concesionario() {
        this.vehiculosList  = new ArrayList<>();
    }

    public void agregarVehiculo(Vehiculos vehiculos) {
        vehiculosList.add(vehiculos);
        System.out.println("Vehículo agregado: "+ vehiculos.getMarca() +" "+ vehiculos.getModelo());
    }

    public void mostrarVehiculos() {
        if (vehiculosList.isEmpty()) {
            System.out.println("No hay vehículos en el concesionario");
            return;
        }

        for (Vehiculos i : vehiculosList){
            i.mostrarInformacion();
            System.out.println("-----------------------");
        }
    }

    public void venderVehiculo (Vehiculos vehiculos){
        if (vehiculosList.remove(vehiculos)){
            System.out.println("Vehiculo vendido: " + vehiculos.getMarca()+" "+vehiculos.getModelo());
        }else {
            System.out.println("El vehículo no se encuentra en el concesionario");
        }
    }
}
