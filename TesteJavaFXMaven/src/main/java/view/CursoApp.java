package view;


import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Curso;
import org.example.testejavafxmaven.DataBaseConnection;
import service.CursoService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;

public class CursoApp extends Application {
    private CursoService cursoService;
    private TableView<Curso> table;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        Connection conexao = DataBaseConnection.getConnection();
        cursoService = new CursoService(conexao);

        VBox root = new VBox(10);
        root.setStyle("-fx-padding: 10;");

        Label lblNome = new Label("Nome do Curso:");
        TextField txtNome = new TextField();

        Label lblNumeroAlunos = new Label("Número de Alunos:");
        TextField txtNumeroAlunos = new TextField();

        Button btnSalvar = new Button("Salvar Curso");
        btnSalvar.setOnAction(e -> {
            try {
                salvarCurso(txtNome.getText(), Integer.parseInt(txtNumeroAlunos.getText()));
                carregarCursos();
                limparCampos(txtNome, txtNumeroAlunos);
            } catch (Exception ex) {
                mostrarAlerta("Erro ao salvar", ex.getMessage());
            }
        });

        table = new TableView<>();
        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Curso, Integer> colNumeroAlunos = new TableColumn<>("Número de Alunos");
        colNumeroAlunos.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumeroAlunos()).asObject());

        table.getColumns().addAll(colNome, colNumeroAlunos);

        Button btnCarregar = new Button("Carregar Cursos");
        btnCarregar.setOnAction(e -> carregarCursos());

        root.getChildren().addAll(lblNome, txtNome, lblNumeroAlunos, txtNumeroAlunos, btnSalvar, table, btnCarregar);

        stage.setScene(new Scene(root, 600, 400));
        stage.setTitle("Gestão de Cursos");
        stage.show();

        carregarCursos();
    }

    private void salvarCurso(String nome, int numeroAlunos) throws SQLException {
        cursoService.salvarCurso(nome, numeroAlunos);
        mostrarAlerta("Sucesso", "Curso salvo com sucesso.");
    }

    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoService.listarCursos();
            table.getItems().setAll(cursos);
        } catch (SQLException e) {
            mostrarAlerta("Erro ao carregar", e.getMessage());
        }
    }

    private void limparCampos(TextField txtNome, TextField txtNumeroAlunos) {
        txtNome.clear();
        txtNumeroAlunos.clear();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
}
