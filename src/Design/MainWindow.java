package Design;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MainWindow extends JFrame {
    private JPanel mainPanel;
    private JButton trasaButton;
    private JButton autoButton;
    private JLabel trasaIcoLBL;
    private JLabel trasaOpisLBL;
    private JLabel autoIcoLBL;
    private JLabel autoOpisLBL;
    private JButton speedButton;
    private JLabel speedIcoLBL;
    private JLabel speedOpisLBL;
    private JButton zdefiniujButton;
    private JButton startButton;
    private JButton historiaButton;
    private JLabel turnIcoLBL;
    private JLabel light1LBL;
    private JLabel light2LBL;
    private JLabel light3LBL;
    private JLabel light4LBL;
    private JLabel testLBL;
    private final SterowanieWindow[] sw = {null, null};

    public SterowanieWindow getSw(int i) {
        return sw[i];
    }

    public void setTestLBL(String s) {
        this.testLBL.setText(s);
    }

    private ImageIcon Red(int size){
        return new ImageIcon(new ImageIcon("resources\\red.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }

    private ImageIcon Green(int size){
        return new ImageIcon(new ImageIcon("resources\\green.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }

    public MainWindow(){
        add(mainPanel);
        setSize(700,500);
        setTitle("Praca dyplomowa - Michał Jarosz");
        setLocation(5,10);
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        trasaIcoLBL.setIcon(Red(30));
        autoIcoLBL.setIcon(Green(30));
        speedIcoLBL.setIcon(Red(30));
        turnIcoLBL.setIcon(Red(30));
        light1LBL.setIcon(Green(40));
        light2LBL.setIcon(Red(40));
        light3LBL.setIcon(Red(40));
        light4LBL.setIcon(Red(40));

//        validate();
//        final ValueReturningWindow[] vw = {null};
//                button1.addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                if (vw[0] == null)
//                    vw[0] = new ValueReturningWindow(MainWindow.this);
//                vw[0].setVisible(true);
//            }
//        });

        speedButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sw[0] == null)
                    sw[0] = new SterowanieWindow(MainWindow.this, 0);
                sw[0].setVisible(true);
            }
        });
        zdefiniujButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (sw[1] == null)
                    sw[1] = new SterowanieWindow(MainWindow.this, 1);
                sw[1].setVisible(true);
            }
        });
    }
}
