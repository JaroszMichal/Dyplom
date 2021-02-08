package Design;

import Project.FunkcjaLiniowa;
import Project.SystemSterowania;

import javax.swing.*;

import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import static javax.swing.WindowConstants.DISPOSE_ON_CLOSE;

public class AutoWindow extends JFrame{
    private JTextField nazwaTF;
    private JTextField predkoscMaksTF;
    private JTextField czasPredkoscMaksTF;
    private JTextField czasHamowaniaTF;
    private JTextField maksSkretTF;
    private JPanel mainPanel;
    private JLabel komunikatLBL;

    public AutoWindow(SystemSterowania systemSterowania, MainWindow mw) {
        add(mainPanel);
        setSize(400, 350);
        setTitle("Auto");
        setLocation(mw.getX() + mw.getWidth(), mw.getY());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        UstawPolaFormularza(systemSterowania);
        nazwaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                systemSterowania.getAuto().setNazwa(nazwaTF.getText());
                mw.UstawPolaFormularza();
            }
        });
        predkoscMaksTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    systemSterowania.getAuto().setPredkoscMaksymalna(Float.parseFloat(predkoscMaksTF.getText()));
                    komunikatLBL.setText("");
                }
                catch (Exception e1){
                    komunikatLBL.setText("Nieprawidłowa wartość prędkości.");
                }
            }
        });
        czasPredkoscMaksTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    systemSterowania.getAuto().setCzasOsiagnieciaPredkosciMaksymalnej(Float.parseFloat(czasPredkoscMaksTF.getText()));
                    komunikatLBL.setText("");
                }
                catch (Exception e1){
                    komunikatLBL.setText("Nieprawidłowa wartość czasu osiągnięcia prędkości maksymalnej.");
                }
            }
        });
        czasHamowaniaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    systemSterowania.getAuto().setCzasWyhamowaniazPredkosciMaksymalnej(Float.parseFloat(czasHamowaniaTF.getText()));
                    komunikatLBL.setText("");
                }
                catch (Exception e1){
                    komunikatLBL.setText("Nieprawidłowa wartość czasu wyhamowania.");
                }
            }
        });
        maksSkretTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                try {
                    systemSterowania.getAuto().setMaksymalnyKatSkretu(Float.parseFloat(maksSkretTF.getText()));
                    komunikatLBL.setText("");
                }
                catch (Exception e1){
                    komunikatLBL.setText("Nieprawidłowa wartość maksymalnego kąta skrętu.");
                }
            }
        });
    }

    private void UstawPolaFormularza(SystemSterowania systemSterowania) {
        nazwaTF.setText(systemSterowania.getAuto().getNazwa());
        predkoscMaksTF.setText(String.valueOf(systemSterowania.getAuto().getPredkoscMaksymalna()));
        czasPredkoscMaksTF.setText(String.valueOf(systemSterowania.getAuto().getCzasOsiagnieciaPredkosciMaksymalnej()));
        czasHamowaniaTF.setText(String.valueOf(systemSterowania.getAuto().getCzasWyhamowaniazPredkosciMaksymalnej()));
        maksSkretTF.setText(String.valueOf(systemSterowania.getAuto().getMaksymalnyKatSkretu()));
    }
}
