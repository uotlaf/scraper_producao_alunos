package Entidades.Discente;

public class AtuacaoProf {
    private String onde;
    private int anoInicial;
    private int anoFinal;
    private String Vinculo;
    private String Enquadramento; // Enquadramento funcional
    private String Regime;

    public AtuacaoProf(String onde, int anoInicial,
                       int anoFinal, String vinculo,
                       String enquadramento, String regime) {
        this.onde = onde;
        this.anoInicial = anoInicial;
        this.anoFinal = anoFinal;
        Vinculo = vinculo;
        Enquadramento = enquadramento;
        Regime = regime;
    }

    public String getOnde() {
        return onde;
    }

    public void setOnde(String onde) {
        this.onde = onde;
    }

    public int getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(int anoInicial) {
        this.anoInicial = anoInicial;
    }

    public int getAnoFinal() {
        return anoFinal;
    }

    public void setAnoFinal(int anoFinal) {
        this.anoFinal = anoFinal;
    }

    public String getVinculo() {
        return Vinculo;
    }

    public void setVinculo(String vinculo) {
        Vinculo = vinculo;
    }

    public String getEnquadramento() {
        return Enquadramento;
    }

    public void setEnquadramento(String enquadramento) {
        Enquadramento = enquadramento;
    }

    public String getRegime() {
        return Regime;
    }

    public void setRegime(String regime) {
        Regime = regime;
    }
}
