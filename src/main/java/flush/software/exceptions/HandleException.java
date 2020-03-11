package flush.software.exceptions;

/** Excepciones relativas a los teclados */
public class HandleException extends Exception {

  private static final String GENERIC_HANDLE_CODE = "HDL0001";
  private static final String GENERIC_HANDLE_MESSAGE = "Se ha producido un error genérico";
  private static final String ERROR_BUILDING_HANDER_CODE = "HDL0002";
  private static final String ERROR_BUILDING_HANDER_MESSAGE =
      "Se ha producido un error al instanciar el handler %s";
  private static final String NO_HANDLER_CODE = "HDL0002";
  private static final String NO_HANDLER_MESSAGE =
      "No se ha encontrado un Handler adecuado para la cadena %s";
  private static final String ERROR_CONFIG_CODE = "HDL0002";
  private static final String ERROR_CONFIG_MESSAGE =
      "Erorr cargando la configuraciónd el Handler %s";

  private HandleException(String code, String message) {}

  private HandleException(String code, String message, Throwable cause) {}

  /** Excepcion generica. */
  public static HandleException getGenericHandleException(Throwable cause) {
            cause.printStackTrace();
    return new HandleException(GENERIC_HANDLE_CODE, GENERIC_HANDLE_MESSAGE, cause);
  }

  /** Error al construir un handler */
  public static HandleException getBuildingHandlerException(Throwable cause) {
      cause.printStackTrace();
    return new HandleException(ERROR_BUILDING_HANDER_CODE, ERROR_BUILDING_HANDER_MESSAGE, cause);
  }

  /** No hay ningun Handler para el mensaje */
  public static HandleException getNoHandlerException(String message) {
    return new HandleException(NO_HANDLER_CODE, String.format(NO_HANDLER_MESSAGE, message));
  }
  /** Error cargando la configuración del Handler */
    public static HandleException getErrorConfigException(String handler,Throwable cause) {
	      cause.printStackTrace();
      return new HandleException(ERROR_CONFIG_CODE, String.format(ERROR_CONFIG_MESSAGE,handler),cause);
  }
}
