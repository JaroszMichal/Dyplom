package Design;

import Project.SpolszczenieJFileChooser;
import Project.SystemSterowania;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
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
    private JLabel komunikatLBL;


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
                JFileChooser fileChooser = new JFileChooser();
                SpolszczenieJFileChooser.PL(fileChooser);
                fileChooser.setDialogTitle("Zapisywanie trasy");
                int returnVal = fileChooser.showSaveDialog(TrasaWindow.this);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    File file = fileChooser.getSelectedFile();
                    if (trasaPNL.zapiszDoPliku(file)) {
                        komunikatLBL.setText("Zapisano: " + file.getName());
                        nazwaPlikuLBL.setText(file.getName());
                    }
                    else
                        komunikatLBL.setText("Błąd zapisu do pliku: " + file.getName());
                } else {
                    komunikatLBL.setText("Anulowano zapis do pliku.");
                }
            }
        });
        wczytajBTN.addActionListener(e -> {
            JFileChooser fileChooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(" rysunki .png", "png");
            fileChooser.setFileFilter(filter);
            SpolszczenieJFileChooser.PL(fileChooser);
            int returnVal = fileChooser.showOpenDialog(TrasaWindow.this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                //WyczyscListe(systemSterowania,ktorySilnik, ktoryCzujnik);
                File file = fileChooser.getSelectedFile();
                returnVal = trasaPNL.WczytajzPliku(file);
                switch (returnVal){
                    case 0:
                        komunikatLBL.setText("Plik: " + file.getName() + " nie zawiera prawidłowego rysunku.");
                        break;
                    default:
                        komunikatLBL.setText("Wczytano dane z pliku: " + file.getName());
                        nazwaPlikuLBL.setText(file.getName());
//                        sw.UstawPolaFormularza(systemSterowania, mw, ktorySilnik);
//                        OdswiezPolaFormularza(systemSterowania,ktorySilnik,ktoryCzujnik);
                        break;

                }
            } else
                komunikatLBL.setText("Anulowano odczyt z pliku.");
        });
    }

    private void UstawPolaFormularza(SystemSterowania systemSterowania) {
        nazwaTF.setText(systemSterowania.getTrasa().getNazwa());
    }
}