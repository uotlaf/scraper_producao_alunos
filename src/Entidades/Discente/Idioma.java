package Entidades.Discente;

public class Idioma {
    private String nome;
    private String Compreende;
    private String Fala;
    private String Le;
    private String Escreve;

    public Idioma(String nome, String compreende,
                  String fala, String le,
                  String escreve) {
        this.nome = nome;
        Compreende = compreende;
        Fala = fala;
        Le = le;
        Escreve = escreve;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCompreende() {
        return Compreende;
    }

    public void setCompreende(String compreende) {
        Compreende = compreende;
    }

    public String getFala() {
        return Fala;
    }

    public void setFala(String fala) {
        Fala = fala;
    }

    public String getLe() {
        return Le;
    }

    public void setLe(String le) {
        Le = le;
    }

    public String getEscreve() {
        return Escreve;
    }

    public void setEscreve(String escreve) {
        Escreve = escreve;
    }

    @Override
    public String toString() {
        return "Idioma{" +
                "nome='" + nome + '\'' +
                ", Compreende='" + Compreende + '\'' +
                ", Fala='" + Fala + '\'' +
                ", Le='" + Le + '\'' +
                ", Escreve='" + Escreve + '\'' +
                '}';
    }
}
