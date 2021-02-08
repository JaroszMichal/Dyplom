package Design;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;

public class TrasaPanel extends JPanel implements MouseMotionListener {
    int x=-10;
    int y=-10;

    public TrasaPanel(){
        addMouseMotionListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
//        try {
//        }
//        catch(Exception e){
//
//        }

    }

    private void RysujMyszka(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillOval(x,y,5,5);
    }

    private void WypelnijNaCzarno(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0,0,getWidth()-1,getHeight()-1);
    }

    private void NarysujRozdzielczosc(Graphics g) {
        g.setColor(Color.WHITE);
        String str = "("+getWidth()+", "+getHeight()+")";
        g.drawString(str, 10,10);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
//        x = e.getX();
//        y = e.getY();
//        repaint();
        Graphics graphics = getGraphics();
        graphics.fillOval(e.getX(), e.getY(), 10,10);
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void saveCanvas() {
        BufferedImage image=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);

        Graphics2D g2=(Graphics2D)image.getGraphics();


        paint(g2);
        try {
            ImageIO.write(image, "png", new File("C:\\java\\canvas.png"));
        } catch (Exception e) {

        }
    }
}
