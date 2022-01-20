package Entidades.Discente;

public class AtuacaoProf {
    private String anoInicial;
    private String anoFinal;
    private String Vinculo;
    private String Enquadramento; // Enquadramento funcional
    private String Regime;
    private String Local;
    private String tipoDVinculo;
    private String Outros;


    public AtuacaoProf(String anoInicial,
                       String anoFinal, String vinculo,
                       String enquadramento, String regime, String Local, String tipoDVinculo, String outros) {

        this.anoInicial = anoInicial;
        this.anoFinal = anoFinal;
        Vinculo = vinculo;
        Enquadramento = enquadramento;
        Regime = regime;
        this.tipoDVinculo = tipoDVinculo;
        this.Outros = outros;
        this.Local = Local;
    }

    public String getLocal() {
        return Local;
    }

    public void setLocal(String local) {
        Local = local;
    }

    public String getOutros() {
        return Outros;
    }

    public void setOutros(String outros) {
        Outros = outros;
    }


    public String getAnoInicial() {
        return anoInicial;
    }

    public void setAnoInicial(String anoInicial) {
        this.anoInicial = anoInicial;
    }

    public String getAnoFinal() {
        return anoFinal;
    }

    public void setAnoFinal(String anoFinal) {
        this.anoFinal = anoFinal;
    }

    public String getTipoDVinculo() {
        return tipoDVinculo;
    }

    public void setTipoDVinculo(String tipoDVinculo) {
        this.tipoDVinculo = tipoDVinculo;
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

    @Override
    public String toString() {
        return "AtuacaoProf{" +
                "anoInicial='" + anoInicial + '\'' +
                ", anoFinal='" + anoFinal + '\'' +
                ", Vinculo='" + Vinculo + '\'' +
                ", Enquadramento='" + Enquadramento + '\'' +
                ", Regime='" + Regime + '\'' +
                ", Local='" + Local + '\'' +
                ", tipoDVinculo='" + tipoDVinculo + '\'' +
                ", Outros='" + Outros + '\'' +
                '}';
    }
}
