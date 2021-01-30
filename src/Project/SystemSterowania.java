package Project;

public class SystemSterowania {
    private Trasa trasa;
    private Auto auto;
    private FunkcjaSterujaca[] funkcjaSterujaca;

    public SystemSterowania(){
        trasa = new Trasa();
        auto = new Auto();
        funkcjaSterujaca = new FunkcjaSterujaca[2];
    }

}
