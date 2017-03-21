package Datos;

import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by doomed on 8/03/16.
 */
public class Llamada implements Fecha, Serializable{
    private static final long serialVersionUID = -1065341850225848464L;

    private String numero;
    private Calendar Fecha;
    private double minutos;

    public Llamada(){}

    public Llamada(String numero,double minutos){
        this.numero=numero;
        this.minutos=minutos;
        Fecha=Calendar.getInstance();

    }

    public String getNumero(){ return numero;}

    public double getMinutos(){return minutos;}


    public void setNumero(String newNum){numero=newNum;}

    public void setFecha(Calendar newFecha){Fecha=newFecha;}

    public void setMinutos(double newMin){minutos=newMin;}


    @Override
    public Calendar getFecha() {
        return Fecha;
    }

    @Override
    public String toString(){
        StringBuilder cadena= new StringBuilder();
        cadena.append("Número de la llamada: ");
        cadena.append(numero);
        cadena.append("\n");
        cadena.append("Fecha de la llamada: ");
        cadena.append(Fecha.getTime().toString());
        cadena.append("\n");
        cadena.append("Minutos de la llamada: ");
        cadena.append(minutos);
        cadena.append("\n");
        return cadena.toString();
    }
}
