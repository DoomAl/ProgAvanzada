package Modelo;

import Contactos.Cliente;
import Datos.Factura;
import Datos.Llamada;
import gestor.Tarifas.Tarifa;

import java.util.List;

/**
 * Created by doomed on 4/05/16.
 */
public interface InterrogaModelo {
    List<Cliente> listaClientes();
    List<Llamada> listaLlamadas(String dni);
    List<Factura> listaFacturas(String dni);
    Tarifa  getTarifa(String dni);
    List<String> getDnis();
    List<Integer> getCodigos();
    Cliente filtraCliente(String dni);
    Factura filtaFactura(String dni, long codfac);
    void filtraLlamada();
}
