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

public class QDiceRollBot extends TelegramLongPollingBot {


    private final static String[][] buttons = {{"1D4","1D6","1D8","1D10","1D12","1D20"} ,
					       {"2D4","2D6","2D8","2D10","2D12","2D20"} ,
					       {"3D4","3D6","3D8","3D10","1D100","2D100"}};



    private final static String START = "start";
    @Override
    public void onUpdateReceived(Update update) {

	// We check if the update has a message and the message has text
	if (update.hasMessage() && update.getMessage().hasText()) {
	    SendMessage message = new SendMessage();
	    try {  
		if(update.getMessage().getText().indexOf(START)>0){

		    message.setChatId(update.getMessage().getChatId())
			.setText("Bot iniciado").setReplyMarkup((ReplyKeyboard)buildKeyBoard());
		}
		else{
		    message.setChatId(update.getMessage().getChatId())
			.setText(rollDices(update.getMessage().getText()));
		    message.setReplyMarkup((ReplyKeyboard)buildKeyBoard());
		    message.setReplyToMessageId(update.getMessage().getMessageId());

		} 

		execute(message); // Call method to send the message
	    } catch (TelegramApiException e) {
		e.printStackTrace();
	    }
	    catch(Exception e){
		e.printStackTrace();
	    }
	}
    }

    @Override
    public String getBotUsername() {

        return "QDiceroll";
    }

    @Override 
    public String getBotToken() {
	String key = null;
	try{
	    Properties props = new Properties();
	    props.load(QDiceRollBot.class.getClassLoader().getResourceAsStream("qdiceroll.properties"));
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
     * Builds the keyboard
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

    private String rollDices(String roll) throws Exception{
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
	    if(numberOfDices>1){
		sbf.append(String.valueOf((result)));
		if(i+1 < numberOfDices){
		    sbf.append(",");
		}
	    }
	   
	}

	if(numberOfDices>1){
	    sbf.append(" = ");

	}
	sbf.append(String.valueOf(total));

	return sbf.toString();

	

    }
}
