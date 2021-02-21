package Design;

import Project.Czujniki;
import Project.FunkcjaLiniowa;
import Project.Punkt;
import Project.SystemSterowania;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.font.FontRenderContext;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

public class PanelGry extends JPanel implements KeyListener {

    //  System sterowania
    private SystemSterowania systemSterowania;
    private boolean systemSteruje;
    private double sterowaniePredkoscia;
    private double sterowanieSkretem;

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
    private double predkosc;
    private double speed;
    private Punkt srodekPrzodu;
    private Punkt lewePrzednie;
    private Punkt prawePrzednie;
    private Punkt leweTylne;
    private Punkt praweTylne;
    //  czujniki
    private boolean pokazDzialanieCzujnikow;
    private boolean pokazWartosciCzujnikow;
    private boolean pokazParametryAuta;

    private double czPredkosc;
    private double czPolozenieNaTorze;
    private double czOdleglosciKierunekZakretu;
    private double czCombo;
    private Punkt punktZlewej;
    private Punkt punktZprawej;
    private Punkt punktPrzedLewym;
    private Punkt punktPrzedPrawym;

//  pomiar czasu
    private boolean naPoluStart;
    private long czasStart;
    private long ostatnieKolko;
    //    private
    private String komunikat="";

    public void setImage(BufferedImage image) {
        this.image = image;
    }
    public void setSystemSterowania(SystemSterowania systemSterowania) {
        this.systemSterowania = systemSterowania;
    }


