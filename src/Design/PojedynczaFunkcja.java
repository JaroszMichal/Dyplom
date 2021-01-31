package Design;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.*;
import java.util.ArrayList;

import Project.FunkcjaLiniowa;
import Project.Punkt;
import Project.SystemSterowania;

public class PojedynczaFunkcja extends JFrame {

    private JTextField nazwaTF;
    private JTextField fileNameTF;
    private JButton zapiszButton;
    private JPanel mainPanel;
    private JTextField xTF;
    private JTextField yTF;
    private JButton dodajBTN;
    private JButton zmienBTN;
    private JButton usunBTN;
    private JButton wyczyscBTN;
    private JLabel KomunikatLBL;
    private JLabel dziedzinaLBL;
    private JTable punktyTBL;
    private int FrameHeight = 500;
    String nazwafunkcji;

    public PojedynczaFunkcja(SystemSterowania systemSterowania, FunkcjeCzujnika fc, int ktorySilnik, int ktoryCzujnik, FunkcjaLiniowa fl){
        nazwafunkcji = fl.getNazwa();
        add(mainPanel);
        setSize(900,FrameHeight);
        setLocation(fc.getX()+20,fc.getY()+20);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        Object[] Kolumny = {"x", "y"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(Kolumny);
        punktyTBL.setModel(dtm);
        punktyTBL.setAutoCreateRowSorter(true);
        TableRowSorter<TableModel> sorter = new TableRowSorter<TableModel>(punktyTBL.getModel());
        punktyTBL.setRowSorter(sorter);
        ArrayList<RowSorter.SortKey> sortKeys = new ArrayList<RowSorter.SortKey>();
        int columnIndexToSort = 0;
        sortKeys.add(new RowSorter.SortKey(columnIndexToSort, SortOrder.ASCENDING));
        sorter.setSortKeys(sortKeys);
        sorter.sort();

        InitComponents(fl);
        setVisible(true);

        dodajBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = fl.Dodaj(new Punkt(Double.parseDouble(xTF.getText()), Double.parseDouble(yTF.getText())));
                    switch (i) {
                        case 0:
                            KomunikatLBL.setText("Dodano punkt do funkcji");
                            Object[] row = new Object[2];
                            row[0] = xTF.getText();
                            row[1] = yTF.getText();
                            dtm.addRow(row);

                            xTF.setText("");
                            yTF.setText("");
                            break;
                        case 1:
                            KomunikatLBL.setText("Nie dodano puntu. Jest już zdefiniowana wartość dla x = " + xTF.getText());
                            break;
                        case 2:
                            KomunikatLBL.setText("Nie dodano puntu. Wartość x = " + xTF.getText() + " nie należy do dziedziny funkcji.");
                            break;
                    }
                }
                catch (Exception e1){
                    KomunikatLBL.setText("Niepoprawna wartość zmiennej");
                }
            }
        });
        nazwaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                fl.setNazwa(nazwaTF.getText());
                setTitles(fl.getNazwa());
                fc.dm.setElementAt(nazwaTF.getText(), Pozycja(fc, nazwafunkcji));
                nazwafunkcji = nazwaTF.getText();
            }
        });
        punktyTBL.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                xTF.setText((String)punktyTBL.getValueAt(punktyTBL.getSelectedRow(),0));
                yTF.setText((String)punktyTBL.getValueAt(punktyTBL.getSelectedRow(),1));
            }
        });
        usunBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow;
                try{
                    selectedRow = punktyTBL.convertRowIndexToModel(punktyTBL.getSelectedRow());
                }
                catch (Exception e1){
                    selectedRow = -1;
                }
                if (selectedRow>=0) {
                    try {
                        if (fl.Usun(new Punkt(Double.parseDouble(dtm.getValueAt(selectedRow,0).toString()),Double.parseDouble(dtm.getValueAt(selectedRow,1).toString())))==1)
                            KomunikatLBL.setText("Brak pozycji o tych wartościach do usunięcia.");
                        else {
                            dtm.removeRow(IndekswTabeli(dtm, selectedRow));
                            KomunikatLBL.setText("Pozycja usunięta.");
                            xTF.setText("");
                            yTF.setText("");
                        }
                    }
                    catch (Exception e1){
                        KomunikatLBL.setText("Błąd podczas usuwania.");
                    }
                }
                else
                    KomunikatLBL.setText("Błąd usunięcia pozycji!");
            }
        });
        zmienBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int selectedRow;
                try{
                    selectedRow = punktyTBL.convertRowIndexToModel(punktyTBL.getSelectedRow());
                }
                catch (Exception e1){
                    selectedRow = -1;
                }
                if (selectedRow>=0) {
                    try {
                        if (fl.Zmien(new Punkt(Double.parseDouble(dtm.getValueAt(selectedRow,0).toString()),Double.parseDouble(dtm.getValueAt(selectedRow,1).toString())),
                                new Punkt(Double.parseDouble(xTF.getText()),Double.parseDouble(yTF.getText())))==1)
                            KomunikatLBL.setText("Błąd zmiany wartości.");
                        else {
                            dtm.setValueAt(xTF.getText(), selectedRow,0);
                            dtm.setValueAt(yTF.getText(), selectedRow,1);
                            KomunikatLBL.setText("Zmiana zapisana.");
                        }
                    }
                    catch (Exception e1){
                        KomunikatLBL.setText("Błąd podczas modyfikacji.");
                    }
                }
                else
                    KomunikatLBL.setText("Błąd modyfikacji pozycji!");
            }
        });
        wyczyscBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                KomunikatLBL.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getNazwa());
                fl.getPunkty().clear();
                dtm.setNumRows(0);
                KomunikatLBL.setText("Usunięto wszystkie dane z tabeli.");
            }
        });
    }

    private int IndekswTabeli(DefaultTableModel dtm, int selectedRow) {
        for (int i=0; i<dtm.getRowCount();i++)
            if (i == selectedRow) return i;
        return -1;
    }

    private int Pozycja(FunkcjeCzujnika fc, String nazwafunkcji) {
        for (int i=0; i<fc.dm.size();i++)
            if (nazwafunkcji.equals(fc.dm.getElementAt(i))) return i;
        return -1;
    }

    private void InitComponents(FunkcjaLiniowa fl) {
        setTitles(fl.getNazwa());
        dziedzinaLBL.setText(opsiDziedziny(fl));
        nazwaTF.setText(fl.getNazwa());
    }

    private void setTitles(String s) {
        setTitle("Funkcja - "+s);
        mainPanel.setBorder(BorderFactory.createTitledBorder("Funkcja - "+s));
    }

    private String opsiDziedziny(FunkcjaLiniowa fl) {
        String s;
        if ((!fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk()) && (fl.getDziedzinaCzujnika().isDziedzinaOdCzyNalezy()))
            s = "<";
        else
            s = "(";
        if (fl.getDziedzinaCzujnika().isDziedzinaCzyOdNiesk())
            s += "-∞";
        else
            s += Double.toString(fl.getDziedzinaCzujnika().getDziedzinaOdWart());
        s += ", ";
        if (fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk())
            s += "+∞";
        else
            s += Double.toString(fl.getDziedzinaCzujnika().getDziedzinaDoWart());
        if ((!fl.getDziedzinaCzujnika().isDziedzinaCzyDoNiesk()) && (fl.getDziedzinaCzujnika().isDziedzinaDoCzyNalezy()))
            s += ">";
        else
            s += ")";
        return s;
    }

}
