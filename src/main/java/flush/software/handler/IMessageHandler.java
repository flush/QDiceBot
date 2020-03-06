package flush.software.handler;

import flush.software.exceptions.KeyBoardException;
import java.util.regex.Pattern;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;

/** Interfaz para los manejadores de Mensajes del telegram */
public interface IMessageHandler {

  /** La expresiiÃ³n regular Java que activa el manejador */
  Pattern getActivationPatter();

  /**
   * Construye la respuesta.
   *
   * @param inputMessage Mensaje de entrada del bot
   * @return SendMessage respuesta del bot al mensaje
   */
  SendMessage handleMessage(Message inputMessage) throws KeyBoardException;
}
