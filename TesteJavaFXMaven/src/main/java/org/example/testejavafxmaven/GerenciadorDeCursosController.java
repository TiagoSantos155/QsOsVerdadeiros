package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

public class GerenciadorDeCursosController {

    private Stage stage;  // Para armazenar o stage atual
    private final CursosDAO cursosDAO = new CursosBD(); // Instância do DAO para buscar os cursos

    @FXML
    private ListView<String> listViewCursos;

    // Método chamado quando um curso é selecionado
    @FXML
    private void cursoSelecionado() {
        String curso = listViewCursos.getSelectionModel().getSelectedItem();
        if (curso != null) {
            try {
                // Carregar o controlador de EscolhaUCs
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/EscolhaUCs.fxml"));
                Parent root = loader.load();

                // Passar o curso selecionado para o controlador de UCs
                EscolhaUCsController escolhaUCsController = loader.getController();
                escolhaUCsController.setCursoSelecionado(curso);  // Passar curso para o controlador de UCs

                // Passar o stage atual para o controlador de EscolhaUCs
                escolhaUCsController.setStage((Stage) listViewCursos.getScene().getWindow());

                // Carregar a nova cena com o controlador de UCs
                Scene scene = new Scene(root);
                Stage stage = (Stage) listViewCursos.getScene().getWindow();  // Obter o stage da lista de cursos
                stage.setScene(scene);
                stage.setTitle("Definir UC's");
                stage.show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    // Método para inicializar a lista de cursos
    @FXML
    public void initialize() {
        // Buscar cursos do banco de dados e adicionar na ListView
        List<String> cursos = getCursos(); // Agora busca os cursos da base de dados
        listViewCursos.getItems().addAll(cursos);  // Adicionando cursos na ListView
    }

    // Método para obter cursos diretamente do banco de dados
    private List<String> getCursos() {
        // Usa o DAO para buscar todos os cursos e retorna os nomes como uma lista de strings
        return cursosDAO.buscarTodosCursos()
                .stream()
                .map(Cursos::getNome) // Extrai apenas o nome do curso
                .collect(Collectors.toList());
    }

    @FXML
    public void voltar() {
        try {
            // Carregar a tela de Gerenciar Cursos
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/coordinator_panel.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador da nova cena
            CoordinatorPanelController coordinatorPanelController = loader.getController();

            // Passar o Stage atual para o controlador da nova cena
            coordinatorPanelController.setStage(stage);

            // Alterar a cena no Stage atual
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de Coordenador.");
            e.printStackTrace();
        }
    }

    // Método para passar o Stage para o controlador
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
