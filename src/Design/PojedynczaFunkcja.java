package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import Project.FunkcjaLiniowa;
import Project.Punkt;

public class PojedynczaFunkcja extends JFrame {

    private JTextField nazwaTF;
    private JTextField fileNameTF;
    private JButton zapiszButton;
    private JRadioButton odNieskRB;
    private JRadioButton odWartRB;
    private JTextField odWartTF;
    private JCheckBox odCzyNalezyCHK;
    private JPanel mainPanel;
    private JRadioButton doNieskRB;
    private JRadioButton doWartRB;
    private JTextField doWartTF;
    private JCheckBox doCzyNalezyCHK;
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
    private FunkcjaLiniowa fl;

    public PojedynczaFunkcja(FunkcjeCzujnika fc, int nr){
        fl = new FunkcjaLiniowa();
        add(mainPanel);
        setSize(900,FrameHeight);
        setTitles();
        setLocation(20*(nr / 2),10+(nr % 2));
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        InitComponents();

        dodajBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    int i = fl.Dodaj(new Punkt(Double.parseDouble(xTF.getText()), Double.parseDouble(yTF.getText())));
                    switch (i) {
                        case 0:
                            KomunikatLBL.setText("Dodano punkt do funkcji");
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
        odNieskRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fl.setDziedzinaCzyOdNiesk(true);
                odWartTF.setEnabled(false);
                odCzyNalezyCHK.setEnabled(false);
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        odWartRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fl.setDziedzinaCzyOdNiesk(false);
                odWartTF.setEnabled(true);
                odCzyNalezyCHK.setEnabled(true);
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        odCzyNalezyCHK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fl.setDziedzinaOdCzyNalezy(odCzyNalezyCHK.isSelected());
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        odWartTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try{
                    fl.setDziedzinaOdWart(Double.parseDouble(odWartTF.getText()));
                    KomunikatLBL.setText("");
                }
                catch (Exception e1){
                    KomunikatLBL.setText("Niepoprawna wartość pola lewostronnaj granicy dziedziny.");
                }
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        doNieskRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fl.setDziedzinaCzyDoNiesk(true);
                doWartTF.setEnabled(false);
                doCzyNalezyCHK.setEnabled(false);
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        doWartRB.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fl.setDziedzinaCzyDoNiesk(false);
                doWartTF.setEnabled(true);
                doCzyNalezyCHK.setEnabled(true);
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        doCzyNalezyCHK.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                fl.setDziedzinaDoCzyNalezy(doCzyNalezyCHK.isSelected());
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        doWartTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try{
                    fl.setDziedzinaDoWart(Double.parseDouble(doWartTF.getText()));
                    KomunikatLBL.setText("");
                }
                catch (Exception e1){
                    KomunikatLBL.setText("Niepoprawna wartość pola prawostronnaj granicy dziedziny.");
                }
                dziedzinaLBL.setText(opsiDziedziny());
            }
        });
        nazwaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                fl.setNazwa(nazwaTF.getText());
                setTitles();
            }
        });
    }

    private void setTitles() {
        setTitle("Funkcja - "+fl.getNazwa());
        mainPanel.setBorder(BorderFactory.createTitledBorder("Funkcja - "+fl.getNazwa()));
    }

    private void InitComponents() {
        nazwaTF.setText(fl.getNazwa());
        odNieskRB.setSelected(fl.isDziedzinaCzyOdNiesk());
        odWartRB.setSelected(!fl.isDziedzinaCzyOdNiesk());
        odWartTF.setText(Double.toString(fl.getDziedzinaOdWart()));
        odWartTF.setEnabled(!fl.isDziedzinaCzyOdNiesk());
        odCzyNalezyCHK.setSelected(fl.isDziedzinaOdCzyNalezy());
        odCzyNalezyCHK.setEnabled(!fl.isDziedzinaCzyOdNiesk());

        doNieskRB.setSelected(fl.isDziedzinaCzyDoNiesk());
        doWartRB.setSelected(!fl.isDziedzinaCzyDoNiesk());
        doWartTF.setText(Double.toString(fl.getDziedzinaDoWart()));
        doWartTF.setEnabled(!fl.isDziedzinaCzyDoNiesk());
        doCzyNalezyCHK.setSelected(fl.isDziedzinaDoCzyNalezy());
        doCzyNalezyCHK.setEnabled(!fl.isDziedzinaCzyDoNiesk());

        dziedzinaLBL.setText(opsiDziedziny());
    }

    private String opsiDziedziny() {
        String s;
        if ((!fl.isDziedzinaCzyOdNiesk()) && (fl.isDziedzinaOdCzyNalezy()))
            s = "<";
        else
            s = "(";
        if (fl.isDziedzinaCzyOdNiesk())
            s += "-∞";
        else
            s += Double.toString(fl.getDziedzinaOdWart());
        s += ", ";
        if (fl.isDziedzinaCzyDoNiesk())
            s += "+∞";
        else
            s += Double.toString(fl.getDziedzinaDoWart());
        if ((!fl.isDziedzinaCzyDoNiesk()) && (fl.isDziedzinaDoCzyNalezy()))
            s += ">";
        else
            s += ")";
        return s;
    }

}
