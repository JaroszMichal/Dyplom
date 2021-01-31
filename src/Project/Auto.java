package Project;

public class Auto {
    private String nazwa;
    private float predkoscMaksymalna;
    private float czasOsiagnieciaPredkosciMaksymalnej;
    private float czasWyhamowaniazPredkosciMaksymalnej;
    private float maksymalnyPromienSkretu;

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

    public Auto() {
        this.nazwa = "Auto";
        this.predkoscMaksymalna = 100;                  // km/h
        this.czasOsiagnieciaPredkosciMaksymalnej = 10;  // s
        this.czasWyhamowaniazPredkosciMaksymalnej = 5;  // s
        this.maksymalnyPromienSkretu = 3;               // stopnie
    }

    public Auto(String nazwa, float predkoscMaksymalna, float czasOsiagnieciaPredkosciMaksymalnej, float czasWyhamowaniazPredkosciMaksymalnej, float maksymalnyPromienSkretu) {
        this.nazwa = nazwa;
        this.predkoscMaksymalna = predkoscMaksymalna;
        this.czasOsiagnieciaPredkosciMaksymalnej = czasOsiagnieciaPredkosciMaksymalnej;
        this.czasWyhamowaniazPredkosciMaksymalnej = czasWyhamowaniazPredkosciMaksymalnej;
        this.maksymalnyPromienSkretu = maksymalnyPromienSkretu;
    }
}
