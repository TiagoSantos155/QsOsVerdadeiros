package view;

import dao.UtilizadorDAO;
import javafx.application.Application;
import javafx.geometry.Insets;
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
        VBox root = new VBox(10);
        root.setPadding(new Insets(20));

        // Campos de login
        Label lblEmail = new Label("E-mail:");
        TextField txtEmail = new TextField();
        txtEmail.setPromptText("Digite o e-mail");

        Label lblSenha = new Label("Senha:");
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Digite a senha");

        // Botão de login
        Button btnLogin = new Button("Login");

        // Botão de voltar
        Button btnVoltar = new Button("Voltar");
        btnVoltar.setOnAction(e -> stage.close());

        // Ação de login
        btnLogin.setOnAction(e -> {
            try (Connection conexao = DataBaseConnection.getConnection()) {
                UtilizadorDAO dao = new UtilizadorDAO(conexao);
                Utilizador utilizador = dao.buscarPorEmail(txtEmail.getText());
                if (utilizador != null && utilizador.getSenha().equals(txtSenha.getText())) {
                    if (utilizador.getTipo().equals(tipoUtilizador)) {
                        Alert alert = new Alert(Alert.AlertType.INFORMATION, "Login bem-sucedido!", ButtonType.OK);
                        alert.showAndWait();
                        if (tipoUtilizador.equals("Administrador")) {
                            new GestaoUtilizadores().start(new Stage());
                        } else {
                            //new GestaoCurso(utilizador).start(new Stage());
                        }
                        stage.close();
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

        root.getChildren().addAll(lblEmail, txtEmail, lblSenha, txtSenha, btnLogin, btnVoltar);

        Scene scene = new Scene(root, 300, 250); // Ajustado para acomodar o botão de voltar
        stage.setScene(scene);
        stage.setTitle("Login - " + tipoUtilizador);
        stage.show();
    }

    private void mostrarErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.showAndWait();
    }
}
