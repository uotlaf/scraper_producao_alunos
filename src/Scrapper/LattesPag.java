package Scrapper;

import Entidades.Discente.Titulacao;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LattesPag {
    public void ScrapPessoa(String path_do_html) throws IOException {
        File arquivo = new File(path_do_html);
        Document doc = Jsoup.parse(arquivo, "ISO-8859-1", "");

        // Pega o nome da pessoa
        String nome = doc.getElementsByClass("nome").first().text();

        // Conseguindo id lattes
        Element informacoesAutor = doc.getElementsByClass("informacoes-autor").first();
        List<Element> lis = informacoesAutor.getElementsByTag("li");

        // Procurando id nas lis acima
        long idlattes = 0;
        for (Element el : lis) {
            if (el.text().contains("ID Lattes")) {
                idlattes = Long.parseLong(el.text().split(":")[1].trim());
                break;
            }
        }

        List<Element> titulos = doc.getElementsByClass("title-wrapper");

        // Pegando a citação bibliográfica
        Element identificacao = null; // "Aba" com o nome em citação
        String citacao = "";

        // Achando a identificacao
        for (Element e : titulos) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
            if (h1 != null && h1.text().equals("Identificação")) {
                identificacao = e;
                break;
            }
        }

        // Encontrando o nome em citação
        if (identificacao != null) {
            List<Element> identDivs = identificacao.getElementsByTag("div");
            boolean achou = false;
            for (Element e : identDivs) {
                // Acha a linha que precisamos
                if (e.text().equals("Nome em citações bibliográficas")) {
                    achou = true;
                    continue;
                }
                if (achou) {
                    citacao = e.text();
                    break;
                }
            }
        }

        Element t_test = null;
        List<Element> titulacoes = null;
        // Achando e pegando a parte de titulação
        for (Element e : titulos) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
                if (h1 != null && h1.text().equals("Formação acadêmica/titulação")) {
                    // Pega uma lista de todas as titulações. São divs que ficam na primeira div
                    titulacoes = e.getElementsByTag("div").first().children();

                    for (Element a : titulacoes) {
                        if (a.tagName().equals("div")) {
                            titulacoes = a.children();
                            break;
                        }
                    }
                    break;
                }
        }

        // Divs pares são os anos e ímpares são as formações
        // Essa var troca entre elas
        boolean troca = true;
        boolean pular = false; // Pula o próximo resultado

        // Separa e cria as titulações
        ArrayList<Titulacao> listaTit = new ArrayList<>();
        if (titulacoes != null) {
            for (Element e : titulacoes) {
                if (e.tagName().equals("div")) {
                    // Informações usadas para criar a titulacao
                    int anoInicial      = 0 ;
                    int anoFinal        = 0 ;
                    String nivel        = "";
                    String local        = "";
                    String titulo       = "";
                    int anoDObtencao    = 0 ;
                    String orientador   = "";
                    String coorientador = "";
                    String outros       = "";
                    String[] temp;
                    List<Node> templist;

                    // Ano
                    if (troca) {
                        temp = e.text().split("-");
                        anoInicial = Integer.parseInt(temp[0].trim());
                        if (temp.length > 1) {
                            anoFinal = Integer.parseInt(temp[1].trim());
                        }
                        System.out.println(anoInicial);
                        System.out.println(anoFinal);

                        // Na próxima vez, entra no parser de texto
                        troca = false;
                    } else {
                        templist = e.children().first().childNodes();


                        int curOcorrencia = 0; // Conta quantas ocorrências de string vão acontecendo
                        for (Node n : templist) {
                            // Deve verificar se cada um dos requisitos estão disponíveis

                            // Continua toda vez que encontra uma tag
                            if (n.toString().contains("<")) {
                                continue;
                            }

                            // Se a string estiver vazia, pula
                            if (n.toString().replace(".", "").trim() == "") {
                                continue;
                            }


                            curOcorrencia++;

                            // 0 é sempre o nível
                            if (curOcorrencia == 1) {
                                nivel = n.toString();

                            // 2 é sempre local.
                            }else if (curOcorrencia == 2) {
                                local = n.toString();

                            } else if (n.toString().contains("Título")) {
                                temp = n.toString().split(",");

                                System.out.println(temp[0]);

                                titulo = temp[0].split(":")[1];


                            } else if (n.toString().contains("Orientador")) {
                                temp = n.toString().split(":");

                                if (temp.length > 1) {
                                    orientador = temp[1];
                                }

                            } else if (n.toString().contains("Coorientador")) {
                                temp = n.toString().split(":");

                                if (temp.length > 1) {
                                    coorientador = temp[1];
                                }

                            } else {
                                outros += n.toString();
                            }
                        }

                        System.out.println(new Titulacao(anoInicial, anoFinal,
                                nivel, local, titulo, anoDObtencao, orientador, coorientador,
                                outros));
                        listaTit.add(new Titulacao(anoInicial, anoFinal,
                                nivel, local, titulo, anoDObtencao, orientador, coorientador,
                                outros));
                        // Na próxima vez, entra no parser de ano
                        troca = true;
                    }
                }
            }
        }

        System.out.println("Encontrados:");
        System.out.println(nome);
        System.out.println(idlattes);
        System.out.println(citacao);
        System.out.println("Titulações:");
        for (Titulacao t : listaTit) {
            System.out.println(t);
        }


    }
}
