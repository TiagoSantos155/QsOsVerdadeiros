package view;

import dao.UtilizadorDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import modelo.Utilizador;
import org.example.testejavafxmaven.DataBaseConnection;
import service.UtilizadorService;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public class GestaoUtilizadores extends Application {
    private TableView<Utilizador> table;
    private UtilizadorService utilizadorService;
    private ObservableList<Utilizador> listaUtilizadores;

    @Override
    public void start(Stage stage) {
        // Inicializar Serviço
        Connection conexao = DataBaseConnection.getConnection();
        utilizadorService = new UtilizadorService(conexao);

        // Configurar Tabela
        table = new TableView<>();
        TableColumn<Utilizador, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Utilizador, String> colNome = new TableColumn<>("Nome");
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        TableColumn<Utilizador, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Utilizador, String> colEmail = new TableColumn<>("E-mail");
        colEmail.setCellValueFactory(new PropertyValueFactory<>("email"));

        table.getColumns().addAll(colId, colNome, colTipo, colEmail);
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);

        // Carregar Dados na Tabela
        carregarDados();

        // Label Título
        Label lblTitulo = new Label("Gestão de Utilizadores");
        lblTitulo.getStyleClass().add("titulo-label");

        // Botões de Ação
        Button btnAdicionar = new Button("Adicionar");
        btnAdicionar.getStyleClass().add("botao-adicionar");
        btnAdicionar.setOnAction(e -> abrirFormulario(null));

        Button btnEditar = new Button("Editar");
        btnEditar.getStyleClass().add("botao-editar");
        btnEditar.setOnAction(e -> {
            Utilizador selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                abrirFormulario(selecionado);
            } else {
                mostrarErro("Seleção Inválida", "Nenhum utilizador selecionado para editar.");
            }
        });

        Button btnExcluir = new Button("Deletar");
        btnExcluir.getStyleClass().add("botao-deletar");
        btnExcluir.setOnAction(e -> {
            Utilizador selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                excluirUtilizador(selecionado);
            } else {
                mostrarErro("Seleção Inválida", "Nenhum utilizador selecionado para excluir.");
            }
        });

        VBox botoes = new VBox(10, btnAdicionar, btnEditar, btnExcluir);
        botoes.setPadding(new Insets(10));

        // Layout Principal
        VBox root = new VBox(10, lblTitulo, table, botoes);
        root.setPadding(new Insets(10));

        Scene scene = new Scene(root, 800, 500);
        scene.getStylesheets().add(getClass().getResource("/org/styles/gestaoUtilizadores.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Gestão de Utilizadores");
        stage.show();
    }

    private void carregarDados() {
        try {
            listaUtilizadores = FXCollections.observableArrayList(utilizadorService.listarUtilizadores());
            table.setItems(listaUtilizadores);
        } catch (SQLException e) {
            mostrarErro("Erro ao Carregar Dados", e.getMessage());
        }
    }

    private void abrirFormulario(Utilizador utilizador) {
        Stage formularioStage = new Stage();
        formularioStage.setTitle(utilizador == null ? "Adicionar Utilizador" : "Editar Utilizador");

        // Campos do Formulário
        Label lblTitulo = new Label(utilizador == null ? "Adicionar Utilizador" : "Editar Utilizador");
        lblTitulo.getStyleClass().add("titulo-label");

        TextField txtNome = new TextField();
        txtNome.getStyleClass().add("text-field");
        txtNome.setPromptText("Nome");

        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.getStyleClass().add("text-field");
        cmbTipo.getItems().addAll("Administrador", "Coordenador");
        cmbTipo.setPromptText("Tipo");

        TextField txtEmail = new TextField();
        txtEmail.getStyleClass().add("text-field");
        txtEmail.setPromptText("E-mail");

        PasswordField txtSenha = new PasswordField();
        txtSenha.getStyleClass().add("text-field");
        txtSenha.setPromptText("Senha");

        if (utilizador != null) {
            txtNome.setText(utilizador.getNome());
            cmbTipo.setValue(utilizador.getTipo());
            txtEmail.setText(utilizador.getEmail());
            txtSenha.setText(utilizador.getSenha()); // Nota: Em produção, normalmente não exibe a senha
        }

        Button btnSalvar = new Button("Salvar");
        btnSalvar.getStyleClass().add("botao-adicionar");
        btnSalvar.setOnAction(e -> {
            String nome = txtNome.getText().trim();
            String tipo = cmbTipo.getValue();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();

            // Validações
            if (nome.isEmpty() || tipo == null || email.isEmpty() || senha.isEmpty()) {
                mostrarErro("Validação", "Todos os campos devem ser preenchidos.");
                return;
            }

            try {
                if (utilizador == null) {
                    // Adicionar novo utilizador
                    utilizadorService.salvarUtilizador(nome, tipo, email, senha);
                    mostrarAlerta("Sucesso", "Utilizador adicionado com sucesso.");
                } else {
                    // Atualizar utilizador existente
                    utilizadorService.atualizarUtilizador(utilizador.getId(), nome, tipo, email, senha);
                    mostrarAlerta("Sucesso", "Utilizador atualizado com sucesso.");
                }
                carregarDados();
                formularioStage.close();
            } catch (SQLException ex) {
                mostrarErro("Erro de Banco de Dados", ex.getMessage());
            }
        });

        VBox formulario = new VBox(10, lblTitulo, new Label("Nome:"), txtNome,
                new Label("Tipo:"), cmbTipo,
                new Label("E-mail:"), txtEmail,
                new Label("Senha:"), txtSenha,
                btnSalvar);
        formulario.setPadding(new Insets(10));

        Scene scene = new Scene(formulario, 300, 400);
        formularioStage.setScene(scene);
        formularioStage.show();
    }

    private void excluirUtilizador(Utilizador utilizador) {
        Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
        confirm.setTitle("Confirmação de Exclusão");
        confirm.setHeaderText(null);
        confirm.setContentText("Tem certeza de que deseja excluir o utilizador: " + utilizador.getNome() + "?");

        Optional<ButtonType> resultado = confirm.showAndWait();
        if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
            try {
                utilizadorService.deletarUtilizador(utilizador.getId());
                mostrarAlerta("Sucesso", "Utilizador excluído com sucesso.");
                carregarDados();
            } catch (SQLException e) {
                mostrarErro("Erro ao Excluir", e.getMessage());
            }
        }
    }

    private void mostrarErro(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }

    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION, mensagem, ButtonType.OK);
        alert.setTitle(titulo);
        alert.showAndWait();
    }
}
