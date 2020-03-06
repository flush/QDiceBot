package flush.software.keyboards;

import flush.software.exceptions.KeyBoardException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardMarkup;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.buttons.KeyboardRow;

/** Clase encargada de construir los diferentes teclados */
public class KeyBoardFactory {

  /** Configuracion del teclado de dados */
  private static final List<List<String>> mainButtons =
      Arrays.asList(
          Arrays.asList("1D4", "1D6", "1D8", "1D10", "1D12", "1D20"),
          Arrays.asList("2D4", "2D6", "2D8", "2D10", "2D12", "2D20"),
          Arrays.asList("3D4", "3D6", "3D8", "3D10", "1D100", "2D100"));

  /** Configuración del teclado de Inactividad */
  private static final List<List<String>> inactivityButtons =
      Arrays.asList(
          Arrays.asList("1D4", "1D6", "1D8", "1D10", "1D12", "1D20"),
          Arrays.asList("2D4", "2D6", "2D8", "2D10", "2D12", "2D20"),
          Arrays.asList("3D4", "3D6", "3D8", "3D10", "1D100", "2D100"));

  /** Identificador del teclado principal */
  public static final String MAIN_KB_ID = "main_kb";

  /** Identificador del teclado de inactividad */
  public static final String INACTIVITY_KB_ID = "inactivity_kb";

  /** Construye el teclado correspondiente segun el identificador */
  public static ReplyKeyboardMarkup buildKeyBoard(String kbId) throws KeyBoardException {
    if (MAIN_KB_ID.equals(kbId)) {
      return buildKeyBoard(mainButtons).setResizeKeyboard(true);
    } else if (INACTIVITY_KB_ID.equals(kbId)) {
      return buildKeyBoard(inactivityButtons);
    } else {
      throw KeyBoardException.getWrongKeyBoardException(kbId);
    }
  }

  /** Construye el teclado segun la lista de botones */
  private static ReplyKeyboardMarkup buildKeyBoard(List<List<String>> buttons) {
    List<KeyboardRow> rowButtons = new ArrayList<KeyboardRow>();

    for (List<String> buttonRow : buttons) {
      KeyboardRow kbr = new KeyboardRow();
      kbr.addAll(buttonRow);
      rowButtons.add(kbr);
    }
    return new ReplyKeyboardMarkup(rowButtons).setResizeKeyboard(true);
  }
}
