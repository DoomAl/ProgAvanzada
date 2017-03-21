package gestor.Tarifas;

import Datos.Llamada;

/**
 * Created by doomed on 13/04/16.
 */
public class TarifaDia extends TarifaExtra {

    public TarifaDia(Tarifa tarifa, double precio){super(tarifa,precio);}

    public String descripcion(){
        return getTarifaRecubierta().descripcion()+"\nTarifa aplicada a los Domingos: "+ getPrecio()+"€";
    }

    public double calcularPrecio(Llamada llamada){
        if(llamada.getFecha().getTime().getDay()!=0){
            return getTarifaRecubierta().calcularPrecio(llamada);
        }else
            if(super.getPrecio()*llamada.getMinutos()<getTarifaRecubierta().calcularPrecio(llamada))
                return super.getPrecio()*llamada.getMinutos();
            else
                return getTarifaRecubierta().calcularPrecio(llamada);
    }


}
