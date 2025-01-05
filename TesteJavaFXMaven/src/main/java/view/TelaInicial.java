package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaInicial extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER); // Centralizar os elementos

        // Título da tela
        Label lblTitulo = new Label("Escolha o Tipo de Utilizador");
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2a73cc;");

        // Botões para escolher o tipo de utilizador
        Button btnAdmin = new Button("Administrador");
        btnAdmin.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnAdmin.setOnAction(e -> {
            stage.close(); // Fecha a janela atual
            new TelaLogin("Administrador").start(new Stage());
        });

        Button btnCoordenador = new Button("Coordenador");
        btnCoordenador.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;");
        btnCoordenador.setOnAction(e -> {
            stage.close(); // Fecha a janela atual
            new TelaLogin("Coordenador").start(new Stage());
        });
        // Adicionando animações simples
        btnAdmin.setOnMouseEntered(e -> btnAdmin.setStyle("-fx-background-color: #205b99; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;"));
        btnAdmin.setOnMouseExited(e -> btnAdmin.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;"));
        btnCoordenador.setOnMouseEntered(e -> btnCoordenador.setStyle("-fx-background-color: #218838; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;"));
        btnCoordenador.setOnMouseExited(e -> btnCoordenador.setStyle("-fx-background-color: #28a745; -fx-text-fill: white; -fx-font-size: 14px; -fx-padding: 10 20;"));

        // Adicionando componentes ao layout
        root.getChildren().addAll(lblTitulo, btnAdmin, btnCoordenador);

        // Configurando a cena
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/org/styles/telaInicial.css").toExternalForm()); // Caso deseje adicionar estilos adicionais
        stage.setScene(scene);
        stage.setTitle("Escolha o Tipo de Utilizador");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
