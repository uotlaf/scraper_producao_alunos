package Entidades.Producao;

public class Eventos extends Producao{
    // Participação em eventos, congressos, exposições e feiras
    //
    private String local;
    private String tipo;
    private String nome;


    public Eventos(int ano, String local, String tipo, String nome) {
        super(ano);
        this.local = local;
        this.tipo = tipo;
        this.nome = nome;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }

    @Override
    public String toString() {
        return "Eventos{" +
                "local='" + local + '\'' +
                ", tipo='" + tipo + '\'' +
                ", nome='" + nome + '\'' +
                ", ano=" + ano +
                '}';
    }
}
