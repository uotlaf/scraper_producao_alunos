package Entidades.Producao;

public class Eventos extends Producao{
    // Participação em eventos, congressos, exposições e feiras
    //
    private String local;


    public Eventos(int ano, String local) {
        super(ano);
        this.local = local;
    }

    public String getLocal() {
        return local;
    }

    public void setLocal(String local) {
        this.local = local;
    }
}
