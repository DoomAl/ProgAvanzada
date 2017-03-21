package gestor;

import Contactos.Cliente;
import Contactos.Empresa;
import Datos.Factura;
import gestor.Tarifas.Tarifa;
import gestor.Tarifas.TarifaBase;
import org.junit.Before;
import org.junit.Test;

import java.util.Calendar;
import java.util.TreeMap;

import static org.junit.Assert.*;

/**
 * Created by doomed on 8/04/16.
 */
public class ClientesTest {
    private Clientes clientes;
    private Empresa nuevo;
    @Before
    public void setUp() throws Exception {
        clientes=new Clientes();
        nuevo=new Empresa("Hola","537854") ;
    }

    @Test
    public void testGetEstrucCliente() throws Exception {
        TreeMap<String,Cliente> prueba=new TreeMap<String,Cliente>();
        assertEquals(prueba,clientes.getEstrucCliente());
    }


    @Test
    public void testAñadirCliente() throws Exception {
        assertTrue(clientes.añadirCliente(nuevo,nuevo.getDni()));
        assertFalse(clientes.añadirCliente(nuevo,nuevo.getDni()));
    }

    @Test
    public void testBorrarCliente() throws Exception {
        assertFalse(clientes.borrarCliente(nuevo.getDni()));
        clientes.añadirCliente(nuevo,nuevo.getDni());
        assertTrue(clientes.borrarCliente(nuevo.getDni()));
        assertEquals(0,clientes.getEstrucCliente().size());
    }

    @Test
    public void testRecovery() throws Exception {
        assertEquals(null,clientes.recovery(nuevo.getDni()));
        clientes.añadirCliente(nuevo,nuevo.getDni());
        assertEquals(nuevo, clientes.recovery(nuevo.getDni()));
    }


    @Test
    public void testCambioTarifa() throws Exception {
        clientes.añadirCliente(nuevo,nuevo.getDni());
        Tarifa tarifa =new TarifaBase(0);
        nuevo.setTarifa(tarifa);
        assertEquals(0.0, clientes.getEstrucCliente().get(nuevo.getDni()).getTarifa().getPrecio(),0);
        tarifa=new TarifaBase(5.0);
        clientes.getEstrucCliente().get(nuevo.getDni()).setTarifa(tarifa);
        assertEquals(5.0, clientes.getEstrucCliente().get(nuevo.getDni()).getTarifa().getPrecio(),0);
    }

    @Test
    public void testDevolverFactura() throws Exception {
        Calendar fecha = Calendar.getInstance();
        Calendar fecha2=Calendar.getInstance();
        Tarifa tarifa= new TarifaBase(5);
        Factura nueva=new Factura(tarifa,fecha,fecha2);
        clientes.añadirCliente(nuevo,nuevo.getDni());
        nuevo.getFacturas().put(nueva.getCodigo(),nueva);
        assertEquals(nueva,clientes.getEstrucCliente().get(nuevo.getDni()).getFacturas().get(nueva.getCodigo()));
    }

    @Test
    public void testNuevaFactura() throws Exception {
        Calendar fecha = Calendar.getInstance();
        Calendar fecha2=Calendar.getInstance();
        Tarifa tarifa= new TarifaBase(5);
        Factura esperada=new Factura(tarifa,fecha,fecha2);
        clientes.añadirCliente(nuevo,nuevo.getDni());
        nuevo.getFacturas().put(esperada.getCodigo(),esperada);
        assertEquals(esperada,clientes.getEstrucCliente().get(nuevo.getDni()).getFacturas().get(esperada.getCodigo()));
    }

    @Test
    public void testLlamar() throws Exception {
        clientes.añadirCliente(nuevo,nuevo.getDni());
        clientes.llamar(nuevo.getDni(),"646509577",5);
        assertEquals(clientes.getLlamadas(nuevo.getDni()),nuevo.getLlamadas());
    }

}
