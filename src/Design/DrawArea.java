package Design;//package Design;
//
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.MouseAdapter;
//import java.awt.event.MouseEvent;
//
//public class DrawArea extends JComponent {
//    private Image image;
//    private Graphics2D g2;
//    private int currentX, currentY, oldX, oldY;
//
//    public DrawArea(){
//        setDoubleBuffered(false);
//        addMouseListener(new MouseAdapter() {
//            @Override
//            public void mousePressed(MouseEvent e) {
//                super.mousePressed(e);
//                oldX = e.getX();
//                oldY = e.getY();
//            }
//
//            @Override
//            public void mouseDragged(MouseEvent e) {
//                super.mouseDragged(e);
//                currentX = e.getX();
//                currentY = e.getY();
//                if (g2!=null) {
//                    g2.drawLine(oldX, oldY, currentX, currentY);
//                    repaint();
//                    oldX = currentX;
//                    oldY = currentY;
//                }
//            }
//        });
//    }
//
//    @Override
//    protected void paintComponent(Graphics g) {
//        if (image == null){
//            image = createImage(getWidth(), getHeight());
//            g2 = (Graphics2D)image.getGraphics();
//            g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
//            clear();
//        }
//        g.drawImage(image, 0,0,null);
//    }
//    public void clear(){
//        g2.setPaint(Color.white);
//        g2.fillRect(0,0,getSize().width, getSize().height);
//        g2.setPaint(Color.black);
//        repaint();
//    }
//    public void red(){
//        g2.setPaint(Color.red);
//    }
//}
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.List;

import javax.swing.*;

public class DrawArea extends JPanel {
    private static final int PREF_W = 500;
    private static final int PREF_H = PREF_W;
    private List<Shape> shapes = new ArrayList<>();
    private int oldX, oldY, currentX, currentY;
    Graphics2D g2;

    public DrawArea() {
        setBackground(Color.white);
        addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                oldX = e.getX();
                oldY = e.getY();
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                super.mouseDragged(e);
                currentX = e.getX();
                currentY = e.getY();
                if (g2!=null) {
                    g2.drawLine(oldX, oldY, currentX, currentY);
                    repaint();
                    oldX = currentX;
                    oldY = currentY;
                }
            }
        });
    }

    public void addShape(Shape shape) {
        shapes.add(shape);
        repaint();
    }

    @Override // make it bigger
    public Dimension getPreferredSize() {
        if (isPreferredSizeSet()) {
            return super.getPreferredSize();
        }
        return new Dimension(PREF_W, PREF_H);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                RenderingHints.VALUE_ANTIALIAS_ON);
        for (Shape shape : shapes) {
            g2.draw(shape);
        }
    }

//    private static void createAndShowGui() {
//        DrawArea drawChit = new DrawArea();
//
//        drawChit.addShape(new Line2D.Double(10, 10, 100, 100));
//        drawChit.addShape(new Ellipse2D.Double(120, 120, 200, 200));
//
//        JFrame frame = new JFrame("DrawChit");
//        frame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
//        frame.getContentPane().add(drawChit);
//        frame.pack();
//        frame.setLocationByPlatform(true);
//        frame.setVisible(true);
//    }
//
//    public static void main(String[] args) {
//        SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                createAndShowGui();
//            }
//        });
//    }
}