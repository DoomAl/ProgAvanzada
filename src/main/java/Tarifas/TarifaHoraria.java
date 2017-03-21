package gestor.Tarifas;

import Datos.Llamada;

/**
 * Created by doomed on 12/04/16.
 */
public class TarifaHoraria extends TarifaExtra {
    public TarifaHoraria(Tarifa tarifa, Double precio){
        super(tarifa,precio);
    }


    public String descripcion(){
        return getTarifaRecubierta().descripcion()+"\nTarifa horaria aplicada de 16:00 a 20:00 de Lunes a sabado: " + getPrecio()+"€";
    }

    public double calcularPrecio(Llamada llamada){

        if((llamada.getFecha().getTime().getHours()<16 || llamada.getFecha().getTime().getHours()>=20) || llamada.getFecha().getTime().getDay()==0){
            return getTarifaRecubierta().calcularPrecio(llamada);
        }else{
            if(super.getPrecio()*llamada.getMinutos()<getTarifaRecubierta().calcularPrecio(llamada))
                return super.getPrecio()*llamada.getMinutos();
            else
                return  getTarifaRecubierta().calcularPrecio(llamada);
        }

    }



}
