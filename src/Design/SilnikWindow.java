package Design;

import Project.SystemSterowania;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SilnikWindow extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
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

    public FunkcjeCzujnika getFc(int i) {
        return fc[i];
    }
    private final FunkcjeCzujnika[] fc = {null, null,null};

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
        });
    }

    private void UstawPolaFormularza(SystemSterowania systemSterowania, int ktorySilnik) {
        f1nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getNazwa());
        f2nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getNazwa());
    }
}
