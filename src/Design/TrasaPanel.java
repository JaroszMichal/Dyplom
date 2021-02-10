package Design;

import Project.Punkt;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.util.ArrayList;

public class TrasaPanel extends JPanel implements MouseMotionListener, MouseListener, MouseWheelListener, KeyListener {
    private BufferedImage image;
    private ArrayList<Point> listapunktow = new ArrayList<>();
    private int brush = 50;
    boolean zapis = false;
    boolean rysunekWczytany = false;
    private int xStart = 0;
    private int yStart = 0;
    private int xWidth,yHeight;
    private double zoom = 1;
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
    private boolean start;
    private double liniaStartuX0, liniaStartuY0;
    private int liniaStartuX1, liniaStartuY1;
    private String kolor=".";
    private Graphics2D g2;
    private Cursor blankCursor;

    public boolean isStart() {
        return start;
    }
    public void setStart(boolean start) {
        this.start = start;
    }

    public TrasaPanel(){
        BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
        blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
        addMouseMotionListener(this);
        addMouseListener(this);
        addMouseWheelListener(this);
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
        g2 = (Graphics2D)g;
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
            NarysujRozdzielczosc();
        g2.setColor(Color.MAGENTA);
        g2.drawLine((int)liniaStartuX0, (int)liniaStartuY0, liniaStartuX1, liniaStartuY1);
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

    private void NarysujRozdzielczosc() {
        g2.setColor(Color.WHITE);
        String str = "("+getWidth()+", "+getHeight()+"), zoom = "+zoom+", xStart = "+xStart+", yStart = "+yStart + kolor;
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
        if (start) {
            setCursor(blankCursor);
            BufferedImage bi=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
            Graphics2D g2=(Graphics2D)bi.getGraphics();
            paint(g2);
            setCursor(Cursor.getDefaultCursor());
            liniaStartuX1=e.getX();
            liniaStartuY1=e.getY();
            Color c = new Color(bi.getRGB(liniaStartuX1,liniaStartuY1));
            if (c.equals(Color.WHITE))
                NaszkicujLinieStartu(bi);
            else{
                liniaStartuX0 = -10;
                liniaStartuY0 = -10;
                liniaStartuX1 = -10;
                liniaStartuY1 = -10;
            }
        }
    }

    private void NaszkicujLinieStartu(BufferedImage bi) {
        NajblizszyPunktSpozaTrasy(bi);
        kolor = ", x = "+liniaStartuX1+", y = "+liniaStartuY1+", punkt.x = "+liniaStartuX0+", punkt.y = "+liniaStartuY0;
        repaint();
    }

    private void NajblizszyPunktSpozaTrasy(BufferedImage bi) {
        int r=1;
        int x0, y0;
        do{
            for (int i=0; i<360;i+=1){
                x0 = (int)(r*Math.cos(Math.toRadians(i)));
                y0 = (int)(r*Math.sin(Math.toRadians(i)));
                if (!(new Color(bi.getRGB(liniaStartuX1+x0,liniaStartuY1-y0)).equals(Color.white))){
                    liniaStartuX0 = liniaStartuX1+x0;
                    liniaStartuY0 = liniaStartuY1-y0;
                    return;
                }
            }
            r++;
        }while (r!=1000);
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
        if (e.getWheelRotation() < 0) { //rolka do przodu
            zoom -= 0.1;
        } else {                      // rolka w dół
            zoom += 0.1;
        }
        if (zoom < 0.3) zoom = 0.3;
        if (zoom > 1) zoom = 1;
        repaint();
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

    public BufferedImage PobierzObrazzPanelu(){
        return image;
    }
    public void UstawObraz(BufferedImage image){
        this.image = image;
        rysunekWczytany=true;
        repaint();
    }
    public BufferedImage ZaakceptujTrase(){
        BufferedImage bi=new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
        zapis = true;
        repaint();
        Graphics2D g2=(Graphics2D)bi.getGraphics();
        paint(g2);
        return bi;
    }
}
