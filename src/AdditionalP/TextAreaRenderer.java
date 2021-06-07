package AdditionalP;

import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;

public class TextAreaRenderer extends JTextArea implements TableCellRenderer {
    private final DefaultTableCellRenderer adaptee = new DefaultTableCellRenderer();

    public TextAreaRenderer() {
        setLineWrap(true);
        setWrapStyleWord(true);
        
    }

    int last_row = -1;

    public Component getTableCellRendererComponent(JTable table, Object obj, boolean isSelected, boolean hasFocus, int row, int column) {
        adaptee.getTableCellRendererComponent(table, obj, isSelected, hasFocus, row, column);
        setForeground(adaptee.getForeground());
        setBackground(adaptee.getBackground());
        setBorder(adaptee.getBorder());
        setFont(adaptee.getFont());
        setText(adaptee.getText());

        setText(obj == null ? "" : obj.toString());

        Rectangle rect = table.getCellRect(row, column, true);
        this.setSize(rect.getSize());//для установки ширины компоненты
        int height_wanted = (int) getPreferredSize().getHeight();
        if ((height_wanted > table.getRowHeight(row) | row != last_row) &  //если новая строчка и полученная высота больше чем установленная
                 height_wanted > table.getRowHeight()) //и высота больше чем дефолтная
            table.setRowHeight(row, height_wanted); 

        last_row = row;

        return this;
    }

}