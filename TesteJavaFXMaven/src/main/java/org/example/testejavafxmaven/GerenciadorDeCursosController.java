package org.example.testejavafxmaven;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.Parent;
import javafx.scene.control.ListView;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class GerenciadorDeCursosController {

    private Stage stage;  // Para armazenar o stage atual

    @FXML
    private ListView<String> listViewCursos;

    // Método chamado quando um curso é selecionado
    @FXML
    private void cursoSelecionado() {
        String curso = listViewCursos.getSelectionModel().getSelectedItem();
        if (curso != null) {
            try {
                // Carregar o controlador de UCs
                FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/GerenciadorDeUCs.fxml"));
                Parent root = loader.load();

                // Passar o curso selecionado para o controlador de UCs
                GerenciadorDeUCsController ucController = loader.getController();
                ucController.setCursoSelecionado(curso);  // Passar curso para o controlador de UCs

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
        // Simulando a carga de cursos (em uma aplicação real, você puxaria isso de um banco de dados ou arquivo)
        List<String> cursos = getCursos(); // Método fictício para pegar os cursos de uma fonte de dados
        listViewCursos.getItems().addAll(cursos);  // Adicionando cursos na ListView
    }

    // Método para simular a obtenção de cursos (isso seria do banco de dados ou de algum outro lugar)
    private List<String> getCursos() {
        return List.of("Curso de Computação", "Curso de Matemática", "Curso de Física", "Curso de Biologia");
    }

    @FXML
    public void voltar() {
        try {
            // Carregar a tela de Login (ou a tela anterior conforme necessário)
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/Login.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador da nova cena
            MainController mainController = loader.getController();

            // Obter o Stage da tela atual
            Stage currentStage = (Stage) listViewCursos.getScene().getWindow();

            // Passar o Stage atual para o controlador da nova cena
            mainController.setStage(currentStage);

            // Alterar a cena no Stage atual
            currentStage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao voltar para a tela de login.");
            e.printStackTrace();
        }
    }

    // Método para passar o Stage para o controlador
    public void setStage(Stage stage) {
        this.stage = stage;
    }
}
