package view;

import javafx.application.Application;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
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
import java.util.stream.Collectors;

public class CursoApp extends Application {
    private CursoService cursoService;
    private TableView<Curso> table;
    private TextField txtNome;
    private TextField txtNumeroAlunos;
    private TextField txtFiltro; // Campo de busca

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
        Label lblTitulo = criarTitulo("Gestão de Cursos");
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
        btnSalvar.setOnAction(e -> {
            if (validarCampos()) salvarCurso();
        });

        formulario.getChildren().addAll(lblNome, txtNome, lblNumeroAlunos, txtNumeroAlunos, btnSalvar);

        // Campo de busca
        HBox buscaBox = new HBox(10);
        buscaBox.setPadding(new Insets(10));
        buscaBox.setAlignment(Pos.CENTER);

        txtFiltro = new TextField();
        txtFiltro.setPromptText("Buscar cursos...");
        txtFiltro.getStyleClass().add("text-field");
        txtFiltro.textProperty().addListener((obs, oldVal, newVal) -> filtrarCursos(newVal));

        buscaBox.getChildren().add(txtFiltro);

        // Tabela
        table = new TableView<>();
        TableColumn<Curso, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(data -> new SimpleStringProperty(data.getValue().getNome()));

        TableColumn<Curso, Integer> colNumeroAlunos = new TableColumn<>("Número de Alunos");
        colNumeroAlunos.setCellValueFactory(data -> new SimpleIntegerProperty(data.getValue().getNumeroAlunos()).asObject());

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
        btnDeletar.setOnAction(e -> {
            if (confirmarAcao("Tem certeza de que deseja excluir este curso?")) deletarCurso();
        });

        Button btnCarregar = new Button("Carregar Cursos");
        btnCarregar.getStyleClass().add("botao-carregar");
        btnCarregar.setOnAction(e -> carregarCursos());

        botoes.getChildren().addAll(btnEditar, btnDeletar, btnCarregar);

        // Layout final
        root.setTop(header);
        root.setLeft(formulario);
        root.setCenter(table);
        root.setBottom(botoes);
        root.setTop(buscaBox);

        // Configurar cena
        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/org/Styles/cursoStyles.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Gestão de Cursos");
        stage.show();

        // Carregar cursos no início
        carregarCursos();
    }

    private Label criarTitulo(String texto) {
        Label titulo = new Label(texto);
        titulo.getStyleClass().add("titulo-label");
        return titulo;
    }

    private boolean validarCampos() {
        if (txtNome.getText().isEmpty()) {
            mostrarAlerta("Erro de Validação", "O nome do curso não pode estar vazio.");
            return false;
        }
        try {
            int numeroAlunos = Integer.parseInt(txtNumeroAlunos.getText());
            if (numeroAlunos < 0) {
                mostrarAlerta("Erro de Validação", "O número de alunos deve ser um valor positivo.");
                return false;
            }
        } catch (NumberFormatException e) {
            mostrarAlerta("Erro de Validação", "Número de alunos deve ser um número válido.");
            return false;
        }
        return true;
    }

    private boolean confirmarAcao(String mensagem) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION, mensagem, ButtonType.YES, ButtonType.NO);
        confirm.setTitle("Confirmação");
        confirm.showAndWait();
        return confirm.getResult() == ButtonType.YES;
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

    private void filtrarCursos(String filtro) {
        try {
            List<Curso> cursos = cursoService.listarCursos();
            table.getItems().setAll(
                    cursos.stream()
                            .filter(curso -> curso.getNome().toLowerCase().contains(filtro.toLowerCase()))
                            .collect(Collectors.toList())
            );
        } catch (SQLException e) {
            mostrarAlerta("Erro ao Filtrar Cursos", e.getMessage());
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
