package Project;

public class DziedzinaCzujnika {
//  lewostronna
    private boolean dziedzinaCzyOdNiesk;
    private double dziedzinaOdWart;
    private boolean dziedzinaOdCzyNalezy;
//  prawostronna
    private boolean dziedzinaCzyDoNiesk;
    private double dziedzinaDoWart;
    private boolean dziedzinaDoCzyNalezy;

    public boolean isDziedzinaCzyOdNiesk() {
        return dziedzinaCzyOdNiesk;
    }
    public double getDziedzinaOdWart() {
        return dziedzinaOdWart;
    }
    public boolean isDziedzinaOdCzyNalezy() {
        return dziedzinaOdCzyNalezy;
    }
    public boolean isDziedzinaCzyDoNiesk() {
        return dziedzinaCzyDoNiesk;
    }
    public double getDziedzinaDoWart() {
        return dziedzinaDoWart;
    }
    public boolean isDziedzinaDoCzyNalezy() {
        return dziedzinaDoCzyNalezy;
    }

    public DziedzinaCzujnika(boolean dziedzinaCzyOdNiesk, double dziedzinaOdWart, boolean dziedzinaOdCzyNalezy, boolean dziedzinaCzyDoNiesk, double dziedzinaDoWart, boolean dziedzinaDoCzyNalezy) {
        this.dziedzinaCzyOdNiesk = dziedzinaCzyOdNiesk;
        this.dziedzinaOdWart = dziedzinaOdWart;
        this.dziedzinaOdCzyNalezy = dziedzinaOdCzyNalezy;
        this.dziedzinaCzyDoNiesk = dziedzinaCzyDoNiesk;
        this.dziedzinaDoWart = dziedzinaDoWart;
        this.dziedzinaDoCzyNalezy = dziedzinaDoCzyNalezy;
    }
}
