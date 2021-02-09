package Design;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TrasaPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener {
    int x=-10;
    int y=-10;
    private BufferedImage image;
    private Graphics2D g2;
    private ArrayList<Point> listapunktow = new ArrayList<>();
    private int brush = 30;
    boolean zapis = false;
    boolean rysunekWczytany = false;
    private int xStart = 0;
    private int yStart = 0;
    private double zoom = 1;

    public TrasaPanel(){
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (rysunekWczytany) {
            BufferedImage sub;
            sub = image.getSubimage(xStart,yStart,rozmiarX(image),rozmiarY(image));
            g2.drawImage(sub, 0, 0, this.getWidth(), this.getHeight(), null);
        }
        else {
            g2.setColor(Color.BLACK);
            g2.fillRect(0, 0, getWidth() - 1, getHeight() - 1);
        }
            g2.setColor(Color.WHITE);
            for (int i = 0; i < listapunktow.size(); i++)
                g2.fillOval(listapunktow.get(i).x, listapunktow.get(i).y, brush, brush);
        if (!zapis)
            NarysujRozdzielczosc(g2);
    }

    private int rozmiarX(BufferedImage image) {
        int result;
        result = (int)(image.getWidth()*zoom);
        return result;
    }

    private int rozmiarY(BufferedImage image) {
        int result;
        result = (int)(image.getHeight()*zoom);
        return result;
    }

    private void NarysujRozdzielczosc(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        String str = "("+getWidth()+", "+getHeight()+"), zoom = "+zoom;
        g2.drawString(str, 10,10);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            listapunktow.add(new Point(e.getX() - brush / 2, e.getY() - brush / 2));
            repaint();
        }
        if (SwingUtilities.isRightMouseButton(e)){
            xStart+=e.getX();
            yStart+=e.getY();
            if (xStart<0) xStart=0;
            if (yStart<0) yStart=0;
//            if (xStart+image.getWidth())
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public void saveCanvas() {

        BufferedImage bi=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        zapis = true;
        repaint();
        Graphics2D g2=(Graphics2D)bi.getGraphics();

        paint(g2);
        try {
            ImageIO.write(bi, "png", new File("C:\\java\\canvas.png"));
        } catch (Exception e) {

        }
        zapis = false;
        repaint();
    }

    public void loadImage(){
        try {
            image = ImageIO.read(new File("C:\\java\\canvas.png"));
            rysunekWczytany = true;
            listapunktow.clear();
            repaint();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        listapunktow.add(new Point(e.getX() - brush / 2, e.getY() - brush / 2));
        repaint();
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void mouseWheelMoved(MouseWheelEvent e) {
        if(e.getWheelRotation()<0){ //rolka do przodu
            zoom+=0.1;
        }
        else {                      // rolka w dół
            zoom-=0.1;
        }
        if (zoom<0.3) zoom = 0.3;
        if (zoom>1) zoom = 1;
        repaint();
    }
}
