package Design;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ValueReturningWindow extends JFrame{
    private JPanel panel1;
    private JTextField valueTF;
    private JButton OKButton;
    private JButton addToListButton;
    private JList list1;
    private JButton clearListButton;
    private JButton updateButton;
    private JButton deleteButton;
    DefaultListModel dm = new DefaultListModel();

    public ValueReturningWindow(MainWindow mw){
        add(panel1);
        setSize(600,400);
        setTitle("Value Window");
        setLocation(mw.getX()+mw.getWidth(),mw.getY());
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        getRootPane().setDefaultButton(addToListButton);


        OKButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setVisible(false);
                mw.setTestLBL(valueTF.getText());
            }
        });

        addToListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                add(valueTF.getText());
                valueTF.setText("");
            }
        });
        clearListButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dm.clear();
                list1.setModel(dm);
            }
        });
        list1.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                valueTF.setText(list1.getSelectedValue().toString());
            }
        });
    }

    private void add(String s){
        list1.setModel(dm);
        dm.addElement(s);
    }
}
