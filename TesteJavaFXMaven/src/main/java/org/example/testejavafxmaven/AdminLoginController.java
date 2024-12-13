package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class AdminLoginController implements Main.StageAwareController {

    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;

    private Stage stage;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadScene(String fxmlPath, String title) {
        if (stage == null) {
            System.err.println("Erro crítico: o Stage não foi configurado no controlador antes de carregar a cena.");
            throw new IllegalStateException("Stage não configurado no controlador.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            // Configura o Stage no controlador da próxima cena
            Object controller = loader.getController();
            if (controller instanceof Main.StageAwareController) {
                ((Main.StageAwareController) controller).setStage(stage);
            }

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }

    @FXML
    public void validarCredenciais() {
        String username = usernameField.getText();
        String password = passwordField.getText();

        UtilizadorDAO utilizadorDAO = new UtilizadorBD();
        Utilizador utilizador = utilizadorDAO.buscarPorEmail(username);

        if (utilizador != null && utilizador.getSenha().equals(password)) {
            if (utilizador instanceof Admin) {
                loadScene("/org/example/testejavafxmaven/admin_panel.fxml", "Painel do Administrador");
            } else {
                exibirMensagemErro("O usuário não tem permissões de administrador.");
            }
        } else {
            exibirMensagemErro("Usuário ou senha inválidos. Tente novamente.");
        }
    }

    @FXML
    public void voltar() {
        loadScene("/org/example/testejavafxmaven/Login.fxml", "Tela Inicial");
    }

    private void exibirMensagemErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }
}
