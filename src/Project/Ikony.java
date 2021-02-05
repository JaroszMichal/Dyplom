package Project;

import javax.swing.*;
import java.awt.*;

public class Ikony{

    public static final ImageIcon Red(int size){
        return new ImageIcon(new ImageIcon("resources\\red.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }

    public static final ImageIcon Green(int size){
        return new ImageIcon(new ImageIcon("resources\\green.png").getImage().getScaledInstance(size, size, Image.SCALE_DEFAULT));
    }


}
