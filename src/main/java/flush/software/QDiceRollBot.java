package flush.software;

import flush.software.handler.HandlerManager;
import flush.software.handler.IMessageHandler;
import flush.software.keyboards.KeyBoardFactory;
import java.io.IOException;
import java.util.List;
import java.util.Properties;
import org.telegram.telegrambots.bots.DefaultBotOptions;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

/** QDiceRollBot Implementación de un bot con tiradas de dados para las partidas online de Rol. */
public class QDiceRollBot extends TelegramLongPollingBot {

  private static final String PROP_FILE = "qdiceroll.properties";

  /** Cadena para detectar el comando de inicio del bot */
  private static final String START = "start";

  private static final Properties config = new Properties();

  private static List<IMessageHandler> handlers;

  public QDiceRollBot() {
    super();
    readConfig();
    HandlerManager.configureHandlers(config);
  }

  public QDiceRollBot(DefaultBotOptions options) {
    super(options);
    readConfig();
    HandlerManager.configureHandlers(config);
  }

  private void readConfig() {
    try {
      config.load(QDiceRollBot.class.getClassLoader().getResourceAsStream(PROP_FILE));
    } catch (IOException e) {
      System.err.println(
          "Error fatal, no se encuentra la clave de telegram o el archivo qdiceroll.properties");
      System.exit(1);
    } catch (NullPointerException e) {
      System.err.println(
          "Error fatal, no se encuentra la clave de telegram o el archivo qdiceroll.properties");
      System.exit(1);
    }
  }

  /**
   * OnUpdatereceived Se ejecuta cada vez que el bot de telegram recibe un mensaje
   *
   * @param Update Mensaje que ha recibido el bot
   */
  @Override
  public void onUpdateReceived(Update update) {

    // Se comprueba si la actualización contiene un mensaje
    if (update.hasMessage() && update.getMessage().hasText()) {
      SendMessage message;
      try {
        // Si el mensaje contiene la cadena start devolvemos el mensaje "bot Iniciado" y cambiado el
        // keyboard.
        if (update.getMessage().getText().indexOf(START) > 0) {
          message = new SendMessage();
          message
              .setChatId(update.getMessage().getChatId())
              .setText("Bot iniciado")
              .setReplyMarkup(KeyBoardFactory.buildKeyBoard(KeyBoardFactory.MAIN_KB_ID));
        }
        // Si el mensaje no contiene la cadena start,
        // aplicamos el manejador correspondiente.

        else {
          message =
              HandlerManager.getHandler(update.getMessage().getText())
                  .handleMessage(update.getMessage());
        }
        execute(message); // Devolver el mensaje
      } catch (TelegramApiException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }
    }
  }

  /** Devuelve el nombre del bot */
  @Override
  public String getBotUsername() {
    return config.getProperty("botName");
  }

  /**
   * Devuelve la clave del Bot obtenida del archivo "qdiceroll.properties" bajo la propiedad "key"
   * Si no es capaz de obtener la clave, se sale de la aplicación
   */
  @Override
  public String getBotToken() {
    return config.getProperty("key");
  }
}
