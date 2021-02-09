package Design;

import Project.SystemSterowania;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

public class TrasaWindow extends JFrame {
    private JTextField nazwaTF;
    private JButton wczytajBTN;
    private JButton zapiszBTN;
    private JLabel nazwaPlikuLBL;
    private JPanel mainPanel;
    private JPanel rtPNL;
    private TrasaPanel trasaPNL;


    public TrasaWindow(SystemSterowania systemSterowania, MainWindow mw) {
        add(mainPanel);
//        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//        int width = (int)screenSize.getWidth();
//        int height = (int)screenSize.getHeight();
        setSize(600, 400);
        setLocation(20, 20);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setTitle("Trasa");
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        setVisible(true);
        UstawPolaFormularza(systemSterowania);
        nazwaTF.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                super.keyReleased(e);
                systemSterowania.getTrasa().setNazwa(nazwaTF.getText());
                mw.UstawPolaFormularza();
            }
        });
        zapiszBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               trasaPNL.saveCanvas();
            }
        });
        wczytajBTN.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                trasaPNL.loadImage();
            }
        });
    }

    private void UstawPolaFormularza(SystemSterowania systemSterowania) {
        nazwaTF.setText(systemSterowania.getTrasa().getNazwa());
    }
}