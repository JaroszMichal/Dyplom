package Design;

import Project.Czujniki;
import Project.FunkcjaLiniowa;
import Project.SystemSterowania;

import javax.swing.*;
import java.awt.event.*;

public class FunkcjeCzujnika extends JFrame{
    private JPanel mainPanel;
    private JTextField nazwaTF;
    private JComboBox czujnikCMB;
    private JTextField nazwaPlikuTF;
    private JButton zapiszButton;
    private JButton wczytajButton;
    private JButton dodajButton;
    private JButton zmieńButton;
    private JList listaFunkcji;
    private JTextArea opisCzujnikaTextArea;
    private JButton usuńButton;
    private JButton wyczyscListeBTN;
    private JLabel komunikatLBL;

    public WykresPanel getWykresPNL() {
        return wykresPNL;
    }

    private WykresPanel wykresPNL;
    private JLabel opisCzujnikaLBL;
    private int FrameHeight = 500;
    DefaultListModel dm = new DefaultListModel();

    public JComboBox getCzujnikCMB() {
        return czujnikCMB;
    }

    public FunkcjeCzujnika(SystemSterowania systemSterowania, SilnikWindow sw, int ktorySilnik, int ktoryCzujnik){
        add(mainPanel);
        setSize(800,FrameHeight);
        String s="Silnik: ";
        s += (ktorySilnik==0) ? "Prędkość" : "Skręt";
        s += ", zestaw funkcji dla czujnika "+(ktoryCzujnik+1);
        setTitle(s);
        mainPanel.setBorder(BorderFactory.createTitledBorder(s));
        UstawPolaFormularza(systemSterowania,ktorySilnik,ktoryCzujnik);
        setLocation(20,40);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        dodajButton.addActionListener(e -> {
            FunkcjaLiniowa funkcjaLiniowa = new FunkcjaLiniowa(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getDziedzinaCzujnika());
            systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getListaFunkcji().add(funkcjaLiniowa);
            listaFunkcji.setModel(dm);
            dm.addElement(funkcjaLiniowa.getNazwa());
            PojedynczaFunkcja pf = new PojedynczaFunkcja(systemSterowania,  FunkcjeCzujnika.this, ktorySilnik, ktoryCzujnik, funkcjaLiniowa);
        });
        nazwaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).setNazwa(nazwaTF.getText());
                sw.setF1nazwaTF(nazwaTF.getText());
            }
        });
        czujnikCMB.addActionListener(e -> {
            WyczyscListe(systemSterowania, ktorySilnik, ktoryCzujnik);
            systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).ZmienCzujnik(czujnikCMB.getSelectedItem().toString());
            czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
            opisCzujnikaTextArea.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
            wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
        });
        zmieńButton.addActionListener(e -> {
            try{
                PojedynczaFunkcja pf = new PojedynczaFunkcja(systemSterowania,  FunkcjeCzujnika.this, ktorySilnik, ktoryCzujnik, systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getFunkcja(listaFunkcji.getSelectedValue().toString()));
            }
            catch (Exception e1){
            }
        });
        usuńButton.addActionListener(e -> {
            try {
                if (systemSterowania.getSilnik(ktorySilnik).
                        getFunkcjaCzujnika(ktoryCzujnik).
                        UsunFunkcjeZListy(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getFunkcja(listaFunkcji.getSelectedValue().toString())))
                    dm.remove(IndeksNaLiscie(dm, listaFunkcji.getSelectedValue().toString()));
                wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
            }
            catch (Exception e1){}
        });
        wyczyscListeBTN.addActionListener(e -> {
            WyczyscListe(systemSterowania, ktorySilnik, ktoryCzujnik);
            wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
        });
        listaFunkcji.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), listaFunkcji.getSelectedValue().toString());
            }
        });
        zapiszButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                komunikatLBL.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).ZapiszFunkcjeCzujnikaDoPliku(nazwaPlikuTF.getText()));
            }
        });
    }

    private void WyczyscListe(SystemSterowania systemSterowania, int ktorySilnik, int ktoryCzujnik) {
        systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getListaFunkcji().clear();
        dm.clear();
    }

    private int IndeksNaLiscie(DefaultListModel dm, String nazwa) {
        for (int i=0; i<dm.size();i++)
            if (dm.get(i).equals(nazwa)) return i;
        return -1;
    }

    private void UstawPolaFormularza(SystemSterowania systemSterowania, int ktorySilnik, int ktoryCzujnik) {
        nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getNazwa());
        for (String s: Czujniki.czujniki )
            czujnikCMB.addItem(s);
        czujnikCMB.setSelectedItem(systemSterowania.getSilnik(ktoryCzujnik).getFunkcjaCzujnika(ktoryCzujnik).getCzujnik());
        czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktoryCzujnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        opisCzujnikaTextArea.setText(systemSterowania.getSilnik(ktoryCzujnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        opisCzujnikaTextArea.setLineWrap(true);
        wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
    }
}
