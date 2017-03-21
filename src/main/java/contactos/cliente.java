package Contactos;

import Datos.Direccion;
import Datos.Factura;
import Datos.Fecha;
import Datos.Llamada;
import Lectura.leerTeclado;
import gestor.Tarifas.Tarifa;
import gestor.Tarifas.TarifaBase;
import gestor.Tarifas.TarifaDia;

import java.io.Serializable;
import java.util.*;

/**
 * Created by doomed on 8/03/16.
 */
public abstract class Cliente implements Fecha, Serializable{
    private static final long serialVersionUID = -6528741850225848464L;
    private String nombre;
    private String dni;
    private String correoElectronico;
    private Calendar fechaAlta;
    private Tarifa tarifa_actual;
    private Direccion direccion;
    private long codigo;
    private TreeMap<Long,Factura> facturas;
    private ArrayList<Llamada> llamadas;

    public Cliente(){
        codigo=0;
        facturas= new TreeMap<Long, Factura>();
        llamadas=new ArrayList<Llamada>();
        fechaAlta=Calendar.getInstance();

    };

    public Cliente(String Nombre, String dni){
        codigo=0;
        nombre=Nombre;
        this.dni=dni;
        facturas= new TreeMap<Long, Factura>();
        llamadas=new ArrayList<Llamada>();
        fechaAlta=Calendar.getInstance();


    }

    public Cliente rellenarCliente(Cliente devolver){

        leerTeclado lectura=new leerTeclado();

        String email=lectura.leeEmail();
        Tarifa tarifa=lectura.leeTarifa();
        String provincia=lectura.leeProvincia();
        String poblacion=lectura.leePoblacion();
        int codigoPostal=lectura.leeCodigoPostal();

        direccion=new Direccion(codigoPostal,provincia,poblacion);

        devolver.setCorreoElectronico(email);
        devolver.setFechaAlta(fechaAlta);
        devolver.setTarifa(tarifa);

        return devolver;
    }

    public String getNombre(){
        return nombre;
    }


    public String getCorreoElectronico(){
        return correoElectronico;
    }

    public Tarifa getTarifa(){
        return tarifa_actual;
    }



    public void setNombre(String Nombre){
        nombre=Nombre;
    }

    public void setCodigo(long codigo){
        this.codigo=codigo+1;
    }

    public void setCorreoElectronico(String correo){
        correoElectronico=correo;
    }

    public void setFechaAlta (Calendar Fecha){
        fechaAlta=Fecha;
    }

    public void setTarifa (Tarifa Tarifa){
        tarifa_actual=Tarifa;
    }

    public Direccion getDireccion(){
        return direccion;
    }

    public void setDireccion(Direccion direccion){
        this.direccion=direccion;
    }

    public TreeMap<Long,Factura> getFacturas(){
        return facturas;
    }

    public ArrayList<Llamada> getLlamadas(){
        return llamadas;
    }

    public String getDni(){
        return dni;
    }


    @Override
    public Calendar getFecha() {
        return fechaAlta;
    }

    public long getCodigo() {
        return codigo;
    }


    @Override
    public String toString(){
        StringBuilder cadena=new StringBuilder();
        cadena.append("Nombre: ");
        cadena.append(nombre);
        cadena.append("\n");
        cadena.append("DNI: ");
        cadena.append(dni);
        cadena.append("\n");
        cadena.append("Email: ");
        cadena.append(correoElectronico);
        cadena.append("\n");
        cadena.append("Fecha de alta: ");
        cadena.append(fechaAlta.getTime().toString());
        cadena.append("\n");
        cadena.append("Tarifas aplicadas: \n");
        cadena.append(tarifa_actual.descripcion());
        cadena.append("\n");
        cadena.append("Código postal: ");
        cadena.append(direccion.getCodigoPostal());
        cadena.append("\n");
        cadena.append("Población: ");
        cadena.append(direccion.getPoblacion());
        cadena.append("\n");
        cadena.append("Provincia: ");
        cadena.append(direccion.getProvincia());
        cadena.append("\n");
        return cadena.toString();
    }
}
