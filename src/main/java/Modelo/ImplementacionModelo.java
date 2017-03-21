package Modelo;

import Contactos.Cliente;
import Controlador.Controlador;
import Datos.Direccion;
import Datos.Factura;
import Datos.Listar;
import Datos.Llamada;
import Vista.Interfaces.ActualizaVistaPagPrincipal;
import Vista.Pag_principal_vista;
import gestor.Clientes;
import gestor.Fabrica.*;
import gestor.Tarifas.Tarifa;
import sun.util.resources.cldr.ne.CalendarData_ne_IN;

import javax.swing.*;
import java.io.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by doomed on 10/05/16.
 */
public class ImplementacionModelo implements CambiaModelo, InterrogaModelo {
    private Clientes cliente;
    private ActualizaVistaPagPrincipal vista;
    private FabricaClientes fabricaClientes= new ClienteFabrica();
    private FabricaDirecciones fabricaDirecciones = new DireccionesFabrica();
    private FabricaTarifas fabricaTarifas = new TarifaFabrica();

    public ImplementacionModelo(){
        cargaDatos();
        if (cliente==null)
            cliente=new Clientes();


    }

    @Override
    public void anyadeCliente(String[] argumentos) {
        Cliente nuevo=fabricaClientes.ClienteNuevoParticular(argumentos[1],argumentos[2],argumentos[0]);
        Direccion direccion=fabricaDirecciones.getDireccion(Integer.parseInt(argumentos[6]),argumentos[4],argumentos[5]);
        Tarifa tarifa= fabricaTarifas.NuevaTarifaBase(Double.parseDouble(argumentos[argumentos.length-1]));
        nuevo.setTarifa(tarifa);
        nuevo.setDireccion(direccion);
        nuevo.setCorreoElectronico(argumentos[3]);
        cliente.añadirCliente(nuevo,argumentos[0]);
        vista.ActualizarListaClientes(listaClientes());
    }

    @Override
    public void anyadeEmpresa(String[] argumentos) {
        Cliente nuevo=fabricaClientes.ClienteNuevoEmpresa(argumentos[1],argumentos[0]);
        Direccion direccion=fabricaDirecciones.getDireccion(Integer.parseInt(argumentos[6]),argumentos[4],argumentos[5]);
        Tarifa tarifa= fabricaTarifas.NuevaTarifaBase(Double.parseDouble(argumentos[argumentos.length-1]));
        nuevo.setTarifa(tarifa);
        nuevo.setDireccion(direccion);
        nuevo.setCorreoElectronico(argumentos[3]);
        cliente.añadirCliente(nuevo,argumentos[0]);
        vista.ActualizarListaClientes(listaClientes());
    }

    @Override
    public void anyadeFactura(String dni, List<Calendar>lista) {
        cliente.nuevaFactura(dni,lista.get(0),lista.get(1));
        vista.ActualizarListaFacturas(Listar.listaTreeMap(cliente.getEstrucCliente().get(dni).getFacturas()));
    }

    @Override
    public void anyadeTarifa(boolean isDia, String dni) {
        Tarifa nueva;
        if(isDia){
            nueva=fabricaTarifas.NuevaTarifaDia(cliente.getEstrucCliente().get(dni).getTarifa(),0);
        }else{
            nueva=fabricaTarifas.NuevaTarifaHoraria(cliente.getEstrucCliente().get(dni).getTarifa(),0.05);
        }
        cliente.cambioTarifa(dni,nueva);
        vista.ActualizarTarifa();


    }

    @Override
    public void anyadeLlamada(String dni, String telefono, double minutos) {
        cliente.llamar(dni,telefono,minutos);
        vista.ActualizarListaLlamadas(cliente.getLlamadas(dni));
    }

    @Override
    public void filtraClientesFechas(List<Calendar> lista) {
        vista.ActualizarListaClientes(cliente.getClientesFechas(lista.get(0),lista.get(1)));
    }

    @Override
    public void filtraLlamadasFechas(String dni, List<Calendar> lista) {
        vista.ActualizarListaLlamadas(cliente.getLlamadasFechas(lista.get(0),lista.get(1),dni));
    }

    @Override
    public void filtraFacturasFechas(String dni, List<Calendar> lista) {
        vista.ActualizarListaFacturas(cliente.getFacturasFechas(lista.get(0),lista.get(1),dni));
    }

    @Override
    public Cliente filtraCliente(String dni) {
        return cliente.getEstrucCliente().get(dni);

    }

    @Override
    public Factura filtaFactura(String dni,long codfac) {
        return cliente.devolverFactura(dni,codfac);
    }

    @Override
    public void filtraLlamada() {

    }

    @Override
    public void eliminaClientes(String dni) {
        cliente.borrarCliente(dni);
        vista.ActualizarListaClientes(listaClientes());
    }


    @Override
    public void guardaDatos() {
        ObjectOutputStream oos=null;
        try {
            try {
                FileOutputStream fos = new FileOutputStream("proyecto.bin");
                oos = new ObjectOutputStream(fos);
                oos.writeObject(cliente);
            }
            finally {
                oos.close();
            }
        }
        catch(FileNotFoundException exc) {
            System.out.println("El fichero no existe.");
            exc.printStackTrace();
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
    }

    @Override
    public void cargaDatos() {
        ObjectInputStream ois = null;
        try{
            try {
                FileInputStream fis = new FileInputStream("proyecto.bin");
                ois = new ObjectInputStream(fis);
                cliente=(Clientes) ois.readObject();
            }
            finally {

                if(ois != null) ois.close();
            }
        }
        catch(FileNotFoundException exc) {
            System.err.println("Fichero de datos no existe. Se crea una nueva base de datos.");
        }
        catch(IOException exc) {
            exc.printStackTrace();
        }
        catch(ClassNotFoundException exc) {
            exc.printStackTrace();
        }
    }


    @Override
    public List<Cliente> listaClientes() {
        return cliente.getListadoClientes();
    }

    @Override
    public List<Llamada> listaLlamadas(String dni) {
        return cliente.getLlamadas(dni);
    }

    @Override
    public List<Factura> listaFacturas(String dni) {
        return Listar.listaTreeMap(cliente.getEstrucCliente().get(dni).getFacturas());
    }

    @Override
    public Tarifa getTarifa(String dni) {
        return cliente.getEstrucCliente().get(dni).getTarifa();
    }

    @Override
    public List<String> getDnis() {
        return null;
    }

    @Override
    public List<Integer> getCodigos() {
        return null;
    }

    public void setVista(ActualizaVistaPagPrincipal vista){
        this.vista=vista;
    }

    @Override
    public void cambiarTarifaBase(double precio, String dni){
        cliente.getEstrucCliente().get(dni).setTarifa(fabricaTarifas.NuevaTarifaBase(precio));
        vista.ActualizarTarifa();
    }

}
