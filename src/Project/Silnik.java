package Project;

public class Silnik {
    private FunkcjaCzujnika[] funkcjaCzujnika;
//  FunkcjaWartosciSterowanej
//  RegulyWnioskowania
    public FunkcjaCzujnika getFunkcjaCzujnika(int i) {
        return funkcjaCzujnika[i];
    }
    public void setFunkcjaCzujnika(FunkcjaCzujnika[] funkcjaCzujnika) {
        this.funkcjaCzujnika = funkcjaCzujnika;
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
}
