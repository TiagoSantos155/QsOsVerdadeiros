package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.CheckBox;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
/**
public class EditorCursosController {

    @FXML
    private ListView<Cursos> listCursos;

    @FXML
    private ListView<CheckBox> listUCs; // Usaremos CheckBox para selecionar as UCs.

    @FXML
    private Button btnSalvar;

    private final CursosDAO cursoDAO = new CursosDAO();
    private final UnidadeCurricularDAO ucDAO = new UnidadeCurricularDAO();
    private final CursoUnidadeCurricularDAO cursoUcDAO = new CursoUnidadeCurricularDAO();

    @FXML
    public void initialize() {
        try {
            carregarCursos();
            configurarSelecaoCurso();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void carregarCursos() throws SQLException {
        List<Cursos> cursos = cursoDAO.listarCursos();
        listCursos.getItems().addAll(cursos);
        listCursos.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
    }

    private void configurarSelecaoCurso() {
        listCursos.getSelectionModel().selectedItemProperty().addListener((obs, oldCurso, newCurso) -> {
            if (newCurso != null) {
                try {
                    carregarUCs(newCurso.getId());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        });
    }

    private void carregarUCs(int cursoId) throws SQLException {
        listUCs.getItems().clear();
        List<UnidadeCurricular> todasUCs = ucDAO.listarUnidadesCurriculares();
        List<Integer> ucAssociadas = cursoUcDAO.listarUnidadesPorCurso(cursoId); // Retorna IDs associados.

        for (UnidadeCurricular uc : todasUCs) {
            CheckBox checkBox = new CheckBox(uc.getNome());
            checkBox.setUserData(uc); // Armazena o objeto UnidadeCurricular no CheckBox.
            checkBox.setSelected(ucAssociadas.contains(uc.getId())); // Marca os já associados.
            listUCs.getItems().add(checkBox);
        }
    }

    @FXML
    public void handleSalvar() {
        Cursos cursoSelecionado = listCursos.getSelectionModel().getSelectedItem();
        if (cursoSelecionado == null) {
            System.out.println("Por favor, selecione um curso.");
            return;
        }

        List<UnidadeCurricular> ucsSelecionadas = new ArrayList<>();
        for (CheckBox checkBox : listUCs.getItems()) {
            if (checkBox.isSelected()) {
                UnidadeCurricular uc = (UnidadeCurricular) checkBox.getUserData();
                ucsSelecionadas.add(uc);
            }
        }

        try {
            cursoUcDAO.atualizarAssociacoes(cursoSelecionado.getId(), ucsSelecionadas);
            System.out.println("Associações salvas com sucesso!");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @FXML
    public void handleVoltar() {
        // Código para voltar ao layout anterior (coordinator_panel.fxml).
    }
}
*/