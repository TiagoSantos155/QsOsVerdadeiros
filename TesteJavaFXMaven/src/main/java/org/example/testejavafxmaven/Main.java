package org.example.testejavafxmaven;


import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        URL fxmlLocation = getClass().getResource("/org/example/testejavafxmaven/Login.fxml");
        System.out.println("FXML Location: " + fxmlLocation);
        FXMLLoader fxmlLoader = new FXMLLoader(fxmlLocation);
        Scene scene = new Scene(fxmlLoader.load());

        // Passar o stage para o controlador principal
        MainController mainController = fxmlLoader.getController();
        mainController.initialize(primaryStage);

        primaryStage.setTitle("Sistema de Gestão Acadêmica");
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}

