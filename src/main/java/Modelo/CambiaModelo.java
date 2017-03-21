package Modelo;

import javax.swing.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by doomed on 4/05/16.
 */
public interface CambiaModelo {
    void anyadeCliente(String[] atributos);
    void anyadeEmpresa(String[] atributos);
    void anyadeFactura(String dni, List<Calendar> lista);
    void anyadeTarifa(boolean isDia,String dni);
    void anyadeLlamada(String dni, String telefono, double minutos);
    void filtraClientesFechas(List<Calendar> lista);
    void filtraLlamadasFechas(String dni, List<Calendar>lista);
    void filtraFacturasFechas(String dni, List<Calendar>lista);
    void eliminaClientes(String dni);
    void guardaDatos();
    void cargaDatos();
    void cambiarTarifaBase(double precio, String dni);
}
