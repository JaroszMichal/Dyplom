package Design;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class PanelGry extends JPanel implements KeyListener {

//  tło - obraz, położenie zoom
    private BufferedImage image;
    private int xStart = 0;
    private int yStart = 0;
    private int xWidth,yHeight;
    private double zoom = 1;
//  ruch klawiszami
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
//  parametry auta
    private double carXdouble;
    private double carYdouble;
    private int carX;
    private int carY;
    private int angle=0;
    private double speed;
//    private
    private String komunikat="";

    public PanelGry(){
        addKeyListener(this);
        setFocusable(true);
        UstawParametryStartoweAuta();
        new Thread(() -> {
            try {
                while (true) {
                    if (down) speed--;
                    if (up) speed++;
                    if (left) angle--;
                    if (right) angle++;
                    ObliczParametryJazdy();
                    repaint();
                    Thread.sleep(33);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    private void ObliczParametryJazdy() {
        komunikat = "angle = "+angle+", speed = "+speed;
        carXdouble = carXdouble + speed * Math.cos(Math.toRadians(angle));
        carYdouble = carYdouble + speed * Math.sin(Math.toRadians(angle));
        carX = (int)carXdouble;
        carY = (int)carYdouble;
    }

    private void UstawParametryStartoweAuta() {
        carXdouble=100;
        carYdouble=100;
        carX=100;
        carY=100;
        angle=0;
        speed=0;

    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image!=null) {
            Graphics2D g2 = (Graphics2D)g;
            BufferedImage sub = WstawTrase();
            g2.drawImage(sub, 0, 0, Math.min(getWidth(),image.getWidth()),Math.min(getHeight(),image.getHeight()), null);
            NarysujAuto(g);
            NarysujRozdzielczosc(g2);
        }
    }

    private BufferedImage WstawTrase() {
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
        return image.getSubimage(xStart,yStart,xWidth,yHeight);
    }
    private void NarysujAuto(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setColor(Color.RED);
        Rectangle rect2 = new Rectangle(carX, carY, 60, 30);

        g2.rotate(Math.toRadians(angle), rect2.x+rect2.width/2, rect2.y+rect2.height/2);
        g2.draw(rect2);
        g2.fill(rect2);
        g2.setColor(Color.BLUE);
        g2.drawLine(rect2.x, rect2.y, rect2.x+rect2.width/2, rect2.y+rect2.height/2);
        g2.drawLine(rect2.x, rect2.y+rect2.height, rect2.x+rect2.width/2, rect2.y+rect2.height/2);
        g2.drawLine(rect2.x+ rect2.width/2, rect2.y, rect2.x+rect2.width, rect2.y+rect2.height/2);
        g2.drawLine(rect2.x+ rect2.width/2, rect2.y+rect2.height, rect2.x+rect2.width, rect2.y+rect2.height/2);
        g2.drawRect(rect2.x, rect2.y, rect2.width, rect2.height);
        g2.dispose();
    }

    private void NarysujRozdzielczosc(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        String str = "("+getWidth()+", "+getHeight()+"), zoom = "+zoom+", xStart = "+xStart+", yStart = "+yStart+" "+komunikat;
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
