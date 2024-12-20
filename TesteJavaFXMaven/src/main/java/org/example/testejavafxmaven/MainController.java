package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Stage;

import java.io.IOException;

public class MainController implements Main.StageAwareController {

    private Stage stage;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadScene(String fxmlPath, String title) {
        if (this.stage == null) {
            System.err.println("Erro crítico: o Stage não foi configurado no controlador antes de carregar a cena.");
            throw new IllegalStateException("Stage não configurado no controlador.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

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
    public void showAdminPanel() {
        loadScene("/org/example/testejavafxmaven/AdminLogin.fxml", "Painel de Administração");
    }

    @FXML
    public void showCoordinatorPanel() {
        loadScene("/org/example/testejavafxmaven/coordinatorLogin.fxml", "Painel do Coordenador");
    }

    @FXML
    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Tem certeza que deseja sair?");
        alert.setContentText("Você será desconectado do sistema.");

        if (alert.showAndWait().get() == ButtonType.OK) {
            System.exit(0);
        }
    }
}
