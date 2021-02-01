package Project;

import java.util.ArrayList;
import java.util.List;

public class FunkcjaCzujnika {
    private String nazwa = "noname";
    private String czujnik = Czujniki.czujniki[0];
    private String opisCzujnika = Czujniki.czujnikiOpis[0];
    private DziedzinaCzujnika dziedzinaCzujnika = Czujniki.dziedzinaCzujnikow[0];
    private List<FunkcjaLiniowa> listaFunkcji = new ArrayList<FunkcjaLiniowa>();

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    public String getCzujnik() {
        return czujnik;
    }
    public void setCzujnik(String czujnik) {
        this.czujnik = czujnik;
    }
    public List<FunkcjaLiniowa> getListaFunkcji() {
        return listaFunkcji;
    }
    public FunkcjaLiniowa getFunkcja(String nazwa) {
        for (int i=0; i<listaFunkcji.size();i++)
            if (listaFunkcji.get(i).getNazwa().equals(nazwa))
                return listaFunkcji.get(i);
        return null;
    }
    public void setListaFunkcji(List<FunkcjaLiniowa> listaFunkcji) {
        this.listaFunkcji = listaFunkcji;
    }
    public String getOpisCzujnika() {
        return opisCzujnika;
    }
    public void setOpisCzujnika(String opisCzujnika) {
        this.opisCzujnika = opisCzujnika;
    }
    public DziedzinaCzujnika getDziedzinaCzujnika() {
        return dziedzinaCzujnika;
    }
    public void setDziedzinaCzujnika(DziedzinaCzujnika dziedzinaCzujnika) {
        this.dziedzinaCzujnika = dziedzinaCzujnika;
    }

    public void ZmienCzujnik(String s){
        int i = PozycjaCzujnika(s);
        czujnik = Czujniki.czujniki[i];
        opisCzujnika = Czujniki.czujnikiOpis[i];
        dziedzinaCzujnika = Czujniki.dziedzinaCzujnikow[i];
    }

    private int PozycjaCzujnika(String s) {
        for (int i=0; i<Czujniki.czujniki.length;i++)
            if (Czujniki.czujniki[i].equals(s)) return i;
        return -1;
    }
    public void DodajFunkcjeDoListy(FunkcjaLiniowa funkcjaLiniowa){
        listaFunkcji.add(funkcjaLiniowa);
    }

    public boolean UsunFunkcjeZListy(FunkcjaLiniowa funkcjaLiniowa){
        try {
            listaFunkcji.remove(funkcjaLiniowa);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public void WyczyscFunkcjeZListy() {
        listaFunkcji.clear();
    }
}
