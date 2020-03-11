package flush.software.handler;

/**
 * Clase que respresenta un posible resultado de una actividad.
 */
public class InactivityResult {

    /**
     * Inicio del rango (incluido) en el que se aplica la inactividad
     */
    private int rangeInit;

    /**
     * Fin del rango (incluido) en el que se aplica la inactividad
     */
    private int rangeEnd;

    /**
     * Texto descriptivo de la inactividad
     */ 
    private String text;


    public int getRangeInit(){
	return rangeInit;

    }

    public void setRangeInit(int _rangeInit){
	this.rangeInit = _rangeInit;
    }

   public int getRangeEnd(){
	return rangeEnd;

    }

    public void setRangeEnd(int _rangeEnd){
	this.rangeEnd = _rangeEnd;
    }


    public String getText(){
	return this.text;
    }
    public void setText(String _text){
	this.text=_text;
    }
}
