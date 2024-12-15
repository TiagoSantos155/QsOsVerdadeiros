package org.example.testejavafxmaven;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.KeyEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;

public class EditorUcController {

    private Stage stage;

    @FXML
    private TextField barraPesquisa;

    @FXML
    private ListView<UnidadeCurricular> listViewUCs;

    @FXML
    private TextField textFieldNome;

    @FXML
    private ComboBox<String> comboBoxTipoAvaliacao;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    private UnidadeCurricularDAO unidadeCurricularDAO = new UnidadeCurricularDAO();
    private ObservableList<UnidadeCurricular> unidadesCurriculares;

    @FXML
    public void initialize() {
        try {
            unidadesCurriculares = FXCollections.observableArrayList(unidadeCurricularDAO.listarUnidadesCurriculares());
            listViewUCs.setItems(unidadesCurriculares);

            listViewUCs.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    carregarDadosUC(newVal);
                }
            });

            comboBoxTipoAvaliacao.setItems(FXCollections.observableArrayList("Prova", "Trabalho", "Exame"));

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarDadosUC(UnidadeCurricular uc) {
        textFieldNome.setText(uc.getNome());
        comboBoxTipoAvaliacao.setValue(uc.getTipoAvaliacao());
    }

    @FXML
    public void salvarAlteracoes() {
        UnidadeCurricular ucSelecionada = listViewUCs.getSelectionModel().getSelectedItem();
        if (ucSelecionada != null) {
            ucSelecionada.setNome(textFieldNome.getText());
            ucSelecionada.setTipoAvaliacao(comboBoxTipoAvaliacao.getValue());

            try {
                unidadeCurricularDAO.update(ucSelecionada);
                mostrarAlerta("Sucesso", "Unidade Curricular atualizada com sucesso!");
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlerta("Erro", "Ocorreu um erro ao atualizar a Unidade Curricular.");
            }
        }
    }


    @FXML
    public void definirUCs() {
        UnidadeCurricular ucSelecionada = listViewUCs.getSelectionModel().getSelectedItem();

        if (ucSelecionada != null) {
            ucSelecionada.setNome(textFieldNome.getText());
            ucSelecionada.setTipoAvaliacao(comboBoxTipoAvaliacao.getValue());

            try {
                unidadeCurricularDAO.update(ucSelecionada);
            } catch (Exception e) {
                e.printStackTrace();
                mostrarAlertaErro("Erro ao atualizar a Unidade Curricular.");
            }
        }
    }

    private void mostrarAlertaErro(String mensagem) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erro");
        alert.setHeaderText(null);
        alert.setContentText(mensagem);
        alert.showAndWait();
    }

    @FXML
    public void filtrarUCs(KeyEvent event) {
        String filtro = barraPesquisa.getText().toLowerCase();
        listViewUCs.setItems(unidadesCurriculares.filtered(uc -> uc.getNome().toLowerCase().contains(filtro)));
    }

    // Método utilitário para exibir alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.INFORMATION);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void handleVoltar() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/coordinator_panel.fxml"));
            Scene scene = new Scene(loader.load());

            Object controller = loader.getController();
            if (controller instanceof Main.StageAwareController) {
                ((Main.StageAwareController) controller).setStage(stage);
            }

            stage.setScene(scene);
            stage.setTitle("Painel do Coordenador");
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo FXML: /org/example/testejavafxmaven/coordinator_panel.fxml");
            e.printStackTrace();
        }
    }
}
