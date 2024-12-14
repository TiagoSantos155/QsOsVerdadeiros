package org.example.testejavafxmaven;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class CoordinatorPanelController implements Main.StageAwareController {

    private Stage stage;

    @Override
    public void setStage(Stage stage) {
        this.stage = stage;
    }

    // Método para abrir o gerenciamento de cursos
    public void abrirGerenciarCursos() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/GerenciadorDeCursos.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador e configurar o Stage
            GerenciadorDeCursosController cursosController = loader.getController();
            cursosController.setStage(stage); // Passa o Stage atual para o controlador

            // Configurar o curso selecionado
            //cursosController.setCursoSelecionado("Curso de Computação");

            // Alterar a cena
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao abrir a tela de Gerenciador de UCs.");
            e.printStackTrace();
        }      }

    // Método para abrir o gerenciamento de UCs
    public void abrirGerenciarUCs() {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/GerenciadorDeUCs.fxml"));
            Scene scene = new Scene(loader.load());

            // Obter o controlador e configurar o Stage
            GerenciadorDeUCsController ucsController = loader.getController();
            ucsController.setStage(stage); // Passa o Stage atual para o controlador

            // Configurar o curso selecionado
            ucsController.setCursoSelecionado("Curso de Computação");

            // Alterar a cena
            stage.setScene(scene);
        } catch (IOException e) {
            System.err.println("Erro ao abrir a tela de Gerenciador de UCs.");
            e.printStackTrace();
        }
    }

    // Método para abrir o gerenciamento de avaliações
    public void abrirGerenciarAvaliacoes() {
        loadScene("/path/to/gerenciar_avaliacoes.fxml", "Gerenciar Avaliações");
    }

    // Método para abrir o gerenciamento de salas
    public void abrirGerenciarSalas() {
        loadScene("/path/to/gerenciar_salas.fxml", "Gerenciar Salas");
    }

    // Método para voltar ao Login
    public void voltarLogin() {
        loadScene("/org/example/testejavafxmaven/Login.fxml", "Login");
    }

    // Método genérico para carregar cenas
    private void loadScene(String fxmlPath, String title) {
        if (stage == null) {
            System.err.println("Erro crítico: o Stage não foi configurado no controlador antes de carregar a cena.");
            throw new IllegalStateException("Stage não configurado no controlador.");
        }

        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(fxmlPath));
            Scene scene = new Scene(loader.load());

            // Configura o Stage no controlador da próxima cena
            Object controller = loader.getController();
            if (controller instanceof Main.StageAwareController) {
                ((Main.StageAwareController) controller).setStage(stage);
            }

            stage.setScene(scene);
            stage.setTitle(title);
            stage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo FXML: " + fxmlPath);
            e.printStackTrace();
        }
    }
}
