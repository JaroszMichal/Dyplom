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

    public void setDziedzinaCzyOdNiesk(boolean dziedzinaCzyOdNiesk) {
        this.dziedzinaCzyOdNiesk = dziedzinaCzyOdNiesk;
    }

    public double getDziedzinaOdWart() {
        return dziedzinaOdWart;
    }

    public void setDziedzinaOdWart(double dziedzinaOdWart) {
        this.dziedzinaOdWart = dziedzinaOdWart;
    }

    public boolean isDziedzinaOdCzyNalezy() {
        return dziedzinaOdCzyNalezy;
    }

    public void setDziedzinaOdCzyNalezy(boolean dziedzinaOdCzyNalezy) {
        this.dziedzinaOdCzyNalezy = dziedzinaOdCzyNalezy;
    }

    public boolean isDziedzinaCzyDoNiesk() {
        return dziedzinaCzyDoNiesk;
    }

    public void setDziedzinaCzyDoNiesk(boolean dziedzinaCzyDoNiesk) {
        this.dziedzinaCzyDoNiesk = dziedzinaCzyDoNiesk;
    }

    public double getDziedzinaDoWart() {
        return dziedzinaDoWart;
    }

    public void setDziedzinaDoWart(double dziedzinaDoWart) {
        this.dziedzinaDoWart = dziedzinaDoWart;
    }

    public boolean isDziedzinaDoCzyNalezy() {
        return dziedzinaDoCzyNalezy;
    }

    public void setDziedzinaDoCzyNalezy(boolean dziedzinaDoCzyNalezy) {
        this.dziedzinaDoCzyNalezy = dziedzinaDoCzyNalezy;
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
