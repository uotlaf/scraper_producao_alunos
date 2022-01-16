package Entidades.Producao;

import java.util.ArrayList;

public class ResumoExpandido extends Producao{
    // Regex:
    // Autores.;Autores. Título. Local. Editora. Outras infos

    private String editora;
    private String titulo;
    private ArrayList<String> autores = new ArrayList<>();
    private String outrasInfos;

    public String getEditora() {
        return editora;
    }

    public void setEditora(String editora) {
        this.editora = editora;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public ArrayList<String> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<String> autores) {
        this.autores = autores;
    }

    public String getOutrasInfos() {
        return outrasInfos;
    }

    public void setOutrasInfos(String outrasInfos) {
        this.outrasInfos = outrasInfos;
    }

    public ResumoExpandido(int ano, String editora, String titulo, ArrayList<String> autores, String outrasInfos) {
        super(ano);
        this.editora = editora;
        this.titulo = titulo;
        this.autores = autores;
        this.outrasInfos = outrasInfos;
    }
}
