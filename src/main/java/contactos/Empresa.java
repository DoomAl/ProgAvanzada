package Contactos;

import Datos.Direccion;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by doomed on 16/02/16.
 */
public class Empresa extends Cliente implements Serializable{
    private static final long serialVersionUID = -6523741850225848464L;
    public Empresa(){
        super();
    }

    public Empresa(String nombre, String dni){
        super(nombre, dni);
    }
}
