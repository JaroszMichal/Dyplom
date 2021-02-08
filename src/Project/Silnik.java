package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Scanner;

public class Silnik {
    private String nazwa;
    //  3 funkcje - 0 i 1 to funkcje czujnikow, 2 funkcja wyjściowa (skręt, prędkość)
    private FunkcjaCzujnika[] funkcjaCzujnika;
    private int[][] regulywnioskowania;

    public FunkcjaCzujnika getFunkcjaCzujnika(int i) {
        return funkcjaCzujnika[i];
    }
    public void setFunkcjaCzujnika(FunkcjaCzujnika[] funkcjaCzujnika) {
        this.funkcjaCzujnika = funkcjaCzujnika;
    }
    public int[][] getRegulywnioskowania() {
        return regulywnioskowania;
    }
    public void setRegulywnioskowania(int[][] regulywnioskowania) {
        this.regulywnioskowania = regulywnioskowania;
    }
    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Silnik(int ktorySilnik)
    {
        switch (ktorySilnik){
            case 0:
                nazwa = "Prędkość";
                break;
            case 1:
                nazwa = "Skręt";
                break;
        }
        this.funkcjaCzujnika = new FunkcjaCzujnika[3];
        this.funkcjaCzujnika[0] = new FunkcjaCzujnika();
        this.funkcjaCzujnika[1] = new FunkcjaCzujnika();
        this.funkcjaCzujnika[2] = new FunkcjaCzujnika();
        funkcjaCzujnika[2].setDziedzinaCzujnika(new DziedzinaCzujnika(false, -1, true, false, 1, true));
        if (ktorySilnik==0){
            funkcjaCzujnika[2].setNazwa("Przyśpieszenie");
            funkcjaCzujnika[2].setCzujnik("Przyśpieszenie");
            funkcjaCzujnika[2].setOpisCzujnika("wartośc ujemne oznaczają hamowanie, dodatnie przyspieszanie\n" +
                    "-1 - hamowanie maksymalne,\n" +
                    "0 - utrzymanie prędkości,\n" +
                    "+1 to pedał gazu wciśnięty \"do dechy\".");
        }
        else{
            funkcjaCzujnika[2].setNazwa("Skręt");
            funkcjaCzujnika[2].setCzujnik("Skręt");
            funkcjaCzujnika[2].setOpisCzujnika("wartośc ujemne oznaczają skręt w lewo, dodatnie - skręt w prawo\n" +
                    "-1 - maksymalny skręt w lewo,\n" +
                    "0 - utrzymanie kierunku,\n" +
                    "+1 - maksymalny skręt w prawo.");
        }
    }

    public boolean ZapiszDoPliku(File file){
        try {
            PrintWriter writer = new PrintWriter(file);
            for (int nr=0;nr<3;nr++) {
                writer.println("f"+nr+"nazwa:" + funkcjaCzujnika[nr].getNazwa());
                writer.println("f"+nr+"czujnik:" + funkcjaCzujnika[nr].getCzujnik());
                for (int i = 0; i < funkcjaCzujnika[nr].getListaFunkcji().size(); i++) {
                    if (funkcjaCzujnika[nr].getListaFunkcji().get(i).getPunkty().size() > 0) {
                        writer.print("f"+nr+"funkcja:" + funkcjaCzujnika[nr].getListaFunkcji().get(i).getNazwa());
                        for (int j = 0; j < funkcjaCzujnika[nr].getListaFunkcji().get(i).getPunkty().size(); j++)
                            writer.print(":" + funkcjaCzujnika[nr].getListaFunkcji().get(i).getPunkty().get(j).getX() + ":" + funkcjaCzujnika[nr].getListaFunkcji().get(i).getPunkty().get(j).getY());
                        writer.println("");
                    }
                }
            }
            for (int i = 0 ; i < regulywnioskowania.length ; i++) {
                writer.print("reguly");
                for (int j = 0 ; j < regulywnioskowania[i].length ; j++)
                    writer.print(":"+regulywnioskowania[i][j]);
                writer.println("");
            }
            writer.close();
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }
    }

