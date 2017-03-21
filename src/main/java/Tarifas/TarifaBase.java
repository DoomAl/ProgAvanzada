package gestor.Tarifas;

import Datos.Llamada;

/**
 * Created by doomed on 12/04/16.
 */
public class TarifaBase extends Tarifa {
    public TarifaBase(double precio){
        super(precio);
    }

    public String descripcion(){
        return "Tarifa base: "+ getPrecio()+"€";
    }

    public double calcularPrecio(Llamada llamada){
        return llamada.getMinutos()*getPrecio();
    }


}
