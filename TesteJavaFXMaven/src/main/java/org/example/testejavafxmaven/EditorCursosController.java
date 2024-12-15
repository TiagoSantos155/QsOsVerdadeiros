package org.example.testejavafxmaven;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

public class EditorCursosController {

    private Stage stage;

    @FXML
    private ListView<Cursos> listViewCursos;

    @FXML
    private ListView<UnidadeCurricular> listViewUCs;

    @FXML
    private Button btnSalvar;

    @FXML
    private Button btnVoltar;

    private CursosDAO cursosDAO = new CursosDAO();
    private UnidadeCurricularDAO unidadeCurricularDAO = new UnidadeCurricularDAO();
    private CursoUnidadeCurricularDAO cursoUcDAO = new CursoUnidadeCurricularDAO();

    private ObservableList<UnidadeCurricular> unidadesCurriculares;
    private ObservableList<Cursos> cursos;

    @FXML
    public void initialize() {
        try {
            cursos = FXCollections.observableArrayList(cursosDAO.listarCursos());
            listViewCursos.setItems(cursos);

            listViewCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldVal, newVal) -> {
                if (newVal != null) {
                    carregarUCsParaCurso(newVal);
                }
            });

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarUCsParaCurso(Cursos curso) {
        try {
            List<UnidadeCurricular> todasUCs = unidadeCurricularDAO.listarUnidadesCurriculares();
            List<CursoUnidadeCurricular> associacoes = cursoUcDAO.findAll();

            unidadesCurriculares = FXCollections.observableArrayList(todasUCs);
            listViewUCs.setItems(unidadesCurriculares);

            listViewUCs.setCellFactory(param -> new ListCell<>() {
                private final CheckBox checkBox = new CheckBox();

                @Override
                protected void updateItem(UnidadeCurricular item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty || item == null) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        checkBox.setText(item.getNome());
                        checkBox.setSelected(
                                associacoes.stream().anyMatch(assoc ->
                                        assoc.getIdCurso() == curso.getId() &&
                                                assoc.getIdUnidadeCurricular() == item.getId()
                                )
                        );
                        setGraphic(checkBox);
                    }
                }
            });
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Método para lidar com a seleção de um curso na ListView
    @FXML
    public void cursoSelecionado(MouseEvent event) {
        Cursos curso = listViewCursos.getSelectionModel().getSelectedItem();
        if (curso != null) {
            carregarUCsParaCurso(curso);
        }
    }

    // Método para editar as Unidades Curriculares do curso selecionado
    @FXML
    public void editarUCs() {
        Cursos cursoSelecionado = listViewCursos.getSelectionModel().getSelectedItem();
        if (cursoSelecionado != null) {
            // Exibir ou abrir uma nova tela para editar as Unidades Curriculares do curso selecionado
            System.out.println("Editando UCs do curso: " + cursoSelecionado.getNome());
        } else {
            mostrarAlerta("Nenhum curso selecionado", "Por favor, selecione um curso antes de editar as UCs.");
        }
    }

    // Método utilitário para exibir alertas
    private void mostrarAlerta(String titulo, String mensagem) {
        Alert alerta = new Alert(Alert.AlertType.WARNING);
        alerta.setTitle(titulo);
        alerta.setHeaderText(null);
        alerta.setContentText(mensagem);
        alerta.showAndWait();
    }

    @FXML
    public void salvarAssociacoes() {
        Cursos cursoSelecionado = listViewCursos.getSelectionModel().getSelectedItem();
        if (cursoSelecionado != null) {
            // Lógica para salvar as associações entre curso e UC
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
