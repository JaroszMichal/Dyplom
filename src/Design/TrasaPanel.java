package Design;

import java.util.Observable;

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
    private int brush = 90;
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
    private double liniaStartuX1, liniaStartuY1;
    private double liniaStartuX2, liniaStartuY2;
    private String kolor=".";
    private Graphics2D g2;
    private Cursor blankCursor;

    public boolean isStart() {
        return start;
    }
    public void setStart(boolean start) {
        this.start = start;
    }
    public double getZoom() {
        return zoom;
    }
    public void setZoom(double zoom) {
        this.zoom = zoom;
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
        BasicStroke grubaLinia = new BasicStroke(12.0f);
        g2.setStroke(grubaLinia);
        g2.drawLine((int)liniaStartuX0, (int)liniaStartuY0, (int)liniaStartuX2, (int)liniaStartuY2);
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
        String str = "("+getWidth()+", "+getHeight()+"), zoom = "+zoom+", xStart = "+xStart+", yStart = "+yStart +"start = "+start+ kolor;
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
            if (image==null) {
                setCursor(blankCursor);
                image = new BufferedImage(getWidth(), getHeight(), BufferedImage.TYPE_INT_RGB);
                Graphics2D g2 = (Graphics2D) image.getGraphics();
                paint(g2);
                setCursor(Cursor.getDefaultCursor());
            }
            liniaStartuX1=xStart+e.getX();
            liniaStartuY1=yStart+e.getY();
            Color c = new Color(image.getRGB((int)liniaStartuX1,(int)liniaStartuY1));
            if (c.equals(Color.WHITE))
                NaszkicujLinieStartu();
            else{
                liniaStartuX0 = -20;
                liniaStartuY0 = -20;
                liniaStartuX2 = -20;
                liniaStartuY2 = -20;
            }
        }
    }

    private void NaszkicujLinieStartu() {
        NajblizszyPunktSpozaTrasy();
        kolor += ", x1 = ("+liniaStartuX1+", "+liniaStartuY1+"), x0 = ("+liniaStartuX0+", "+liniaStartuY0+")";
        repaint();
    }

    private void NajblizszyPunktSpozaTrasy() {
        int r=1;
        double x0, y0;
        do{
            for (int i=0; i<360;i++){
                x0 = r*Math.cos(Math.toRadians(i));
                y0 = r*Math.sin(Math.toRadians(i));
                int newX0 = (int)(liniaStartuX1+x0);
                int newY0 = (int)(liniaStartuY1-y0);
                int newX1 = (int)(liniaStartuX1-x0);
                int newY1 = (int)(liniaStartuY1+y0);
                if (wObrazie(newX0, newY0) && wObrazie(newX1, newY1)) {
                    if (!(new Color(image.getRGB(newX0, newY0)).equals(Color.white))) {
                        kolor = ", r = "+r+", x0,y0 = ("+x0+", "+y0+"), ";
                        liniaStartuX0 = newX0;
                        liniaStartuY0 = newY0;
                        liniaStartuX2 = newX1;
                        liniaStartuY2 = newY1;
                        return;
//                        r = 99;
                    }
                }
//                    int next=5;
//                    Color c;
//                    do{
//                        next++;
//                        if (x0!=0)
//                            c = new Color(image.getRGB(liniaStartuX1-next,(int)(liniaStartuY1+next*y0/x0)));
//                        else
//                            c = new Color(image.getRGB(liniaStartuX1,liniaStartuY1+next));
//                    }while(!c.equals(Color.white));
//                    if (x0!=0) {
//                        liniaStartuX1 -= next;
//                        liniaStartuY1 = (int) (liniaStartuY1 + next * y0 / x0);
//                    }
//                    else{
//                        liniaStartuY1 = liniaStartuY1 + next;
//                    }
            }
            r++;
        }while (r!=100);
    }

    private boolean wObrazie(int newX, int newY) {
        if ((newX>=0) && (newX<image.getWidth()) && (newY>=0) && (newY<image.getHeight()))
            return true;
        else
            return false;
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
        if (start)
            start = false;
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
//        if (!start) {
//            if (e.getWheelRotation() < 0) { //rolka do przodu
//                zoom -= 0.1;
//            } else {                      // rolka w dół
//                zoom += 0.1;
//            }
//            if (zoom < 0.3) zoom = 0.3;
//            if (zoom > 1) zoom = 1;
//            repaint();
//        }
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
