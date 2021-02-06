package Design;

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
import java.util.ArrayList;

import static Project.Ikony.Green;
import static Project.Ikony.Red;

public class SilnikWindow extends JFrame {
    private JPanel mainPanel;
    private JButton cz1definiujButton;
    private JLabel czujnik1IcoLBL;
    private JButton cz2definiujButton;
    private JButton funcDefiniujButton;
    private JTextField regulyNazwaTF;
    private JLabel f1nazwaTF;
    private JLabel f2nazwaTF;
    private JLabel f3nazwaTF;
    private JButton wczytajZPlikuButton;
    private JButton zapiszDoPlikuButton;
    private JLabel isOkLBL;
    private JLabel czujnik2IcoLBL;
    private JLabel czujnik3IcoLBL;
    private JPanel funkcjaPNL;
    private JTable regulyTBL;

    public FunkcjeCzujnika getFc(int i) {
        return fc[i];
    }
    private final FunkcjeCzujnika[] fc = {null, null, null};

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
        String title="";
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
        Object[] Kolumny = {"", "y", "z", "q", "w", "e"};
        DefaultTableModel dtm = new DefaultTableModel();
        dtm.setColumnIdentifiers(Kolumny);
        regulyTBL.setModel(dtm);
        for (int i=0;i<6;i++) {
            Object[] wiersz = {"a", "b", "c", "d", "e", "f"};
            dtm.addRow(wiersz);
        }
        regulyTBL.getColumnModel().getColumn(0).setCellRenderer(new RowHeaderRenderer());
        regulyTBL.getTableHeader().resizeAndRepaint();
    }
}
