package Lectura;

import gestor.Tarifas.Tarifa;

import java.util.Calendar;

/**
 * Created by doomed on 14/03/16.
 */

public interface Lectura {

    String leeNombre();

    String leeApellido();

    String leeDNI();

    String leeEmail();

    int leeCodigoPostal();

    String leeProvincia();

    String leePoblacion();

    String leeTelefono();

    Tarifa leeTarifa();

    Calendar leerFecha();

    Calendar leeFechaEmision();

    Calendar leeFechaFacturacion();

    String leeTipo();

    double leeMinutos();

    long leeCodigo();

    int leeExtraTarifa();
}
