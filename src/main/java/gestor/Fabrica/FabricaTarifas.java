package gestor.Fabrica;

import gestor.Tarifas.Tarifa;

/**
 * Created by doomed on 19/04/16.
 */
public interface FabricaTarifas {

    Tarifa NuevaTarifaBase(double tarifa);

    Tarifa NuevaTarifaDia(Tarifa tarifa, double precio);

    Tarifa NuevaTarifaHoraria(Tarifa tarifa, double precio);
}
