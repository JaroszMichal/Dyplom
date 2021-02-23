package Project;

import java.util.Objects;

public class Punkt {
    private double x;
    private double y;
    public double getX() {return x;}
    public void setX(double x) {this.x = x;}
    public double getY() {return y;}
    public void setY(double y) {this.y = y;}

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Punkt punkt = (Punkt) o;
        return Double.compare(punkt.x, x) == 0 && Double.compare(punkt.y, y) == 0;
    }

    @Override
    public int hashCode() {
        return Objects.hash(x, y);
    }

    public Punkt(double x, double y) {
        this.x = x;
        this.y = y;
    }

}
