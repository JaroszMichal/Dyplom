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
import java.util.Scanner;

public class FunkcjaCzujnika {
    private String nazwa;
    private String czujnik;
    private String opisCzujnika;
    private DziedzinaCzujnika dziedzinaCzujnika;

    public void setListaFunkcji(List<FunkcjaLiniowa> listaFunkcji) {
        this.listaFunkcji = listaFunkcji;
    }

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
    public boolean WczytajzPliku(File file) {
        try {
            Scanner scanner =new Scanner(file);
            String linia;
            while (scanner.hasNext()){
                linia = scanner.nextLine();
                String[] podzielonaLinia = linia.split(":");
                Punkt punkt;
                FunkcjaLiniowa funkcjaLiniowa;
                if (podzielonaLinia.length>0)
                    switch (podzielonaLinia[0]){
                        case "nazwa":
                            this.nazwa = podzielonaLinia[1];
                            break;
                        case "czujnik":
                            this.czujnik = podzielonaLinia[1];
                            this.opisCzujnika = Czujniki.getOpisCzujnika(this.czujnik);
                            this.dziedzinaCzujnika = Czujniki.getDziedzinaCzujnika(this.czujnik);
                            break;
                        case "funkcja":
                            funkcjaLiniowa = new FunkcjaLiniowa();
                            funkcjaLiniowa.setNazwa(podzielonaLinia[1]);
                            funkcjaLiniowa.setDziedzinaCzujnika(this.dziedzinaCzujnika);
                            for (int i=2;i<podzielonaLinia.length;i+=2) {
                                punkt = new Punkt(Double.parseDouble(podzielonaLinia[i]), Double.parseDouble(podzielonaLinia[i + 1]));
                                funkcjaLiniowa.getPunkty().add(punkt);
                            }
                            this.listaFunkcji.add(funkcjaLiniowa);
                            break;
                    }
            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }
}
