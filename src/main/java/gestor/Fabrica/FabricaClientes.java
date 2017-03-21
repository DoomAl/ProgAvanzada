package gestor.Fabrica;

import Contactos.Cliente;

/**
 * Created by doomed on 19/04/16.
 */
public interface FabricaClientes {
    Cliente ClienteNuevoEmpresa(String nombre, String dni);
    Cliente ClienteNuevoParticular(String nombre, String apellido, String dni);
}
