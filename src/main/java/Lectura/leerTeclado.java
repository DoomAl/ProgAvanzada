package Lectura;

import Salida.SalidaPantalla;
import gestor.Fabrica.TarifaFabrica;
import gestor.Tarifas.Tarifa;
import gestor.Tarifas.TarifaBase;
import gestor.Tarifas.TarifaDia;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Scanner;


/**
 * Created by doomed on 8/03/16.
 */
public class leerTeclado implements Lectura{

    private Scanner scanner;
    private SalidaPantalla salida=new Salida.SalidaPantalla();

    public leerTeclado(){
        scanner= new Scanner(System.in);
    }
    
    public String leeNombre(){
        salida.salGeneral("Nombre: ");
        return scanner.next();
    }

    public String leeApellido(){
        salida.salGeneral("Apellido: ");
        return scanner.next();
    }

    public String leeDNI(){
        salida.salGeneral("DNI del cliente: ");
        return scanner.next();
    }

    public String leeEmail(){
        salida.salGeneral("E-mail: ");
        return scanner.next();
    }

    public int leeCodigoPostal(){
        salida.salGeneral("Código postal: ");
        return scanner.nextInt();
    }



    public String leeProvincia(){
        salida.salGeneral("Provincia: ");
        return scanner.next();
    }

    public String leePoblacion(){
        salida.salGeneral("Población: ");
        return scanner.next();
    }

    public String leeTelefono(){ //telefono al que llaman
        salida.salGeneral("Nº de telefono: ");
        return scanner.next();
    }

    public Tarifa leeTarifa(){
        TarifaFabrica fabricaTarifa=new TarifaFabrica();
        salida.salGeneral("Tarifa (euro/min): ");
        double tarifa= scanner.nextDouble();
        return fabricaTarifa.NuevaTarifaBase(tarifa);
    }

    @Override
    public Calendar leerFecha() {
        String fecha=scanner.next();
        String [] argum=fecha.split("/");
        int año = Integer.parseInt(argum[2]);
        int mes= Integer.parseInt(argum[1]);
        int dia= Integer.parseInt(argum[0]);
        Calendar devolver=new GregorianCalendar();
        devolver.set(año,mes-1,dia,0,0,0);
        return devolver;
    }

    public Calendar leeFechaEmision(){
        salida.salGeneral("Introduce la fecha de emision(dd/mm/aaaa): ");
        Calendar fecha=leerFecha();
        return fecha;
    }

    public Calendar leeFechaFacturacion(){
        salida.salGeneral("Introduce la fecha de facturacion (dd/mm/aaaa): ");
        Calendar fecha=leerFecha();
        return fecha;
    }


    public String leeTipo() {
        salida.salGeneral("¿(E)mpresa o (P)articular?: ");
        return scanner.next();
    }

    public double leeMinutos(){
        salida.salGeneral("Minutos de duración: ");
        return scanner.nextDouble();
    }

    @Override
    public long leeCodigo() {
        salida.salGeneral("Código de factura: ");
        return scanner.nextLong();
    }

    @Override
    public int leeExtraTarifa() {
        salida.salGeneral("¿A qué tarifa quieres cambiar?\n\t1.-Los Domingos llamadas gratis\n\t2.-De lunes a sabado de 16:00 a 20:00 a 0.05 € el minuto\n\t3.-Cambiar el precio de la tarifa Base.\n¿Qué opción eliges?: ");
        return scanner.nextInt();

    }
}
