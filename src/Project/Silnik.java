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
    public Silnik()
    {
        this.funkcjaCzujnika = new FunkcjaCzujnika[3];
        this.funkcjaCzujnika[0] = new FunkcjaCzujnika();
        this.funkcjaCzujnika[1] = new FunkcjaCzujnika();
        this.funkcjaCzujnika[2] = new FunkcjaCzujnika();
    }
}
