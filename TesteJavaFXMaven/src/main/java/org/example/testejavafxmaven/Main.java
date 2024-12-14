package org.example.testejavafxmaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.List;

public class Main extends Application {

    public interface StageAwareController {
        void setStage(Stage stage);
    }

    @Override
    public void start(Stage primaryStage) {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/org/example/testejavafxmaven/Login.fxml"));
            Scene scene = new Scene(fxmlLoader.load());

            Object controller = fxmlLoader.getController();
            if (controller instanceof StageAwareController) {
                ((StageAwareController) controller).setStage(primaryStage);
            } else {
                System.err.println("Erro: O controlador não implementa StageAwareController.");
            }

            primaryStage.setTitle("Sistema de Gestão Acadêmica");
            primaryStage.setScene(scene);
            primaryStage.show();
        } catch (IOException e) {
            System.err.println("Erro ao carregar o arquivo FXML principal.");
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        // Inicializar o esquema do banco de dados
        DataBaseConnection.inicializarSchema();

        // Criar instância do DAO para Utilizadores
        UtilizadorDAO utilizadorDAO = new UtilizadorBD();

        // Salvar utilizadores de teste
        //Utilizador admin = new Admin("admin", "1", "1");
       // Utilizador utilizador = new Utilizador("utilizador", "2", "2");
       // utilizadorDAO.salvar(admin);
        //utilizadorDAO.salvar(utilizador);

        // Criar instâncias das classes DAO
        CursosDAO cursoDAO = new CursosBD();
        UCDAO ucDAO = new UCBD();

        // Inserir Cursos no Banco de Dados
        //cursoDAO.salvarCurso("Engenharia Informática");
        //cursoDAO.salvarCurso("Gestão de Empresas");

        // Inserir Unidades Curriculares no Banco de Dados
        /**ucDAO.salvarUC("Estruturas de Dados", null);  // null significa que a UC ainda não está associada a nenhum curso
        ucDAO.salvarUC("Algoritmos", null);
        ucDAO.salvarUC("Banco de Dados", null);
        ucDAO.salvarUC("Cálculo I", null);
        ucDAO.salvarUC("Álgebra Linear", null);
        ucDAO.salvarUC("Geometria", null);
        ucDAO.salvarUC("Física Clássica", null);
        ucDAO.salvarUC("Física Quântica", null);
        ucDAO.salvarUC("Biologia Geral", null);
        ucDAO.salvarUC("Genética", null);*/

        // Launch JavaFX
        launch(args);
    }

}