    public int WczytajzPliku(File file) {
        int result = 0;
        int ktoraLiniaRegul = 0;
        for (int i=0;i<3;i++)
            funkcjaCzujnika[i].getListaFunkcji().clear();
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
                        case "f0nazwa":
                            this.getFunkcjaCzujnika(0).setNazwa(podzielonaLinia[1]);
                            break;
                        case "f1nazwa":
                            this.getFunkcjaCzujnika(1).setNazwa(podzielonaLinia[1]);
                            break;
                        case "f2nazwa":
                            this.getFunkcjaCzujnika(2).setNazwa(podzielonaLinia[1]);
                            break;
                        case "f0czujnik":
                            this.getFunkcjaCzujnika(0).setCzujnik(podzielonaLinia[1]);
                            this.getFunkcjaCzujnika(0).setOpisCzujnika(Czujniki.getOpisCzujnika(this.getFunkcjaCzujnika(0).getCzujnik()));
                            this.getFunkcjaCzujnika(0).setDziedzinaCzujnika(Czujniki.getDziedzinaCzujnika(this.getFunkcjaCzujnika(0).getCzujnik()));
                            break;
                        case "f1czujnik":
                            this.getFunkcjaCzujnika(1).setCzujnik(podzielonaLinia[1]);
                            this.getFunkcjaCzujnika(1).setOpisCzujnika(Czujniki.getOpisCzujnika(this.getFunkcjaCzujnika(1).getCzujnik()));
                            this.getFunkcjaCzujnika(1).setDziedzinaCzujnika(Czujniki.getDziedzinaCzujnika(this.getFunkcjaCzujnika(1).getCzujnik()));
                            break;
                        case "f2czujnik":
                            this.getFunkcjaCzujnika(2).setCzujnik(podzielonaLinia[1]);
                            this.getFunkcjaCzujnika(2).setOpisCzujnika(Czujniki.getOpisCzujnika(this.getFunkcjaCzujnika(2).getCzujnik()));
                            this.getFunkcjaCzujnika(2).setDziedzinaCzujnika(Czujniki.getDziedzinaCzujnika(this.getFunkcjaCzujnika(2).getCzujnik()));
                            break;
                        case "f0funkcja":
                            funkcjaLiniowa = new FunkcjaLiniowa();
                            funkcjaLiniowa.setNazwa(podzielonaLinia[1]);
                            funkcjaLiniowa.setDziedzinaCzujnika(this.getFunkcjaCzujnika(0).getDziedzinaCzujnika());
                            for (int i=2;i<podzielonaLinia.length;i+=2) {
                                punkt = new Punkt(Double.parseDouble(podzielonaLinia[i]), Double.parseDouble(podzielonaLinia[i + 1]));
                                funkcjaLiniowa.getPunkty().add(punkt);
                            }
                            this.getFunkcjaCzujnika(0).getListaFunkcji().add(funkcjaLiniowa);
                            result++;
                            break;
                        case "f1funkcja":
                            funkcjaLiniowa = new FunkcjaLiniowa();
                            funkcjaLiniowa.setNazwa(podzielonaLinia[1]);
                            funkcjaLiniowa.setDziedzinaCzujnika(this.getFunkcjaCzujnika(1).getDziedzinaCzujnika());
                            for (int i=2;i<podzielonaLinia.length;i+=2) {
                                punkt = new Punkt(Double.parseDouble(podzielonaLinia[i]), Double.parseDouble(podzielonaLinia[i + 1]));
                                funkcjaLiniowa.getPunkty().add(punkt);
                            }
                            this.getFunkcjaCzujnika(1).getListaFunkcji().add(funkcjaLiniowa);
                            result++;
                            break;
                        case "f2funkcja":
                            funkcjaLiniowa = new FunkcjaLiniowa();
                            funkcjaLiniowa.setNazwa(podzielonaLinia[1]);
                            funkcjaLiniowa.setDziedzinaCzujnika(this.getFunkcjaCzujnika(2).getDziedzinaCzujnika());
                            for (int i=2;i<podzielonaLinia.length;i+=2) {
                                punkt = new Punkt(Double.parseDouble(podzielonaLinia[i]), Double.parseDouble(podzielonaLinia[i + 1]));
                                funkcjaLiniowa.getPunkty().add(punkt);
                            }
                            this.getFunkcjaCzujnika(2).getListaFunkcji().add(funkcjaLiniowa);
                            result++;
                            break;
                        case "reguly":
                            if ((regulywnioskowania == null) ||
                                (regulywnioskowania.length == 0) ||
                                (regulywnioskowania.length != funkcjaCzujnika[0].getListaFunkcji().size()) ||
                                (regulywnioskowania[0].length != funkcjaCzujnika[1].getListaFunkcji().size()))
                            {
                                regulywnioskowania = new int[funkcjaCzujnika[0].getListaFunkcji().size()][funkcjaCzujnika[1].getListaFunkcji().size()];
                            }
                            for (int i=1;i<podzielonaLinia.length;i++)
                                regulywnioskowania[ktoraLiniaRegul][i-1] = Integer.parseInt(podzielonaLinia[i]);
                            ktoraLiniaRegul++;
                            break;
                    }
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            result = -1;
        }
        return result;
    }
}
