package Design;

import Project.SpolszczenieJFileChooser;
import Project.SystemSterowania;

import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputMethodEvent;
import java.awt.event.InputMethodListener;
import java.io.File;
import java.util.ArrayList;

import static Project.Ikony.Green;
import static Project.Ikony.Red;

public class SilnikWindow extends JFrame {
    private JPanel mainPanel;
    private JButton cz1definiujButton;
    private JLabel czujnik1IcoLBL;
    private JButton cz2definiujButton;
    private JButton funcDefiniujButton;
    private JLabel f1nazwaTF;
    private JLabel f2nazwaTF;
    private JLabel f3nazwaTF;
    private JButton wczytajZPlikuButton;
    private JButton zapiszDoPlikuButton;
    private JLabel komunikatLBL;
    private JLabel czujnik2IcoLBL;
    private JLabel czujnik3IcoLBL;
    private JPanel funkcjaPNL;
    private JTable regulyTBL;
    private JLabel nazwaPlikuLBL;
    private final FunkcjeCzujnika[] fc = {null, null, null};
    private String title;
    public FunkcjeCzujnika getFc(int i) {
        return fc[i];
    }
    public void setF1nazwaTF(String s) {
        this.f1nazwaTF.setText(s);
    }
    public void setF2nazwaTF(String s) {
        this.f2nazwaTF.setText(s);
    }
    public void setF3nazwaTF(String s) {
        this.f3nazwaTF.setText(s);
    }

