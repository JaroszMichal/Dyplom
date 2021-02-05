package Project;

public class Czujniki {
    public static final String[] czujniki =     {"Prędkość",
                                                "Położenie na torze",
                                                "Kierunek i promień najbliższego zakrętu",
                                                "Odległość do zakrętu",
                                                "Combo"};
    public static final String[] czujnikiOpis = {"Wskazuje aktualną prędkość pojazdu w km/h.",
                                                "Wskazuje położenie na torze w zakresie od -1 (przy lewej krawędzi toru), poprzez 0 (na środku toru) do 1 (przy prawej krawędzi toru).",
                                                "Wskazuje obserwowany przez kierowcę kierunek najbliższego zakrętu i jego promień. Czujnik przyjmuje wartości od -1 (ostro w lewo) do 1 (ostro w prawo).",
                                                "Wskazuje odległość do najbliższego zakrętu.",
                                                "zdolność do wyhamowania z obecnej prędkości przed najbliższym zakrętem w lewo lub prawo. Wyliczana ze wzoru: " +
                                                        "(droga hamowania z obecnej prędkości)/(odległość do zakrętu)*(znak kierunku zakrętu (-1 - w lewo, +1 - w prawo))." +
                                                        "wartości z przedziału <-1, 1> oznaczają zdolność do zatrzymania auta przed zakrętem. Wskazania, których wartość bezwzględna przekracza \"1\" oznaczają, brak takiej możliwości."};
    public static final DziedzinaCzujnika[] dziedzinaCzujnikow = {
            new DziedzinaCzujnika(false, 0, true,true,1,true),
            new DziedzinaCzujnika(false, -1, true,false,1,true),
            new DziedzinaCzujnika(false, -1, true,false,1,true),
            new DziedzinaCzujnika(false, 0, true,true,1,true),
            new DziedzinaCzujnika(true, -1, true,true,1,true),
    };

    public static String getOpisCzujnika(String nazwa) {
        for (int i=0;i<czujniki.length;i++)
            if (czujniki[i].equals(nazwa))
                return czujnikiOpis[i];
        return "błędna nazwa czujnika";
    }

    public static DziedzinaCzujnika getDziedzinaCzujnika(String nazwa){
        for (int i=0;i<czujniki.length;i++)
            if (czujniki[i].equals(nazwa))
                return dziedzinaCzujnikow[i];
        return dziedzinaCzujnikow[0];
    }
}
