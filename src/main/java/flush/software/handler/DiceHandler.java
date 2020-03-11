package flush.software.handler;

import flush.software.exceptions.KeyBoardException;
import flush.software.keyboards.KeyBoardFactory;
import java.util.Random;
import java.util.regex.Pattern;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;

public class DiceHandler implements IMessageHandler {

  private static final String ACTIVATION_PATTERN = "\\d*[dD]\\d*";

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
    public SendMessage handleMessage(Message inputMessage,String callBackData) throws KeyBoardException {
    SendMessage response = new SendMessage();
    response.setText(rollDices(inputMessage.getText()));
    response.setReplyMarkup((ReplyKeyboard) buildKeyBoard());

    return response;
  }

  /**
   * Construye el teclado
   *
   * @return ReplyKeyBoardMarkup
   */
  private ReplyKeyboardMarkup buildKeyBoard() throws KeyBoardException {

    return KeyBoardFactory.buildKeyBoard(KeyBoardFactory.MAIN_KB_ID);
  }

  /**
   * Realiza la tirada de dados correspondiente.
   *
   * @param roll Espera una cadena del tipo NDM donde N es un número entero que indica cuantos
   *     dados se lanzan,D es el separado y M es el número de caras del dado
   * @return Devuelve el resultado de cada dado, separado por comas, y depues la suma de todos los
   *     dados lanzados
   */
  private String rollDices(String roll) {
    // Si el comando empieza por /, este se elimina
    if (roll.startsWith("/")) {
      roll = roll.substring(1);
    }
    int numberOfDices = Integer.valueOf(roll.split("D")[0]);
    int typeOfDices = Integer.valueOf(roll.split("D")[1]);

    Random random = new Random();
    StringBuffer sbf = new StringBuffer();
    int total = 0;
    for (int i = 0; i < numberOfDices; i++) {
      int result = random.nextInt(typeOfDices) + 1;
      total += result;
      sbf.append(String.valueOf((result))).append(",");
    }
    // Se borra la última coma
    sbf.deleteCharAt(sbf.length() - 1);

    // Solo si hay más de un dado se muestra el igual
    if (numberOfDices > 1) {
      sbf.append(" = ").append(String.valueOf(total));
    }

    return sbf.toString();
  }
}
