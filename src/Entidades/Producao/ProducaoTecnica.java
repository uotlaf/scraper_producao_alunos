package Entidades.Producao;

import java.util.ArrayList;

public class ProducaoTecnica extends Producao{
    // Producao técnica = Entrevistas, mesas redondas,
    // programas e comentários na mídia

    // Regex:
    // Autores. Nome do programa. Tipo de produção(Programa de rádio, por ex). Ano (Tipo de trabalho)

    private String titulo; // Nome do programa
    private String tipo;
    private ArrayList<String> autores = new ArrayList<>();
    private String onde;

    public ProducaoTecnica(int ano, String titulo, String tipo, ArrayList<String> autores, String onde) {
        super(ano);
        this.titulo = titulo;
        this.tipo = tipo;
        this.autores = autores;
        this.onde = onde;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public ArrayList<String> getAutores() {
        return autores;
    }

    public void setAutores(ArrayList<String> autores) {
        this.autores = autores;
    }
}
