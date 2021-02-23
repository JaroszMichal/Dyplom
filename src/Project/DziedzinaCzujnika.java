package Project;

import java.util.Objects;

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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        DziedzinaCzujnika that = (DziedzinaCzujnika) o;
        return dziedzinaCzyOdNiesk == that.dziedzinaCzyOdNiesk
                && Double.compare(that.dziedzinaOdWart, dziedzinaOdWart) == 0
                && dziedzinaOdCzyNalezy == that.dziedzinaOdCzyNalezy
                && dziedzinaCzyDoNiesk == that.dziedzinaCzyDoNiesk
                && Double.compare(that.dziedzinaDoWart, dziedzinaDoWart) == 0
                && dziedzinaDoCzyNalezy == that.dziedzinaDoCzyNalezy;
    }

    @Override
    public int hashCode() {
        return Objects.hash(dziedzinaCzyOdNiesk, dziedzinaOdWart, dziedzinaOdCzyNalezy, dziedzinaCzyDoNiesk, dziedzinaDoWart, dziedzinaDoCzyNalezy);
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
