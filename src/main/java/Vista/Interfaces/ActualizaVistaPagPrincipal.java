package Vista.Interfaces;

import Contactos.Cliente;
import Datos.Factura;
import Datos.Llamada;
import gestor.Tarifas.Tarifa;

import java.util.List;

/**
 * Created by doomed on 4/05/16.
 */
public interface ActualizaVistaPagPrincipal{
    void ActualizarListaClientes(List<Cliente> lista);
    void ActualizarTarifa();
    void ActualizarInfoCli();
    void ActualizarListaLlamadas(List<Llamada> lista);
    void ActualizarListaFacturas(List<Factura> lista);


}
