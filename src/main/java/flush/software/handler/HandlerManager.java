package flush.software.handler;

import flush.software.exceptions.HandleException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** Clase encargada de instanciar y crear los manejadores */
public class HandlerManager {

  private static final String PROP_HANDLER_CLASSES = "handlerClasses";

  private static List<IMessageHandler> handlers;

  /** Instancia los manejadores indicados en la propiedad handler_classes */
  public static void configureHandlers(Properties config) {

    handlers = new ArrayList<IMessageHandler>();

    String handlerClasses[] = ((String) config.get(PROP_HANDLER_CLASSES)).split(",");
    for (String handlerClass : handlerClasses) {
      try {
        handlers.add((IMessageHandler) Class.forName(handlerClass).newInstance());
      } catch (Exception e) {
        HandleException he = HandleException.getBuildingHandlerException(e);
        he.printStackTrace();
      }
    }
  }

  /** Obtiene el manejador adecuado para el mensaje obtenido */
  public static IMessageHandler getHandler(String message) throws HandleException {

      System.out.println("Obteniendo Handler para" +message);
      String messageProccesed = message.startsWith("/")?message.substring(1):message;
    for (IMessageHandler handler : handlers) {
      if (handler.getActivationPatter().matcher(messageProccesed).matches()) {
        return handler;
      }
    }
    throw HandleException.getNoHandlerException(message);
  }
}
