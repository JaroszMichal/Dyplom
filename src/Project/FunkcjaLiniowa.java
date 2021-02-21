package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FunkcjaLiniowa {

//  Nazwa funkcji
    private String nazwa;
    private DziedzinaCzujnika dziedzinaCzujnika;
    //  lista punktów funkcji
    private List<Punkt> punkty;

    public DziedzinaCzujnika getDziedzinaCzujnika() {
        return dziedzinaCzujnika;
    }
    public void setDziedzinaCzujnika(DziedzinaCzujnika dziedzinaCzujnika) {
        this.dziedzinaCzujnika = dziedzinaCzujnika;
    }
    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    public List<Punkt> getPunkty() {
        return punkty;
    }

    public FunkcjaLiniowa(){
        nazwa = "no name";
        dziedzinaCzujnika = new DziedzinaCzujnika(true, -1, true, true, 1, true);
        punkty = new ArrayList<Punkt>();
    }
    public FunkcjaLiniowa(DziedzinaCzujnika dziedzinaCzujnika){
        nazwa = "no name";
        this.dziedzinaCzujnika = dziedzinaCzujnika;
        punkty = new ArrayList<Punkt>();
    }

    // zwraca 0 jeżeli udało sie dodać element.
    // zwraca 1 jeżeli wystąpiła próba dodania punktu już istniejącego.
    // zwraca 2 jeżeli wystąpiła próba dodania punktu spoza dziedziny.
    public int Dodaj(Punkt p){
        if (CzyJuzJestTakiX(p.getX()))
            return 1;
        if (JestSpozaDziedziny(p.getX()))
            return 2;
        punkty.add(p);
        Collections.sort(punkty, new Comparator<Punkt>() {
            @Override
            public int compare(Punkt o1, Punkt o2) {
                return Double.valueOf(o1.getX()).compareTo(o2.getX());
            }
        });
        return 0;
    }

    public int Usun(Punkt p){
        if (!CzyJuzJestTakiX(p.getX()))
            return 1;
        punkty.remove(Pozycja(p));
        return 0;
    }

    public int Zmien(Punkt p1, Punkt p2){
        if (!CzyJuzJestTakiX(p1.getX()))
            return 1;
        punkty.set(Pozycja(p1), p2);
        return 0;
    }

    private int Pozycja(Punkt p) {
        for (int i=0; i<punkty.size();i++)
            if ((punkty.get(i).getX() == p.getX())&&(punkty.get(i).getY() == p.getY())) return i;
        return -1;
    }

    private boolean JestSpozaDziedziny(double x) {
        if ((!dziedzinaCzujnika.isDziedzinaCzyOdNiesk()) && ((x<dziedzinaCzujnika.getDziedzinaOdWart()) || ((!dziedzinaCzujnika.isDziedzinaOdCzyNalezy())&&(x==dziedzinaCzujnika.getDziedzinaOdWart())))) return true;
        if ((!dziedzinaCzujnika.isDziedzinaCzyDoNiesk()) && ((x>dziedzinaCzujnika.getDziedzinaDoWart()) || ((!dziedzinaCzujnika.isDziedzinaDoCzyNalezy())&&(x==dziedzinaCzujnika.getDziedzinaDoWart())))) return true;
        return false;
    }

    private boolean CzyJuzJestTakiX(double x) {
        for (int i=0; i<punkty.size(); i++)
            if (punkty.get(i).getX() == x) return true;
        return false;
    }

    public double WartoscFunkcji(double czujnikWartosc) {
        if (punkty.size()==0)
            return 0;
        if (czujnikWartosc<=punkty.get(0).getX())
            return punkty.get(0).getY();
        if (czujnikWartosc>=punkty.get(punkty.size()-1).getX())
            return punkty.get(punkty.size()-1).getY();
        for (int i=0; i<punkty.size()-1;i++)
            if ((czujnikWartosc>=punkty.get(i).getX()) && (czujnikWartosc<=punkty.get(i+1).getX())) {
                double x0, x1, y0,y1;
                x0 = punkty.get(i).getX();
                x1 = punkty.get(i+1).getX();
                y0 = punkty.get(i).getY();
                y1 = punkty.get(i+1).getY();
                return (y1-y0)*czujnikWartosc/(x1-x0)+y0-x0*(y1-y0)/(x1-x0);
            }
        // nie ma prawa tu dojść metoda.
        return 0;
    }

    public static FunkcjaLiniowa DodajFunkcje(FunkcjaLiniowa f0, FunkcjaLiniowa f1) {
        FunkcjaLiniowa result = new FunkcjaLiniowa(f1.getDziedzinaCzujnika());
        for (int i=0; i<f0.getPunkty().size(); i++)
            if (f0.getPunkty().get(i).getY()>=f1.WartoscFunkcji(f0.getPunkty().get(i).getX()))
                result.Dodaj(f0.getPunkty().get(i));
        for (int i=0; i<f1.getPunkty().size(); i++)
            if (f1.getPunkty().get(i).getY()>=f0.WartoscFunkcji(f1.getPunkty().get(i).getX()))
                result.Dodaj(f1.getPunkty().get(i));
        for (int i=1; i<f1.getPunkty().size(); i++)
            if (((f1.WartoscFunkcji(f1.getPunkty().get(i-1).getX())>f0.WartoscFunkcji(f1.getPunkty().get(i-1).getX())) && (f1.WartoscFunkcji(f1.getPunkty().get(i).getX())<f0.WartoscFunkcji(f1.getPunkty().get(i).getX()))) ||
                ((f1.WartoscFunkcji(f1.getPunkty().get(i-1).getX())<f0.WartoscFunkcji(f1.getPunkty().get(i-1).getX())) && (f1.WartoscFunkcji(f1.getPunkty().get(i).getX())>f0.WartoscFunkcji(f1.getPunkty().get(i).getX())))){
//              f0(x) = a0x+b0
//              f1(x) = a1x+b1
                double a0, b0, a1, b1;
                double x0, y0, x1, y1;
                x0 = f1.getPunkty().get(i-1).getX();
                x1 = f1.getPunkty().get(i).getX();
                y0 = f1.getPunkty().get(i-1).getY();
                y1 = f1.getPunkty().get(i).getY();
                a1 = (y1-y0)/(x1-x0);
                b1 = y0+x0*(y1-y0)/(x1-x0);


                for (int j=1; j<f0.getPunkty().size(); j++)
                    if (((f1.WartoscFunkcji(f0.getPunkty().get(j-1).getX())>f0.WartoscFunkcji(f0.getPunkty().get(j-1).getX())) && (f1.WartoscFunkcji(f0.getPunkty().get(j).getX())<f0.WartoscFunkcji(f0.getPunkty().get(j).getX()))) ||
                            ((f1.WartoscFunkcji(f0.getPunkty().get(j-1).getX())<f0.WartoscFunkcji(f0.getPunkty().get(j-1).getX())) && (f1.WartoscFunkcji(f0.getPunkty().get(j).getX())>f0.WartoscFunkcji(f0.getPunkty().get(j).getX())))) {
                        x0 = f0.getPunkty().get(j - 1).getX();
                        x1 = f0.getPunkty().get(j).getX();
                        y0 = f0.getPunkty().get(j - 1).getY();
                        y1 = f0.getPunkty().get(j).getY();
                        a0 = (y1-y0)/(x1-x0);
                        b0 = y0+x0*(y1-y0)/(x1-x0);
                        if (((b1-b0)/(a0-a1)>f1.getPunkty().get(i-1).getX()) && ((b1-b0)/(a0-a1)<f1.getPunkty().get(i).getX()))
                            result.Dodaj(new Punkt((b1-b0)/(a0-a1), a1*(b1-b0)/(a0-a1)+b1));
                    }

            }
        return result;
    }

}
