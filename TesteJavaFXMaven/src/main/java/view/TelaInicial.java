package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class TelaInicial extends Application {

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(20);
        root.setPadding(new Insets(20));

        // Botões para escolher o tipo de utilizador
        Button btnAdmin = new Button("Administrador");
        btnAdmin.setOnAction(e -> new TelaLogin("Administrador").start(new Stage()));

        Button btnCoordenador = new Button("Coordenador");
        btnCoordenador.setOnAction(e -> new TelaLogin("Coordenador").start(new Stage()));

        root.getChildren().addAll(btnAdmin, btnCoordenador);

        Scene scene = new Scene(root, 300, 200);
        stage.setScene(scene);
        stage.setTitle("Escolha o Tipo de Utilizador");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
