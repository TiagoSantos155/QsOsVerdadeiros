package view;

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
import modelo.Epoca;
import modelo.Semestre;
import service.EpocaService;
import service.SemestreService;

import java.time.LocalDate;
import java.util.Optional;

public class GestaoEpocas extends Application {
    private TableView<Semestre> tabelaSemestres;
    private TableView<Epoca> tabelaEpocas;
    private ObservableList<Semestre> listaSemestres;
    private ObservableList<Epoca> listaEpocas;

    private final SemestreService semestreService = new SemestreService();
    private final EpocaService epocaService = new EpocaService();

    @Override
    public void start(Stage stage) {
        // Tabelas
        tabelaSemestres = criarTabelaSemestres();
        tabelaEpocas = criarTabelaEpocas();

        // Carregar Dados
        carregarDados();

        // Título
        Label lblTitulo = new Label("Gestão de Semestres e Épocas");
        lblTitulo.getStyleClass().add("titulo-label");

        // Botões de ação para semestres
        Button btnAdicionarSemestre = criarBotao("Adicionar Semestre", "botao-adicionar", e -> abrirFormularioSemestre(null));
        Button btnEditarSemestre = criarBotao("Editar Semestre", "botao-editar", e -> editarSemestre());
        Button btnExcluirSemestre = criarBotao("Excluir Semestre", "botao-deletar", e -> excluirSemestre());

        HBox botoesSemestres = new HBox(10, btnAdicionarSemestre, btnEditarSemestre, btnExcluirSemestre);
        botoesSemestres.setAlignment(Pos.CENTER);

        VBox semestresSection = new VBox(10, new Label("Semestres"), tabelaSemestres, botoesSemestres);
        semestresSection.setPadding(new Insets(10));

        // Botões de ação para épocas
        Button btnAdicionarEpoca = criarBotao("Adicionar Época", "botao-adicionar", e -> abrirFormularioEpoca(null));
        Button btnEditarEpoca = criarBotao("Editar Época", "botao-editar", e -> editarEpoca());
        Button btnExcluirEpoca = criarBotao("Excluir Época", "botao-deletar", e -> excluirEpoca());

        HBox botoesEpocas = new HBox(10, btnAdicionarEpoca, btnEditarEpoca, btnExcluirEpoca);
        botoesEpocas.setAlignment(Pos.CENTER);

        VBox epocasSection = new VBox(10, new Label("Épocas"), tabelaEpocas, botoesEpocas);
        epocasSection.setPadding(new Insets(10));

        // Layout principal
        VBox root = new VBox(15, lblTitulo, semestresSection, epocasSection);
        root.setPadding(new Insets(20));
        root.setAlignment(Pos.CENTER);

        // Cena e estilo
        Scene scene = new Scene(root, 800, 600);
        scene.getStylesheets().add(getClass().getResource("/org/styles/gestaoSemestresEpocas.css").toExternalForm());
        stage.setScene(scene);
        stage.setTitle("Gestão de Semestres e Épocas");
        stage.show();
    }

