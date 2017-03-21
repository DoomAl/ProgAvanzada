package gestor.Fabrica;

import gestor.Tarifas.Tarifa;
import gestor.Tarifas.TarifaBase;
import gestor.Tarifas.TarifaDia;
import gestor.Tarifas.TarifaHoraria;

/**
 * Created by doomed on 19/04/16.
 */
public class TarifaFabrica implements FabricaTarifas {

    public TarifaFabrica(){};

    @Override
    public Tarifa NuevaTarifaBase(double tarifa) {
        return new TarifaBase(tarifa);
    }

    @Override
    public Tarifa NuevaTarifaDia(Tarifa tarifa, double precio) {
        return new TarifaDia(tarifa, precio);
    }

    @Override
    public Tarifa NuevaTarifaHoraria(Tarifa tarifa, double precio) {
        return new TarifaHoraria(tarifa,precio);
    }
}
