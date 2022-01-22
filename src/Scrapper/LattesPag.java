package Scrapper;

import Entidades.Discente.*;
import Entidades.Producao.Eventos;
import Entidades.Producao.Producao;
import Entidades.Producao.ProducaoTecnica;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.nodes.Node;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LattesPag {

    public ArrayList<Titulacao> SeparaTitulacao(List<Element> titulos) {
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

        // Separa e cria as titulações
        ArrayList<Titulacao> listaTit = new ArrayList<>();
        if (titulacoes != null) {
            for (Element e : titulacoes) {
                if (e.tagName().equals("div")) {
                    // Informações usadas para criar a titulacao
                    String anoInicial   = "";
                    String anoFinal     = "";
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
                        anoInicial = temp[0].trim();
                        if (temp.length > 1) {
                            anoFinal = temp[1].trim();
                        }

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

                            // Pode aumentar uma ocorrência
                            curOcorrencia++;

                            // 0 é sempre o nível
                            if (curOcorrencia == 1) {
                                nivel = n.toString().replace("\n", "");

                                // 2 é sempre local.
                            }else if (curOcorrencia == 2) {
                                local = n.toString();

                            } else if (n.toString().contains("Título")) {
                                temp = n.toString().split(",");


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

                        listaTit.add(new Titulacao(anoInicial, anoFinal,
                                nivel, local, titulo, anoDObtencao, orientador, coorientador,
                                outros));
                        // Na próxima vez, entra no parser de ano
                        troca = true;
                    }
                }
            }
        }

        return listaTit;
    }

    public ArrayList<FormComp> SeparaFormacao(List<Element> titulos) {
        List<Element> titulacoes = null;
        // Achando e pegando a parte de titulação
        for (Element e : titulos) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
            if (h1 != null && h1.text().equals("Formação Complementar")) {
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

        // Separa e cria as titulações
        ArrayList<FormComp> listaTit = new ArrayList<>();
        if (titulacoes != null) {
            for (Element e : titulacoes) {
                if (e.tagName().equals("div")) {
                    // Informações usadas para criar a titulacao
                    String anoInicial = "";
                    String anoFinal   = "";
                    String local      = "";
                    String titulo     = "";

                    String[] temp;
                    List<Node> templist;

                    // Ano
                    if (troca) {
                        temp = e.text().split("-");
                        anoInicial = temp[0].trim();
                        if (temp.length > 1) {
                            anoFinal = temp[1].trim();
                        }

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

                            // Pode aumentar uma ocorrência
                            curOcorrencia++;

                            // 0 é sempre o nível
                            if (curOcorrencia == 1) {
                                titulo = n.toString();

                                // 2 é sempre local.
                            } else if (curOcorrencia == 2) {
                                local = n.toString();
                            }

                            listaTit.add(new FormComp(anoInicial, anoFinal,
                                    titulo, local));
                            // Na próxima vez, entra no parser de ano
                            troca = true;
                        }
                    }
                }
            }


        }
        return listaTit;
    }

    public ArrayList<AtuacaoProf> SeparaAtuacao(List<Element> titulos) {
        List<Element> atuacoes = null;

        for (Element e : titulos) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
            if (h1 != null && h1.text().equals("Atuação Profissional")) {
                // Pega uma lista de todas as titulações. São divs que ficam na primeira div
                atuacoes = e.getElementsByTag("div").first().children();

                for (Element a : atuacoes) {
                    if (a.tagName().equals("div")) {
                        atuacoes = a.children();
                        break;
                    }
                }
                break;
            }
        }

        ArrayList<AtuacaoProf> atuacaoProfs = new ArrayList<>();

        String anoInicial    = "";
        String anoFinal      = "";
        String vinculo       = "";
        String enquadramento = "";
        String regime        = "";
        String local         = "";
        String outros        = "";
        String tipoDVinculo  = "";
        String[] temp;

        boolean skipNext = false;
        for (Element e : atuacoes) {
            if (skipNext) {
                skipNext = false;
                continue;
            } else if (e.hasClass("clear")) { // Zera tudo menos o local
                if (!anoInicial.equals("")) {
                    atuacaoProfs.add(new AtuacaoProf( anoInicial, anoFinal, vinculo, enquadramento, regime,
                            local, tipoDVinculo, outros));
                    anoInicial   = "";
                    anoFinal     = "";
                    vinculo      = "";
                    enquadramento= "";
                    regime       = "";
                    outros       = "";
                    tipoDVinculo = "";
                }
            } else if (e.hasClass("inst_back")) { // Substitui o local
                local = e.text();
            } else if (e.hasClass("subtit-1")) { // Tipo de vínculo
                tipoDVinculo = e.text();
                skipNext = true;
            } else if (e.hasClass("layout-cell-3")) {
                if (!e.text().equals("Outras informações")) {
                    temp = e.text().split("-");
                    if (temp.length > 1) {

                        // Se chegou uma nova data, cria pode adicionar o anterior
                        if (!anoInicial.equals("")) {
                            atuacaoProfs.add(new AtuacaoProf( anoInicial, anoFinal, vinculo, enquadramento, regime,
                                    local, tipoDVinculo, outros));
                        }

                        anoInicial = temp[0].trim();
                        anoFinal = temp[1].trim();
                    }
                }
            } else if (e.hasClass("layout-cell-9")) {
                temp = e.text().split(",");
                for (String s : temp) {
                    if (s.contains("Vínculo")) {
                        vinculo = s.split(":")[1];
                    } else if (s.contains("Enquadramento Funcional")) {
                        enquadramento = s.split(":")[1];
                    } else if (s.contains("Regime")) {
                        regime = s.split(":")[1];
                    } else {
                        outros += s;
                    }
                }
            }
        }


        return atuacaoProfs;
    }

    public ArrayList<Eventos> SeparaEventos(List<Element> titulo) {
        List<Element> eventos = null;

        for (Element e : titulo) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
            if (h1 != null && h1.text().equals("Eventos")) {
                // Pega uma lista de todas as titulações. São divs que ficam na primeira div
                eventos = e.getElementsByTag("div").first().children();

                for (Element a : eventos) {
                    if (a.tagName().equals("div")) {
                        eventos = a.children();
                        break;
                    }
                }
                break;
            }
        }

        ArrayList<Eventos> listEventos = new ArrayList<>();
        ArrayList<Element> templist;
        int ano     = 0 ;
        String local= "";
        String nome = "";
        String tipo = "";
        String[] temp;


        for (Element e : eventos) {
            if (e.hasClass("layout-cell-12")) {
                templist = e.children();
                for (Element i : templist) {
                    if (i.hasClass("clear")) {
                        if (ano != 0) {
                            // Bota o evento achado no arraylist e zera
                            listEventos.add(new Eventos(ano, local, tipo, nome));
                            nome = "";
                            tipo = "";
                            local = "";
                            ano = 0;
                        }
                    } else if (i.hasClass("layout-cell-11")) {
                        temp = i.text().split("\\.");
                        local = temp[0];
                        // Verifica se o nome está disponível
                        try {
                            ano = Integer.parseInt(temp[1].trim());
                            tipo = temp[2].trim().replace("(", "").replace(")", "");

                        } catch (NumberFormatException n) {
                            nome = temp[1];
                            ano = Integer.parseInt(temp[2].trim());
                            tipo = temp[3].trim().replace("(", "").replace(")", "");
                        }
                    }
                }

            }
        }
        return listEventos;
    }

    // TODO: Terminar de separar as Produções Técnicas
    public ArrayList<ProducaoTecnica> SeparaProdTecnica(List<Element> titulo) {
        List<Element> eventos = null;

        for (Element e : titulo) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
            if (h1 != null && h1.text().equals("Produções")) {
                // Pega uma lista de todas as titulações. São divs que ficam na primeira div
                eventos = e.getElementsByTag("div").first().children();

                for (Element a : eventos) {
                    if (a.tagName().equals("div")) {
                        eventos = a.children();
                        break;
                    }
                }
                break;
            }
        }

        ArrayList<ProducaoTecnica> listProd = new ArrayList<>();
        ArrayList<Element> templist;
        int ano     = 0 ;
        String local= "";
        String tituloL = "";
        String tipo = "";
        String[] temp;
        String autores = "";

        for (Element e : eventos) {
            if (e.hasClass("layout-cell-11")) {
                temp = e.text().split(" \\. ");
                autores = temp[0].replace(";", "\n");





                //System.out.println(temp[0]);
            }
        }

        return listProd;
    }

    // TODO: PROJETO DE PESQUISA
    public ArrayList<Idioma> SeparaIdioma(List<Element> titulo) {
        List<Element> idioma = null;

        for (Element e : titulo) {
            Element h1 = e.getElementsByTag("h1").first();
            // Achando a guia com a identificação
            if (h1 != null && h1.text().equals("Idiomas")) {
                // Pega uma lista de todas as titulações. São divs que ficam na primeira div
                idioma = e.getElementsByTag("div").first().children();

                for (Element a : idioma) {
                    if (a.tagName().equals("div")) {
                        idioma = a.children();
                        break;
                    }
                }
                break;
            }
        }

        ArrayList<Idioma> listaIdiomas = new ArrayList<>();

        String nome       = "";
        String compreende = "";
        String fala       = "";
        String le         = "";
        String escreve    = "";
        String[] temp;

        for (Element e : idioma) {
            if (e.hasClass("layout-cell-3")) {
                if (nome.equals("")) {
                    nome = e.text();
                } else {
                    listaIdiomas.add(new Idioma(nome, compreende, fala, le, escreve));
                    nome = e.text();
                    compreende = "";
                    fala = "";
                    le = "";
                    escreve = "";
                }
            } else if (e.hasClass("layout-cell-9")) {
                temp = e.text().split(", ");

                for (String t : temp) {
                    if (t.contains("Compreende")) {
                        compreende = t.split(" ")[1];
                    } else if (t.contains("Fala")) {
                        fala = t.split(" ")[1];
                    } else if (t.contains("Lê")) {
                        le = t.split(" ")[1];
                    } else if (t.contains("Escreve")) {
                        escreve = t.split(" ")[1];
                    }
                }
            }
        }

        return listaIdiomas;
    }

    public Discente ScrapPessoa(String html) throws IOException {
        // File arquivo = new File(path_do_html);
        //Document doc = Jsoup.parse(html, "ISO-8859-1", "");
        Document doc = Jsoup.parse(html, "");

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


        Discente disc = new Discente();
        disc.setIdlattes(idlattes);
        disc.setNome(nome);
        disc.setCitacao(citacao);
        disc.setTitulacao(SeparaTitulacao(titulos));
        disc.setFormacaoComp(SeparaFormacao(titulos));
        disc.setAtuacaoProf(SeparaAtuacao(titulos));
        disc.setIdiomas(SeparaIdioma(titulos));

        return disc;
    }

    public ArrayList<Producao> ScrapProducoes(String html) throws IOException {
        //File arquivo = new File(path_do_html);
        //Document doc = Jsoup.parse(html, "ISO-8859-1", "");
        Document doc = Jsoup.parse(html, "");

        List<Element> titulos = doc.getElementsByClass("title-wrapper");


        ArrayList<Producao> resultado = new ArrayList<>();

        ArrayList<Eventos> eventos = SeparaEventos(titulos);

        for (Eventos e: eventos) {
            resultado.add(e);
        }

        ArrayList<ProducaoTecnica> prodTec = SeparaProdTecnica(titulos);

        for (ProducaoTecnica p : prodTec) {
            resultado.add(p);
        }

        return resultado;
    }
}


