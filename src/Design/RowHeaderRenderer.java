package Design;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

class RowHeaderRenderer extends DefaultTableCellRenderer {
    public RowHeaderRenderer() {
        setHorizontalAlignment(JLabel.LEFT);
    }

    public Component getTableCellRendererComponent(JTable table,
                                                   Object value, boolean isSelected, boolean hasFocus, int row,
                                                   int column) {
        if (table != null) {
            JTableHeader header = table.getTableHeader();

            if (header != null) {
                setForeground(header.getForeground());
                setBackground(header.getBackground());
                setFont(header.getFont());
            }
        }

        if (isSelected) {
            setFont(getFont().deriveFont(Font.BOLD));
        }

        setValue(value);
        return this;
    }
}