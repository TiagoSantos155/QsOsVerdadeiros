package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class AdminDashboard extends Application {
    @Override
    public void start(Stage stage) {
        // Layout principal
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER); // Centralizar elementos

        // Título da Dashboard
        Label lblTitulo = new Label("Painel do Administrador");
        lblTitulo.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-text-fill: #2a73cc;");

        // Botão para Gestão de Utilizadores
        Button btnGestaoUtilizadores = new Button("Gerir Utilizadores");
        btnGestaoUtilizadores.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnGestaoUtilizadores.setOnMouseEntered(e -> btnGestaoUtilizadores.setStyle("-fx-background-color: #205b99; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnGestaoUtilizadores.setOnMouseExited(e -> btnGestaoUtilizadores.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnGestaoUtilizadores.setOnAction(e -> {
            try {
                new GestaoUtilizadores().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Botão para Gestão de Épocas e Semestre
        Button btnGestaoEpocas = new Button("Gerir Épocas e Semestre");
        btnGestaoEpocas.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnGestaoEpocas.setOnMouseEntered(e -> btnGestaoEpocas.setStyle("-fx-background-color: #205b99; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnGestaoEpocas.setOnMouseExited(e -> btnGestaoEpocas.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnGestaoEpocas.setOnAction(e -> {
            try {
                // Substituir pela lógica correta para gerir épocas e semestres
                new GestaoEpocas().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Botão de Voltar para Tela Inicial
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnVoltar.setOnMouseEntered(e -> btnVoltar.setStyle("-fx-background-color: #c82333; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnVoltar.setOnMouseExited(e -> btnVoltar.setStyle("-fx-background-color: #dc3545; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnVoltar.setOnAction(e -> {
            try {
                new TelaInicial().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        // Adicionando componentes ao layout
        root.getChildren().addAll(lblTitulo, btnGestaoUtilizadores, btnGestaoEpocas, btnVoltar);

        // Configuração da cena
        Scene scene = new Scene(root, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/org/styles/adminDashboard.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Administrador - Dashboard");
        stage.show();
    }
}
