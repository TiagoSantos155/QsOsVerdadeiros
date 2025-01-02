package view;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Curso;
import service.CursoService;

import java.util.List;

public class CursoApp extends Application {
    private final CursoService cursoService = new CursoService();
    private TableView<Curso> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 10;");

        Label lblNome = new Label("Nome do Curso:");
        TextField txtNome = new TextField();
        Button btnSalvar = new Button("Salvar Curso");
        btnSalvar.setOnAction(e -> salvarCurso(txtNome.getText()));

        table = new TableView<>();
        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));
        table.getColumns().add(colNome);

        Button btnCarregar = new Button("Carregar Cursos");
        btnCarregar.setOnAction(e -> carregarCursos());

        root.getChildren().addAll(lblNome, txtNome, btnSalvar, table, btnCarregar);

        stage.setScene(new Scene(root, 400, 400));
        stage.setTitle("Gestão de Cursos");
        stage.show();
    }

    private void salvarCurso(String nome) {
        try {
            cursoService.salvarCurso(new Curso(nome, "Professor Exemplo"));
            Alert alert = new Alert(Alert.AlertType.INFORMATION, "Curso salvo com sucesso!", ButtonType.OK);
            alert.showAndWait();
        } catch (Exception e) {
            Alert alert = new Alert(Alert.AlertType.ERROR, e.getMessage(), ButtonType.OK);
            alert.showAndWait();
        }
    }

    private void carregarCursos() {
        List<Curso> cursos = cursoService.listarCursos();
        table.getItems().setAll(cursos);
    }
}
