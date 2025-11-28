package br.feevale;

import java.util.ArrayList;
import java.util.List;

public class GerenciadorPedidos {
    private static GerenciadorPedidos instance;
    private List<Pedido> pedidosFinalizados;
    
    private GerenciadorPedidos() {
        this.pedidosFinalizados = new ArrayList<>();
    }
    
    public static GerenciadorPedidos getInstance() {
        if (instance == null) {
            instance = new GerenciadorPedidos();
        }
        return instance;
    }
    
    public void adicionarPedidoFinalizado(Pedido pedido) {
        // Cria uma cópia do pedido para armazenar
        Pedido pedidoCopia = copiarPedido(pedido);
        pedidosFinalizados.add(pedidoCopia);
    }
    
    private Pedido copiarPedido(Pedido original) {
        Pedido copia = new Pedido();
        // Copia todos os itens
        for (ItemCarrinho item : original.getItens()) {
            for (int i = 0; i < item.getQuantidade(); i++) {
                copia.adicionarProduto(item.getProduto());
            }
        }
        return copia;
    }
    
    public List<Pedido> getPedidosFinalizados() {
        return pedidosFinalizados;
    }
    
    public Pedido buscarPedidoPorId(int id) {
        for (Pedido pedido : pedidosFinalizados) {
            if (pedido.getId() == id) {
                return pedido;
            }
        }
        return null;
    }
    
    public int getTotalPedidos() {
        return pedidosFinalizados.size();
    }
    
    public double getValorTotalPedidos() {
        double total = 0;
        for (Pedido pedido : pedidosFinalizados) {
            if (pedido.getStatus() != Pedido.StatusPedido.CANCELADO) {
                total += pedido.calcularTotal();
            }
        }
        return total;
    }
}