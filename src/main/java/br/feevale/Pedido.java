package br.feevale;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Pedido {
    private static int contadorId = 1;
    
    private int id;
    private ArrayList<ItemCarrinho> itens;
    private StatusPedido status;
    private LocalDateTime dataHora;
    
    public enum StatusPedido {
        AGUARDANDO("Aguardando"),
        EM_PREPARO("Em Preparo"),
        PRONTO("Pronto"),
        ENTREGUE("Entregue"),
        CANCELADO("Cancelado");
        
        private String descricao;
        
        StatusPedido(String descricao) {
            this.descricao = descricao;
        }
        
        public String getDescricao() {
            return descricao;
        }
    }
    
    public Pedido() {
        this.id = contadorId++;
        this.itens = new ArrayList<>();
        this.status = StatusPedido.AGUARDANDO;
        this.dataHora = LocalDateTime.now();
    }
    
    public int getId() {
        return id;
    }
    
    public ArrayList<ItemCarrinho> getItens() {
        return itens;
    }
    
    public StatusPedido getStatus() {
        return status;
    }
    
    public void setStatus(StatusPedido status) {
        this.status = status;
    }
    
    public LocalDateTime getDataHora() {
        return dataHora;
    }
    
    public String getDataHoraFormatada() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
        return dataHora.format(formatter);
    }
    
    public void adicionarProduto(Produto produto) {
        // Verifica se o produto já existe no carrinho
        for (ItemCarrinho item : itens) {
            if (item.getProduto().getNome().equals(produto.getNome())) {
                item.incrementarQuantidade();
                return;
            }
        }
        // Se não existe, adiciona novo item
        itens.add(new ItemCarrinho(produto));
    }
    
    public void removerItem(int index) {
        if (index >= 0 && index < itens.size()) {
            itens.remove(index);
        }
    }
    
    public void diminuirQuantidade(int index) {
        if (index >= 0 && index < itens.size()) {
            ItemCarrinho item = itens.get(index);
            if (item.getQuantidade() > 1) {
                item.decrementarQuantidade();
            } else {
                itens.remove(index);
            }
        }
    }
    
    public double calcularTotal() {
        double total = 0;
        for (ItemCarrinho item : itens) {
            total += item.getSubtotal();
        }
        return total;
    }
    
    public void limpar() {
        itens.clear();
    }
    
    public boolean isEmpty() {
        return itens.isEmpty();
    }
    
    @Override
    public String toString() {
        return String.format("Pedido #%03d - %s - %s - R$ %.2f", 
            id, getDataHoraFormatada(), status.getDescricao(), calcularTotal());
    }
}