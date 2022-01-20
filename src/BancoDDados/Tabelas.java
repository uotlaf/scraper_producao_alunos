package BancoDDados;

import java.sql.SQLException;

public class Tabelas extends Conexao{

    private final String tabelaDiscentes =
            "CREATE TABLE IF NOT EXISTS DISCENTES (" +
                    "IDLATTES BIGINT NOT NULL PRIMARY KEY," +
                    "NOME VARCHAR(100) NOT NULL," +
                    "CITACAO VARCHAR(100) NOT NULL," +
                    "TITULACAO VARCHAR(1000)," +
                    "ATUACAO_PROF VARCHAR(1000)," +
                    "FORMACAO_COMP VARCHAR(1000)," +
                    "AREA_D_ATUACAO VARCHAR(1000)," +
                    "IDIOMAS VARCHAR(1000));";

    private final String tabelaProducoes =
            "CREATE TABLE IF NOT EXISTS PRODUCOES (" +
                    "ID INTEGER IDENTITY PRIMARY KEY," +
                    "TIPO VARCHAR(50) NOT NULL," +
                    "TITULO VARCHAR(100)," +
                    "ANO VARCHAR(30)," +
                    "LOCAL VARCHAR(500)," +
                    "AUTORES VARCHAR(1000)," +
                    "EDITORA VARCHAR(500)," +
                    "OUTROS VARCHAR(1000)," +
                    "TIPODPROD VARCHAR(1000));";


    public Tabelas(String path, String nome, Boolean travarDB) throws SQLException {
        super(path, nome, travarDB);
        construir();
    }

    public void construir() throws SQLException {
        executarScript(tabelaProducoes);
        executarScript(tabelaDiscentes);
    }
}
