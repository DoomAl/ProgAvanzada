package gestor.Fabrica;

import Datos.Direccion;

/**
 * Created by doomed on 18/05/16.
 */
public interface FabricaDirecciones {
    Direccion getDireccion(int CP, String provincia, String poblacion);
}
