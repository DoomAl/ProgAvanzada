ackage main;

        import Contactos.*;
        import Controlador.ImplementacionControlador;
        import Datos.*;
        import Modelo.ImplementacionModelo;
        import Salida.*;

        import Vista.Pag_principal_vista;
        import excepciones.*;

        import gestor.Clientes;
        import gestor.Fabrica.ClienteFabrica;
        import gestor.Fabrica.TarifaFabrica;
        import gestor.Tarifas.Tarifa;
        import Lectura.*;

        import java.io.*;
        import java.util.*;

/**
 * Created by doomed on 8/04/16.
 */
public class Main {

    public static void main(String[] args) {
        new Main().ejecuta();
    }

    private void ejecuta() {

        ImplementacionControlador controlador = new ImplementacionControlador();
        Pag_principal_vista vista = new Pag_principal_vista();
        ImplementacionModelo modelo = new ImplementacionModelo();
        vista.setControlador(controlador);
        controlador.setVista(vista);
        controlador.setModelo(modelo);
        modelo.setVista(vista);
        vista.setModelo(modelo);
        vista.creaGUI();
    }

}
