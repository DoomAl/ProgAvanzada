package Contactos;

import Contactos.Empresa;
import Datos.Direccion;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by doomed on 17/02/16.
 */
public class Particular extends Cliente implements Serializable {
    private static final long serialVersionUID = -1062981850225848464L;
    private String apellido;

    public Particular(){
        super();
    };

    public Particular (String Nombre, String apellido, String dni){
        super(Nombre,dni);
        this.apellido=apellido;
    }

    @Override
    public String getNombre(){
        return super.getNombre()+" "+apellido;
    }

    public void setApellido(String Apellido){
        apellido=Apellido;
    }
}
