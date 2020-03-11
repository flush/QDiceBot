package flush.software.handler;

import java.util.regex.Pattern;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import flush.software.exceptions.HandleException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.InlineKeyboardMarkup;
import java.util.Random;

public class InactivityHandler implements IMessageHandler {

    private static final String ACTIVATION_PATTERN = "Inactividad[[|][+-]\\d*]*";

    private InactivityConfig config ; 

    public InactivityHandler() throws HandleException{

	config = InactivityConfig.loadConfig();
    }

    /** La expresion regular Java que activa el manejador */
    public Pattern getActivationPatter() {
	return Pattern.compile(ACTIVATION_PATTERN);
    }

    /**
     * Construye la respuesta.
     *
     * @param inputMessage Mensaje de entrada del bot
     * @return SendMessage respuesta del bot al mensaje
     */
    public SendMessage handleMessage(Message inputMessage,String callBackData) {
	SendMessage response = new SendMessage();
	String inputCommand = callBackData==null? inputMessage.getText():callBackData;

	if(inputCommand.indexOf("|")<0){
    	    //Si el mensaje es Inactividad se muestra el mensaje para pedir al jugador que
	    // elija la actividad en la que va a gastar la semana
	    InlineKeyboardMarkup kbMarkup = new InlineKeyboardMarkup();
	    kbMarkup.setKeyboard(config.getButtonsWeekJob());
	    response.setReplyMarkup(kbMarkup).
		setText(config.getTxtPCWeekJob());


	}
	else{
	    //El jugador ya ha elegido la actividad
	    System.out.println(inputCommand);
	    int bonus = Integer.valueOf(inputCommand.split("\\|")[1]);
	    int diceResult = new Random().nextInt(20)+1;
	    response.setText(String.format(config.getTxtResul(),diceResult,bonus,diceResult+bonus)+getResult(diceResult+bonus));



	}

	return response;
    }



    /**
     * Obtiene el resultado a aplicar segun la tirada
     * @param resultado de la tirada de inactividad aplicando los bonus
     * @result Texto donde se explica la tirada de inactividad del personaje
     */
    public String getResult(int total){
	String resultTxt="";
	for(InactivityResult result : config.getInactivityResults()){

	    if(result.getRangeInit()<=total && result.getRangeEnd()>=total){
		resultTxt = result.getText();
		break;
	    }
	}

	return resultTxt;
	
    }
}
