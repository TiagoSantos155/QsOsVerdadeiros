package org.example.testejavafxmaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

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

// Inicializando os DAOs
        CursosDAO cursoDAO = new CursosDAO();
        UnidadeCurricularDAO unidadeCurricularDAO = new UnidadeCurricularDAO();

        // Adicionando 5 Cursos
        System.out.println("Adicionando cursos...");
        cursoDAO.save(new Cursos(0, "Engenharia Informática"));
        cursoDAO.save(new Cursos(0, "Engenharia Civil"));
        cursoDAO.save(new Cursos(0, "Gestão de Empresas"));
        cursoDAO.save(new Cursos(0, "Design Gráfico"));
        cursoDAO.save(new Cursos(0, "Biologia"));

        // Adicionando 10 Unidades Curriculares
        System.out.println("Adicionando unidades curriculares...");
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Programação Avançada", "MISTA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Estruturas de Dados", "CONTINUA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Bases de Dados", "MISTA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Sistemas Operativos", "CONTINUA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Redes de Computadores", "MISTA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Cálculo Diferencial e Integral", "CONTINUA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Mecânica dos Materiais", "MISTA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Marketing Digital", "CONTINUA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Bioquímica", "MISTA"));
        unidadeCurricularDAO.save(new UnidadeCurricular(0, "Anatomia Humana", "CONTINUA"));

        System.out.println("Cursos e Unidades Curriculares adicionados com sucesso!");

        // Launch JavaFX
        launch(args);
    }

}
