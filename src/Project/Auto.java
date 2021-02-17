package Project;

public class Auto {
    private String nazwa;
    private float predkoscMaksymalna;
    private float czasOsiagnieciaPredkosciMaksymalnej;
    private float czasWyhamowaniazPredkosciMaksymalnej;
    private float maksymalnyKatSkretu;

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
    public float getMaksymalnyKatSkretu() {
        return maksymalnyKatSkretu;
    }
    public void setMaksymalnyKatSkretu(float maksymalnyKatSkretu) {
        this.maksymalnyKatSkretu = maksymalnyKatSkretu;
    }
    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }

    public Auto() {
        this.nazwa = "Citroen Berlingo";
        this.predkoscMaksymalna = 100;                  // km/h
        this.czasOsiagnieciaPredkosciMaksymalnej = 5;  // s
        this.czasWyhamowaniazPredkosciMaksymalnej = 5;  // s
        this.maksymalnyKatSkretu = 3;               // stopnie
    }

    public Auto(String nazwa, float predkoscMaksymalna, float czasOsiagnieciaPredkosciMaksymalnej, float czasWyhamowaniazPredkosciMaksymalnej, float maksymalnyKatSkretu) {
        this.nazwa = nazwa;
        this.predkoscMaksymalna = predkoscMaksymalna;
        this.czasOsiagnieciaPredkosciMaksymalnej = czasOsiagnieciaPredkosciMaksymalnej;
        this.czasWyhamowaniazPredkosciMaksymalnej = czasWyhamowaniazPredkosciMaksymalnej;
        this.maksymalnyKatSkretu = maksymalnyKatSkretu;
    }
}
