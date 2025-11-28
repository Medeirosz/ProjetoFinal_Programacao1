package br.feevale;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.control.Label;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.stage.Modality;
import javafx.stage.Stage;
import java.io.IOException;

public class SecondaryController {
    
    @FXML
    private ListView<String> listaPedido;
    
    @FXML
    private Label labelTotal;
    
    private Pedido pedidoAtual;
    private ObservableList<String> itensObservable;
    private GerenciadorPedidos gerenciador;

    @FXML
    public void initialize() {
        pedidoAtual = new Pedido();
        gerenciador = GerenciadorPedidos.getInstance();
        itensObservable = FXCollections.observableArrayList();
        listaPedido.setItems(itensObservable);
        atualizarCarrinho();
    }

    @FXML
    private void addExpresso() {
        abrirModal(new CafeExpresso());
    }
    
    @FXML
    private void addPassado() {
        abrirModal(new CafePassado());
    }
    
    @FXML
    private void addComLeite() {
        abrirModal(new CafeComLeite());
    }
    
    @FXML
    private void addPaoQueijo() {
        abrirModal(new PaoDeQueijo());
    }
    
    @FXML
    private void addMisto() {
        abrirModal(new MistoQuente());
    }
    
    @FXML
    private void addPaoChapa() {
        abrirModal(new PaoNaChapa());
    }

    // modal confirmacao
    
    private void abrirModal(Produto produto) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("modal.fxml"));
            Parent root = loader.load();
            
            ModalController modalController = loader.getController();
            modalController.setProduto(produto);
            
            Stage modalStage = new Stage();
            modalStage.setTitle("Adicionar ao Carrinho");
            modalStage.initModality(Modality.APPLICATION_MODAL);
            modalStage.setScene(new Scene(root));
            modalStage.setResizable(false);
            
            modalStage.showAndWait();
            
            // Se confirmou, adiciona ao carrinho
            if (modalController.isConfirmado()) {
                pedidoAtual.adicionarProduto(produto);
                atualizarCarrinho();
                
            }
            
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", 
                         "Erro ao abrir modal", 
                         "Não foi possível abrir a janela de confirmação.");
        }
    }

    // gerenciamento carrinho
    
    private void atualizarCarrinho() {
        itensObservable.clear();
        
        for (ItemCarrinho item : pedidoAtual.getItens()) {
            itensObservable.add(item.toString());
        }
        
        labelTotal.setText(String.format("R$ %.2f", pedidoAtual.calcularTotal()));
    }
    
    @FXML
    private void diminuirQuantidade() {
        int index = listaPedido.getSelectionModel().getSelectedIndex();
        
        if (index >= 0) {
            pedidoAtual.diminuirQuantidade(index);
            atualizarCarrinho();
        } else {
            mostrarAlerta(AlertType.WARNING, "Nenhum Item Selecionado", 
                         "Selecione um item", 
                         "Por favor, selecione um item da lista para remover.");
        }
    }
    
    @FXML
    private void limparCarrinho() {
        if (pedidoAtual.isEmpty()) {
            mostrarAlerta(AlertType.INFORMATION, "Carrinho Vazio", 
                         "Nada para limpar", 
                         "O carrinho já está vazio.");
            return;
        }
        
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Limpeza");
        confirmacao.setHeaderText("Limpar Carrinho");
        confirmacao.setContentText("Deseja realmente remover todos os itens do carrinho?");
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                pedidoAtual.limpar();
                atualizarCarrinho();
                mostrarAlerta(AlertType.INFORMATION, "Carrinho Limpo", 
                             "Sucesso!", 
                             "Todos os itens foram removidos do carrinho.");
            }
        });
    }
    
    @FXML
    private void confirmarPedido() {
        if (pedidoAtual.isEmpty()) {
            mostrarAlerta(AlertType.WARNING, "Carrinho Vazio", 
                         "Adicione produtos", 
                         "Adicione pelo menos um produto antes de finalizar o pedido.");
            return;
        }
        
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Pedido");
        confirmacao.setHeaderText("Finalizar Pedido");
        confirmacao.setContentText(String.format(
            "Deseja finalizar o pedido?\n\nTotal: R$ %.2f\nItens: %d",
            pedidoAtual.calcularTotal(),
            pedidoAtual.getItens().size()
        ));
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                // adiciona o pedido ao gerenciador
                gerenciador.adicionarPedidoFinalizado(pedidoAtual);
                
                // confirmação
                mostrarAlerta(AlertType.INFORMATION, "Pedido Realizado", 
                             "Sucesso!", 
                             String.format("Pedido #%03d realizado com sucesso!\n\nTotal: R$ %.2f\n\nAguarde, seu pedido está sendo preparado.",
                                          pedidoAtual.getId(),
                                          pedidoAtual.calcularTotal()));
                
                // cria novo pedido
                pedidoAtual = new Pedido();
                atualizarCarrinho();
            }
        });
    }
    
    @FXML
    private void switchToPrimary() {
        if (!pedidoAtual.isEmpty()) {
            Alert confirmacao = new Alert(AlertType.CONFIRMATION);
            confirmacao.setTitle("Voltar ao Menu");
            confirmacao.setHeaderText("Pedido em Andamento");
            confirmacao.setContentText("Você tem itens no carrinho. Deseja realmente voltar?\n\nOs itens serão perdidos.");
            
            confirmacao.showAndWait().ifPresent(response -> {
                if (response == ButtonType.OK) {
                    voltarMenu();
                }
            });
        } else {
            voltarMenu();
        }
    }
    
    private void voltarMenu() {
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", 
                         "Erro ao voltar", 
                         "Não foi possível voltar ao menu principal.");
        }
    }
    
    private void mostrarAlerta(AlertType tipo, String titulo, String cabecalho, String conteudo) {
        Alert alert = new Alert(tipo);
        alert.setTitle(titulo);
        alert.setHeaderText(cabecalho);
        alert.setContentText(conteudo);
        alert.showAndWait();
    }
}