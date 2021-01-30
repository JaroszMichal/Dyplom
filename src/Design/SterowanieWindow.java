package Design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class SterowanieWindow extends JFrame {
    private JPanel mainPanel;
    private JTable table1;
    private JButton wczytajZPlikuButton;
    private JButton cz1definiujButton;
    private JLabel czujnik1IcoLBL;
    private JButton wczytajZPlikuButton1;
    private JButton cz2definiujButton;
    private JButton wczytajZPlikuButton2;
    private JButton funcDefiniujButton;
    private JButton wczytajZPlikuButton3;
    private JButton zapiszDoPlikuButton;
    private JTextField nonameMjsTextField;
    public FunkcjeCzujnika getFc(int i) {
        return fc[i];
    }
    private final FunkcjeCzujnika[] fc = {null, null,null};

    public SterowanieWindow(MainWindow mw, int nr) {
        add(mainPanel);
        setSize(600,400);
        String title="";
        int y=0;
        switch (nr){
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
        setLocation(mw.getX()+mw.getWidth(),mw.getY()+y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        cz1definiujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fc[0] == null)
                    fc[0] = new FunkcjeCzujnika(SterowanieWindow.this, 0);
                fc[0].setVisible(true);
            }
        });
        cz2definiujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fc[1] == null)
                    fc[1] = new FunkcjeCzujnika(SterowanieWindow.this, 1);
                fc[1].setVisible(true);
            }
        });
        funcDefiniujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (fc[2] == null)
                    fc[2] = new FunkcjeCzujnika(SterowanieWindow.this, 2);
                fc[2].setVisible(true);
            }
        });
    }
}
