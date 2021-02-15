package Design;
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
    private int xWidth,yHeight;
    private boolean start;
    private double liniaStartuX0, liniaStartuY0;
    private double liniaStartuX1, liniaStartuY1;
    private double liniaStartuX2, liniaStartuY2;
    private String komunikat="";
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
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g2 = (Graphics2D)g;
        if (rysunekWczytany) {
            BufferedImage sub;
            Oblicz();
            sub = image.getSubimage(0,0,xWidth,yHeight);
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
        if ((liniaStartuX0>0) && (liniaStartuY0>0) && (liniaStartuX2>0) && (liniaStartuY2>0)) {
            g2.setColor(Color.MAGENTA);
            BasicStroke grubaLinia = new BasicStroke(12.0f);
            g2.setStroke(grubaLinia);
            g2.drawLine((int) liniaStartuX0, (int) liniaStartuY0, (int) liniaStartuX2, (int) liniaStartuY2);
        }
    }

    private void Oblicz() {
        xWidth = Math.min(this.getWidth(), image.getWidth());
        yHeight= Math.min(this.getHeight(), image.getHeight());
    }

    private void NarysujRozdzielczosc() {
        g2.setColor(Color.WHITE);
        String str = "start = "+start +", "+komunikat;
        g2.drawString(str, 10,10);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            listapunktow.add(new Point(e.getX() - brush / 2, e.getY() - brush / 2));
            image = this.ZaakceptujTrase();
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
            liniaStartuX1=e.getX();
            liniaStartuY1=e.getY();
            Color c = new Color(image.getRGB((int)liniaStartuX1,(int)liniaStartuY1));
            komunikat = "Kolor pod kursorem = "+c;
            if (c.equals(Color.WHITE))
                NaszkicujLinieStartu();
            else{
                liniaStartuX0 = -100;
                liniaStartuY0 = -100;
                liniaStartuX2 = -100;
                liniaStartuY2 = -100;
            }
            repaint();
        }
    }

    private void NaszkicujLinieStartu() {
        NajblizszyPunktSpozaTrasy();
        //komunikat += ", x1 = ("+liniaStartuX1+", "+liniaStartuY1+"), x0 = ("+liniaStartuX0+", "+liniaStartuY0+")";
        repaint();
    }

    private void NajblizszyPunktSpozaTrasy() {
        int r=1;
        do{
            for (int i=0; i<360;i++){
                liniaStartuX0 = liniaStartuX1+r*Math.cos(Math.toRadians(i));
                liniaStartuY0 = liniaStartuY1-r*Math.sin(Math.toRadians(i));
                if (wObrazie((int)liniaStartuX0, (int)liniaStartuY0)) {
                    if (!(new Color(image.getRGB((int)liniaStartuX0, (int)liniaStartuY0)).equals(Color.white))) {
                        liniaStartuX2 = liniaStartuX1-r*Math.cos(Math.toRadians(i));
                        liniaStartuY2 = liniaStartuY1+r*Math.sin(Math.toRadians(i));

                        return;
                    }
                }
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
        repaint();
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
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {
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
