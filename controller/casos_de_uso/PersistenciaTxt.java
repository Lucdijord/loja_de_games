package controller.casos_de_uso;

import model.Console;
import model.Jogo;
import model.Produto;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PersistenciaTxt {
    private static final String ARQUIVO_CATALOGO = "catalogo.txt";
    private static final String ARQUIVO_VENDAS = "vendas.txt";

    public void salvarCatalogo(List<Produto> catalogo) {
        try (FileWriter writer = new FileWriter(ARQUIVO_CATALOGO)) {
            for (Produto p : catalogo) {
                if (p instanceof Jogo) {
                    Jogo j = (Jogo) p;
                    writer.write("J;" + j.getNome() + ";" + j.getCodigo() + ";" + 
                                 j.getPreco() + ";" + j.getGenero() + ";" + j.getPlataforma() + "\n");
                } else if (p instanceof Console) {
                    Console c = (Console) p;
                    writer.write("C;" + c.getNome() + ";" + c.getCodigo() + ";" + 
                                 c.getPreco() + ";" + c.getFabricante() + ";" + c.getGeracao() + "\n");
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao salvar catálogo: " + e.getMessage());
        }
    }

    public List<Produto> carregarCatalogo() {
        List<Produto> catalogoCarregado = new ArrayList<>();
        try (BufferedReader reader = new BufferedReader(new FileReader(ARQUIVO_CATALOGO))) {
            String linha;
            while ((linha = reader.readLine()) != null) {
                String[] dados = linha.split(";");
                
                if (dados[0].equals("J")) {
                    catalogoCarregado.add(new Jogo(dados[1], Integer.parseInt(dados[2]), 
                            Double.parseDouble(dados[3]), dados[4], dados[5]));
                } else if (dados[0].equals("C")) {
                    catalogoCarregado.add(new Console(dados[1], Integer.parseInt(dados[2]), 
                            Double.parseDouble(dados[3]), dados[4], dados[5]));
                }
            }
        } catch (IOException e) {
            System.out.println("Erro ao carregar catálogo: " + e.getMessage());
        }
        return catalogoCarregado;
    }

    public void salvarVenda(String recibo) {
        try (FileWriter writer = new FileWriter(ARQUIVO_VENDAS, true)) {
            writer.write(recibo + "\n");
            System.out.println("vendas.txt salvo");
        } catch (IOException e) {
            System.out.println("Erro ao salvar venda: " + e.getMessage());
        }
    }
}