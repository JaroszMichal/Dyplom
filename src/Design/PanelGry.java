package Design;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PanelGry extends JPanel implements KeyListener {
    private BufferedImage image;
    private int xStart = 0;
    private int yStart = 0;
    private int xWidth,yHeight;
    private double zoom = 1;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;

    public PanelGry(){
        addKeyListener(this);
        setFocusable(true);
        new Thread(() -> {
            try {
                while (true) {
                    if (down) yStart++;
                    if (up) yStart--;
                    if (left) xStart--;
                    if (right) xStart++;
                    repaint();
                    Thread.sleep(30);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image!=null) {
            Graphics2D g2 = (Graphics2D)g;
            BufferedImage sub;
            Oblicz();
            sub = image.getSubimage(xStart,yStart,xWidth,yHeight);
            g2.drawImage(sub, 0, 0, Math.min(getWidth(),image.getWidth()),Math.min(getHeight(),image.getHeight()), null);
            g2.setColor(Color.WHITE);
            NarysujRozdzielczosc(g2);
        }
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
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
    }

    public void UstawObraz(BufferedImage image){
        this.image = image;
        repaint();
    }
}
