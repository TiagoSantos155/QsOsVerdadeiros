package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.IOException;

public class CoordinatorPanelController implements Main.StageAwareController {

    @FXML
    private Button btnListarCursos;

    @FXML
    private Button btnListarUC;

    @FXML
    private Button btnEditorCursos;

    @FXML
    private Button btnEditorUC;

    private Stage stage;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    private void loadScene(String fxmlPath, String title) {
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
    public void handleListarCursos() {
        loadScene("/org/example/testejavafxmaven/ListarCursos.fxml", "Listar Cursos");
    }

    @FXML
    public void handleListarUC() {
        loadScene("/org/example/testejavafxmaven/ListarUC.fxml", "Listar Unidades Curriculares");
    }

    @FXML
    public void handleEditorCursos() {
        loadScene("/org/example/testejavafxmaven/editor_cursos.fxml", "Editor de Cursos");
    }

    @FXML
    public void handleEditorUC() {
        loadScene("/org/example/testejavafxmaven/editor_uc.fxml", "Editor de UC's");
    }

    @FXML
    public void handleVoltar() {
        loadScene("/org/example/testejavafxmaven/Login.fxml", "Tela Inicial");
    }
}
