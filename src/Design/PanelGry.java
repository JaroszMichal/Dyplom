package Design;

import Project.Punkt;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;

public class PanelGry extends JPanel implements KeyListener {

//  tło - obraz, położenie zoom
    private BufferedImage image;
    private int carWidth;
    private int carHeight;
    private int xStart;
    private int yStart;
    private int xWidth,yHeight;
    private double polPrzekatnejAuta;
    private boolean pauza;
    private String pauzaString;
//  ruch klawiszami
    private boolean left = false;
    private boolean right = false;
    private boolean up = false;
    private boolean down = false;
//  parametry auta
    private double carXdouble;
    private double carYdouble;
    private double angle;
    private double speed;
    private Punkt srodekPrzodu;
    private Punkt lewePrzednie;
    private Punkt prawePrzednie;
    private Punkt leweTylne;
    private Punkt praweTylne;
    private boolean kolizja;
    //    private
    private String komunikat="";

    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public PanelGry(){
        addKeyListener(this);
        setFocusable(true);
        xStart = 0;
        yStart = 0;
        carWidth = 60;
        carHeight = 30;
        polPrzekatnejAuta = Math.sqrt(Math.pow(carWidth,2)+Math.pow(carHeight,2)) / 2;
        angle=0;
        speed=0;
        kolizja = false;
        pauza = false;
        srodekPrzodu = new Punkt(0,0);
        lewePrzednie = new Punkt(0,0);
        prawePrzednie = new Punkt(0,0);
        leweTylne = new Punkt(0,0);
        praweTylne = new Punkt(0,0);

        new Thread(() -> {
            try {
                while (true) {
                    if (down) speed--;
                    if (up) speed++;
                    if (left) angle+=Math.toRadians(1);
                    if (right) angle-=Math.toRadians(1);
                    angle = angle % (2*Math.PI);
                    repaint();
                    Thread.sleep(33);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }).start();
    }

    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (image!=null) {
            Graphics2D g2 = (Graphics2D) g;
            if ((this.getWidth()<300)||(this.getHeight()<350)){
                pauza = true;
                pauzaString = "Zwiększ rozmiar okna";
            }
            else {
                pauzaString = "PAUZA";
                BufferedImage sub = WstawTrase();
                g2.drawImage(sub, 0, 0, xWidth, yHeight, null);
            }
            if (pauza) {
                g2.setColor(Color.RED);
                Rectangle rectangle = new Rectangle(0, 0, this.getWidth(), this.getHeight());
                centerString(g2, rectangle, pauzaString, new Font("Helvetica", Font.BOLD, 20));
            } else {
                ObliczParametryJazdy();
                NarysujAuto(g);
                NarysujKomunikaty(g2);
            }
        }
    }

    @Override
    public void keyTyped(KeyEvent e) {}

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = true;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = true;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = true;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = true;
        if (e.getKeyCode() == KeyEvent.VK_P) pauza = !pauza;
    }

