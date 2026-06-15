package model;

public class Console extends Produto {
    private String fabricante;
    private String geracao;

    public Console(String nome, int codigo, double preco, String fabricante, String geracao) {
        super(nome, codigo, preco);
        this.fabricante = fabricante;
        this.geracao = geracao;
    }

    public String getFabricante() { return fabricante; }
    public String getGeracao() { return geracao; }
    
}
