package AdditionalP;

import java.awt.Color;
import javax.swing.JTextField;

public class IsNullOrEmpty {

    public static boolean isNullOrEmpty(JTextField... txts) {
        for (JTextField tx : txts) {
            if (tx.getForeground() == Color.LIGHT_GRAY || tx.getText().isBlank() || tx.getText().isEmpty()) {
                return true;
            }

        }
        return false;
    }
}
