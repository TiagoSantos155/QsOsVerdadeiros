package org.example.testejavafxmaven;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class MainController {
    private Stage stage;

    public void initialize(Stage stage) {
        this.stage = stage;
    }

    public void showAdminPanel() {
        loadScene("/org/example/testejavafxmaven/admin_panel.fxml", "Painel do Administrador");
    }

    public void showCoordinatorPanel() {
        loadScene("/org/example/testejavafxmaven/coordinator_panel.fxml", "Painel do Coordenador");
    }

    private void loadScene(String fxmlPath, String title) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());
            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}

