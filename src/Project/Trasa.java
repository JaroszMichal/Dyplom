package Project;

import java.awt.image.BufferedImage;

public class Trasa {
    private String nazwa;
    private BufferedImage image;

    public String getNazwa() {
        return nazwa;
    }
    public void setNazwa(String nazwa) {
        this.nazwa = nazwa;
    }
    public BufferedImage getImage() {
        return image;
    }
    public void setImage(BufferedImage image) {
        this.image = image;
    }

    public Trasa(){
        this.image = null;
        nazwa = "Route 66";
    }

}
