package view;

import dao.UtilizadorDAO;
import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.*;
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
        table.getStyleClass().add("table-view");

        // Carregar Dados na Tabela
        carregarDados();

        // Label Título
        Label lblTitulo = new Label("Gestão de Utilizadores");
        lblTitulo.getStyleClass().add("titulo-label");

        // Botões de Ação
        Button btnAdicionar = criarBotao("Adicionar", "botao-adicionar", e -> abrirFormulario(null));
        Button btnEditar = criarBotao("Editar", "botao-editar", e -> {
            Utilizador selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                abrirFormulario(selecionado);
            } else {
                mostrarErro("Seleção Inválida", "Nenhum utilizador selecionado para editar.");
            }
        });
        Button btnExcluir = criarBotao("Deletar", "botao-deletar", e -> {
            Utilizador selecionado = table.getSelectionModel().getSelectedItem();
            if (selecionado != null) {
                excluirUtilizador(selecionado);
            } else {
                mostrarErro("Seleção Inválida", "Nenhum utilizador selecionado para excluir.");
            }
        });
        Button btnVoltar = criarBotao("Voltar", "botao-voltar", e -> {
            try {
                new AdminDashboard().start(stage);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        });

        HBox botoes = new HBox(10, btnAdicionar, btnEditar, btnExcluir, btnVoltar);
        botoes.setAlignment(Pos.CENTER);
        botoes.setPadding(new Insets(10));

        // Layout Principal
        VBox root = new VBox(15, lblTitulo, table, botoes);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        Scene scene = new Scene(root, 900, 600);
        scene.getStylesheets().add(getClass().getResource("/org/styles/gestaoUtilizadores.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Gestão de Utilizadores");
        stage.show();
    }

    private Button criarBotao(String texto, String estilo, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button botao = new Button(texto);
        botao.getStyleClass().add(estilo);
        botao.setOnAction(acao);
        return botao;
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

        // Título estilizado
        Label lblTitulo = new Label(utilizador == null ? "Adicionar Utilizador" : "Editar Utilizador");
        lblTitulo.getStyleClass().add("form-container-title");

        // Campos do formulário estilizados
        TextField txtNome = criarCampoTexto("Nome", utilizador == null ? "" : utilizador.getNome());

        ComboBox<String> cmbTipo = new ComboBox<>(FXCollections.observableArrayList("Administrador", "Coordenador"));
        cmbTipo.setPromptText("Tipo Utilizador");
        cmbTipo.setValue(utilizador == null ? null : utilizador.getTipo());

        TextField txtEmail = criarCampoTexto("E-mail", utilizador == null ? "" : utilizador.getEmail());
        PasswordField txtSenha = new PasswordField();
        txtSenha.setPromptText("Senha");
        if (utilizador != null) txtSenha.setText(utilizador.getSenha());

        // Botão salvar estilizado
        Button btnSalvar = criarBotao("Salvar", "form-button", e -> {
            String nome = txtNome.getText().trim();
            String tipo = cmbTipo.getValue();
            String email = txtEmail.getText().trim();
            String senha = txtSenha.getText().trim();

            if (nome.isEmpty() || tipo == null || email.isEmpty() || senha.isEmpty()) {
                mostrarErro("Validação", "Todos os campos devem ser preenchidos.");
                return;
            }

            try {
                if (utilizador == null) {
                    utilizadorService.salvarUtilizador(nome, tipo, email, senha);
                    mostrarAlerta("Sucesso", "Utilizador adicionado com sucesso.");
                } else {
                    utilizadorService.atualizarUtilizador(utilizador.getId(), nome, tipo, email, senha);
                    mostrarAlerta("Sucesso", "Utilizador atualizado com sucesso.");
                }
                carregarDados();
                formularioStage.close();
            } catch (SQLException ex) {
                mostrarErro("Erro de Banco de Dados", ex.getMessage());
            }
        });

        VBox formulario = new VBox(20, lblTitulo, txtNome, cmbTipo, txtEmail, txtSenha, btnSalvar);
        formulario.setPadding(new Insets(30));
        formulario.setAlignment(Pos.CENTER);
        formulario.getStyleClass().add("form-container");

        Scene scene = new Scene(formulario, 450, 500);
        scene.getStylesheets().add(getClass().getResource("/org/styles/formularioUtilizador.css").toExternalForm());
        formularioStage.setScene(scene);
        formularioStage.show();
    }

    private TextField criarCampoTexto(String prompt, String valorInicial) {
        TextField campo = new TextField(valorInicial);
        campo.setPromptText(prompt);
        campo.getStyleClass().add("form-input");
        return campo;
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
