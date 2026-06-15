package model;

public class Jogo extends Produto {
    private String genero;
    private String plataforma;

    public Jogo(String nome, int codigo, double preco, String genero, String plataforma) {
        super(nome, codigo, preco);
        this.genero = genero;
        this.plataforma = plataforma;
    }

    public String getGenero() { return genero; }
    public String getPlataforma() { return plataforma; }
    
}