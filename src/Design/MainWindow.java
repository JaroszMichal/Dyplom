package Design;

import Project.Ikony;
import Project.SystemSterowania;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import static Project.Ikony.Green;
import static Project.Ikony.Red;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JButton trasaButton;
    private JButton autoButton;
    private JLabel trasaIcoLBL;
    private JLabel trasaOpisLBL;
    private JLabel autoIcoLBL;
    private JLabel autoOpisLBL;
    private JButton predkoscBTN;
    private JLabel predkoscIcoLBL;
    private JLabel predkoscOpisLBL;
    private JButton skretBTN;
    private JButton startButton;
    private JButton historiaButton;
    private JLabel skretIcoLBL;
    private JLabel light1LBL;
    private JLabel light2LBL;
    private JLabel light3LBL;
    private JLabel light4LBL;
    private JLabel skretOpisLBL;
    private PanelGry graPNL;
    private SystemSterowania systemSterowania;
    private final SilnikWindow[] sw = {null, null};

    public SilnikWindow getSw(int i) {
        return sw[i];
    }

    public MainWindow(){
        systemSterowania = new SystemSterowania();
        add(mainPanel);
        setSize(700,500);
        setTitle("Praca dyplomowa - Michał Jarosz");
        setLocation(5,10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        UstawPolaFormularza();
        graPNL.setSystemSterowania(systemSterowania);
        predkoscBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sw[0] == null)
                    sw[0] = new SilnikWindow(systemSterowania, MainWindow.this, 0);
                sw[0].setVisible(true);
            }
        });
        skretBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sw[1] == null)
                    sw[1] = new SilnikWindow(systemSterowania, MainWindow.this, 1);
                sw[1].setVisible(true);
            }
        });
        autoButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                AutoWindow aw = new AutoWindow(systemSterowania, MainWindow.this);
            }
        });
        trasaButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                graPNL.setImage(null);
                TrasaWindow tw = new TrasaWindow(systemSterowania, MainWindow.this);
            }
        });
    }

    public void UstawPolaFormularza(){
        int ileZielonych=0;
        if (systemSterowania.getTrasa().getImage()!=null) {
            trasaIcoLBL.setIcon(Ikony.Green(30));
            trasaOpisLBL.setText(systemSterowania.getTrasa().getNazwa());
            ileZielonych++;
        }
        else {
            trasaIcoLBL.setIcon(Ikony.Red(30));
            trasaOpisLBL.setText("nie zdefiniowana");
        }
        if (systemSterowania.getAuto()!=null) {
            autoIcoLBL.setIcon(Ikony.Green(30));
            autoOpisLBL.setText(systemSterowania.getAuto().getNazwa());
            ileZielonych++;
        }
        else {
            autoIcoLBL.setIcon(Ikony.Red(30));
            autoOpisLBL.setText("nie zdefiniowane");
        }
        if ((systemSterowania.getSilnik(0).getFunkcjaCzujnika(0).getListaFunkcji().size()>0) &&
            (systemSterowania.getSilnik(0).getFunkcjaCzujnika(1).getListaFunkcji().size()>0) &&
            (systemSterowania.getSilnik(0).getFunkcjaCzujnika(2).getListaFunkcji().size()>0)){
            predkoscIcoLBL.setIcon(Ikony.Green(30));
            predkoscOpisLBL.setText(systemSterowania.getSilnik(0).getNazwa());
            ileZielonych++;
        }
        else{
            predkoscIcoLBL.setIcon(Ikony.Red(30));
            predkoscOpisLBL.setText("nie zdefiniowany");
        }
        if ((systemSterowania.getSilnik(1).getFunkcjaCzujnika(0).getListaFunkcji().size()>0) &&
            (systemSterowania.getSilnik(1).getFunkcjaCzujnika(1).getListaFunkcji().size()>0) &&
            (systemSterowania.getSilnik(1).getFunkcjaCzujnika(2).getListaFunkcji().size()>0)){
            skretIcoLBL.setIcon(Ikony.Green(30));
            skretOpisLBL.setText(systemSterowania.getSilnik(1).getNazwa());
            ileZielonych++;
        }
        else{
            skretIcoLBL.setIcon(Ikony.Red(30));
            skretOpisLBL.setText("nie zdefiniowany");
        }
        light1LBL.setIcon(Red(40));
        light2LBL.setIcon(Red(40));
        light3LBL.setIcon(Red(40));
        light4LBL.setIcon(Red(40));
        if (ileZielonych>0)
            light1LBL.setIcon(Green(40));
        if (ileZielonych>1)
            light2LBL.setIcon(Green(40));
        if (ileZielonych>2)
            light3LBL.setIcon(Green(40));
        if (ileZielonych>3) {
            light4LBL.setIcon(Green(40));
            startButton.setEnabled(true);
        }
        if (systemSterowania.getTrasa().getImage()!=null)
            graPNL.UstawObraz(systemSterowania.getTrasa().getImage());
    }

}
