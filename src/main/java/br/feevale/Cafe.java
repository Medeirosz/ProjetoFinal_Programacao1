package br.feevale;

public abstract class Cafe extends Produto {
    private String tipo;
    private String temperatura;
    
    public Cafe(String nome, double preco, String descricao, String tipo, String temperatura) {
        super(nome, preco, descricao);
        this.tipo = tipo;
        this.temperatura = temperatura;
    }
    
    public String getTipo() {
        return tipo;
    }
    
    public String getTemperatura() {
        return temperatura;
    }
}