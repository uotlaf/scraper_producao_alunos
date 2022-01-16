package Entidades.Discente;

public class Titulacao {
    private int anoInicial;
    private int anoFinal;
    private String nivel;
    private String local;
    private String titulo;
    private int anoDObtencao;
    private String orientador;
    private String coorientador;
    private String outros;

    public Titulacao(int anoInicial, int anoFinal,
                     String nivel, String local,
                     String titulo, int anoDObtencao,
                     String orientador, String coorientador,
                     String outros) {
        this.anoInicial = anoInicial;
        this.anoFinal = anoFinal;
        this.nivel = nivel;
        this.local = local;
        this.titulo = titulo;
        this.anoDObtencao = anoDObtencao;
        this.orientador = orientador;
        this.coorientador = coorientador;
        this.outros = outros;
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

    public String getNivel() {
        return nivel;
    }

    public void setNivel(String nivel) {
        this.nivel = nivel;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public int getAnoDObtencao() {
        return anoDObtencao;
    }

    public void setAnoDObtencao(int anoDObtencao) {
        this.anoDObtencao = anoDObtencao;
    }

    public String getOrientador() {
        return orientador;
    }

    public void setOrientador(String orientador) {
        this.orientador = orientador;
    }

    public String getCoorientador() {
        return coorientador;
    }

    public void setCoorientador(String coorientador) {
        this.coorientador = coorientador;
    }

    public String getOutros() {
        return outros;
    }

    public void setOutros(String outros) {
        this.outros = outros;
    }
}
