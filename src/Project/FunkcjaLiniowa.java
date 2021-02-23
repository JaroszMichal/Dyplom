package Project;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.*;
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

    public double WartoscFunkcji(double x) {
        if (punkty.size()==0)
            return 0;
        if (x<=punkty.get(0).getX())
            return punkty.get(0).getY();
        if (x>=punkty.get(punkty.size()-1).getX())
            return punkty.get(punkty.size()-1).getY();
        for (int i=1; i<punkty.size()-1;i++)
            if ((x>=punkty.get(i-1).getX()) && (x<=punkty.get(i).getX())) {
                double x0, x1, y0,y1;
                x0 = punkty.get(i-1).getX();
                x1 = punkty.get(i).getX();
                y0 = punkty.get(i-1).getY();
                y1 = punkty.get(i).getY();
                return (y1-y0)*x/(x1-x0)+y0-x0*(y1-y0)/(x1-x0);
            }
        // nie ma prawa tu dojść metoda.
        return 0;
    }

}