    public PanelGry(){
        addKeyListener(this);
        setFocusable(true);
        xStart = 0;
        yStart = 0;
        carWidth = 40;
        carHeight = 20;
        polPrzekatnejAuta = Math.sqrt(Math.pow(carWidth,2)+Math.pow(carHeight,2)) / 2;
        angle=0;
        predkosc=0;
        speed=0;
        pauza = false;
        srodekPrzodu = new Punkt(0,0);
        lewePrzednie = new Punkt(0,0);
        prawePrzednie = new Punkt(0,0);
        leweTylne = new Punkt(0,0);
        praweTylne = new Punkt(0,0);
        pokazDzialanieCzujnikow = false;
        pokazWartosciCzujnikow = true;
        systemSteruje = false;

        new Thread(() -> {
            try {
                while (true) {
                    if (systemSteruje){
                        ObliczWspolczynnikiSterowania();
                    }
                    else {
                        sterowaniePredkoscia = 1;
                        sterowanieSkretem = 1;
                    }
                    if (((!systemSteruje) && (down)) || ((systemSteruje) && (sterowaniePredkoscia<0))) {
                        double dV = Math.abs(sterowaniePredkoscia) * systemSterowania.getAuto().getPredkoscMaksymalna()*0.033*1000/3600/systemSterowania.getAuto().getCzasWyhamowaniazPredkosciMaksymalnej();
                        predkosc-=dV;
                        if (predkosc<0)
                            predkosc=0;
                        speed = predkosc*0.033*10;
                    }
                    if (((!systemSteruje) && (up)) || ((systemSteruje) && (sterowaniePredkoscia>=0))) {
                        double dV = sterowaniePredkoscia * systemSterowania.getAuto().getPredkoscMaksymalna()*0.033*1000/3600/systemSterowania.getAuto().getCzasOsiagnieciaPredkosciMaksymalnej();
                        predkosc+=dV;
                        if (predkosc>systemSterowania.getAuto().getPredkoscMaksymalna()*1000/3600)
                            predkosc=systemSterowania.getAuto().getPredkoscMaksymalna()*1000/3600;
                        speed = predkosc*0.033*10;
                    }
                    if ((speed!=0) && (((!systemSteruje) && (left)) || ((systemSteruje) && (sterowanieSkretem<0))))
//                    if ((speed!=0) && (left))
                        angle += Math.abs(sterowanieSkretem) * Math.toRadians(systemSterowania.getAuto().getMaksymalnyKatSkretu());
                    if ((speed!=0) && (((!systemSteruje) && (right)) || ((systemSteruje) && (sterowanieSkretem>=0))))
//                    if ((speed!=0) && (right))
                        angle -= sterowanieSkretem * Math.toRadians(systemSterowania.getAuto().getMaksymalnyKatSkretu());
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
                MierzCzas();
                if (systemSteruje)
                    NarysujSystemSteruje(g2);
                if (pokazWartosciCzujnikow)
                    NarysujWskazaniaCzujnikow(g2);
                if (pokazParametryAuta)
                    NarysujParametryAuta(g2);
                NarysujKomunikaty(g2);
            }
        }
    }

    private void NarysujSystemSteruje(Graphics2D g2) {
        g2.setColor(Color.RED);
        Rectangle rectangle = new Rectangle(0, 0, this.getWidth(), 50);
        centerString(g2, rectangle, "AUTOSTEROWANIE", new Font("Helvetica", Font.BOLD, 20));

    }

    private void NarysujWskazaniaCzujnikow(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Helvetica", Font.PLAIN+Font.LAYOUT_LEFT_TO_RIGHT, 10));
        FontMetrics fontMetrics = g2.getFontMetrics();
        String s = Double.toString(Math.round(czPredkosc*1000) / 1000.0);
        g2.drawString("Prędkość [km/h] = " , this.getWidth() - 250,10);
        g2.drawString(s , this.getWidth() -fontMetrics.stringWidth(s)-10 ,10);
        s = Double.toString(Math.round(czPolozenieNaTorze*1000) / 1000.0);
        g2.drawString("Położenie na torze = ", this.getWidth() -250,20);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,20);
        s = Double.toString(Math.round(Math.abs(czOdleglosciKierunekZakretu)*1000) / 1000.0);
        if (czOdleglosciKierunekZakretu<0)
            g2.drawString("Zakręt w lewo za = ", this.getWidth() -250,30);
        else
            g2.drawString("Zakręt w prawo za = ", this.getWidth() -250,30);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,30);
        s = Double.toString(Math.round(czCombo*1000) / 1000.0);
        g2.drawString("Combo = ", this.getWidth() -250,40);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,40);
    }

    private void NarysujParametryAuta(Graphics2D g2) {
        g2.setColor(Color.GREEN);
        g2.setFont(new Font("Helvetica", Font.PLAIN+Font.LAYOUT_LEFT_TO_RIGHT, 10));
        FontMetrics fontMetrics = g2.getFontMetrics();
        String s = systemSterowania.getAuto().getNazwa();
        g2.drawString("Nazwa auta" , this.getWidth() - 250,this.getHeight()-50);
        g2.drawString(s , this.getWidth() -fontMetrics.stringWidth(s)-10 ,this.getHeight()-50);
        s = Double.toString(Math.round(systemSterowania.getAuto().getPredkoscMaksymalna()*1000) / 1000.0);
        g2.drawString("Prędkość maksymalna = ", this.getWidth() -250,this.getHeight()-40);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,this.getHeight()-40);
        s = Double.toString(Math.round(systemSterowania.getAuto().getCzasOsiagnieciaPredkosciMaksymalnej()*1000) / 1000.0);
        g2.drawString("Czas uzyskania prędkości maksymalnej = ", this.getWidth() -250,this.getHeight()-30);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,this.getHeight()-30);
        s = Double.toString(Math.round(systemSterowania.getAuto().getCzasWyhamowaniazPredkosciMaksymalnej()*1000) / 1000.0);
        g2.drawString("Czas wyhamowania z prędkości maksymalnej = ", this.getWidth() -250,this.getHeight()-20);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,this.getHeight()-20);
        s = Double.toString(Math.round(systemSterowania.getAuto().getMaksymalnyKatSkretu()*1000) / 1000.0);
        g2.drawString("Maksymalny kąt skrętu = ", this.getWidth() -250,this.getHeight()-10);
        g2.drawString(s, this.getWidth() -fontMetrics.stringWidth(s)-10,this.getHeight()-10);
    }

    private void MierzCzas() {
        if ((naPoluStart)&&(!(new Color(image.getRGB((int)srodekPrzodu.getX(), (int)srodekPrzodu.getY())).equals(Color.MAGENTA)))){
            naPoluStart=false;
            czasStart = System.currentTimeMillis();
        }
        if ((!naPoluStart)&&((new Color(image.getRGB((int)srodekPrzodu.getX(), (int)srodekPrzodu.getY()))).equals(Color.MAGENTA))){
            naPoluStart=true;
            ostatnieKolko = System.currentTimeMillis() - czasStart;
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
        if (e.getKeyCode() == KeyEvent.VK_A) systemSteruje = !systemSteruje;
        if (e.getKeyCode() == KeyEvent.VK_1) pokazWartosciCzujnikow = !pokazWartosciCzujnikow;
        if (e.getKeyCode() == KeyEvent.VK_2) pokazDzialanieCzujnikow = !pokazDzialanieCzujnikow;
        if (e.getKeyCode() == KeyEvent.VK_3) pokazParametryAuta = !pokazParametryAuta;
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
        angle = alfa+Math.PI/2;

        carXdouble = (gornyLewy.getX()+dolnyPrawy.getX())/2 - carWidth/2*Math.cos(angle);
        carYdouble = (gornyLewy.getY()+dolnyPrawy.getY())/2 + carWidth/2*Math.sin(angle);
        naPoluStart = true;
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

        return image.getSubimage(xStart,yStart,xWidth,yHeight);
    }

    private void ObliczParametryJazdy() {
        ObliczCzujniki();
        double tmpCarX = carXdouble;
        double tmpCarY = carYdouble;
        double tmpSpeed = speed;
        for (int i=0;i<10;i++) {
            carXdouble = tmpCarX + (i*0.1+0.1)*speed*Math.cos(angle);
            carYdouble = tmpCarY - (i*0.1+0.1)*speed*Math.sin(angle);
            ObliczPozycjeKol();
            if (przednieKoloPozaTrasa()) {
                tmpSpeed = ((i-1)*0.1+0.1)*speed;
                break;
            }
        }
        carXdouble = carXdouble + tmpSpeed * Math.cos(angle);
        carYdouble = carYdouble - tmpSpeed * Math.sin(angle);
        if (tmpSpeed!=speed) {
            predkosc = 0;
            speed = 0;
        }
        if (carXdouble <= polPrzekatnejAuta)
            carXdouble = polPrzekatnejAuta;
        if (carXdouble >= image.getWidth()-polPrzekatnejAuta)
            carXdouble = image.getWidth()-polPrzekatnejAuta;
        if (carYdouble <= polPrzekatnejAuta)
            carYdouble = polPrzekatnejAuta;
        if (carYdouble >= image.getHeight()-polPrzekatnejAuta)
            carYdouble = image.getHeight()-polPrzekatnejAuta;
        ObliczPozycjeKol();
        //komunikat += "Kolizja = "+kolizja;
    }

    private void ObliczCzujniki() {
        try {
//      CZUJNIK PRĘDKOŚCI
            czPredkosc = predkosc*3.6;
//      CZUJNIK POŁOŻENIA NA TORZE - LEWO, PRAWO
            punktZlewej = new Punkt(0, 0);
            punktZprawej = new Punkt(0, 0);
            int lewo = 0;
            do {
                lewo++;
                punktZlewej.setX(carXdouble + lewo * Math.cos(angle + Math.PI / 2));
                punktZlewej.setY(carYdouble - lewo * Math.sin(angle + Math.PI / 2));
            } while (PunktwObrazie(punktZlewej) &&((new Color(image.getRGB((int) punktZlewej.getX(), (int) punktZlewej.getY())).equals(Color.white))
                    || (new Color(image.getRGB((int) punktZlewej.getX(), (int) punktZlewej.getY())).equals(Color.MAGENTA))));
            lewo--;
            punktZlewej.setX(carXdouble + lewo * Math.cos(angle + Math.PI / 2));
            punktZlewej.setY(carYdouble - lewo * Math.sin(angle + Math.PI / 2));
            int prawo = 0;
            do {
                prawo++;
                punktZprawej.setX(carXdouble + prawo * Math.cos(angle - Math.PI / 2));
                punktZprawej.setY(carYdouble - prawo * Math.sin(angle - Math.PI / 2));
            } while ((PunktwObrazie(punktZprawej)) && ((new Color(image.getRGB((int) punktZprawej.getX(), (int) punktZprawej.getY())).equals(Color.white))
                    || (new Color(image.getRGB((int) punktZprawej.getX(), (int) punktZprawej.getY())).equals(Color.MAGENTA))));
            prawo--;
            punktZprawej.setX(carXdouble + prawo * Math.cos(angle - Math.PI / 2));
            punktZprawej.setY(carYdouble - prawo * Math.sin(angle - Math.PI / 2));
            // dla "lewo =0" czyli auto na lewej bandzie, czujnik przyjmie wartość -1,
            // jeżeli lewo=prawo (środek toru) czujnik = 0,
            // jeżeli na prawej bandzie, czujnik = 1
            czPolozenieNaTorze = (2.0 / (lewo + prawo)) * lewo - 1;
//      CZUJNIK ODLEGŁOŚCI I KIERUNKU NAJBLIŻESZEGO ZAKRĘTU
            punktPrzedLewym = new Punkt(0, 0);
            lewo = 0;
            do {
                lewo++;
                punktPrzedLewym.setX(lewePrzednie.getX() + lewo * Math.cos(angle));
                punktPrzedLewym.setY(lewePrzednie.getY() - lewo * Math.sin(angle));
            } while ((PunktwObrazie(punktPrzedLewym)) && ((new Color(image.getRGB((int) punktPrzedLewym.getX(), (int) punktPrzedLewym.getY())).equals(Color.white))
                    || (new Color(image.getRGB((int) punktPrzedLewym.getX(), (int) punktPrzedLewym.getY())).equals(Color.MAGENTA))));
            lewo--;
            punktPrzedLewym.setX(lewePrzednie.getX() + lewo * Math.cos(angle));
            punktPrzedLewym.setY(lewePrzednie.getY() - lewo * Math.sin(angle));
            punktPrzedPrawym = new Punkt(0, 0);
            prawo = 0;
            do {
                prawo++;
                punktPrzedPrawym.setX(prawePrzednie.getX() + prawo * Math.cos(angle));
                punktPrzedPrawym.setY(prawePrzednie.getY() - prawo * Math.sin(angle));
            } while ((PunktwObrazie(punktPrzedPrawym)) && ((new Color(image.getRGB((int) punktPrzedPrawym.getX(), (int) punktPrzedPrawym.getY())).equals(Color.white))
                    || (new Color(image.getRGB((int) punktPrzedPrawym.getX(), (int) punktPrzedPrawym.getY())).equals(Color.MAGENTA))));
            prawo--;
            punktPrzedPrawym.setX(prawePrzednie.getX() + prawo * Math.cos(angle));
            punktPrzedPrawym.setY(prawePrzednie.getY() - prawo * Math.sin(angle));

            double odlegloscPrzedLewym = Math.sqrt(Math.pow(lewePrzednie.getX() - punktPrzedLewym.getX(), 2) + Math.pow(lewePrzednie.getY() - punktPrzedLewym.getY(), 2));
            double odlegloscPrzedPrawym = Math.sqrt(Math.pow(prawePrzednie.getX() - punktPrzedPrawym.getX(), 2) + Math.pow(prawePrzednie.getY() - punktPrzedPrawym.getY(), 2));
            if (odlegloscPrzedPrawym < odlegloscPrzedLewym)
                czOdleglosciKierunekZakretu = -odlegloscPrzedPrawym;
            else
                czOdleglosciKierunekZakretu = odlegloscPrzedLewym;
            czOdleglosciKierunekZakretu/=10;
        }
        catch (Exception e){}
//      CZUJNIK COMBO
        if (czOdleglosciKierunekZakretu!=0)
            czCombo = Math.pow(czPredkosc,2)*systemSterowania.getAuto().getCzasWyhamowaniazPredkosciMaksymalnej()/(2*systemSterowania.getAuto().getPredkoscMaksymalna()) / czOdleglosciKierunekZakretu;
        else {
            czCombo = -100;
            czOdleglosciKierunekZakretu = -0.1;
        }

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
        if (pokazDzialanieCzujnikow) {
            g2.setColor(Color.MAGENTA);
            g2.drawLine((int) (punktZlewej.getX() - xStart), (int) (punktZlewej.getY() - yStart), (int) (punktZprawej.getX() - xStart), (int) (punktZprawej.getY() - yStart));
            g2.drawLine((int) (lewePrzednie.getX() - xStart), (int) (lewePrzednie.getY() - yStart), (int) (punktPrzedLewym.getX() - xStart), (int) (punktPrzedLewym.getY() - yStart));
            g2.drawLine((int) (prawePrzednie.getX() - xStart), (int) (prawePrzednie.getY() - yStart), (int) (punktPrzedPrawym.getX() - xStart), (int) (punktPrzedPrawym.getY() - yStart));
        }
        g2.dispose();
    }

    private void NarysujKomunikaty(Graphics2D g2) {
        g2.setColor(Color.BLUE);
        g2.drawString(komunikat, 10,10);
        g2.setFont(new Font("Helvetica", Font.BOLD, 20));
        if (ostatnieKolko!=0)
            g2.drawString("Ostatnie kółko: "+ SformatujCzas(ostatnieKolko),10,yHeight-60);
        if (!naPoluStart)
            g2.drawString("Czas: "+ SformatujCzas(System.currentTimeMillis() - czasStart),10,yHeight-30);
    }

    private String SformatujCzas(long l) {
        long milis = l % 1000;
        l /= 1000;
        long sec = l % 60;
        l /= 60;
        long min = l % 60;
        l /= 60;
        return l +":"+Uzupelnij(min,2)+":"+Uzupelnij(sec,2)+":"+Uzupelnij(milis,3);
    }

    private String Uzupelnij(long l, int i) {
        String result = Long.toString(l);
        while (result.length()<i)
            result = "0"+result;
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

    private boolean przednieKoloPozaTrasa() {
        if (!(PunktwObrazie(lewePrzednie)) || (!(new Color(image.getRGB((int)lewePrzednie.getX(), (int)lewePrzednie.getY())).equals(Color.white))
                && !(new Color(image.getRGB((int)lewePrzednie.getX(), (int)lewePrzednie.getY())).equals(Color.MAGENTA)))) return true;
        if (!(PunktwObrazie(prawePrzednie)) || (!(new Color(image.getRGB((int)prawePrzednie.getX(), (int)prawePrzednie.getY())).equals(Color.white))
                && !(new Color(image.getRGB((int)prawePrzednie.getX(), (int)prawePrzednie.getY())).equals(Color.MAGENTA)))) return true;
        return false;
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
    private boolean PunktwObrazie(Punkt p){
        if ((p.getX()<0)||(p.getX()>=image.getWidth())) return false;
        if ((p.getY()<0)||(p.getY()>=image.getHeight())) return false;
        return true;
    }

    private void ObliczWspolczynnikiSterowania() {
        for (int ktorySilnik=0;ktorySilnik<2;ktorySilnik++){
            int czujnikNr = -1;
    //      analizujemy najpierw funkcję pierwszego czujnika
            for (int i = 0; i < Czujniki.czujniki.length; i++)
                if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getCzujnik().equals(Czujniki.czujniki[i]))
                    czujnikNr = i;
            double czujnikWartosc;
            switch (czujnikNr) {
                case 0:
                    czujnikWartosc = czPredkosc;
                    break;
                case 1:
                    czujnikWartosc = czPolozenieNaTorze;
                    break;
                case 2:
                    czujnikWartosc = czOdleglosciKierunekZakretu;
                    break;
                case 3:
                    czujnikWartosc = czCombo;
                    break;
                default:
                    czujnikWartosc = 0;
            }

    //      list "punkty" przechowuje pary - (numer funkcji o niezerowej wartości dla czujnika, wartość funkcji w tym punkcie)
            List<Punkt> punkty0 = new ArrayList<>();
            for (int i = 0; i < systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().size(); i++) {
                double wartosc = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(0).getListaFunkcji().get(i).WartoscFunkcji(czujnikWartosc);
                if (wartosc != 0)
                    punkty0.add(new Punkt(i, wartosc));
            }

    //      analizujemy teraz funkcję drugirgo czujnika
            for (int i = 0; i < Czujniki.czujniki.length; i++)
                if (systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getCzujnik().equals(Czujniki.czujniki[i]))
                    czujnikNr = i;
            switch (czujnikNr) {
                case 0:
                    czujnikWartosc = czPredkosc;
                    break;
                case 1:
                    czujnikWartosc = czPolozenieNaTorze;
                    break;
                case 2:
                    czujnikWartosc = czOdleglosciKierunekZakretu;
                    break;
                case 3:
                    czujnikWartosc = czCombo;
                    break;
                default:
                    czujnikWartosc = 0;
            }

    //      list "punkty" przechowuje pary - (numer funkcji o niezerowej wartości dla czujnika, wartość funkcji w tym punkcie)
            List<Punkt> punkty1 = new ArrayList<>();
            for (int i = 0; i < systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().size(); i++) {
                double wartosc = systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(1).getListaFunkcji().get(i).WartoscFunkcji(czujnikWartosc);
                if (wartosc != 0)
                    punkty1.add(new Punkt(i, wartosc));
            }
    //      teraz krzyżujemy wskazania obu czujników i zbieramy wskazane konkluzje, najpierw przyporządkuwując im minimum z warotści funkcji czujników,
    //      potem maximum spośród różnych wskazan na tą samą konkluzję.
    //      Lista punkty2 zawiera pary liczb, z których pierwsza wskazuje numer funkcji wyjściowej, a druga to warotść do jakie ma zostać przycięta.
            List<Punkt> punkty2 = new ArrayList<>();
            boolean powtorzylSie;
            for (int i = 0; i < punkty0.size(); i++)
                for (int j = 0; j < punkty1.size(); j++) {
                    powtorzylSie = false;
                    Punkt p = new Punkt(systemSterowania.getSilnik(ktorySilnik).getRegulywnioskowania()[(int) punkty0.get(i).getX()][(int) punkty1.get(j).getX()],
                            Math.min(punkty0.get(i).getY(), punkty1.get(j).getY()));
                    for (int k = 0; k < punkty2.size(); k++)
                        if (punkty2.get(k).getX() == p.getX()) {
                            punkty2.set(k, new Punkt(p.getX(), Math.max(p.getY(), punkty2.get(k).getY())));
                            powtorzylSie = true;
                        }
                    if (!powtorzylSie)
                        punkty2.add(p);
                }
    //      funkcje wskazane w liscie punkty2 przycinamy do odpowiednich wartości
            List<FunkcjaLiniowa> funkcjeWyjsciowe = new ArrayList<>();
            for (int i = 0; i < punkty2.size(); i++) {
                FunkcjaLiniowa fl = PrzytnijDoWartosci(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(2).getListaFunkcji().get((int) punkty2.get(i).getX()), punkty2.get(i).getY());
                funkcjeWyjsciowe.add(fl);
            }
    //      i sumujemy
            FunkcjaLiniowa wynik = new FunkcjaLiniowa(systemSterowania.getSilnik(ktorySilnik).getFunkcjaCzujnika(2).getDziedzinaCzujnika());
            for (int i = 0; i < funkcjeWyjsciowe.size(); i++)
                wynik = FunkcjaLiniowa.DodajFunkcje(wynik, funkcjeWyjsciowe.get(i));

    //      no to liczymy wyjście
            double licznik = 0;
            double mianownik = 0;
            int iteracje = 1000;
            for (int i = 0; i < iteracje; i++) {
                try {
                    licznik += ((wynik.getPunkty().get(wynik.getPunkty().size() - 1).getX() - wynik.getPunkty().get(0).getX()) * i / (iteracje - 1) + wynik.getPunkty().get(0).getX()) * wynik.WartoscFunkcji((wynik.getPunkty().get(wynik.getPunkty().size() - 1).getX() - wynik.getPunkty().get(0).getX()) * i / (iteracje - 1) + wynik.getPunkty().get(0).getX());
                    mianownik += wynik.WartoscFunkcji((wynik.getPunkty().get(wynik.getPunkty().size() - 1).getX() - wynik.getPunkty().get(0).getX()) * i / (iteracje - 1) + wynik.getPunkty().get(0).getX());
                } catch (Exception e) {
                    System.out.println(".");
                }
            }
            if (ktorySilnik==0)
                sterowaniePredkoscia = licznik/mianownik;
            else
                sterowanieSkretem = licznik/mianownik;
        }
    }

    public FunkcjaLiniowa PrzytnijDoWartosci(FunkcjaLiniowa funkcja, double y) {
        FunkcjaLiniowa result = new FunkcjaLiniowa(funkcja.getDziedzinaCzujnika());
        for (int i=0; i<funkcja.getPunkty().size();i++)
            if (funkcja.getPunkty().get(i).getY() <= y)
                result.Dodaj(funkcja.getPunkty().get(i));
            else
                result.Dodaj(new Punkt(funkcja.getPunkty().get(i).getX(), y));
        for (int i=1; i<funkcja.getPunkty().size();i++) {
            if (((funkcja.getPunkty().get(i - 1).getY() < y) && (funkcja.getPunkty().get(i).getY() > y)) ||
                    ((funkcja.getPunkty().get(i - 1).getY() > y) && (funkcja.getPunkty().get(i).getY()) < y)) {
                double x0, x1, y0, y1;
                x0 = funkcja.getPunkty().get(i - 1).getX();
                x1 = funkcja.getPunkty().get(i).getX();
                y0 = funkcja.getPunkty().get(i - 1).getY();
                y1 = funkcja.getPunkty().get(i).getY();
                result.Dodaj(new Punkt((x1 - x0) * (y - y0) / (y1 - y0) + x0, y));
            }
        }
        return result;
    }

}
