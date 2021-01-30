package Design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FunkcjeCzujnika extends JFrame{
    private JPanel mainPanel;
    private JTextField textField1;
    private JComboBox comboBox1;
    private JTextField textField2;
    private JButton zapiszButton;
    private JButton wczytajButton;
    private JButton dodajButton;
    private JButton zmiańButton;
    private JList list1;
    private int FrameHeight = 400;
    private final PojedynczaFunkcja[] pf = {null, null,null};

    public FunkcjeCzujnika(SterowanieWindow sw, int nr){
        add(mainPanel);
        setSize(600,FrameHeight);
        String title="";
        int y=0;
        switch (nr){
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
        y = FrameHeight*nr;
        setTitle("Definiowanie silnika - "+title);
        mainPanel.setBorder(BorderFactory.createTitledBorder(title));
        setLocation(sw.getX()+sw.getWidth(),10+y);
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);

        dodajButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (pf[1] == null)
                    pf[1] = new PojedynczaFunkcja(FunkcjeCzujnika.this, 0);
                pf[1].setVisible(true);
            }
        });
    }
}
