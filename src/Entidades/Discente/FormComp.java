package Entidades.Discente;

public class FormComp { // Formacao Complementar
    private String anoInicial;
    private String anoFinal;
    private String titulo;
    private String onde;

    public FormComp(String anoInicial, String anoFinal,
                    String titulo, String onde) {
        this.anoInicial = anoInicial;
        this.anoFinal = anoFinal;
        this.titulo = titulo;
        this.onde = onde;
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


    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getOnde() {
        return onde;
    }

    public void setOnde(String onde) {
        this.onde = onde;
    }
}
