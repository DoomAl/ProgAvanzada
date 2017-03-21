package gestor.Fabrica;

import Datos.Direccion;

/**
 * Created by doomed on 18/05/16.
 */
public class DireccionesFabrica implements FabricaDirecciones {
    @Override
    public Direccion getDireccion(int CP, String provincia, String poblacion) {
        return new Direccion(CP,provincia,poblacion);
    }
}
