package Datos;


import java.io.Serializable;
import java.util.*;

/**
 * Created by doomed on 22/03/16.
 */
public class Listar {

    public static <T extends Fecha> List<T> listarPorFecha(List<T> lista, Calendar inicio, Calendar fin){
        LinkedList<T> devolver=new LinkedList<T>();
        for (T elem : lista){
            if (elem.getFecha().compareTo(inicio)>=0 && elem.getFecha().compareTo(fin)<=0){
                devolver.add(elem);
            }
        }
        return devolver;
    }

    public static <T1,T2> List<T2> listaTreeMap(Map<T1, T2> map) {
        List<T2> devolver = new LinkedList<T2>();
        if(!map.isEmpty()) {

            Set<T1> conjunto = map.keySet();
            Iterator<T1> iter = conjunto.iterator();
            while (iter.hasNext()) {
                T1 key = iter.next();
                T2 value = map.get(key);
                devolver.add(value);
            }
        }
        return devolver;
    }

}
