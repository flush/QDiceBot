package flush.software;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import java.util.Properties;
import java.io.IOException;

import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * QDiceRollBot
 * Implementación de un bot con tiradas de dados para las partidas online de Rol. 
 */
public class QDiceRollBot extends TelegramLongPollingBot {


    /**
     * Configuración del teclado de dados
     */
    private final static String[][] buttons = {{"1D4","1D6","1D8","1D10","1D12","1D20"} ,
					       {"2D4","2D6","2D8","2D10","2D12","2D20"} ,
					       {"3D4","3D6","3D8","3D10","1D100","2D100"}};

    private final static String PROP_FILE= "qdiceroll.properties";


    /**
     * Cadena para detectar el comando de inicio del bot
     */
    private final static String START = "start";

    /**
     * OnUpdatereceived
     * Se ejecuta cada vez que el bot de telegram recibe un mensaje
     * @param  Update Mensaje que ha recibido el bot
     */
    @Override
    public void onUpdateReceived(Update update) {


	// Se comprueba si la actualización contiene un mensaje
	if (update.hasMessage() && update.getMessage().hasText()) {
	    SendMessage message = new SendMessage();
	    try {
		//Si el mensaje contiene la cadena start devolvemos el mensaje "bot Iniciado" y cambiado el keyboard.
		if(update.getMessage().getText().indexOf(START)>0){
		    message.setChatId(update.getMessage().getChatId())
			.setText("Bot iniciado").setReplyMarkup((ReplyKeyboard)buildKeyBoard());
		}
		//Si el mensaje no contiene la cadena start, respondemos con la tirada de dados correspondiente.
		// Además se mantiene el teclado de tiradas y se responde al mensaje en el que se solicitó el comando del bot
		else{
		    message.setChatId(update.getMessage().getChatId())
			.setText(rollDices(update.getMessage().getText()));
		    message.setReplyMarkup((ReplyKeyboard)buildKeyBoard());
		    message.setReplyToMessageId(update.getMessage().getMessageId());
		} 
		execute(message); // Devolver el mensaje
	    } catch (TelegramApiException e) {
		e.printStackTrace();
	    }
	    catch(Exception e){
		e.printStackTrace();
	    }
	}
    }

    /**
     * Devuelve el nombre del bot
     */
    @Override
    public String getBotUsername() {

        return "QDiceroll";
    }

    /**
     * Devuelve la clave del Bot obtenida del archivo "qdiceroll.properties" bajo la propiedad "key"
     * Si no es capaz de obtener la clave, se sale de la aplicación
     */
    @Override 
    public String getBotToken() {
	String key = null;
	try{
	    Properties props = new Properties();
	    props.load(QDiceRollBot.class.getClassLoader().getResourceAsStream(PROP_FILE));
	    key =  props.getProperty("key");
	}
	catch (IOException e){
	    System.err.println("Error fatal, no se encuentra la clave de telegram o el archivo qdiceroll.properties");
	    System.exit(1);
	}
	catch (NullPointerException e){
	    System.err.println("Error fatal, no se encuentra la clave de telegram o el archivo qdiceroll.properties");
	    System.exit(1);
	}
	return key;
    }

    /**
     * Construye el teclado
     * @return ReplyKeyBoardMarkup 
     */
    private  ReplyKeyboardMarkup buildKeyBoard(){
	List<KeyboardRow> rowButtons= new ArrayList<KeyboardRow>();
	for(int i =0; i< buttons.length;i++ ){
	    KeyboardRow  kbr = new KeyboardRow();
	    kbr.addAll(Arrays.asList(buttons[i]));
	    rowButtons.add(kbr);
	}
	return new ReplyKeyboardMarkup(rowButtons).setResizeKeyboard(true);
    }

    /**
     * Realiza la tirada de dados correspondiente.
     * @param roll Espera una cadena del tipo NDM donde N es un número entero que indica cuantos dados se lanzan,D es el separado y M 
     * es el número de caras del dado
     * @return Devuelve el resultado de cada dado, separado por comas, y depues la suma de todos los dados lanzados
     */
    private String rollDices(String roll) throws Exception{
	//Si el comando empieza por /, está se elimina
	if(roll.startsWith("/")){
	    roll=roll.substring(1);
	}
	int numberOfDices = Integer.valueOf(roll.split("D")[0]);
	int typeOfDices  = Integer.valueOf(roll.split("D")[1]);
	
	Random random = new Random();
	StringBuffer sbf = new StringBuffer();
	int total = 0;
	for(int i=0;i<numberOfDices;i++){
	    int result= random.nextInt(typeOfDices)+1;
	    total +=result;
	    //Solo si hay más de un dado se va construyendo la cadena,
	    if(numberOfDices>1){
		sbf.append(String.valueOf((result)));
		if(i+1 < numberOfDices){
		    sbf.append(",");
		}
	    }
	   
	}

	//Solo si hay más de un dado se muestra el igual
	if(numberOfDices>1){
	    sbf.append(" = ");

	}
	sbf.append(String.valueOf(total));

	return sbf.toString();

	

    }
}
