package Controlador;

import Modelo.CambiaModelo;
import Modelo.ImplementacionModelo;
import Vista.Interfaces.ActualizaVistaPagPrincipal;
import Vista.Interfaces.InterrogaVista;
import Vista.Pag_principal_vista;
import gestor.Clientes;

import javax.swing.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by doomed on 3/05/16.
 */
public class ImplementacionControlador implements Controlador{

    private CambiaModelo modelo;
    private InterrogaVista vista;
    public void setModelo(CambiaModelo modelo) {
        this.modelo = modelo;
    }

    public void setVista(InterrogaVista vista) {
        this.vista = vista;
    }

    public ImplementacionControlador(){}

    @Override
    public void nuevoCliente() {

        String[] argumento =vista.getAtributosClientes();
        if(vista.isEmpresa()){
            modelo.anyadeEmpresa(argumento);
        }else{
            modelo.anyadeCliente(argumento);
        }


    }

    @Override
    public void nuevaFactura() {
        String dni=vista.getDni();
        List<Calendar> lista=vista.getFechasBusquedas();
        modelo.anyadeFactura(dni,lista);
    }

    @Override
    public void nuevaTarifa() {
        boolean isDia=vista.isDia();
        String dni = vista.getDni();
        modelo.anyadeTarifa(isDia,dni);

    }

    @Override
    public void nuevaLlamada() {
        String dni=vista.getDni();
        String [] datos=vista.getCampoLlamadas();
        modelo.anyadeLlamada(dni,datos[1],Double.parseDouble(datos[0]));
    }

    @Override
    public void buscarCliente() {
        List<Calendar> lista= vista.getFechasBusquedas();
        modelo.filtraClientesFechas(lista);
    }

    @Override
    public void buscarFactura() {
        String dni=vista.getDni();
        List<Calendar> lista=vista.getFechasBusquedas();
        modelo.filtraFacturasFechas(dni,lista);
    }

    @Override
    public void busacrLlamada() {
        String dni=vista.getDni();
        List<Calendar> lista=vista.getFechasBusquedas();
        modelo.filtraLlamadasFechas(dni,lista);
    }

    @Override
    public void eliminaDatos() {
        String dni=vista.getDniEliminar();
        modelo.eliminaClientes(dni);

    }



    @Override
    public void almacenaDatos() {
        modelo.guardaDatos();
    }

    @Override
    public void recuperaDatos() {

    }

    @Override
    public void nuevaTarifaBase() {
        modelo.cambiarTarifaBase(vista.getPrecioTarifaBaseNueva(), vista.getDni());
    }
}
