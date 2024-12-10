package org.example.testejavafxmaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    public interface StageAwareController {
        void setStage(Stage stage);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Object controller = fxmlLoader.getController();
            if (controller instanceof StageAwareController) {
                ((StageAwareController) controller).setStage(primaryStage);
            } else {
                System.err.println("Erro: O controlador não implementa StageAwareController.");
            }

            primaryStage.setTitle("Sistema de Gestão Acadêmica");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo FXML principal.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
