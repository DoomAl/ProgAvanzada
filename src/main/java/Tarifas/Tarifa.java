package gestor.Tarifas;

import Datos.Llamada;

import java.io.Serializable;


/**
 * Created by doomed on 12/04/16.
 */
public abstract class Tarifa implements Serializable{
    private static final long serialVersionUID = -1068965245848464L;
    private double precio;

    public Tarifa(){
        precio =0;
    }

    public Tarifa(Double tarifa){
        this.precio =tarifa;
    }

    public Double getPrecio(){
        return precio;
    }

    public void setPrecio(Double precio){
        this.precio = precio;
    }

    public abstract String descripcion();


    public abstract double calcularPrecio(Llamada llamada);


}
