package Datos;

import java.io.Serializable;

/**
 * Created by doomed on 16/02/16.
 */
public class Direccion implements Serializable {
    private static final long serialVersionUID = -1065265740225848464L;
    private int codigoPostal;
    private String provincia;
    private String poblacion;

    public Direccion(){

    }

    public Direccion(int codigoPostal, String provincia, String poblacion){
        this.codigoPostal=codigoPostal;
        this.provincia=provincia;
        this.poblacion=poblacion;
    }

    public int getCodigoPostal(){
        return codigoPostal;
    }

    public String getProvincia(){
        return provincia;
    }

    public String getPoblacion(){
        return poblacion;
    }

    public void setCodigoPostal(int codigoPostal){
        this.codigoPostal=codigoPostal;
    }

    public void setProvincia(String provincia){
        this.provincia=provincia;
    }

    public void setPoblacion(String poblacion){
        this.poblacion=poblacion;
    }

    public String toString(){
        return codigoPostal+" "+poblacion+" "+provincia;
    }
}
