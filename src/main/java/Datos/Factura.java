package Datos;

import gestor.Tarifas.Tarifa;
import gestor.Tarifas.TarifaBase;
import gestor.Tarifas.TarifaDia;
import gestor.Tarifas.TarifaHoraria;

import java.io.Serializable;
import java.util.Calendar;
import java.util.List;


/**
 * Created by doomed on 8/03/16.
 */
public class Factura implements Fecha, Serializable{
    private static final long serialVersionUID = -1065341850246289464L;
    private Tarifa tarifa;
    private Calendar fecha_emision;
    private Calendar fecha_facturacion;
    private double precio;
    private long codigoFac;
    private static long contador=0;

    public Factura(){}
    public Factura(Tarifa tarifa, Calendar Fecha_Emision, Calendar Fecha_Facturacion){
        contador++;
        codigoFac=contador;
        this.tarifa=tarifa;
        fecha_emision=Fecha_Emision;
        fecha_facturacion=Fecha_Facturacion;
        precio=0;
    }

    public Tarifa getTarifa() {
        return tarifa;
    }


    public double importe(List<Llamada> llamadas){
        double devolver=0.0;
        for(Llamada llamada:llamadas){

            devolver+=tarifa.calcularPrecio(llamada);
        }
        return devolver;
    }
    public void setTarifa(Tarifa tarifa) {
        this.tarifa = tarifa;
    }

    public Calendar getFecha_emision(){return fecha_emision;}

    public Calendar getFecha_facturacion(){return fecha_facturacion;}

    public void setFecha_emision(Calendar nueva){fecha_emision=nueva;}

    public void setFecha_facturacion(Calendar nueva){fecha_facturacion=nueva;}

    public double getPrecio(){
        return precio;
    }
    public void setPrecio(double Precio){
        precio=Precio;
    }

    public long getCodigo(){
        return codigoFac;
    }


    @Override
    public Calendar getFecha() {
        return fecha_emision;
    }

    @Override
    public String toString(){
        StringBuilder cadena= new StringBuilder();
        cadena.append("Codigo de la factura: ");
        cadena.append(codigoFac);
        cadena.append("\n");
        cadena.append("Tarifa: \n");
        cadena.append(tarifa.descripcion());
        cadena.append("\n");
        cadena.append("Fecha de emisión: ");
        cadena.append(fecha_emision.getTime());/*
        cadena.append(fecha_emision.getTime().getDate());
        cadena.append("/");
        cadena.append(fecha_emision.getTime().getMonth());
        cadena.append("/");
        cadena.append(fecha_emision.getTime().getYear());
        */cadena.append("\n");
        cadena.append("Fecha de facturación: ");
        cadena.append(fecha_facturacion.getTime());/*
        cadena.append(fecha_facturacion.getTime().getDate());
        cadena.append("/");
        cadena.append(fecha_facturacion.getTime().getMonth());
        cadena.append("/");
        cadena.append(fecha_facturacion.getTime().getYear());
        */cadena.append("\n");
        cadena.append("Precio de la factura: ");
        cadena.append(precio);
        cadena.append(" €");
        cadena.append("\n");
        return cadena.toString();
    }


}


