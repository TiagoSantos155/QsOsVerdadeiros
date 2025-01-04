package view;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
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
    private TextField txtNome;
    private TextField txtNumeroAlunos;

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) {
        // Conectar ao banco de dados
        Connection conexao = DataBaseConnection.getConnection();
        cursoService = new CursoService(conexao);

        // Layout principal
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        // Cabeçalho
        Label lblTitulo = new Label("Gestão de Cursos");
        lblTitulo.getStyleClass().add("titulo-label");
        VBox header = new VBox(lblTitulo);
        header.setAlignment(Pos.CENTER);

        // Formulário
        VBox formulario = new VBox(10);
        formulario.setPadding(new Insets(10));
        formulario.getStyleClass().add("formulario-box");

        Label lblNome = new Label("Nome do Curso:");
        txtNome = new TextField();
        txtNome.setPromptText("Digite o nome do curso");
        txtNome.getStyleClass().add("text-field");

        Label lblNumeroAlunos = new Label("Número de Alunos:");
        txtNumeroAlunos = new TextField();
        txtNumeroAlunos.setPromptText("Digite o número de alunos");
        txtNumeroAlunos.getStyleClass().add("text-field");

        Button btnSalvar = new Button("Salvar Curso");
        btnSalvar.getStyleClass().add("botao-salvar");
        btnSalvar.setOnAction(e -> salvarCurso());

        formulario.getChildren().addAll(lblNome, txtNome, lblNumeroAlunos, txtNumeroAlunos, btnSalvar);

        // Tabela
        table = new TableView<>();
        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new javafx.beans.property.SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Curso, Integer> colNumeroAlunos = new TableColumn<>("Número de Alunos");
        colNumeroAlunos.setCellValueFactory(data -> new javafx.beans.property.SimpleIntegerProperty(data.getValue().getNumeroAlunos()).asObject());

        table.getColumns().addAll(colNome, colNumeroAlunos);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        table.getStyleClass().add("table-view");

        // Botões de ação
        HBox botoes = new HBox(10);
        botoes.setAlignment(Pos.CENTER);
        botoes.setPadding(new Insets(10));

        Button btnEditar = new Button("Editar Curso");
        btnEditar.getStyleClass().add("botao-editar");
        btnEditar.setOnAction(e -> carregarCursoParaEdicao());

        Button btnDeletar = new Button("Deletar Curso");
        btnDeletar.getStyleClass().add("botao-deletar");
        btnDeletar.setOnAction(e -> deletarCurso());

        Button btnCarregar = new Button("Carregar Cursos");
        btnCarregar.getStyleClass().add("botao-carregar");
        btnCarregar.setOnAction(e -> carregarCursos());

        botoes.getChildren().addAll(btnEditar, btnDeletar, btnCarregar);

        // Layout final
        root.setTop(header);
        root.setLeft(formulario);
        root.setCenter(table);
        root.setBottom(botoes);

        // Configurar cena
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/org/cursoStyles/cursoStyles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Gestão de Cursos");
        stage.show();

        // Carregar cursos no início
        carregarCursos();
    }

    private void salvarCurso() {
        try {
            String nome = txtNome.getText();
            int numeroAlunos = Integer.parseInt(txtNumeroAlunos.getText());
            Curso cursoSelecionado = table.getSelectionModel().getSelectedItem();

            if (cursoSelecionado != null) {
                cursoService.atualizarCurso(cursoSelecionado.getId(), nome, numeroAlunos);
                mostrarAlerta("Sucesso", "Curso atualizado com sucesso.");
            } else {
                cursoService.salvarCurso(nome, numeroAlunos);
                mostrarAlerta("Sucesso", "Curso salvo com sucesso.");
            }

            carregarCursos();
            limparCampos();
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro", "Número de alunos deve ser um número válido.");
        } catch (Exception e) {
            mostrarAlerta("Erro", e.getMessage());
        }
    }

    private void carregarCursoParaEdicao() {
        Curso cursoSelecionado = table.getSelectionModel().getSelectedItem();
        if (cursoSelecionado != null) {
            txtNome.setText(cursoSelecionado.getNome());
            txtNumeroAlunos.setText(String.valueOf(cursoSelecionado.getNumeroAlunos()));
        } else {
            mostrarAlerta("Seleção Inválida", "Nenhum curso foi selecionado para edição.");
        }
    }

    private void deletarCurso() {
        Curso cursoSelecionado = table.getSelectionModel().getSelectedItem();
        if (cursoSelecionado != null) {
            try {
                cursoService.deletarCurso(cursoSelecionado.getId());
                mostrarAlerta("Sucesso", "Curso deletado com sucesso.");
                carregarCursos();
            } catch (SQLException e) {
                mostrarAlerta("Erro ao Deletar", e.getMessage());
            }
        } else {
            mostrarAlerta("Seleção Inválida", "Nenhum curso foi selecionado para exclusão.");
        }
    }

    private void carregarCursos() {
        try {
            List<Curso> cursos = cursoService.listarCursos();
            table.getItems().setAll(cursos);
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Carregar Cursos", e.getMessage());
        }
    }

    private void limparCampos() {
        txtNome.clear();
        txtNumeroAlunos.clear();
        table.getSelectionModel().clearSelection();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
}
