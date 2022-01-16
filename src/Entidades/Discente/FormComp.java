package Entidades.Discente;

public class FormComp { // Formacao Complementar
    private int anoInicial;
    private int anoFinal;
    private String nome;
    private int cargaHoraria; // Em horas
    private String onde;

    public FormComp(int anoInicial, int anoFinal,
                    String nome, int cargaHoraria,
                    String onde) {
        this.anoInicial = anoInicial;
        this.anoFinal = anoFinal;
        this.nome = nome;
        this.cargaHoraria = cargaHoraria;
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

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getCargaHoraria() {
        return cargaHoraria;
    }

    public void setCargaHoraria(int cargaHoraria) {
        this.cargaHoraria = cargaHoraria;
    }

    public String getOnde() {
        return onde;
    }

    public void setOnde(String onde) {
        this.onde = onde;
    }
}