    private TableView<Semestre> criarTabelaSemestres() {
        TableView<Semestre> tabela = new TableView<>();
        TableColumn<Semestre, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Semestre, LocalDate> colInicio = new TableColumn<>("Início");
        colInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));

        TableColumn<Semestre, LocalDate> colFim = new TableColumn<>("Fim");
        colFim.setCellValueFactory(new PropertyValueFactory<>("fim"));

        tabela.getColumns().addAll(colId, colInicio, colFim);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tabela;
    }

    private TableView<Epoca> criarTabelaEpocas() {
        TableView<Epoca> tabela = new TableView<>();
        TableColumn<Epoca, Integer> colId = new TableColumn<>("ID");
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));

        TableColumn<Epoca, String> colTipo = new TableColumn<>("Tipo");
        colTipo.setCellValueFactory(new PropertyValueFactory<>("tipo"));

        TableColumn<Epoca, LocalDate> colInicio = new TableColumn<>("Início");
        colInicio.setCellValueFactory(new PropertyValueFactory<>("inicio"));

        TableColumn<Epoca, LocalDate> colFim = new TableColumn<>("Fim");
        colFim.setCellValueFactory(new PropertyValueFactory<>("fim"));

        TableColumn<Epoca, Integer> colSemestreId = new TableColumn<>("Semestre ID");
        colSemestreId.setCellValueFactory(new PropertyValueFactory<>("semestreId"));

        tabela.getColumns().addAll(colId, colTipo, colInicio, colFim, colSemestreId);
        tabela.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        return tabela;
    }

    private void carregarDados() {
        listaSemestres = FXCollections.observableArrayList(semestreService.listarSemestres());
        tabelaSemestres.setItems(listaSemestres);

        listaEpocas = FXCollections.observableArrayList(epocaService.listarEpocas());
        tabelaEpocas.setItems(listaEpocas);
    }

    private Button criarBotao(String texto, String estilo, javafx.event.EventHandler<javafx.event.ActionEvent> acao) {
        Button botao = new Button(texto);
        botao.getStyleClass().add(estilo);
        botao.setOnAction(acao);
        return botao;
    }

    private void abrirFormularioSemestre(Semestre semestre) {
        Stage formularioStage = new Stage();
        formularioStage.setTitle(semestre == null ? "Adicionar Semestre" : "Editar Semestre");

        Label lblTitulo = new Label(semestre == null ? "Adicionar Semestre" : "Editar Semestre");
        lblTitulo.getStyleClass().add("form-container-title");

        DatePicker dpInicio = new DatePicker(semestre == null ? null : semestre.getInicio());
        dpInicio.setPromptText("Data de Início");

        DatePicker dpFim = new DatePicker(semestre == null ? null : semestre.getFim());
        dpFim.setPromptText("Data de Fim");

        Button btnSalvar = criarBotao("Salvar", "form-button", e -> {
            LocalDate inicio = dpInicio.getValue();
            LocalDate fim = dpFim.getValue();

            if (inicio == null || fim == null) {
                mostrarErro("Validação", "Todas as datas devem ser preenchidas.");
                return;
            }

            if (!inicio.isBefore(fim)) {
                mostrarErro("Validação", "A data de início deve ser anterior à data de fim.");
                return;
            }

            try {
                if (semestre == null) {
                    semestreService.salvarSemestre(inicio, fim);
                    mostrarAlerta("Sucesso", "Semestre adicionado com sucesso.");
                } else {
                    semestreService.atualizarSemestre(semestre.getId(), inicio, fim);
                    mostrarAlerta("Sucesso", "Semestre atualizado com sucesso.");
                }
                carregarDados();
                formularioStage.close();
            } catch (Exception ex) {
                mostrarErro("Erro ao Salvar", ex.getMessage());
            }
        });

        VBox formulario = new VBox(15, lblTitulo, dpInicio, dpFim, btnSalvar);
        formulario.setPadding(new Insets(20));
        formulario.setAlignment(Pos.CENTER);
        formulario.getStyleClass().add("form-container");

        Scene scene = new Scene(formulario, 400, 300);
        scene.getStylesheets().add(getClass().getResource("/org/styles/gestaoSemestresEpocas.css").toExternalForm());
        formularioStage.setScene(scene);
        formularioStage.show();
    }

    private void abrirFormularioEpoca(Epoca epoca) {
        Stage formularioStage = new Stage();
        formularioStage.setTitle(epoca == null ? "Adicionar Época" : "Editar Época");

        Label lblTitulo = new Label(epoca == null ? "Adicionar Época" : "Editar Época");
        lblTitulo.getStyleClass().add("form-container-title");

        // Substituir TextField por ComboBox para o tipo de época
        ComboBox<String> cmbTipo = new ComboBox<>();
        cmbTipo.setPromptText("Tipo de Época");
        cmbTipo.getItems().addAll("Normal", "Recurso", "Especial");
        if (epoca != null) {
            cmbTipo.setValue(epoca.getTipo());
        }

        DatePicker dpInicio = new DatePicker(epoca == null ? null : epoca.getInicio());
        dpInicio.setPromptText("Data de Início");

        DatePicker dpFim = new DatePicker(epoca == null ? null : epoca.getFim());
        dpFim.setPromptText("Data de Fim");

        ComboBox<Integer> cmbSemestreId = new ComboBox<>();
        cmbSemestreId.setPromptText("Selecione o Semestre");
        listaSemestres.forEach(semestre -> cmbSemestreId.getItems().add(semestre.getId()));
        cmbSemestreId.setValue(epoca == null ? null : epoca.getSemestreId());

        Button btnSalvar = criarBotao("Salvar", "form-button", e -> {
            String tipo = cmbTipo.getValue();
            LocalDate inicio = dpInicio.getValue();
            LocalDate fim = dpFim.getValue();
            Integer semestreId = cmbSemestreId.getValue();

            if (tipo == null || inicio == null || fim == null || semestreId == null) {
                mostrarErro("Validação", "Todos os campos devem ser preenchidos.");
                return;
            }

            if (!inicio.isBefore(fim)) {
                mostrarErro("Validação", "A data de início deve ser anterior à data de fim.");
                return;
            }

            try {
                if (epoca == null) {
                    epocaService.salvarEpoca(tipo, inicio, fim, semestreId);
                    mostrarAlerta("Sucesso", "Época adicionada com sucesso.");
                } else {
                    epocaService.atualizarEpoca(epoca.getId(), tipo, inicio, fim, semestreId);
                    mostrarAlerta("Sucesso", "Época atualizada com sucesso.");
                }
                carregarDados();
                formularioStage.close();
            } catch (Exception ex) {
                mostrarErro("Erro ao Salvar", ex.getMessage());
            }
        });

        VBox formulario = new VBox(15, lblTitulo, cmbTipo, dpInicio, dpFim, cmbSemestreId, btnSalvar);
        formulario.setPadding(new Insets(20));
        formulario.setAlignment(Pos.CENTER);
        formulario.getStyleClass().add("form-container");

        Scene scene = new Scene(formulario, 400, 400);
        scene.getStylesheets().add(getClass().getResource("/org/styles/gestaoSemestresEpocas.css").toExternalForm());
        formularioStage.setScene(scene);
        formularioStage.show();
    }



    private void editarSemestre() {
        Semestre selecionado = tabelaSemestres.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            abrirFormularioSemestre(selecionado);
        } else {
            mostrarErro("Nenhum Semestre Selecionado", "Selecione um semestre para editar.");
        }
    }

    private void excluirSemestre() {
        Semestre selecionado = tabelaSemestres.getSelectionModel().getSelectedItem();
        if (selecionado != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmação de Exclusão");
            confirm.setContentText("Deseja realmente excluir o semestre?");
            Optional<ButtonType> resultado = confirm.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                semestreService.excluirSemestre(selecionado.getId());
                carregarDados();
            }
        } else {
            mostrarErro("Nenhum Semestre Selecionado", "Selecione um semestre para excluir.");
        }
    }

    private void editarEpoca() {
        Epoca selecionada = tabelaEpocas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            abrirFormularioEpoca(selecionada);
        } else {
            mostrarErro("Nenhuma Época Selecionada", "Selecione uma época para editar.");
        }
    }

    private void excluirEpoca() {
        Epoca selecionada = tabelaEpocas.getSelectionModel().getSelectedItem();
        if (selecionada != null) {
            Alert confirm = new Alert(Alert.AlertType.CONFIRMATION);
            confirm.setTitle("Confirmação de Exclusão");
            confirm.setContentText("Deseja realmente excluir a época?");
            Optional<ButtonType> resultado = confirm.showAndWait();
            if (resultado.isPresent() && resultado.get() == ButtonType.OK) {
                epocaService.excluirEpoca(selecionada.getId());
                carregarDados();
            }
        } else {
            mostrarErro("Nenhuma Época Selecionada", "Selecione uma época para excluir.");
        }
    }

    private TextField criarCampoTexto(String prompt, String valorInicial) {
        TextField campo = new TextField(valorInicial);
        campo.setPromptText(prompt);
        campo.getStyleClass().add("form-input");
        return campo;
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
