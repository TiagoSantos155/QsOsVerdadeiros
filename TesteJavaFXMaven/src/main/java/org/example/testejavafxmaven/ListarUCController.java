package org.example.testejavafxmaven;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarUCController implements Main.StageAwareController {

    @FXML
    private TableView<UnidadeCurricular> tableUCs;

    @FXML
    private TableColumn<UnidadeCurricular, Integer> colId;

    @FXML
    private TableColumn<UnidadeCurricular, String> colNome;

    @FXML
    private TableColumn<UnidadeCurricular, String> colTipoAvaliacao;

    @FXML
    private TableColumn<UnidadeCurricular, Integer> colNumeroAlunos;

    private Stage stage;
    private UnidadeCurricularDAO ucDAO;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        ucDAO = new UnidadeCurricularDAO();

        // Configura as colunas da tabela
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));
        colTipoAvaliacao.setCellValueFactory(new PropertyValueFactory<>("tipoAvaliacao"));
        colNumeroAlunos.setCellValueFactory(new PropertyValueFactory<>("numeroAlunos"));

        // Carrega os dados
        carregarDados();
    }

    private void carregarDados() {
        try {
            List<UnidadeCurricular> listaUCs = ucDAO.listarUnidadesCurriculares();
            ObservableList<UnidadeCurricular> data = FXCollections.observableArrayList(listaUCs);
            tableUCs.setItems(data);
        } catch (SQLException e) {
            e.printStackTrace();
            System.err.println("Erro ao carregar Unidades Curriculares.");
        }
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
