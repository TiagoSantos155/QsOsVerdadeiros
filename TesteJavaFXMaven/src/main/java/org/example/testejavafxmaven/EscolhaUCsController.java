package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.util.Callback;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
/**
public class EscolhaUCsController {

    @FXML
    private ListView<UnidadeCurricular> listViewUCs;

    private String cursoSelecionado;
    private final UnidadeCurricularDAO ucDAO = new UCBD();
    private final CursosDAO cursosDAO = new CursosBD();

    private Stage stage;

    // Método chamado para configurar o curso selecionado
    public void setCursoSelecionado(String cursoSelecionado) {
        this.cursoSelecionado = cursoSelecionado;
        carregarUCs();
    }

    // Carrega as UCs e define quais estão associadas ao curso
    private void carregarUCs() {
        // Buscar curso pelo nome
        Cursos curso = cursosDAO.buscarTodosCursos().stream()
                .filter(c -> c.getNome().equals(cursoSelecionado))
                .findFirst()
                .orElse(null);

        if (curso != null) {
            // Obter as UCs associadas ao curso
            List<UnidadeCurricular> ucsAssociadas = ucDAO.buscarUcsPorCurso(curso.getId());
            List<UnidadeCurricular> todasUCs = ucDAO.buscarTodasUCs(); // Buscar todas as UCs

            // Criar lista completa de UCs (associadas e não associadas)
            List<UnidadeCurricular> listaCompleta = new ArrayList<>(todasUCs);

            // Configurar a ListView com CheckBoxes
            listViewUCs.getItems().setAll(listaCompleta);
            listViewUCs.setCellFactory(new Callback<>() {
                @Override
                public ListCell<UnidadeCurricular> call(ListView<UnidadeCurricular> param) {
                    return new ListCell<>() {
                        private final CheckBox checkBox = new CheckBox();

                        @Override
                        protected void updateItem(UnidadeCurricular item, boolean empty) {
                            super.updateItem(item, empty);
                            if (item != null) {
                                checkBox.setText(item.getNome());
                                checkBox.setSelected(ucsAssociadas.contains(item)); // Marcar como selecionada se associada
                                checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> {
                                    if (newValue) {
                                        if (!ucsAssociadas.contains(item)) {
                                            ucsAssociadas.add(item);
                                        }
                                    } else {
                                        ucsAssociadas.remove(item);
                                    }
                                });
                                setGraphic(checkBox);
                            } else {
                                setGraphic(null);
                            }
                        }
                    };
                }
            });
        } else {
            System.err.println("Curso não encontrado: " + cursoSelecionado);
        }
    }

    @FXML
    private void definirUCs() {
        Cursos curso = cursosDAO.buscarTodosCursos().stream()
                .filter(c -> c.getNome().equals(cursoSelecionado))
                .findFirst()
                .orElse(null);

        if (curso != null) {
            List<UnidadeCurricular> ucsSelecionadas = new ArrayList<>();
            for (UnidadeCurricular uc : listViewUCs.getItems()) {
                if (listViewUCs.getSelectionModel().getSelectedItems().contains(uc)) {
                    ucsSelecionadas.add(uc);
                }
            }

            // Salvar as associações entre as UCs selecionadas e o curso
            for (UnidadeCurricular uc : ucsSelecionadas) {
                ucDAO.salvarUC(uc.getNome(), curso.getId());
            }
            System.out.println("UCs definidas para o curso " + cursoSelecionado);
        }
    }

    @FXML
    public void voltar() {
        try {
            // Carregar a tela de Gerenciar Cursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/GerenciadorDeCursos.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador da nova cena
            GerenciadorDeCursosController gerenciadorDeCursosController = loader.getController();

            // Passar o Stage atual para o controlador da nova cena
            gerenciadorDeCursosController.setStage(stage);

            // Alterar a cena no Stage atual
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de Coordenador.");
            e.printStackTrace();
        }
    }


    public void setStage(Stage stage) {
        this.stage = stage;
    }
}*/
