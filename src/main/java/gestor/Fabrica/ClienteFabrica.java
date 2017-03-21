package gestor.Fabrica;

import Contactos.Cliente;
import Contactos.Empresa;
import Contactos.Particular;

/**
 * Created by doomed on 19/04/16.
 */
public class ClienteFabrica implements FabricaClientes {

    public ClienteFabrica(){};

    @Override
    public Cliente ClienteNuevoEmpresa(String nombre, String dni) {
        return new Empresa(nombre,dni);
    }

    @Override
    public Cliente ClienteNuevoParticular(String nombre, String apellido, String dni) {
        return new Particular(nombre,apellido,dni);
    }

}
