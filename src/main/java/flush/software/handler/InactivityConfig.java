package flush.software.handler;

import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.InlineKeyboardButton;
import java.util.List;
import com.fasterxml.jackson.databind.ObjectMapper;
import flush.software.exceptions.HandleException;
import java.io.IOException;
import com.google.common.io.ByteStreams;

/**
 * Representa la configuración necesaria para que funcione el Handler de inactividad
 */

public class InactivityConfig {

    private static final String CONFIG_FILE="inactivity.json";
    /**
     * Texto en el que se solicita al jugador que indique en que actividad va a gastar su semana
     */
    private String txtPCWeekJob;

    /**
     * Texto para comunicar el resultado numerico de la inactividad
     */
    private String txtResult;


    /**
     * Botones que componente las opciones en ls que un jugador va a gastar la semana de inactividad
     */
    private List<List<InlineKeyboardButton>> buttonsWeekJob;

    /**
     * Posibles resultados de inactividad.
     */
    private List<InactivityResult> inactivityResults;

    public String getTxtPCWeekJob(){
	return txtPCWeekJob;
    }
    public void setTxtPCWeekJob(String _txtPCWeekJob){
	this.txtPCWeekJob=_txtPCWeekJob;
    }
    public List<List<InlineKeyboardButton>> getButtonsWeekJob(){
	return buttonsWeekJob;
    }
    public void setButtosWeekJob(List<List<InlineKeyboardButton>> _buttonsWeekJob){
	this.buttonsWeekJob=_buttonsWeekJob;
    }

    public List<InactivityResult> getInactivityResults(){
	return inactivityResults;
    }
    public void setInactivityResults(List<InactivityResult> _inactivityResults){
	this.inactivityResults= _inactivityResults;
    }

    public String getTxtResul(){
	return txtResult;
    }

    public void setTxtResult(String _txtResult){
	this.txtResult=_txtResult;
    }
    /**
     * Carga la configuración de inactividad del archivo inactivity.json
     */
    public static InactivityConfig loadConfig() throws HandleException{
	ObjectMapper objMapper = new ObjectMapper();
	InactivityConfig config = null;
	try{

	    config = objMapper.readValue(InactivityConfig.class.getClassLoader().getResourceAsStream(CONFIG_FILE),InactivityConfig.class);
	}
	catch (IOException ioe){
	    throw HandleException.getErrorConfigException("Inactivity Handler", ioe);
	}
	return config;

    }
    

}
