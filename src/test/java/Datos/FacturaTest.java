package Datos;

import gestor.Tarifas.Tarifa;
import gestor.Tarifas.TarifaBase;
import gestor.Tarifas.TarifaDia;
import gestor.Tarifas.TarifaHoraria;
import org.junit.Test;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by doomed on 8/04/16.
 */
public class FacturaTest {
    private Calendar fecha1=Calendar.getInstance();
    private Calendar fecha2=Calendar.getInstance();
    private Calendar fecha3=Calendar.getInstance();
    private Calendar fecha4=Calendar.getInstance();
    private Calendar fecha5=Calendar.getInstance();
    Tarifa tarifa;
    private Factura factura;
    @Test
    public void testImporte() throws Exception {
        tarifa=new TarifaBase(5);
        fecha5=new GregorianCalendar(2016,3,14,11,55);
        fecha1=new GregorianCalendar(2016,3,1,0,0);
        fecha2=new GregorianCalendar(2016,3,15,0,0);
        fecha3= new GregorianCalendar(2016,3,10,17,20);
        fecha4=new GregorianCalendar(2016,3,15,17,0);
        factura=new Factura(tarifa,fecha1,fecha2);

        Llamada llamada=new Llamada("5685898",5);
        Llamada llamada1=new Llamada("859747",6);
        Llamada llamada2= new Llamada("584278",4);

        llamada.setFecha(fecha5);
        llamada1.setFecha(fecha3);
        llamada2.setFecha(fecha4);

        List<Llamada> lista=new LinkedList<Llamada>();

        lista.add(llamada);
        lista.add(llamada1);
        lista.add(llamada2);

        assertEquals(75,factura.importe(lista),0);

        tarifa=new TarifaHoraria(tarifa,0.05);
        factura.setTarifa(tarifa);

        assertEquals(55.2,factura.importe(lista),0);

        tarifa=new TarifaDia(tarifa,0);
        factura.setTarifa(tarifa);

        assertEquals(25.2,factura.importe(lista),0);
    }
}
