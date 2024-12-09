package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController {

    @FXML private TextField usernameField;
    @FXML private PasswordField passwordField;

    // Credenciais fictícias para validação (em um sistema real, use uma base de dados ou outro método seguro)
    private static final String ADMIN_USERNAME = "admin";
    private static final String ADMIN_PASSWORD = "1234";

    @FXML
    public void validarCredenciais() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        if (ADMIN_USERNAME.equals(username) && ADMIN_PASSWORD.equals(password)) {
            try {
                // Carregar a próxima tela (AdminPanel.fxml)
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/AdminPanel.fxml"));
                Scene scene = new Scene(loader.load());

                // Obter o Stage atual e definir a nova cena
                Stage stage = (Stage) usernameField.getScene().getWindow();
                stage.setScene(scene);
            } catch (IOException e) {
                exibirMensagemErro("Erro ao carregar o painel de administrador.");
                e.printStackTrace();
            }
        } else {
            exibirMensagemErro("Usuário ou senha inválidos. Tente novamente.");
        }
    }

    @FXML
    public void voltar() {
        try {
            // Carregar a página anterior (Menu ou Tela de Escolha)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o Stage atual e definir a nova cena
            Stage stage = (Stage) usernameField.getScene().getWindow();
            stage.setScene(scene);
        } catch (IOException e) {
            exibirMensagemErro("Erro ao voltar para a tela inicial.");
            e.printStackTrace();
        }
    }

    private void exibirMensagemErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
