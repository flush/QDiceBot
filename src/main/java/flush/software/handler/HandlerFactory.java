package flush.software.handler;

import flush.software.exceptions.HandleException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

/** Clase encargada de instanciar y crear los manejadores */
public class HandlerFactory {

  private static final String PROP_HANDLER_CLASSES = "handler_classes";

  /** Instancia los manejadores indicados en la propiedad handler_classes */
  public static List<IMessageHandler> buildHandlers(Properties config) throws HandleException {

    List<IMessageHandler> handlers = new ArrayList<IMessageHandler>();

    String handlerClasses[] = ((String) config.get(PROP_HANDLER_CLASSES)).split(",");
    for (String handlerClass : handlerClasses) {
      try {
        handlers.add((IMessageHandler) Class.forName(handlerClass).newInstance());
      } catch (Exception e) {
        throw HandleException.getBuildingHandlerException(e);
      }
    }
    return handlers;
  }
}
