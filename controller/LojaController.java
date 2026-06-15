package controller;

import exceptions.EstoqueInsuficienteException;
import model.Jogo;
import model.Produto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import controller.casos_de_uso.DescontoProgressivo;
import controller.casos_de_uso.PersistenciaTxt;

public class LojaController {
    
    private List<Produto> catalogo;
    private Map<Integer, Integer> estoque; 
    private List<Produto> carrinhoProdutos;
    private List<Integer> carrinhoQuantidades;
    private List<Boolean> carrinhoUsados; 

    public LojaController() {
        this.catalogo = new ArrayList<>();
        this.estoque = new HashMap<>();
        this.carrinhoProdutos = new ArrayList<>();
        this.carrinhoQuantidades = new ArrayList<>();
        this.carrinhoUsados = new ArrayList<>();
    }

    public void cadastrarProduto(Produto produto, int quantidadeEstoque) {
        catalogo.add(produto);
        estoque.put(produto.getCodigo(), quantidadeEstoque);
    }

    public void adicionarAoCarrinho(int codigo, int quantidade, boolean usado) throws EstoqueInsuficienteException {
        int qtdDisponivel = estoque.getOrDefault(codigo, 0);
        if (quantidade > qtdDisponivel) {
            throw new EstoqueInsuficienteException("ERRO: Estoque insuficiente");
        }

        Produto prod = buscarProdutoPorCodigo(codigo);
        if (prod != null) {
            carrinhoProdutos.add(prod);
            carrinhoQuantidades.add(quantidade);
            carrinhoUsados.add(usado);
            estoque.put(codigo, qtdDisponivel - quantidade);
        }
    }

    public double calcularDescontoAtual() {
        double subtotal = 0.0;
        for (int i = 0; i < carrinhoProdutos.size(); i++) {
            subtotal += carrinhoProdutos.get(i).getPreco() * carrinhoQuantidades.get(i);
        }
        DescontoProgressivo calculadoraDesconto = new DescontoProgressivo();
        return calculadoraDesconto.calcularDescontoTotal(carrinhoProdutos, carrinhoQuantidades, carrinhoUsados, subtotal);
    }

    public String gerarRecibo() {
        int totalItens = 0;
        double subtotal = 0.0;

        StringBuilder recibo = new StringBuilder();
        recibo.append("=== GameZone Store ===\n");
        recibo.append("Itens no carrinho:\n");

        for (int i = 0; i < carrinhoProdutos.size(); i++) {
            Produto p = carrinhoProdutos.get(i);
            int quantidade = carrinhoQuantidades.get(i);
            boolean usado = carrinhoUsados.get(i);
            
            totalItens += quantidade;
            double precoItem = p.getPreco() * quantidade;
            subtotal += precoItem;

            String status = (p instanceof Jogo && usado) ? " (Usado)" : "";
            recibo.append(String.format("- %dx %s%s: R$ %.2f\n", quantidade, p.getNome(), status, precoItem));
        }

        DescontoProgressivo calculadoraDesconto = new DescontoProgressivo();
        double totalDescontos = calculadoraDesconto.calcularDescontoTotal(
                carrinhoProdutos, carrinhoQuantidades, carrinhoUsados, subtotal
        );

        double totalFinal = subtotal - totalDescontos;

        recibo.append("----------------------\n");
        recibo.append(String.format("Carrinho: %d itens | Subtotal: R$ %.2f | Descontos: -R$ %.2f\n", totalItens, subtotal, totalDescontos));
        recibo.append(String.format("Total: R$ %.2f\n", totalFinal));

        return recibo.toString();
    }

    private Produto buscarProdutoPorCodigo(int codigo) {
        for (Produto p : catalogo) {
            if (p.getCodigo() == codigo) return p;
        }
        return null;
    }

    private PersistenciaTxt persistencia = new PersistenciaTxt();

    public void salvarCatalogoTxt() {
        persistencia.salvarCatalogo(this.catalogo);
    }

    public void carregarCatalogoTxt() {
        this.catalogo = persistencia.carregarCatalogo();
        System.out.println("Catálogo carregado com sucesso:");
        
        for (Produto p : this.catalogo) {
            System.out.printf("- [%d] %s | Preço: R$ %.2f\n", p.getCodigo(), p.getNome(), p.getPreco());
        }
    }

    public void salvarVendaTxt(String reciboGerado) {
        persistencia.salvarVenda(reciboGerado);
    }
}