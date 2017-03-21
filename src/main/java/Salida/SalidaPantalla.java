package Salida;

import Contactos.Cliente;
import Datos.Direccion;
import Datos.Factura;
import Datos.Llamada;

/**
 * Created by doomed on 15/03/16.
 */
public class SalidaPantalla implements Salida {




    @Override
    public void salGeneral(String cadena) {
        System.out.print(cadena);
    }

}
