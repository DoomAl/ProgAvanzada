package excepciones;

/**
 * Created by doomed on 5/04/16.
 */
public class IntervaloFecha extends Exception {
    public IntervaloFecha(){
        super("Fecha facturacion anterior a la emision");
    }
}
