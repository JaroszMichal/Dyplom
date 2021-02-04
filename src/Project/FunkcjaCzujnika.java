package Project;

import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
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
    public String ZapiszFunkcjeCzujnikaDoPliku(String fileName){
        FileChooser fileChooser = new FileChooser();
        fileChooser.setInitialDirectory(new File("C:\\java"));
        fileChooser.setTitle("Dupa");
        fileChooser.setInitialFileName("test");
        fileChooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("dupa blada", "*.dpa"));
        File file = fileChooser.showSaveDialog();

        return "Zapisano do pliku: "+fileName;
    }
}
