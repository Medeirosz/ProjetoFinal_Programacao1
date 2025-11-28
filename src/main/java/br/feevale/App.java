package br.feevale;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import java.io.IOException;

public class App extends Application {
    private static Scene scene;
    private static Stage primaryStage;
    
    // ===== CONFIGURE O TAMANHO DA JANELA AQUI =====
    private static final double WINDOW_WIDTH = 800;   // Largura desejada
    private static final double WINDOW_HEIGHT = 1200;  // Altura desejada
    private static final boolean RESIZABLE = true;    // true = redimensionável, false = fixo
    // ==============================================
    
    @Override
    public void start(Stage stage) throws IOException {
        primaryStage = stage;
        
        // Carrega o FXML
        Parent root = loadFXML("primary");
        
        // Criação da cena com o FXML e o tamanho inicial
        scene = new Scene(root, WINDOW_WIDTH, WINDOW_HEIGHT);
        
        // IMPORTANTE: Adiciona o CSS manualmente
        String css = this.getClass().getResource("style.css").toExternalForm();
        scene.getStylesheets().add(css);
        
        // Configurações da janela
        stage.setScene(scene);
        stage.setTitle("Cafeteria Ragu - Totem de Autoatendimento");
        
        // Define se a janela pode ser redimensionada
        stage.setResizable(RESIZABLE);
        
        // Define tamanhos mínimos para manter proporção
        stage.setMinWidth(600);
        stage.setMinHeight(800);
        
        // Se não for redimensionável, fixa o tamanho
        if (!RESIZABLE) {
            stage.setMinWidth(WINDOW_WIDTH);
            stage.setMinHeight(WINDOW_HEIGHT);
            stage.setMaxWidth(WINDOW_WIDTH);
            stage.setMaxHeight(WINDOW_HEIGHT);
        }
        
        // Mostra a janela
        stage.show();
    }
    
    // Método para mudar a cena dinamicamente
    static void setRoot(String fxml) throws IOException {
        Parent root = loadFXML(fxml);
        scene.setRoot(root);
        
        // Reaplica o CSS quando trocar de tela
        String css = App.class.getResource("style.css").toExternalForm();
        if (!scene.getStylesheets().contains(css)) {
            scene.getStylesheets().add(css);
        }
    }
    
    // Método auxiliar para carregar o FXML
    private static Parent loadFXML(String fxml) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(App.class.getResource(fxml + ".fxml"));
        return fxmlLoader.load();
    }
    
    public static void main(String[] args) {
        launch();
    }
}