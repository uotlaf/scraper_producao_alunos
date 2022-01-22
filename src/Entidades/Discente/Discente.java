package Entidades.Discente;


import java.util.ArrayList;


public class Discente {
    private Long                    idlattes;
    private String                  nome;                               // Nome completo
    private String                  citacao;                            // Nome em citações Bibliográficas
    private ArrayList<Titulacao>    titulacao    = new ArrayList<>();   // Formação Acadêmica / Titulação
    private ArrayList<FormComp>     formacaoComp = new ArrayList<>();   // Formação Complementar
    private ArrayList<AtuacaoProf>  AtuacaoProf  = new ArrayList<>();   // Atuação Profissional
    private ArrayList<AreaDAtuacao> AreaDAtuacao = new ArrayList<>();
    private ArrayList<Idioma>       idiomas      = new ArrayList<>();

    public Long getIdlattes() {
        return idlattes;
    }

    public void setIdlattes(Long idlattes) {
        this.idlattes = idlattes;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCitacao() {
        return citacao;
    }

    public void setCitacao(String citacao) {
        this.citacao = citacao;
    }

    public ArrayList<Titulacao> getTitulacao() {
        return titulacao;
    }

    public void setTitulacao(ArrayList<Titulacao> titulacao) {
        this.titulacao = titulacao;
    }

    public void addTitulacao(Titulacao t) {
        System.out.println(t.getNivel());
        System.out.println(this.titulacao);
        this.titulacao.add(t);
    }

    public ArrayList<FormComp> getFormacaoComp() {
        return formacaoComp;
    }

    public void setFormacaoComp(ArrayList<FormComp> formacaoComp) {
        this.formacaoComp = formacaoComp;
    }

    public void addFormacaoComp(FormComp f) {
        this.formacaoComp.add(f);
    }

    public ArrayList<Entidades.Discente.AtuacaoProf> getAtuacaoProf() {
        return AtuacaoProf;
    }

    public void setAtuacaoProf(ArrayList<Entidades.Discente.AtuacaoProf> atuacaoProf) {
        AtuacaoProf = atuacaoProf;
    }

    public void addAtuacaoProf(AtuacaoProf a) {
        this.AtuacaoProf.add(a);
    }

    public ArrayList<Entidades.Discente.AreaDAtuacao> getAreaDAtuacao() {
        return AreaDAtuacao;
    }

    public void setAreaDAtuacao(ArrayList<Entidades.Discente.AreaDAtuacao> areaDAtuacao) {
        AreaDAtuacao = areaDAtuacao;
    }

    public void addAreaDAtuacao(AreaDAtuacao a) {
        this.AreaDAtuacao.add(a);
    }

    public ArrayList<Idioma> getIdiomas() {
        return idiomas;
    }

    public void setIdiomas(ArrayList<Idioma> idiomas) {
        this.idiomas = idiomas;
    }

    public void addIdiomas(Idioma i) {
        this.idiomas.add(i);
    }

    @Override
    public String toString() {
        return "Discente{" +
                "idlattes=" + idlattes +
                ", nome='" + nome + '\'' +
                '}';
    }
}
