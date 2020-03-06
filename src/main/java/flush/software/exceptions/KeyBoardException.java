package flush.software.exceptions;

/** Excepciones relativas a los teclados */
public class KeyBoardException extends Exception {

  private static final String WRONG_KB_CODE = "KB0001";
  private static final String WRONG_KB_MESSAGE = "El teclado %s indicado no existe";

  private KeyBoardException(String code, String message) {}

  /** Devuelve una exepción que representa el hecho de que el teclado no existe. */
  public static KeyBoardException getWrongKeyBoardException(String kbId) {

    return new KeyBoardException(WRONG_KB_CODE, String.format(WRONG_KB_MESSAGE, kbId));
  }
}
