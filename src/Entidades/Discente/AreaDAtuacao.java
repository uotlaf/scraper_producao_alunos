package Entidades.Discente;

public class AreaDAtuacao {
    private String grandeArea;
    private String area;

    public AreaDAtuacao(String grandeArea, String area) {
        this.grandeArea = grandeArea;
        this.area = area;
    }

    public String getGrandeArea() {
        return grandeArea;
    }

    public void setGrandeArea(String grandeArea) {
        this.grandeArea = grandeArea;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}
