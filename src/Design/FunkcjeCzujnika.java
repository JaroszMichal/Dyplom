package Design;

import Project.*;
import javafx.application.Application;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;
import java.util.Locale;

public class FunkcjeCzujnika extends JFrame{
    private JPanel mainPanel;
    private JTextField nazwaTF;
    private JComboBox czujnikCMB;
    private JButton zapiszButton;
    private JButton wczytajButton;
    private JButton dodajButton;
    private JButton zmieńButton;
    private JList listaFunkcji;
    private JTextArea opisCzujnikaTextArea;
    private JButton usuńButton;
    private JButton wyczyscListeBTN;
    private JLabel komunikatLBL;
    private String title;
    private boolean isListenerActive;
    public WykresPanel getWykresPNL() {
        return wykresPNL;
    }

    private WykresPanel wykresPNL;
    private JLabel nazwaPlikuLBL;
    private int FrameHeight = 500;
    DefaultListModel dm = new DefaultListModel();

    public JComboBox getCzujnikCMB() {
        return czujnikCMB;
    }

    public FunkcjeCzujnika(SystemSterowania systemSterowania, SilnikWindow sw, int ktorySilnik, int ktoryCzujnik){
        add(mainPanel);
        setSize(800,FrameHeight);
        isListenerActive = true;
        title="Silnik: ";
        title += (ktorySilnik==0) ? "Prędkość" : "Skręt";
        if (ktoryCzujnik<2)
            title += ", zestaw funkcji dla czujnika "+(ktoryCzujnik+1);
        else
            if (ktorySilnik==0)
                title += ", zestaw funkcji dla przyśpieszenia.";
            else
                title += ", zestaw funkcji dla skrętu.";
        setTitle(title);
        mainPanel.setBorder(BorderFactory.createTitledBorder(title));
        UstawPolaFormularza(systemSterowania,ktorySilnik,ktoryCzujnik);
        setLocation(20,40);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        dodajButton.addActionListener(e -> {
            FunkcjaLiniowa funkcjaLiniowa = new FunkcjaLiniowa(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getDziedzinaCzujnika());
            systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getListaFunkcji().add(funkcjaLiniowa);
            sw.UstawPolaFormularza(systemSterowania, ktorySilnik);
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
            if (isListenerActive) {
                WyczyscListe(systemSterowania, ktorySilnik, ktoryCzujnik);
                sw.UstawPolaFormularza(systemSterowania, ktorySilnik);
                systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).ZmienCzujnik(czujnikCMB.getSelectedItem().toString());
                czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
                opisCzujnikaTextArea.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
                wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
            }
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
                        UsunFunkcjeZListy(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getFunkcja(listaFunkcji.getSelectedValue().toString()))) {
                    dm.remove(IndeksNaLiscie(dm, listaFunkcji.getSelectedValue().toString()));
                    sw.UstawPolaFormularza(systemSterowania, ktorySilnik);
                }
                wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
            }
            catch (Exception e1){}
        });
        wyczyscListeBTN.addActionListener(e -> {
            WyczyscListe(systemSterowania, ktorySilnik, ktoryCzujnik);
            sw.UstawPolaFormularza(systemSterowania, ktorySilnik);
            wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
        });
        listaFunkcji.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                try {
                    wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), listaFunkcji.getSelectedValue().toString());
                }
                catch (Exception e1){}
            }
        });

        zapiszButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            SpolszczenieJFileChooser.PL(fileChooser);
            fileChooser.setDialogTitle(title);
            int returnVal = fileChooser.showSaveDialog(FunkcjeCzujnika.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).ZapiszDoPliku(file)) {
                    komunikatLBL.setText("Zapisano: " + file.getName());
                    nazwaPlikuLBL.setText(file.getName());
                }
                else
                    komunikatLBL.setText("Błąd zapisu do pliku: " + file.getName());
            } else {
                komunikatLBL.setText("Anulowano zapis do pliku.");
            }
        });

        wczytajButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            SpolszczenieJFileChooser.PL(fileChooser);
            int returnVal = fileChooser.showOpenDialog(FunkcjeCzujnika.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                returnVal = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).WczytajzPliku(file);
                switch (returnVal){
                    case 0:
                        komunikatLBL.setText("Plik: " + file.getName() + " nie zawiera prawidłowej definicji funkcji.");
                        break;
                    case -1:
                        komunikatLBL.setText("Błąd odczytu z pliku: " + file.getName());
                        break;
                    default:
                        komunikatLBL.setText("Wczytano dane z pliku: " + file.getName());
                        nazwaPlikuLBL.setText(file.getName());
                        sw.UstawPolaFormularza(systemSterowania, ktorySilnik);
                        OdswiezPolaFormularza(systemSterowania,ktorySilnik,ktoryCzujnik);
                        break;

                }
            } else
                komunikatLBL.setText("Anulowano odczyt z pliku.");
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
        opisCzujnikaTextArea.setEnabled(false);
        if (ktoryCzujnik<2)
            for (String s: Czujniki.czujniki )
                czujnikCMB.addItem(s);
        else
            czujnikCMB.addItem(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getCzujnik());
        czujnikCMB.setSelectedItem(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getCzujnik());
        czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        if (ktoryCzujnik==2)
            czujnikCMB.setEnabled(false);
        opisCzujnikaTextArea.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        opisCzujnikaTextArea.setLineWrap(true);
        wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
    }
    private void OdswiezPolaFormularza(SystemSterowania systemSterowania, int ktorySilnik, int ktoryCzujnik) {
        nazwaTF.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getNazwa());
        isListenerActive = false;
        czujnikCMB.setSelectedItem(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getCzujnik());
        isListenerActive = true;
        czujnikCMB.setToolTipText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        opisCzujnikaTextArea.setText(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getOpisCzujnika());
        opisCzujnikaTextArea.setLineWrap(true);
        listaFunkcji.setModel(dm);
        for (int i=0; i<systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getListaFunkcji().size();i++)
            dm.addElement(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).getListaFunkcji().get(i).getNazwa());
        wykresPNL.ZaktualizujWartoscListyFunkcji(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik), "");
    }
}
