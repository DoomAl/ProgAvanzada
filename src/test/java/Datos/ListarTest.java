package Datos;

import Contactos.Cliente;
import Contactos.Empresa;
import org.junit.Before;
import org.junit.Test;

import java.util.*;

import static Datos.Listar.listaTreeMap;
import static Datos.Listar.listarPorFecha;
import static org.junit.Assert.*;

/**
 * Created by doomed on 8/04/16.
 */
public class ListarTest {
    private TreeMap<String,Llamada> treemap;
    private Llamada llamada;
    private List<Llamada> lista;
    @Before
    public void setUp() throws Exception {
        lista= new LinkedList<Llamada>();
        llamada=new Llamada("569858",5);
    }

    @Test
    public void testListarPorFecha() throws Exception {
        Calendar inicio= Calendar.getInstance();
        Calendar fin= Calendar.getInstance();
        lista.add(llamada);

        inicio.set(2017,5,8);
        fin.set(2018,5,8);
        List<Llamada> copia=listarPorFecha(lista,inicio,fin);

        assertEquals(0,copia.size());

        inicio.set(2015,5,8,0,0);
        copia= listarPorFecha(lista,inicio, fin);

        assertEquals(1,copia.size());


    }

    @Test
    public void testListaTreeMap() throws Exception {
        treemap=new TreeMap<String,Llamada>();
        lista=listaTreeMap(treemap);
        assertEquals(0,lista.size());
        treemap.put(llamada.getNumero(),llamada);
        lista=listaTreeMap(treemap);
        assertEquals(1,lista.size());
    }

}