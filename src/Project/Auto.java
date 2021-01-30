package Project;

public class Auto {
    private float predkoscMaksymalna;
    private float czasOsiagnieciaPredkosciMaksymalnej;

    public Auto() {
        this.predkoscMaksymalna = 100;                  // km/h
        this.czasOsiagnieciaPredkosciMaksymalnej = 10;  // s
        this.czasWyhamowaniazPredkosciMaksymalnej = 5;  // s
        this.maksymalnyPromienSkretu = 3;               // stopnie
    }

    public Auto(float predkoscMaksymalna, float czasOsiagnieciaPredkosciMaksymalnej, float czasWyhamowaniazPredkosciMaksymalnej, float maksymalnyPromienSkretu) {
        this.predkoscMaksymalna = predkoscMaksymalna;
        this.czasOsiagnieciaPredkosciMaksymalnej = czasOsiagnieciaPredkosciMaksymalnej;
        this.czasWyhamowaniazPredkosciMaksymalnej = czasWyhamowaniazPredkosciMaksymalnej;
        this.maksymalnyPromienSkretu = maksymalnyPromienSkretu;
    }

    private float czasWyhamowaniazPredkosciMaksymalnej;

    public float getPredkoscMaksymalna() {
        return predkoscMaksymalna;
    }

    public void setPredkoscMaksymalna(float predkoscMaksymalna) {
        this.predkoscMaksymalna = predkoscMaksymalna;
    }

    public float getCzasOsiagnieciaPredkosciMaksymalnej() {
        return czasOsiagnieciaPredkosciMaksymalnej;
    }

    public void setCzasOsiagnieciaPredkosciMaksymalnej(float czasOsiagnieciaPredkosciMaksymalnej) {
        this.czasOsiagnieciaPredkosciMaksymalnej = czasOsiagnieciaPredkosciMaksymalnej;
    }

    public float getCzasWyhamowaniazPredkosciMaksymalnej() {
        return czasWyhamowaniazPredkosciMaksymalnej;
    }

    public void setCzasWyhamowaniazPredkosciMaksymalnej(float czasWyhamowaniazPredkosciMaksymalnej) {
        this.czasWyhamowaniazPredkosciMaksymalnej = czasWyhamowaniazPredkosciMaksymalnej;
    }

    public float getMaksymalnyPromienSkretu() {
        return maksymalnyPromienSkretu;
    }

    public void setMaksymalnyPromienSkretu(float maksymalnyPromienSkretu) {
        this.maksymalnyPromienSkretu = maksymalnyPromienSkretu;
    }

    private float maksymalnyPromienSkretu;



}
