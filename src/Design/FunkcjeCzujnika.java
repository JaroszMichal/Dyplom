package Design;

import Project.Czujniki;
import Project.FunkcjaLiniowa;
import Project.SystemSterowania;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

public class FunkcjeCzujnika extends JFrame{
    private JPanel mainPanel;
    private JTextField nazwaTF;
    private JComboBox czujnikCMB;
    private JTextField textField2;
    private JButton zapiszButton;
    private JButton wczytajButton;
    private JButton dodajButton;
    private JButton zmieńButton;
    private JList listaFunkcji;
    private JTextArea textArea1;
    private JButton usuńButton;
    private JButton wyczyśćListęButton;
    private JLabel opisCzujnikaLBL;
    private int FrameHeight = 500;
    DefaultListModel dm = new DefaultListModel();

    public FunkcjeCzujnika(SystemSterowania systemSterowania, SilnikWindow sw, int ktorySilnik, int ktoryCzujnik){
        add(mainPanel);
        setSize(800,FrameHeight);
        String title="";
        switch (ktoryCzujnik){
            case 0:
                title = "Funkcja ("+sw.getTitle().substring(23)+") dla czujnika 1";
                break;
            case 1:
                title = "Funkcja ("+sw.getTitle().substring(23)+") dla czujnika 2";
                break;
            case 2:
                title = "Funkcja - "+sw.getTitle().substring(23);
                break;
        }
        setTitle("Definiowanie silnika - "+title);
        mainPanel.setBorder(BorderFactory.createTitledBorder(title));
        UstawPolaFormularza(systemSterowania,ktorySilnik,ktoryCzujnik);
        setLocation(20,20);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                FunkcjaLiniowa funkcjaLiniowa = new FunkcjaLiniowa(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getDziedzinaCzujnika());
                systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getListaFunkcji().add(funkcjaLiniowa);
                listaFunkcji.setModel(dm);
                dm.addElement(funkcjaLiniowa.getNazwa());
                PojedynczaFunkcja pf = new PojedynczaFunkcja(systemSterowania,  FunkcjeCzujnika.this, ktorySilnik, ktoryCzujnik, funkcjaLiniowa);
            }
        });
        nazwaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).setNazwa(nazwaTF.getText());
                sw.setF1nazwaTF(nazwaTF.getText());
            }
        });
        czujnikCMB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).ZmienCzujnik(czujnikCMB.getSelectedItem().toString());
                czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
                textArea1.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
            }
        });
    }

    private void UstawPolaFormularza(SystemSterowania systemSterowania, int ktorySilnik, int ktoryCzujnik) {
        nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getNazwa());
        for (String s: Czujniki.czujniki )
            czujnikCMB.addItem(s);
        czujnikCMB.setSelectedItem(systemSterowania.getSilnik(ktoryCzujnik).getFunkcjaCzujnika(ktoryCzujnik).getCzujnik());
        czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktoryCzujnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        textArea1.setText(systemSterowania.getSilnik(ktoryCzujnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        textArea1.setLineWrap(true);
    }
}
