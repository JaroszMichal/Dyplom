package Design;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class TrasaPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {
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
    private int xWidth,yHeight;
    private double proporcjeObrazka;
    private double zoom = 1;

    public TrasaPanel(){
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
        addKeyListener(this);
        setFocusable(true);
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D)g;
        if (rysunekWczytany) {
            BufferedImage sub;
            Oblicz();
            sub = image.getSubimage(xStart,yStart,xWidth,yHeight);
            g2.drawImage(sub, 0, 0, Math.min(getWidth(),image.getWidth()),Math.min(getHeight(),image.getHeight()), null);
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

    private void Oblicz() {
        xWidth = Math.min((int)(this.getWidth()*zoom), image.getWidth());
        yHeight= Math.min((int)(this.getHeight()*zoom), image.getHeight());
        if (xStart<0)
            xStart = 0;
        if (xStart > image.getWidth()- xWidth)
            xStart = image.getWidth()- xWidth;
        if (yStart<0)
            yStart = 0;
        if (yStart > image.getHeight()-yHeight)
            yStart = image.getHeight()-yHeight;
    }

    private void NarysujRozdzielczosc(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        String str = "("+getWidth()+", "+getHeight()+"), zoom = "+zoom+", xStart = "+xStart+", yStart = "+yStart;
        g2.drawString(str, 10,10);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            listapunktow.add(new Point(e.getX() - brush / 2, e.getY() - brush / 2));
            repaint();
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {

    }

    public boolean zapiszDoPliku(File file) {

        BufferedImage bi=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        zapis = true;
        repaint();
        Graphics2D g2=(Graphics2D)bi.getGraphics();
        paint(g2);
        try {
            ImageIO.write(bi, "png", file);
            zapis = false;
            repaint();
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public int WczytajzPliku(File file){
        try {
            image = ImageIO.read(file);
            rysunekWczytany = true;
            listapunktow.clear();
            repaint();
            return 1;
        } catch (Exception ex) {
            ex.printStackTrace();
            return 0;
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
            zoom-=0.1;
        }
        else {                      // rolka w dół
            zoom+=0.1;
        }
        if (zoom<0.3) zoom = 0.3;
        if (zoom>1) zoom = 1;
        repaint();
    }

    @Override
    public void keyTyped(KeyEvent e) {
        System.out.println(".");
    }

    @Override
    public void keyPressed(KeyEvent e) {
        int keyCode = e.getKeyCode();
        switch (keyCode){
            case KeyEvent.VK_UP:
                yStart--;
                if (yStart<0)
                    yStart = 0;
                break;
            case KeyEvent.VK_DOWN:
                yStart++;
                break;
            case KeyEvent.VK_LEFT:
                xStart--;
                if (xStart<0)
                    xStart=0;
                break;
            case KeyEvent.VK_RIGHT:
                xStart++;
                break;
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }
}
