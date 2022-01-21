package BancoDDados;

import Entidades.Discente.*;
import Entidades.Discente.Discente;
import Entidades.Producao.Eventos;
import Entidades.Producao.Producao;
import Entidades.Producao.ProducaoTecnica;
import Entidades.Producao.ResumoExpandido;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class ProducaoDAO extends Conexao implements DAO{

    /**
     * Construtor da classe
     * path = pasta com a db
     * nome = nome da db
     * travarDB = Deseja travar a database para ninguém usar? Na dúvida, false
     *
     * */
    public ProducaoDAO(String path, String nome, Boolean travarDB) throws SQLException {
        super(path, nome, travarDB);
    }


    @Override
    public void atualizar(Object entidade) throws SQLException {

    }

    @Override
    public ArrayList<?> listarTodos() throws SQLException {
        return null;
    }


    @Override
    public void salvar(Object entidade) throws SQLException {

        // Montando a string sql
        String comando =
                "INSERT INTO PRODUCOES (" +
                        "TIPO, TITULO, ANO, LOCAL, AUTORES, EDITORA, OUTROS, TIPODPROD) VALUES " +
                        "(?,?,?,?,?,?,?,?);";

        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);


        if (entidade instanceof Eventos) {
            Eventos evento = (Eventos) entidade;
            pstm.setString(1, "Evento");
            pstm.setString(2, "");
            pstm.setInt(3, evento.getAno());
            pstm.setString(4, evento.getLocal());
            pstm.setString(5, "");
            pstm.setString(6, "");
            pstm.setString(7, "");
            pstm.setString(8, "");

        } else if (entidade instanceof ProducaoTecnica) {
            ProducaoTecnica prod = (ProducaoTecnica) entidade;
            pstm.setString(1, "Producao Tecnica");
            pstm.setString(2, prod.getTitulo());
            pstm.setInt(3, prod.getAno());
            pstm.setString(4, "");
            pstm.setString(6, "");
            pstm.setString(7, "");
            pstm.setString(8, prod.getTipo());

            String autores = "";
            for (String i : prod.getAutores()) {
                autores += i + "\n"; // Adiciona o final e pula para outra entidade
            }
            pstm.setString(5, autores);

        }else if (entidade instanceof ResumoExpandido) {
            ResumoExpandido resumo = (ResumoExpandido) entidade;
            pstm.setString(1, "Resumo Expandido");
            pstm.setString(2, resumo.getTitulo());
            pstm.setInt(3, resumo.getAno());
            pstm.setString(4, "");
            pstm.setString(6, resumo.getEditora());
            pstm.setString(7, resumo.getOutrasInfos());
            pstm.setString(8, "");

            String autores = "";
            for (String i : resumo.getAutores()) {
                autores += i + "\n"; // Adiciona o final e pula para outra entidade
            }
            pstm.setString(5, autores);
        } else {
            System.out.println("Que diabos é isso");
        }

        pstm.execute();
        conexao.commit();
        desconectar();
    }


    @Override
    public void apagar(Object entidade) throws SQLException {

        String comando = "DELETE FROM DISCENTE WHERE ";

        conectar();
        PreparedStatement pstm = null;
        
        if (entidade instanceof Eventos) {
            Eventos evento = (Eventos) entidade;
            comando += "EVENTO = ?";
            pstm = conexao.prepareStatement(comando);
            pstm.setString(1, evento.getLocal());
        } else if (entidade instanceof ProducaoTecnica) {
            ProducaoTecnica prod = (ProducaoTecnica) entidade;
            comando += "TITULO = ?";
            pstm = conexao.prepareStatement(comando);
            pstm.setString(1, prod.getTitulo());
        } else if (entidade instanceof ResumoExpandido) {
            ResumoExpandido resumo = (ResumoExpandido) entidade;
            comando += "TITULO = ?";
            pstm = conexao.prepareStatement(comando);
            pstm.setString(1, resumo.getTitulo());
        }

        if (pstm != null) {
            pstm.execute();
            conexao.commit();
        }
        desconectar();
    }


    public ArrayList<Producao> buscarPorTipo(String tipo) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE TIPO LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + tipo + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }   

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorTitulo(String titulo) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE TITULO LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + titulo + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorAno(int ano) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE ANO = ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setInt(1, ano);

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorLocal(String local) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE LOCAL LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + local + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorAutores(String autor) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE AUTORES LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + autor + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorEditora(String editora) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE EDITORA LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + editora + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorOutros(String pesquisa) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE OUTROS LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + pesquisa + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores, lista.getString("TITULO"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }

    public ArrayList<Producao> buscarPorTipoDeProducao(String tipo) throws SQLException {
        String comando = "SELECT * FROM DISCENTES WHERE TIPODPROD LIKE ?";


        conectar();
        PreparedStatement pstm = conexao.prepareStatement(comando);
        pstm.setString(1, "%" + tipo + "%");

        ResultSet lista = pstm.executeQuery();

        ArrayList<Producao> encontrados = new ArrayList<>();

        while (lista.next()) {
            if (lista.getString("TIPO").equals("Evento")) {
                Eventos prod = new Eventos(lista.getInt("ANO"), lista.getString("LOCAL"), lista.getString("TIPODPROD"), lista.getString("NOME"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Producao Tecnica")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ProducaoTecnica prod = new ProducaoTecnica(lista.getInt("ANO"),
                        lista.getString("TITULO"),
                        lista.getString("TIPODPROD"),
                        autores,
                        lista.getString("LOCAL"));
                encontrados.add(prod);
            } else if (lista.getString("TIPO").equals("Resumo Expandido")) {
                String autores_str = lista.getString("AUTORES");
                ArrayList<String> autores = new ArrayList<>();

                for (String s : autores_str.split("\n")) {
                    autores.add(s);
                }

                ResumoExpandido prod = new ResumoExpandido(lista.getInt("ANO"),
                        lista.getString("EDITORA"),
                        lista.getString("TITULO"),
                        autores, lista.getString("OUTROS"));
                encontrados.add(prod);
            }
        }

        desconectar();
        return encontrados;

    }


}
