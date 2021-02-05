package Design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class MojaTabela extends JTable {

    public MojaTabela() {
        ListModel lm = new AbstractListModel() {
            String[] naglowkiWierszy = {"1","2","3","4"};
            @Override
            public int getSize() {
                return naglowkiWierszy.length;
            }

            @Override
            public Object getElementAt(int index) {
                return naglowkiWierszy[index];
            }
        };
        DefaultTableModel dm = new DefaultTableModel(lm.getSize(), 10);
        JTable table = new JTable(dm);
        table.setAutoResizeMode(JTable.AUTO_RESIZE_ALL_COLUMNS);
        JList rowHeader = new JList(lm);
        rowHeader.setFixedCellWidth(80);
        rowHeader.setFixedCellHeight(table.getRowHeight());
        rowHeader.setCellRenderer(new RowRenderer(table));
    }
}
class RowRenderer extends JLabel implements ListCellRenderer{
    public RowRenderer(JTable table) {
        JTableHeader header = table.getTableHeader();
        setOpaque(true);
        setBorder(UIManager.getBorder("TableHeader.cellBorder"));
        setHorizontalAlignment(CENTER);
        setForeground(header.getForeground());
        setBackground(header.getBackground());
        setFont(header.getFont());
    }

    @Override
    public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
        setText((value==null) ? "" : value.toString());
        return this;
    }
}