    // ktora funkcja - 0- prędkość, 1 - skręt
    public SilnikWindow(SystemSterowania systemSterowania, MainWindow mw, int ktorySilnik) {
        add(mainPanel);
        setSize(600,400);
        title="";
        int y=0;
        switch (ktorySilnik){
            case 0:
                title = "sterowanie prędkością";
                if ((mw.getSw(1)!=null)&&(mw.getSw(1).isVisible()))
                    y = mw.getSw(1).getHeight();
                break;
            case 1:
                title = "sterowanie skrętem";
                if ((mw.getSw(0)!=null)&&(mw.getSw(0).isVisible()))
                    y = mw.getSw(0).getHeight();
                break;
        }
        setTitle("Definiowanie silnika - "+title);
        mainPanel.setBorder(BorderFactory.createTitledBorder(title));
        UstawPolaFormularza(systemSterowania, ktorySilnik);
        setLocation(mw.getX()+mw.getWidth(),mw.getY()+y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cz1definiujButton.addActionListener(e -> {
            if (fc[0] == null)
                fc[0] = new FunkcjeCzujnika(systemSterowania, SilnikWindow.this, ktorySilnik,0);
            fc[0].setVisible(true);
        });
        cz2definiujButton.addActionListener(e -> {
            if (fc[1] == null)
                fc[1] = new FunkcjeCzujnika(systemSterowania, SilnikWindow.this, ktorySilnik,1);
            fc[1].setVisible(true);
        });
        funcDefiniujButton.addActionListener(e -> {
            if (fc[2] == null)
                fc[2] = new FunkcjeCzujnika(systemSterowania, SilnikWindow.this, ktorySilnik,2);
            fc[2].setVisible(true);
        });
        zapiszDoPlikuButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                SpolszczenieJFileChooser.PL(fileChooser);
                fileChooser.setDialogTitle(title);
                int returnVal = fileChooser.showSaveDialog(SilnikWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (systemSterowania.getSilnik(ktorySilnik).ZapiszDoPliku(file)) {
                        komunikatLBL.setText("Zapisano: " + file.getName());
                        nazwaPlikuLBL.setText(file.getName());
                    }
                    else
                        komunikatLBL.setText("Błąd zapisu do pliku: " + file.getName());
                } else {
                    komunikatLBL.setText("Anulowano zapis do pliku.");
                }
            }
        });
    }

    public void UstawPolaFormularza(SystemSterowania systemSterowania, int ktorySilnik) {
        if (ktorySilnik==0)
            funkcjaPNL.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funkcja przyśpieszenia", TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12)));
        else
            funkcjaPNL.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Funkcja skrętu", TitledBorder.LEFT, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Segoe UI", 0, 12)));
        f1nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getCzujnik()+" - "+systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getNazwa());
        f2nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getCzujnik()+" - "+systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getNazwa());
        if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size()>0)
            czujnik1IcoLBL.setIcon(Green(30));
        else
            czujnik1IcoLBL.setIcon(Red(30));
        if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().size()>0)
            czujnik2IcoLBL.setIcon(Green(30));
        else
            czujnik2IcoLBL.setIcon(Red(30));
        if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(2).getListaFunkcji().size()>0)
            czujnik3IcoLBL.setIcon(Green(30));
        else
            czujnik3IcoLBL.setIcon(Red(30));

        String[] Kolumny = new String[1+systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().size()];
        if (Kolumny.length>1) {
            Kolumny[0] = "";
            for (int i = 0; i < Kolumny.length-1; i++)
                Kolumny[i + 1] = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().get(i).getNazwa();
            DefaultTableModel dtm = new DefaultTableModel();
            dtm.setColumnIdentifiers(Kolumny);
            regulyTBL.setModel(dtm);
            String[] wiersz = new String[Kolumny.length];
            for (int i = 0; i < systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size(); i++) {
                wiersz[0] = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().get(i).getNazwa();
                for (int j=1; j<Kolumny.length;j++)
                    wiersz[j] = "Ustal...";
                dtm.addRow(wiersz);
            }
            if (systemSterowania.getSilnik(ktorySilnik).getRegulywnioskowania()==null)
                systemSterowania.getSilnik(ktorySilnik).setRegulywnioskowania(new int[systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size()][systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().size()]);
            int regWn0 = systemSterowania.getSilnik(ktorySilnik).getRegulywnioskowania().length;
            int silF0Length = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size();
            int regWn1 = systemSterowania.getSilnik(ktorySilnik).getRegulywnioskowania()[0].length;
            int silF1Leng= systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().size();
            if ((regWn0!=silF0Length) || (regWn1!=silF1Leng))
                    systemSterowania.getSilnik(ktorySilnik).setRegulywnioskowania(new int[systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size()][systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().size()]);
            String[] reguly = new String[systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(2).getListaFunkcji().size()];
            for (int i=0;i<systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(2).getListaFunkcji().size();i++)
                reguly[i] = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(2).getListaFunkcji().get(i).getNazwa();
            if (reguly.length>0)
                for (int i = 0; i < systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size(); i++) {
                    for (int j=1; j<Kolumny.length;j++)
                        regulyTBL.setValueAt(reguly[0],i,j);
            }
            JComboBox cb = new JComboBox(reguly);
            final int[] lastRow = {-1};
            final int[] lastCol = {-1};
            cb.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
//                if (systemSterowania.getSilnik(ktorySilnik).getRegulywnioskowania()!=null)
//                    JOptionPane.showMessageDialog(null, "Row = "+regulyTBL.getSelectedRow()+
//                            ", col = "+(regulyTBL.getSelectedColumn()-1)+"value = "+cb.getSelectedIndex());
                    if ((regulyTBL.getSelectedRow()>-1)&&(regulyTBL.getSelectedColumn()>-1)&&(regulyTBL.getSelectedRow()!= lastRow[0])&&(regulyTBL.getSelectedColumn()!= lastCol[0])) {
                        systemSterowania.getSilnik(ktorySilnik).getRegulywnioskowania()[regulyTBL.getSelectedRow()][regulyTBL.getSelectedColumn() - 1] = cb.getSelectedIndex();
                        lastRow[0] = regulyTBL.getSelectedRow();
                        lastCol[0] = regulyTBL.getSelectedColumn();
                    }
                    else{
                        lastRow[0] = -1;
                        lastCol[0] = -1;
                    }
                }
            });
            for (int i=1; i<Kolumny.length;i++)
                regulyTBL.getColumnModel().getColumn(i).setCellEditor(new DefaultCellEditor(cb));
            regulyTBL.getColumnModel().getColumn(0).setCellRenderer(new RowHeaderRenderer());
            regulyTBL.getTableHeader().resizeAndRepaint();
        }
//        JTableHeader naglowekTabeli = regulyTBL.getTableHeader();
//        naglowekTabeli.setBackground(Color.WHITE);
//        naglowekTabeli.setForeground(Color.BLACK);


    }
}
