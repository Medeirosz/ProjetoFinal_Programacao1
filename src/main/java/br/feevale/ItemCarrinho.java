package br.feevale;

public class ItemCarrinho {
    private Produto produto;
    private int quantidade;
    
    public ItemCarrinho(Produto produto) {
        this.produto = produto;
        this.quantidade = 1;
    }
    
    public Produto getProduto() {
        return produto;
    }
    
    public int getQuantidade() {
        return quantidade;
    }
    
    public void incrementarQuantidade() {
        this.quantidade++;
    }
    
    public void decrementarQuantidade() {
        if (this.quantidade > 1) {
            this.quantidade--;
        }
    }
    
    public double getSubtotal() {
        return produto.getPreco() * quantidade;
    }
    
    @Override
    public String toString() {
        return quantidade + "x " + produto.getNome() + 
               " - R$ " + String.format("%.2f", getSubtotal());
    }
}
