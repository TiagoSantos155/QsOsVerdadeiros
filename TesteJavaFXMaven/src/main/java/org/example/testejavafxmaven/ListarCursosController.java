package org.example.testejavafxmaven;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class ListarCursosController implements Main.StageAwareController {

    @FXML
    private TableView<Cursos> tableCursos;

    @FXML
    private TableColumn<Cursos, Integer> colId;

    @FXML
    private TableColumn<Cursos, String> colNome;

    @FXML
    private Button btnVoltar;

    private Stage stage;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    @FXML
    public void initialize() {
        colId.setCellValueFactory(new PropertyValueFactory<>("id"));
        colNome.setCellValueFactory(new PropertyValueFactory<>("nome"));

        carregarCursos();
    }

    private void carregarCursos() {
        CursosDAO cursosDAO = new CursosDAO();
        try {
            List<Cursos> cursos = cursosDAO.listarCursos();
            ObservableList<Cursos> cursosObservableList = FXCollections.observableArrayList(cursos);
            tableCursos.setItems(cursosObservableList);
        } catch (SQLException e) {
            System.err.println("Erro ao carregar os cursos: " + e.getMessage());
            e.printStackTrace();
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