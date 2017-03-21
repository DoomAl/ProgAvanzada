package gestor.Tarifas;

/**
 * Created by doomed on 12/04/16.
 */
public abstract class TarifaExtra extends Tarifa {
    private Tarifa tarifaRecubierta;

    public TarifaExtra(Tarifa tarifa, double precio){
        super(precio);
        this.tarifaRecubierta =tarifa;

    }

    @Override
    public Double getPrecio(){
        return super.getPrecio();
    }

    public Tarifa getTarifaRecubierta(){
        return tarifaRecubierta;
    }


}
