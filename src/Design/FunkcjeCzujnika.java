package Design;

import Project.Czujniki;
import Project.FunkcjaLiniowa;
import Project.SystemSterowania;

import javax.swing.*;
import java.awt.event.*;
import java.io.File;

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

    public WykresPanel getWykresPNL() {
        return wykresPNL;
    }

    private WykresPanel wykresPNL;
    private JLabel nazwaPlikuLBL;
    private JLabel opisCzujnikaLBL;
    private int FrameHeight = 500;
    DefaultListModel dm = new DefaultListModel();

    public JComboBox getCzujnikCMB() {
        return czujnikCMB;
    }

    public FunkcjeCzujnika(SystemSterowania systemSterowania, SilnikWindow sw, int ktorySilnik, int ktoryCzujnik){
        add(mainPanel);
        setSize(800,FrameHeight);
        title="Silnik: ";
        title += (ktorySilnik==0) ? "Prędkość" : "Skręt";
        title += ", zestaw funkcji dla czujnika "+(ktoryCzujnik+1);
        setTitle(title);
        mainPanel.setBorder(BorderFactory.createTitledBorder(title));
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

        zapiszButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            fileChooser.setDialogTitle(title);
            int returnVal = fileChooser.showSaveDialog(FunkcjeCzujnika.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(ktoryCzujnik).ZapiszDoPliku(file)) {
                    komunikatLBL.setText("Zapisano: " + file.getName() + ".");
                    nazwaPlikuLBL.setText(file.getName());
                }
                else
                    komunikatLBL.setText("Błąd zapisu do pliku: " + file.getName() + ".");
            } else {
                komunikatLBL.setText("Anulowano zapis do pliku.");
            }
        });

        wczytajButton.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            int returnVal = fileChooser.showOpenDialog(FunkcjeCzujnika.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File file = fileChooser.getSelectedFile();
                //This is where a real application would open the file.
                komunikatLBL.setText("Opening: " + file.getName() + ".");
            } else {
                komunikatLBL.setText("Open command cancelled by user.");
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
