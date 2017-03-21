package excepciones;

/**
 * Created by doomed on 6/04/16.
 */
public class ComprobarCliente extends Exception {
    public ComprobarCliente(){
        super("Este dni no pertenece a ningun cliente");
    }
}
