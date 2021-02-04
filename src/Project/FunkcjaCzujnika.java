package Project;

import Design.FunkcjeCzujnika;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import javax.swing.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

public class FunkcjaCzujnika {
    private String nazwa;
    private String czujnik;
    private String opisCzujnika;
    private DziedzinaCzujnika dziedzinaCzujnika;
    private List<FunkcjaLiniowa> listaFunkcji;

    public FunkcjaCzujnika(){
        nazwa = "noname";
        czujnik = Czujniki.czujniki[0];
        opisCzujnika = Czujniki.czujnikiOpis[0];
        dziedzinaCzujnika = Czujniki.dziedzinaCzujnikow[0];
        listaFunkcji = new ArrayList<>();
    }

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    public String getCzujnik() {
        return czujnik;
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
    public String getOpisCzujnika() {
        return opisCzujnika;
    }
    public DziedzinaCzujnika getDziedzinaCzujnika() {
        return dziedzinaCzujnika;
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

    public boolean UsunFunkcjeZListy(FunkcjaLiniowa funkcjaLiniowa){
        try {
            listaFunkcji.remove(funkcjaLiniowa);
            return true;
        }
        catch (Exception e){
            return false;
        }
    }

    public boolean ZapiszDoPliku(File file){
        try {
            PrintWriter writer = new PrintWriter(file);
            writer.println("nazwa:"+nazwa);
            writer.println("czujnik:"+czujnik);
            for (int i=0;i<listaFunkcji.size();i++) {
                if (listaFunkcji.get(i).getPunkty().size()>0) {
                    writer.print("funkcja:" + listaFunkcji.get(i).getNazwa());
                    for (int j = 0; j < listaFunkcji.get(i).getPunkty().size(); j++)
                        writer.print(":" + listaFunkcji.get(i).getPunkty().get(j).getX() + ":" + listaFunkcji.get(i).getPunkty().get(j).getY());
                    writer.println("");
                }
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
