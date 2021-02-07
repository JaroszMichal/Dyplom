package Project;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;

public class Silnik {
    private FunkcjaCzujnika[] funkcjaCzujnika;
//  3 punkcje - 0 i 1 to funkcje czujnikow, 2 funkcja wyjściowa (skręt, prędkość)
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

    public Silnik(int ktorySilnik)
    {
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
}
