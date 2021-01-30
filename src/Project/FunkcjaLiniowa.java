package Project;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class FunkcjaLiniowa {

//  Nazwa funkcji
    private String nazwa;
//  dziedzina
//  lewostronna
    private boolean dziedzinaCzyOdNiesk;
    private double dziedzinaOdWart;
    private boolean dziedzinaOdCzyNalezy;
//  prawostronna
    private boolean dziedzinaCzyDoNiesk;
    private double dziedzinaDoWart;
    private boolean dziedzinaDoCzyNalezy;

//  lista punktów funkcji
    private List<Punkt> punkty;

    public String getNazwa() {
        return nazwa;
    }

    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public boolean isDziedzinaCzyOdNiesk() {
        return dziedzinaCzyOdNiesk;
    }

    public void setDziedzinaCzyOdNiesk(boolean dziedzinaCzyOdNiesk) {
        this.dziedzinaCzyOdNiesk = dziedzinaCzyOdNiesk;
    }

    public double getDziedzinaOdWart() {
        return dziedzinaOdWart;
    }

    public void setDziedzinaOdWart(double dziedzinaOdWart) {
        this.dziedzinaOdWart = dziedzinaOdWart;
    }

    public boolean isDziedzinaOdCzyNalezy() {
        return dziedzinaOdCzyNalezy;
    }

    public void setDziedzinaOdCzyNalezy(boolean dziedzinaOdCzyNalezy) {
        this.dziedzinaOdCzyNalezy = dziedzinaOdCzyNalezy;
    }

    public boolean isDziedzinaCzyDoNiesk() {
        return dziedzinaCzyDoNiesk;
    }

    public void setDziedzinaCzyDoNiesk(boolean dziedzinaCzyDoNiesk) {
        this.dziedzinaCzyDoNiesk = dziedzinaCzyDoNiesk;
    }

    public double getDziedzinaDoWart() {
        return dziedzinaDoWart;
    }

    public void setDziedzinaDoWart(double dziedzinaDoWart) {
        this.dziedzinaDoWart = dziedzinaDoWart;
    }

    public boolean isDziedzinaDoCzyNalezy() {
        return dziedzinaDoCzyNalezy;
    }

    public void setDziedzinaDoCzyNalezy(boolean dziedzinaDoCzyNalezy) {
        this.dziedzinaDoCzyNalezy = dziedzinaDoCzyNalezy;
    }

    public List<Punkt> getPunkty() {
        return punkty;
    }

    public void setPunkty(List<Punkt> punkty) {
        this.punkty = punkty;
    }

    public FunkcjaLiniowa(){
        nazwa = "no name";
        dziedzinaCzyOdNiesk = true;
        dziedzinaOdWart = -1;
        dziedzinaOdCzyNalezy = true;
        dziedzinaCzyDoNiesk = true;
        dziedzinaDoWart = 1;
        dziedzinaDoCzyNalezy = true;
        punkty = new ArrayList<Punkt>();
    }

    // zwraca 0 jeżeli udało sie dodać element.
    // zwraca 1 jeżeli wystąpiła próba dodania punktu już istniejącego.
    // zwraca 2 jeżeli wystąpiła próba dodania punktu spoza dziedziny.
    public int Dodaj(Punkt p){
        if (CzyJuzJestTakiX(p.getX()))
            return 1;
        if (CzyJestSpozaDziedziny(p.getX()))
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

    private boolean CzyJestSpozaDziedziny(double x) {
        if ((!dziedzinaCzyOdNiesk) && ((x<dziedzinaOdWart) || ((!dziedzinaOdCzyNalezy)&&(x==dziedzinaOdWart)))) return true;
        if ((!dziedzinaCzyDoNiesk) && ((x>dziedzinaDoWart) || ((!dziedzinaDoCzyNalezy)&&(x==dziedzinaDoWart)))) return true;
        return false;
    }

    private boolean CzyJuzJestTakiX(double x) {
        for (int i=0; i<punkty.size(); i++)
            if (punkty.get(i).getX() == x) return true;
        return false;
    }

}