    @Override
    public void keyReleased(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) left = false;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT) right = false;
        if (e.getKeyCode() == KeyEvent.VK_UP) up = false;
        if (e.getKeyCode() == KeyEvent.VK_DOWN) down = false;
    }

    private boolean UstawNaStarcie() {
        boolean result = false;
        Punkt gornyLewy = new Punkt(0,0);
        Punkt gornyPrawy = new Punkt(0,0);
        Punkt dolnyLewy = new Punkt(0,0);
        Punkt dolnyPrawy = new Punkt(0,0);
        for (int i=0; i<image.getWidth();i++)
            for (int j=0; j<image.getHeight();j++)
                if ((new Color(image.getRGB(i, j))).equals(Color.MAGENTA)){
                    gornyLewy.setX(i);
                    gornyLewy.setY(j);
                    i=image.getWidth();
                    j=image.getHeight();
                    result = true;
                }
        for (int j=0; j<image.getHeight();j++)
            for (int i=image.getWidth()-1; i>=0;i--)
                if ((new Color(image.getRGB(i, j))).equals(Color.MAGENTA)){
                    gornyPrawy.setX(i);
                    gornyPrawy.setY(j);
                    i=-1;
                    j=image.getHeight();
                }
        for (int j=image.getHeight()-1; j>=0;j--)
            for (int i=0; i<image.getWidth();i++)
                if ((new Color(image.getRGB(i, j))).equals(Color.MAGENTA)){
                    dolnyLewy.setX(i);
                    dolnyLewy.setY(j);
                    i=image.getWidth();
                    j=-1;
                }
        for (int i=image.getWidth()-1;i>=0;i--)
            for (int j=image.getHeight()-1;j>=0;j--)
                if ((new Color(image.getRGB(i, j))).equals(Color.MAGENTA)){
                    dolnyPrawy.setX(i);
                    dolnyPrawy.setY(j);
                    i=-1;
                    j=-1;
                }

        Punkt p1 = new Punkt(0,0);
        Punkt p2 = new Punkt(0,0);
        if (odleglosc(gornyLewy, gornyPrawy)>odleglosc(gornyLewy, dolnyLewy)){
            p1.setX((gornyLewy.getX()+dolnyLewy.getX())/2);
            p1.setY((gornyLewy.getY()+dolnyLewy.getY())/2);
            p2.setX((gornyPrawy.getX()+dolnyPrawy.getX())/2);
            p2.setY((gornyPrawy.getY()+dolnyPrawy.getY())/2);
        }
        else{
            p2.setX((gornyLewy.getX()+gornyPrawy.getX())/2);
            p2.setY((gornyLewy.getY()+gornyPrawy.getY())/2);
            p1.setX((dolnyLewy.getX()+dolnyPrawy.getX())/2);
            p1.setY((dolnyLewy.getY()+dolnyPrawy.getY())/2);
        }
        double alfa;
        if (p1.getX()==p2.getX())
            alfa = Math.PI/2;
        else
            alfa = Math.atan((p1.getY()-p2.getY())/(p2.getX()-p1.getX()));
        angle = alfa-Math.PI/2;

        carXdouble = (gornyLewy.getX()+dolnyPrawy.getX())/2 - carWidth/2*Math.cos(angle);
        carYdouble = (gornyLewy.getY()+dolnyPrawy.getY())/2 + carWidth/2*Math.sin(angle);

//      ustawiamy ramkę widoku
        if (image.getWidth()<this.getWidth())
            xStart = 0;
        else{
            if (carXdouble<0.8*this.getWidth())
                xStart = 0;
            else
                if (carXdouble>image.getWidth()-0.8*this.getWidth())
                    xStart = image.getWidth() - this.getWidth();
                else
                    xStart = (int)(carXdouble - this.getWidth()/2);
        }

        if (image.getHeight()<this.getHeight())
            yStart = 0;
        else{
            if (carYdouble<0.8*this.getHeight())
                yStart = 0;
            else
                if (carYdouble>image.getHeight()-0.8*this.getHeight())
                    yStart = image.getHeight() - this.getHeight();
                else
                    yStart = (int)(carYdouble - this.getHeight()/2);
        }


        return result;
    }

    private double odleglosc(Punkt p1, Punkt p2) {
        return (Math.sqrt(Math.pow(p1.getX()-p2.getX(),2)+Math.pow(p1.getY()-p2.getY(),2)));
    }

    private BufferedImage WstawTrase() {
        int thw = this.getWidth();
        int imW = image.getWidth();
        int thh = this.getHeight();
        int imh = image.getHeight();
        xWidth = Math.min(this.getWidth(), image.getWidth());
        yHeight= Math.min(this.getHeight(), image.getHeight());

        xStart = (int)(carXdouble - this.getWidth()/2);
        if (xStart<0)
            xStart = 0;
        if (xStart+xWidth>image.getWidth())
            xStart = image.getWidth()-xWidth;

        yStart = (int)(carYdouble - this.getHeight()/2);
        if (yStart<0)
            yStart = 0;
        if (yStart+yHeight>image.getHeight())
            yStart = image.getHeight()-yHeight;
//        xStart = image.getWidth()-xWidth;
//        yStart = image.getHeight()-yHeight;

        return image.getSubimage(xStart,yStart,xWidth,yHeight);
    }

    private void ObliczParametryJazdy() {
        double deltaX = speed * Math.cos(angle);
        double deltaY = - speed * Math.sin(angle);
        int intCarX = (int)carXdouble;
        int intCarY = (int)carYdouble;
        int docelowyIntCarX = (int)(carXdouble+deltaX);
        int docelowyIntCarY = (int)(carYdouble+deltaY);


        carXdouble = carXdouble + deltaX;
        carYdouble = carYdouble +deltaY;
        if (carXdouble <= polPrzekatnejAuta)
            carXdouble = polPrzekatnejAuta;
        if (carXdouble >= image.getWidth()-polPrzekatnejAuta)
            carXdouble = image.getWidth()-polPrzekatnejAuta;
        if (carYdouble <= polPrzekatnejAuta)
            carYdouble = polPrzekatnejAuta;
        if (carYdouble >= image.getHeight()-polPrzekatnejAuta)
            carYdouble = image.getHeight()-polPrzekatnejAuta;
        ObliczPozycjeKol();
        kolizja = !jestMozliwyRuch();
        //komunikat += "Kolizja = "+kolizja;
    }

    private boolean jestMozliwyRuch() {
        if (ileKolPozaTrasa()>0)
            return false;
        else
            return true;
    }

    private void NarysujAuto(Graphics g){
        Graphics2D g2 = (Graphics2D)g.create();
        g2.setColor(Color.RED);
        int x[] = {(int)(lewePrzednie.getX()-xStart), (int)(prawePrzednie.getX()-xStart),(int)(praweTylne.getX()-xStart), (int)(leweTylne.getX()-xStart)};
        int y[] = {(int)(lewePrzednie.getY()-yStart), (int)(prawePrzednie.getY()-yStart),(int)(praweTylne.getY()-yStart), (int)(leweTylne.getY()-yStart)};
        g2.fillPolygon(x,y,4);
        g2.setColor(Color.YELLOW);
        g2.drawLine((int)(leweTylne.getX()-xStart),(int)(leweTylne.getY()-yStart),(int)(srodekPrzodu.getX()-xStart),(int)(srodekPrzodu.getY()-yStart));
        g2.drawLine((int)(praweTylne.getX()-xStart),(int)(praweTylne.getY()-yStart),(int)(srodekPrzodu.getX()-xStart),(int)(srodekPrzodu.getY()-yStart));
        g2.dispose();
    }

    private void NarysujKomunikaty(Graphics2D g2) {
        g2.setColor(Color.WHITE);
        g2.drawString(komunikat, 10,10);
    }

    private int ileKolPozaTrasa() {
        int result = 0;
        if (!(new Color(image.getRGB((int)lewePrzednie.getX(), (int)lewePrzednie.getY())).equals(Color.white))
                && !(new Color(image.getRGB((int)lewePrzednie.getX(), (int)lewePrzednie.getY())).equals(Color.MAGENTA))) result++;
        if (!(new Color(image.getRGB((int)prawePrzednie.getX(), (int)prawePrzednie.getY())).equals(Color.white))
                && !(new Color(image.getRGB((int)prawePrzednie.getX(), (int)prawePrzednie.getY())).equals(Color.MAGENTA))) result++;
        if (!(new Color(image.getRGB((int)leweTylne.getX(), (int)leweTylne.getY())).equals(Color.white))
                && !(new Color(image.getRGB((int)leweTylne.getX(), (int)leweTylne.getY())).equals(Color.MAGENTA))) result++;
        if (!(new Color(image.getRGB((int)praweTylne.getX(), (int)praweTylne.getY())).equals(Color.white))
                && !(new Color(image.getRGB((int)praweTylne.getX(), (int)praweTylne.getY())).equals(Color.MAGENTA))) result++;
        return result;
    }

    public void UstawObraz(BufferedImage image){
        this.image = image;
        if (!UstawNaStarcie()){
            JOptionPane.showMessageDialog(this, "Rysunek nie zawiera zdefiniowanego pola START / META", "Uwaga!", JOptionPane.ERROR_MESSAGE);
            this.image = null;
        }
        repaint();
    }

    private void ObliczPozycjeKol() {
//        double polPrzekatnej = Math.sqrt(Math.pow(carWidth/2, 2)+Math.pow(carHeight/2,2));
//        double srodekX = carX + carWidth/2;
//        double srodekY = carY + carHeight/2;
//        double katMiedzyPrzekatnymi = 2 * Math.toDegrees(Math.atan(carHeight*1.0/carWidth));
//        lewePrzednie.setX(srodekX+polPrzekatnej*Math.cos(Math.toRadians(angle + katMiedzyPrzekatnymi / 2)));
//        lewePrzednie.setY(srodekY+polPrzekatnej*Math.sin(Math.toRadians(angle + katMiedzyPrzekatnymi / 2)));
//        prawePrzednie.setX(srodekX+polPrzekatnej*Math.cos(Math.toRadians(angle - katMiedzyPrzekatnymi / 2)));
//        prawePrzednie.setY(srodekY+polPrzekatnej*Math.sin(Math.toRadians(angle - katMiedzyPrzekatnymi / 2)));
//        leweTylne.setX(srodekX-polPrzekatnej*Math.cos(Math.toRadians(angle - katMiedzyPrzekatnymi / 2)));
//        leweTylne.setY(srodekY-polPrzekatnej*Math.sin(Math.toRadians(angle - katMiedzyPrzekatnymi / 2)));
//        praweTylne.setX(srodekX-polPrzekatnej*Math.cos(Math.toRadians(angle + katMiedzyPrzekatnymi / 2)));
//        praweTylne.setY(srodekY-polPrzekatnej*Math.sin(Math.toRadians(angle + katMiedzyPrzekatnymi / 2)));
        srodekPrzodu.setX(carXdouble+carWidth/2*Math.cos(angle));
        srodekPrzodu.setY(carYdouble-carWidth/2*Math.sin(angle));
        lewePrzednie.setX(srodekPrzodu.getX()-carHeight/2*Math.cos(Math.PI/2-angle));
        lewePrzednie.setY(srodekPrzodu.getY()-carHeight/2*Math.sin(Math.PI/2-angle));
        prawePrzednie.setX(srodekPrzodu.getX()+carHeight/2*Math.cos(Math.PI/2-angle));
        prawePrzednie.setY(srodekPrzodu.getY()+carHeight/2*Math.sin(Math.PI/2-angle));
        leweTylne.setX(lewePrzednie.getX()-carWidth*Math.cos(angle));
        leweTylne.setY(lewePrzednie.getY()+carWidth*Math.sin(angle));
        praweTylne.setX(prawePrzednie.getX()-carWidth*Math.cos(angle));
        praweTylne.setY(prawePrzednie.getY()+carWidth*Math.sin(angle));
    }

    public void centerString(Graphics g, Rectangle r, String s,
                             Font font) {
        FontRenderContext frc =
                new FontRenderContext(null, true, true);

        Rectangle2D r2D = font.getStringBounds(s, frc);
        int rWidth = (int) Math.round(r2D.getWidth());
        int rHeight = (int) Math.round(r2D.getHeight());
        int rX = (int) Math.round(r2D.getX());
        int rY = (int) Math.round(r2D.getY());

        int a = (r.width / 2) - (rWidth / 2) - rX;
        int b = (r.height / 2) - (rHeight / 2) - rY;

        g.setFont(font);
        g.drawString(s, r.x + a, r.y + b);
    }
}
