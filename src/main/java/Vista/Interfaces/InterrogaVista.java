package Vista.Interfaces;

import javax.swing.*;
import java.util.Calendar;
import java.util.List;

/**
 * Created by doomed on 4/05/16.
 */
public interface InterrogaVista extends ActualizaVistaPagPrincipal{
    double getTarifaBase();
    String[] getAtributosClientes();
    boolean isEmpresa();
    String getDniEliminar();
    boolean isDia();
    double getPrecioTarifaBaseNueva();
    String getDni();
    List<Calendar> getFechasBusquedas();
    String [] getCampoLlamadas();
}
