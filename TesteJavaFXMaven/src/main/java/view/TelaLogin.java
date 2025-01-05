package view;

import dao.UtilizadorDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Utilizador;
import org.example.testejavafxmaven.DataBaseConnection;

import java.sql.Connection;

public class TelaLogin extends Application {
    private final String tipoUtilizador;

    public TelaLogin(String tipoUtilizador) {
        this.tipoUtilizador = tipoUtilizador;
    }

    @Override
    public void start(Stage stage) {
        // Configuração do layout
        VBox root = new VBox(15);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER); // Centralizar os elementos

        // Título da tela
        Label lblTitulo = new Label("Login - " + tipoUtilizador);
        lblTitulo.setStyle("-fx-font-size: 20px; -fx-font-weight: bold; -fx-text-fill: #2a73cc;");

        // Campos de login
        Label lblEmail = new Label("E-mail:");
        lblEmail.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Digite o e-mail");
        txtEmail.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");

        Label lblSenha = new Label("Senha:");
        lblSenha.setStyle("-fx-font-size: 14px; -fx-font-weight: bold;");

        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite a senha");
        txtSenha.setStyle("-fx-padding: 10; -fx-border-radius: 5; -fx-background-radius: 5;");

        // Botão de login
        Button btnLogin = new Button("Login");
        btnLogin.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;");
        btnLogin.setOnMouseEntered(e -> btnLogin.setStyle("-fx-background-color: #205b99; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));
        btnLogin.setOnMouseExited(e -> btnLogin.setStyle("-fx-background-color: #2a73cc; -fx-text-fill: white; -fx-font-weight: bold; -fx-padding: 10 20;"));

        // Botão de voltar
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

        // Ação de login
        btnLogin.setOnAction(e -> {
            try (Connection conexao = DataBaseConnection.getConnection()) {
                UtilizadorDAO dao = new UtilizadorDAO(conexao);
                Utilizador utilizador = dao.buscarPorEmail(txtEmail.getText());
                if (utilizador != null && utilizador.getSenha().equals(txtSenha.getText())) {
                    if (utilizador.getTipo().equalsIgnoreCase(tipoUtilizador)) {
                        if (tipoUtilizador.equalsIgnoreCase("Administrador")) {
                            new AdminDashboard().start(stage);
                        } else if (tipoUtilizador.equalsIgnoreCase("Coordenador")) {
                            // Substitua por a lógica para coordenador
                        }
                    } else {
                        mostrarErro("Acesso negado para este tipo de utilizador.");
                    }
                } else {
                    mostrarErro("E-mail ou senha incorretos.");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                mostrarErro("Erro ao conectar ao banco de dados.");
            }
        });

        // Adicionando componentes ao layout
        root.getChildren().addAll(lblTitulo, lblEmail, txtEmail, lblSenha, txtSenha, btnLogin, btnVoltar);

        // Configuração da cena
        Scene scene = new Scene(root, 350, 400);
        scene.getStylesheets().add(getClass().getResource("/org/styles/telaLogin.css").toExternalForm()); // Adicione estilos extras se desejar
        stage.setScene(scene);
        stage.setTitle("Login - " + tipoUtilizador);
        stage.show();
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.showAndWait();
    }
}
