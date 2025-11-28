package br.feevale;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import java.io.IOException;

public class AdminController {

    @FXML
    private ListView<Pedido> listaPedidos;
    
    @FXML
    private TextArea detalhesArea;
    
    @FXML
    private ComboBox<Pedido.StatusPedido> comboStatus;
    
    @FXML
    private Label labelTotalPedidos;
    
    @FXML
    private Label labelValorTotal;
    
    @FXML
    private Label labelPedidoSelecionado;

    private ObservableList<Pedido> pedidosObservable;
    private GerenciadorPedidos gerenciador;
    private Pedido pedidoSelecionado;

    @FXML
    public void initialize() {
        gerenciador = GerenciadorPedidos.getInstance();
        pedidosObservable = FXCollections.observableArrayList(gerenciador.getPedidosFinalizados());
        
        // Configura a ListView
        listaPedidos.setItems(pedidosObservable);
        listaPedidos.setCellFactory(param -> new ListCell<Pedido>() {
            @Override
            protected void updateItem(Pedido pedido, boolean empty) {
                super.updateItem(pedido, empty);
                
                if (empty || pedido == null) {
                    setText(null);
                    setStyle("");
                } else {
                    setText(pedido.toString());
                    
                    // Estiliza baseado no status
                    switch (pedido.getStatus()) {
                        case AGUARDANDO:
                            setStyle("-fx-background-color: rgba(255, 193, 7, 0.2); -fx-text-fill: #FFC107; -fx-font-weight: bold;");
                            break;
                        case EM_PREPARO:
                            setStyle("-fx-background-color: rgba(33, 150, 243, 0.2); -fx-text-fill: #2196F3; -fx-font-weight: bold;");
                            break;
                        case PRONTO:
                            setStyle("-fx-background-color: rgba(76, 175, 80, 0.2); -fx-text-fill: #4CAF50; -fx-font-weight: bold;");
                            break;
                        case ENTREGUE:
                            setStyle("-fx-background-color: rgba(158, 158, 158, 0.2); -fx-text-fill: #9E9E9E; -fx-font-weight: bold;");
                            break;
                        case CANCELADO:
                            setStyle("-fx-background-color: rgba(244, 67, 54, 0.2); -fx-text-fill: #F44336; -fx-font-weight: bold;");
                            break;
                        default:
                            setStyle("");
                    }
                }
            }
        });
        
        // Configura o ComboBox de status
        comboStatus.setItems(FXCollections.observableArrayList(Pedido.StatusPedido.values()));
        comboStatus.setDisable(true);
        
        // Listener para seleção de pedido
        listaPedidos.getSelectionModel().selectedItemProperty().addListener(
            (observable, oldValue, newValue) -> {
                if (newValue != null) {
                    pedidoSelecionado = newValue;
                    exibirDetalhesPedido(newValue);
                    comboStatus.setValue(newValue.getStatus());
                    comboStatus.setDisable(false);
                    labelPedidoSelecionado.setText("Pedido #" + String.format("%03d", newValue.getId()));
                } else {
                    detalhesArea.clear();
                    comboStatus.setValue(null);
                    comboStatus.setDisable(true);
                    labelPedidoSelecionado.setText("DETALHES DO PEDIDO");
                    pedidoSelecionado = null;
                }
            }
        );
        
        atualizarEstatisticas();
        
        // Mensagem inicial se não houver pedidos
        if (pedidosObservable.isEmpty()) {
            detalhesArea.setText("Nenhum pedido registrado ainda.\n\nOs pedidos finalizados aparecerão aqui.");
        }
    }

    private void exibirDetalhesPedido(Pedido pedido) {
        if (pedido == null) {
            detalhesArea.clear();
            return;
        }
        
        StringBuilder detalhes = new StringBuilder();
        detalhes.append("═══════════════════════════════════════\n");
        detalhes.append(String.format("PEDIDO #%03d\n", pedido.getId()));
        detalhes.append("═══════════════════════════════════════\n\n");
        detalhes.append("Data/Hora: ").append(pedido.getDataHoraFormatada()).append("\n");
        detalhes.append("Status: ").append(pedido.getStatus().getDescricao()).append("\n\n");
        detalhes.append("───────────────────────────────────────\n");
        detalhes.append("ITENS DO PEDIDO:\n");
        detalhes.append("───────────────────────────────────────\n\n");
        
        for (ItemCarrinho item : pedido.getItens()) {
            detalhes.append(String.format("%dx %s\n", 
                item.getQuantidade(), 
                item.getProduto().getNome()));
            detalhes.append(String.format("   R$ %.2f x %d = R$ %.2f\n\n", 
                item.getProduto().getPreco(), 
                item.getQuantidade(), 
                item.getSubtotal()));
        }
        
        detalhes.append("───────────────────────────────────────\n");
        detalhes.append(String.format("TOTAL: R$ %.2f\n", pedido.calcularTotal()));
        detalhes.append("═══════════════════════════════════════\n");
        
        detalhesArea.setText(detalhes.toString());
    }

    @FXML
    private void alterarStatus() {
        if (pedidoSelecionado == null) {
            mostrarAlerta(AlertType.WARNING, "Nenhum Pedido Selecionado", 
                         "Selecione um pedido", 
                         "Por favor, selecione um pedido para alterar o status.");
            return;
        }
        
        Pedido.StatusPedido novoStatus = comboStatus.getValue();
        if (novoStatus == null) {
            mostrarAlerta(AlertType.WARNING, "Status não selecionado", 
                         "Selecione um status", 
                         "Por favor, selecione um status antes de confirmar.");
            return;
        }
        
        Alert confirmacao = new Alert(AlertType.CONFIRMATION);
        confirmacao.setTitle("Confirmar Alteração");
        confirmacao.setHeaderText("Alterar Status do Pedido");
        confirmacao.setContentText(String.format(
            "Deseja alterar o status do Pedido #%03d de '%s' para '%s'?",
            pedidoSelecionado.getId(),
            pedidoSelecionado.getStatus().getDescricao(),
            novoStatus.getDescricao()
        ));
        
        confirmacao.showAndWait().ifPresent(response -> {
            if (response == ButtonType.OK) {
                pedidoSelecionado.setStatus(novoStatus);
                listaPedidos.refresh();
                exibirDetalhesPedido(pedidoSelecionado);
                atualizarEstatisticas();
                
                mostrarAlerta(AlertType.INFORMATION, "Status Alterado", 
                             "Sucesso!", 
                             "Status do pedido atualizado com sucesso.");
            }
        });
    }

    @FXML
    private void atualizarLista() {
        pedidosObservable.setAll(gerenciador.getPedidosFinalizados());
        listaPedidos.refresh();
        atualizarEstatisticas();
        
        if (pedidosObservable.isEmpty()) {
            detalhesArea.setText("Nenhum pedido registrado ainda.\n\nOs pedidos finalizados aparecerão aqui.");
        }
        
        mostrarAlerta(AlertType.INFORMATION, "Lista Atualizada", 
                     "Atualização concluída", 
                     String.format("Total de pedidos: %d", pedidosObservable.size()));
    }

    private void atualizarEstatisticas() {
        labelTotalPedidos.setText(String.valueOf(gerenciador.getTotalPedidos()));
        labelValorTotal.setText(String.format("R$ %.2f", gerenciador.getValorTotalPedidos()));
    }

    @FXML
    private void voltarMenu() {
        try {
            App.setRoot("primary");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarAlerta(AlertType.ERROR, "Erro", 
                         "Erro ao voltar ao menu", 
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