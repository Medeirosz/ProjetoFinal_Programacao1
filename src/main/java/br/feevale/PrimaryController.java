package br.feevale;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextInputDialog;
import java.util.Optional;

public class PrimaryController {

    @FXML
    private Button bStartOrder;
    
    @FXML
    private Button bAdmin;

    @FXML
    private void switchToSecondary() {
        try {
            App.setRoot("secondary");
        } catch (IOException e) {
            e.printStackTrace();
            mostrarErro("Erro ao carregar tela de pedidos");
        }
    }
    
    @FXML
    private void abrirAdmin() {
        // Solicita senha
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Acesso Administrativo");
        dialog.setHeaderText("Área Restrita");
        dialog.setContentText("Digite a senha:");
        
        Optional<String> result = dialog.showAndWait();
        result.ifPresent(senha -> {
            if (senha.equals("123")) {
                try {
                    App.setRoot("admin");
                } catch (IOException e) {
                    e.printStackTrace();
                    mostrarErro("Erro ao carregar painel administrativo");
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Acesso Negado");
                alert.setHeaderText("Senha Incorreta");
                alert.setContentText("A senha digitada está incorreta!");
                alert.showAndWait();
            }
        });
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(mensagem);
        alert.showAndWait();
    }
}