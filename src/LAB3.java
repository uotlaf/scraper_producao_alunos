
import BancoDDados.DiscenteDAO;
import BancoDDados.ProducaoDAO;
import BancoDDados.Tabelas;
import Entidades.Discente.*;
import Entidades.Discente.Discente;

import java.sql.SQLException;
import java.util.ArrayList;

public class LAB3 {
    public static void main(String[] args) throws SQLException {

        // Informações que vão ser usadas
        String path = "banco";
        String nomedb = "banco.db";

        // Checa se o banco de dados foi criado
        Tabelas tabelas = new Tabelas(path, nomedb, false);

        // Gestor da tabela dos discentes
        DiscenteDAO tabDiscente = new DiscenteDAO(path, nomedb, false);

        // Gestor da tabela das produções
        ProducaoDAO tabProducao = new ProducaoDAO(path, nomedb, false);

        // Cria novo discente
        Discente disc = new Discente();

        // Bota as infos no discente
        disc.setIdlattes(244);
        disc.setNome("ASS");
        disc.setCitacao("sss");
        disc.addTitulacao(new Titulacao(2004, 2005, "Básico",
                "Sei lá qual local", "Algum titulo aí", 2004,
                "Geraldão", "Eu mesmo", "Alguém aí"));

        disc.addAreaDAtuacao(new AreaDAtuacao("Grande", "Pequeno"));
        disc.addAreaDAtuacao(new AreaDAtuacao("Pequeno", "grande"));
        disc.addFormacaoComp(new FormComp(2004, 2005,
                "Nome aleatorio", 25, "Espaço sideral"));

        disc.addAtuacaoProf(new AtuacaoProf("Sei lá", 2001, 2200, "a", "ass", "sss"));
        disc.addIdiomas(new Idioma("Ingles", "Nada",
                "Nada", "Nada", "Nada"));

        //tabDiscente.salvar(disc);
        //tabDiscente.apagar(disc);
        //ArrayList<Discente> dis_Enc = tabDiscente.buscarPorIdioma("Nada");

        // Lista todas as infos encontradas
        //for (Discente d : dis_Enc) {
        //    System.out.println(d.getNome());
        //}

        ArrayList<Discente> todosOsDisc = tabDiscente.listarTodos();

        for (Discente d : todosOsDisc ) {
            System.out.println(d.getNome());
        }
    }

}
