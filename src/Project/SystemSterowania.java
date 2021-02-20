package Project;

public class SystemSterowania {

    private Trasa trasa;
    private Auto auto;
    private Silnik[] silnik;

    public Trasa getTrasa() {
        return trasa;
    }
    public Auto getAuto() {
        return auto;
    }
    public Silnik getSilnik(int i) {
        return silnik[i];
    }

    public SystemSterowania(){
        trasa = new Trasa();
        auto = new Auto();
        silnik = new Silnik[2];
        silnik[0] = new Silnik(0);
        silnik[1] = new Silnik(1);
    }

}
