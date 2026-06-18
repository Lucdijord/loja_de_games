package view;

import controller.LojaController;
import exceptions.EstoqueInsuficienteException;
import model.Console;
import model.Jogo;

import java.util.Scanner;

public class LojaView {
    
    private LojaController loja;
    private Scanner scanner;

    public LojaView(LojaController loja) {
        this.loja = loja;
        this.scanner = new Scanner(System.in);
    }

    public void iniciarMenu() {
        int opcao = -1;

        while (opcao != 0) {
            System.out.println("\n=== GAMEZONE STORE - TERMINAL ===");
            System.out.println("1. Cadastrar Novo Produto no Catálogo");
            System.out.println("2. Adicionar Item ao Carrinho");
            System.out.println("3. Finalizar Venda e Exibir Recibo");
            System.out.println("4. Salvar e Recarregar Catálogo");
            System.out.println("0. Sair do Sistema");
            System.out.print("Escolha uma opção: ");

            try {
                opcao = scanner.nextInt();
                scanner.nextLine(); 
            } catch (Exception e) {
                System.out.println("Por favor, digite um número válido.");
                scanner.nextLine(); 
                continue;
            }

            switch (opcao) {
                case 1:
                    cadastrarCatalogoInterativo();
                    break;
                case 2:
                    adicionarItemInterativo();
                    break;
                case 3:
                    finalizarVenda();
                    break;
                case 4:
                    salvarArquivos();
                    break;
                case 0:
                    System.out.println("Encerrando o sistema.");
                    break;
                default:
                    System.out.println("Opção inválida!");
            }
        }
        scanner.close();
    }

    private void cadastrarCatalogoInterativo() {
        boolean continuar = true;

        while (continuar) {
            System.out.println("\n--- CADASTRAR PRODUTO ---");
            try {
                System.out.print("Tipo de produto (1 - Jogo | 2 - Console): ");
                int tipo = scanner.nextInt();
                scanner.nextLine(); 

                System.out.print("Nome do produto: ");
                String nome = scanner.nextLine();

                System.out.print("Código numérico: ");
                int codigo = scanner.nextInt();

                System.out.print("Preço (Ex: 150,00 ou 150): ");
                double preco = scanner.nextDouble();

                System.out.print("Quantidade inicial em estoque: ");
                int quantidade = scanner.nextInt();
                scanner.nextLine(); 

                if (tipo == 1) {
                    System.out.print("Gênero do jogo: ");
                    String genero = scanner.nextLine();
                    System.out.print("Plataforma: ");
                    String plataforma = scanner.nextLine();

                    Jogo jogo = new Jogo(nome, codigo, preco, genero, plataforma);
                    loja.cadastrarProduto(jogo, quantidade);
                    System.out.println("Jogo cadastrado com sucesso!");

                } else if (tipo == 2) {
                    System.out.print("Fabricante do console: ");
                    String fabricante = scanner.nextLine();
                    System.out.print("Geração (Ex: 8ª Geração): ");
                    String geracao = scanner.nextLine();

                    Console console = new Console(nome, codigo, preco, fabricante, geracao);
                    loja.cadastrarProduto(console, quantidade);
                    System.out.println("Console cadastrado com sucesso!");
                } else {
                    System.out.println("Tipo inválido! Escolha 1 ou 2.");
                }

                System.out.print("\nDeseja cadastrar outro produto? (S/N): ");
                String resposta = scanner.nextLine();
                if (resposta.equalsIgnoreCase("N")) {
                    continuar = false;
                }

            } catch (Exception e) {
                System.out.println("Erro de formatação! Se for preço, lembre-se de usar vírgula (,) e não ponto.");
                scanner.nextLine(); 
            }
        }
    }

    private void adicionarItemInterativo() {
        try {
            System.out.print("Digite o código do produto: ");
            int codigo = scanner.nextInt();

            System.out.print("Digite a quantidade: ");
            int quantidade = scanner.nextInt();
            scanner.nextLine(); 

            System.out.print("O item é usado? (S/N): ");
            String resposta = scanner.nextLine();
            boolean usado = resposta.equalsIgnoreCase("S");

            loja.adicionarAoCarrinho(codigo, quantidade, usado);
            System.out.println("Item adicionado ao carrinho!");

        } catch (EstoqueInsuficienteException e) {
            System.out.println("\n " + e.getMessage() + " -> EstoqueInsuficienteException");
        } catch (Exception e) {
            System.out.println("Erro de digitação. Operação cancelada.");
            scanner.nextLine(); 
        }
    }

    private void finalizarVenda() {
        System.out.println("\nProcessando a venda...");
        String recibo = loja.gerarRecibo();
        System.out.print(recibo);
        
        loja.salvarVendaTxt(recibo);
    }

    private void salvarArquivos() {
        System.out.println("\n--- Persistência txt ---");
        loja.salvarCatalogoTxt();
        loja.carregarCatalogoTxt();
    }
}