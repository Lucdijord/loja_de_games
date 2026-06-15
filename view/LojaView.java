package view;

import controller.LojaController;
import exceptions.EstoqueInsuficienteException;
import model.Console;
import model.Jogo;

public class LojaView {
    
    private LojaController loja;

    public LojaView(LojaController loja) {
        this.loja = loja;
    }

    public void executarDemonstracao() {
        Jogo j1 = new Jogo("Resident Evil 9", 101, 100.00, "Ação", "PS5");
        Jogo j2 = new Jogo("Elden Ring", 102, 100.00, "RPG", "PC");
        Jogo j3 = new Jogo("Zelda BOTW", 103, 100.00, "RPG", "SWITCH");
        Console c1 = new Console("Nintendo Switch", 201, 2200.00, "Nintendo", "8ª Geração");

        loja.cadastrarProduto(j1, 10);
        loja.cadastrarProduto(j2, 10);
        loja.cadastrarProduto(j3, 3); 
        loja.cadastrarProduto(c1, 5);

        try {
            loja.adicionarAoCarrinho(101, 5, false);
        } catch (EstoqueInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        try {
            loja.adicionarAoCarrinho(102, 1, true);
        } catch (EstoqueInsuficienteException e) {
            System.out.println(e.getMessage());
        }

        try {
            loja.adicionarAoCarrinho(103, 10, false);
        } catch (EstoqueInsuficienteException e) {
            System.out.println(e.getMessage() + " -> EstoqueInsuficienteException");
        }

        String recibo = loja.gerarRecibo();
        System.out.print(recibo);

        loja.salvarVendaTxt(recibo); 
        loja.salvarCatalogoTxt();
        loja.carregarCatalogoTxt();
    }
}