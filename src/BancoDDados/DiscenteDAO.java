package BancoDDados;

import Entidades.Discente.*;
import Entidades.Discente.Discente;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class DiscenteDAO extends Conexao implements DAO{

    /**
     * Construtor da classe
     * path = pasta com a db
     * nome = nome da db
     * travarDB = Deseja travar a database para ninguém usar? Na dúvida, false
     *
     * */
    public DiscenteDAO(String path, String nome, Boolean travarDB) throws SQLException {
        super(path, nome, travarDB);
    }

    /**
     *
     * @param entidade Discente que vai ser salvo na tabela
     * @throws SQLException Quando existe algum erro na pesquisa
     */
    @Override
    public void salvar(Object entidade) throws SQLException {

        Discente discente = (Discente) entidade;

        // Montando a string sql
        String comando =
                "INSERT INTO DISCENTES (" +
                        "IDLATTES, NOME, CITACAO, ATUACAO_PROF, TITULACAO, FORMACAO_COMP, AREA_D_ATUACAO, IDIOMAS) VALUES " +
                        "(?,?,?,?,?,?,?,?);";

        conectar();

        String titulacao = "";
        for (Titulacao t : discente.getTitulacao()) {
            // Use "," para separar objetos da titulacao.
            // Use ":" para separar titulacoes
            titulacao += t.getAnoInicial()  + ","
                    + t.getAnoFinal()       + ","
                    + t.getNivel()          + ","
                    + t.getLocal()          + ","
                    + t.getTitulo()         + ","
                    + t.getAnoDObtencao()   + ","
                    + t.getOrientador()     + ","
                    + t.getCoorientador()   + ","
                    + t.getOutros()         + "\n"; // Adiciona o final e pula para outra titulacao
        }

        String atuacaoProf = "";
        for (AtuacaoProf a : discente.getAtuacaoProf()) {
            // Use "," para separar objetos da titulacao.
            // Use ":" para separar titulacoes
            atuacaoProf += a.getAnoInicial()+ ","
                    + a.getAnoFinal()       + ","
                    + a.getVinculo()        + ","
                    + a.getEnquadramento()  + ","
                    + a.getRegime()         + ","
                    + a.getLocal()          + ","
                    + a.getTipoDVinculo()   + ","
                    + a.getOutros()         + "\n"; // Adiciona o final e pula para outra titulacao
        }

        String formcomp = "";
        for (FormComp f : discente.getFormacaoComp()) {
            // Use "," para separar campos
            // Use ":" para separar entidades
            formcomp += f.getAnoInicial()  + ","
                    + f.getAnoFinal()      + ","
                    + f.getTitulo()        + ","
                    + f.getOnde()          + "\n"; // Adiciona o final e pula para outra entidade
        }

        String areaDAtuacao = "";
        for (AreaDAtuacao a : discente.getAreaDAtuacao()) {
            // Use "," para separar campos
            // Use ":" para separar entidades
            areaDAtuacao += a.getGrandeArea()  + ","
                         + a.getArea()         + "\n"; // Adiciona o final e pula para outra entidade
        }

        String idiomas = "";
        for (Idioma i : discente.getIdiomas()) {
            // Use "," para separar campos
            // Use ":" para separar entidades
            idiomas += i.getNome()  + ","
                    + i.getCompreende() + ","
                    + i.getFala() + ","
                    + i.getLe() + ","
                    + i.getEscreve() + "\n"; // Adiciona o final e pula para outra entidade
        }


        // Bota tudo certinho na string acima
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setLong(1, discente.getIdlattes());
        pstm.setString(2, discente.getNome());
        pstm.setString(3, discente.getCitacao());
        pstm.setString(4, titulacao);
        pstm.setString(5, atuacaoProf);
        pstm.setString(6, formcomp);
        pstm.setString(7, areaDAtuacao);
        pstm.setString(8, idiomas);

        pstm.execute();
        conexao.commit();
        desconectar();
    }

    /**
     *
     * @param entidade Discente que deve ser retirado da tabela
     * @throws SQLException Quando existe algum erro na pesquisa
     */
    @Override
    public void apagar(Object entidade) throws SQLException {
        Discente discente = (Discente) entidade;

        String comando = "DELETE FROM DISCENTES WHERE IDLATTES = ?";

        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setLong(1, discente.getIdlattes());
        pstm.execute();
        conexao.commit();
        desconectar();
    }

    /**
     *
     * @param idlattes O id que deve ser procurado
     * @return O primeiro(e único) discente encontrado. Se não encontrar, retorna null
     * @throws SQLException Quando existe algum erro na pesquisa
     */
    public Discente buscarPorIDLattes(long idlattes) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE IDLATTES = ?";

        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setLong(1, idlattes);

        ResultSet lista = pstm.executeQuery();

        Discente discente = new Discente();

        if (lista.next()) {
            discente.setIdlattes(lista.getLong("IDLATTES"));;
            discente.setNome    (lista.getString("NOME"));
            discente.setCitacao (lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            desconectar();
            return discente;
        }

        desconectar();
        return null;
    }

    /**
     *
     * @param nome O nome que deve ser procurado. Pode ser uma parte de um nome
     * @return Uma lista com todas as entradas encontradas, ou uma lista vazia se não encontrar ninguém
     * @throws SQLException Quando existe algum erro na pesquisa
     */
    public ArrayList<Discente> buscarPorNome(String nome) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE NOME LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + nome + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

    /**
     *
     * @param citacao String que deve ser procurada. Pode ser parte de uma
     * @return Uma lista das entradas encontradas, ou uma lista vazia se não encontrar nada
     * @throws SQLException Quando existe algum erro na busca
     */
    public ArrayList<Discente> buscarPorCitacao(String citacao) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE CITACAO LIKE ?";

        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + citacao + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

    /**
     *
     * @param atuacao String que deve ser procurada. Pode ser parte de uma
     * @return Uma lista das entradas encontradas, ou uma lista vazia se não encontra nada
     * @throws SQLException Quando existe algum erro na busca
     */
    public ArrayList<Discente> buscarPorAtuacao(String atuacao) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE ATUACAO_PROF LIKE ?";

        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + atuacao + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

    /**
     *
     * @param tit String que deve ser procurada. Pode ser parte de uma
     * @return Uma lista das entradas encontradas, ou uma lista vazia se não encontra nada
     * @throws SQLException Quando existe algum erro na busca
     */
    public ArrayList<Discente> buscarPorTitulacao(String tit) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE TITULACAO_PROF LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + tit + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

    /**
     *
     * @param Formacao String que deve ser procurada. Pode ser parte de uma
     * @return Uma lista das entradas encontradas, ou uma lista vazia se não encontra nada
     * @throws SQLException Quando existe algum erro na busca
     */
    public ArrayList<Discente> buscarPorFormacao(String Formacao) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE FORMACAO_COMP LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + Formacao + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

    /**
     *
     * @param areaDAtuacao String que deve ser procurada. Pode ser parte de uma
     * @return Uma lista das entradas encontradas, ou uma lista vazia se não encontra nada
     * @throws SQLException Quando existe algum erro na busca
     */
    public ArrayList<Discente> buscarPorAreaDAtuacao(String areaDAtuacao) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE AREA_D_ATUACAO LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + areaDAtuacao + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

    /**
     *
     * @param Idioma String que deve ser procurada. Pode ser parte de uma
     * @return Uma lista das entradas encontradas, ou uma lista vazia se não encontra nada
     * @throws SQLException Quando existe algum erro na busca
     */
    public ArrayList<Discente> buscarPorIdioma(String Idioma) throws SQLException{
        String comando = "SELECT * FROM DISCENTES WHERE IDIOMAS LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + Idioma + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }


    @Override
    public void atualizar(Object entidade) throws SQLException {

    }

    /**
     *
     * @return Todos os discentes da planilha
     * @throws SQLException Quando encontra algum erro
     */
    @Override
    public ArrayList<Discente> listarTodos() throws SQLException{
        String comando = "SELECT * FROM DISCENTES";

        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);

        ResultSet lista = pstm.executeQuery();

        ArrayList<Discente> encontrados = new ArrayList<>();

        while (lista.next()) {

            Discente discente = new Discente();

           discente.setIdlattes(lista.getLong("IDLATTES"));
            discente.setNome(lista.getString("NOME"));
            discente.setCitacao(lista.getString("CITACAO"));


            String[] titulacoes = lista.getString("TITULACAO").split("\n");
            for (String s : titulacoes) {
                String[] titulacao = s.split(",");
                discente.addTitulacao(new Titulacao(titulacao[0],
                        titulacao[1], titulacao[2],
                        titulacao[3], titulacao[4],
                        Integer.parseInt(titulacao[5]), titulacao[6],
                        titulacao[7], titulacao[8]));
            }

            String[] formacoes = lista.getString("FORMACAO_COMP").split("\n");
            for (String s : formacoes) {
                String[] formacao = s.split(",");
                discente.addFormacaoComp(new FormComp(formacao[0],
                        formacao[1], formacao[2], formacao[4]));
            }

            String[] areasDAtuacao = lista.getString("AREA_D_ATUACAO").split("\n");
            for (String s : areasDAtuacao) {
                String[] area = s.split(",");
                discente.addAreaDAtuacao(new AreaDAtuacao(area[0], area[1]));
            }

            String[] idiomas = lista.getString("IDIOMAS").split("\n");
            for (String s : idiomas) {
                String[] idioma = s.split(",");
                discente.addIdiomas(new Idioma(idioma[0], idioma[1], idioma[2], idioma[3],
                        idioma[4]));
            }
            encontrados.add(discente);
        }

        desconectar();
        return encontrados;
    }

}
