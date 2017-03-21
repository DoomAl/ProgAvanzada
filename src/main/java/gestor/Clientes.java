package gestor;
import Contactos.*;
import Datos.*;

import gestor.Fabrica.ClienteFabrica;
import gestor.Fabrica.FabricaClientes;
import gestor.Fabrica.FabricaTarifas;
import gestor.Fabrica.TarifaFabrica;
import gestor.Tarifas.Tarifa;

import java.io.*;
import java.util.*;

import static Datos.Listar.*;

/**
 * Created by doomed on 8/03/16.
 */

public class Clientes implements Serializable{
    private static final long serialVersionUID = -65287598642850464L;

    private TreeMap<String,Cliente> estrucCliente;
    private List<Object> lista;


    public Clientes(){
        estrucCliente=new TreeMap<String, Cliente>();

    }

    public Map<String, Cliente> getEstrucCliente(){
        return estrucCliente;
    }

    public List<Object> getLista(){
       return lista;
   }
    public boolean añadirCliente(Cliente nuevo, String dni) {
        if (estrucCliente.containsValue(nuevo)){
            return false;
        }else {
            estrucCliente.put(dni, nuevo);
            return true;
        }
    }

    public boolean borrarCliente(String DNI) {
        if(estrucCliente.containsValue(estrucCliente.get(DNI))==true){
            estrucCliente.remove(DNI);
            return true;
        }else{
            return false;
        }

    }

    public Cliente recovery(String dni){
        if(estrucCliente.containsValue(estrucCliente.get(dni))){
            return estrucCliente.get(dni);
        } else
            return null;
    }

    public List<Llamada> getLlamadasFechas(Calendar fecha_emision, Calendar fecha_fin, String dni) {
        if(estrucCliente.containsValue(estrucCliente.get(dni))) {
            List<Llamada> lista = listarPorFecha(estrucCliente.get(dni).getLlamadas(), fecha_emision, fecha_fin);
            return lista;
        }else{
            return null;
        }
    }

    public List<Factura> getFacturasFechas(Calendar fecha_emision, Calendar fecha_fin, String dni) {
        if(estrucCliente.containsValue(estrucCliente.get(dni))) {
            List<Factura> listado = listaTreeMap(estrucCliente.get(dni).getFacturas());
            return listarPorFecha(listado, fecha_emision, fecha_fin);
        }else{
            return null;
        }
    }

    public List<Cliente> getClientesFechas(Calendar fecha_emision, Calendar fecha_fin) {
        List<Cliente> listado = listaTreeMap(estrucCliente);
        return  listarPorFecha(listado, fecha_emision, fecha_fin);
    }
    public List<Cliente> getListadoClientes() {
        return listaTreeMap(estrucCliente);
    }

    public void cambioTarifa(String DNI, Tarifa nuevaTarifa) {
        estrucCliente.get(DNI).setTarifa(nuevaTarifa);
    }

    public Factura devolverFactura(String dni, Long codigo) {
       return estrucCliente.get(dni).getFacturas().get(codigo);
    }

    public void nuevaFactura(String DNI, Calendar fecha_emision, Calendar fecha_facturacion) {
        Cliente nuevo = estrucCliente.get(DNI);
        Factura nueva = new Factura(nuevo.getTarifa(), fecha_emision, fecha_facturacion);
        ArrayList<Llamada> llamada=nuevo.getLlamadas();
        double precio = nueva.importe(listarPorFecha(llamada, fecha_emision,fecha_facturacion));
        nueva.setPrecio(precio);
        nuevo.getFacturas().put(nueva.getCodigo(), nueva);
    }
    public void llamar(String DNI, String nTelefono, double minutos) {
        Llamada llamada = new Llamada(nTelefono, minutos);
        estrucCliente.get(DNI).getLlamadas().add(llamada);
    }

    public List<Llamada> getLlamadas (String dni){
        return estrucCliente.get(dni).getLlamadas();
    }


}
