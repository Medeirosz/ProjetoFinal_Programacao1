package br.feevale;

public abstract class Produto {
    private String nome, descricao;
    private double preco;
    
    public Produto(String nome, double preco, String descricao) {
        this.nome = nome;
        this.preco = preco;
        this.descricao = descricao;
    }
    
    public String getNome() {
        return nome;
    }
    
    public double getPreco() {
        return preco;
    }
    
    public void setPreco(double preco) {
        this.preco = preco;
    }
    
    public String getDescricao() {
        return descricao;
    }
}