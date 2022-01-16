package BancoDDados;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;


public class Conexao {
    private String path = "jdbc:hsqldb:file:"; // URL do banco
    private String nome; // Nome do banco
    private String user;
    private String senha;

    public Conexao(String path, String nome, Boolean travarDB) {
        this.path += path;
        this.nome = (nome + ";hsqldb.lock_file=" + travarDB);
        this.user = "SA"; // Padrão do hsqldb
        this.senha = ""; // Padrão do hsqldb
    }

    public Connection conexao;

    public void conectar() throws SQLException {
        conexao = DriverManager.getConnection(path + "/" + nome,
                user, senha);
    };

    public void desconectar() throws SQLException {
        conexao.close();
    }

    public void executarScript(String comandosql) throws SQLException {
        System.out.println("Executando "+ comandosql);
        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comandosql);
        pstm.execute();
        conexao.commit();
        desconectar();
    }

}
