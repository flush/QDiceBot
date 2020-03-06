package flush.software.handler;

import java.util.regex.Pattern;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

public class InactivityHandler implements IMessageHandler {

  private static final String ACTIVATION_PATTERN = "inactivity";

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
  public SendMessage handleMessage(Message inputMessage) {
    SendMessage respuesta = new SendMessage();
    return respuesta;
  }
}
