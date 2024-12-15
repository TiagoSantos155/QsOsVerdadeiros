package org.example.testejavafxmaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import java.util.Scanner;

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
        //Utilizador utilizador = new Utilizador("utilizador", "2", "2");
        //utilizadorDAO.salvar(admin);
        //utilizadorDAO.salvar(utilizador);

        // Launch JavaFX
        launch(args);

        try {
            // Inicializa o esquema do banco de dados
            DataBaseConnection.inicializarSchema();

            // DAO para manipular os cursos e unidades curriculares
            CursosDAO cursoDAO = new CursosDAO();
            UnidadeCurricularDAO unidadeCurricularDAO = new UnidadeCurricularDAO();
            CursoUnidadeCurricularDAO cursoUnidadeCurricularDAO = new CursoUnidadeCurricularDAO();

            // Criação inicial de cursos
            //cursoDAO.adicionarCurso("Engenharia Informática");
            //cursoDAO.adicionarCurso("Gestão de Empresas");
            //cursoDAO.adicionarCurso("Design Gráfico");
            //cursoDAO.adicionarCurso("Matemática Aplicada");
            //cursoDAO.adicionarCurso("Biologia");

            // Criação inicial de unidades curriculares
            /**unidadeCurricularDAO.adicionarUnidadeCurricular("Programação", "MISTA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Algoritmos", "MISTA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Bases de Dados", "CONTINUA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Marketing", "CONTINUA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Gestão de Projetos", "MISTA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Biologia Molecular", "MISTA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Estatística", "CONTINUA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Matemática Discreta", "CONTINUA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Design Digital", "MISTA");
            unidadeCurricularDAO.adicionarUnidadeCurricular("Animação 3D", "CONTINUA");*/

            // Menu interativo
            Scanner scanner = new Scanner(System.in);
            while (true) {
                System.out.println("\nMenu Principal:");
                System.out.println("1. Listar Cursos");
                System.out.println("2. Listar Unidades Curriculares");
                System.out.println("3. Associar Unidade Curricular a Curso");
                System.out.println("4. Atualizar Unidade Curricular");
                System.out.println("5. Sair");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                scanner.nextLine(); // Consumir nova linha

                switch (opcao) {
                    case 1 -> {
                        // Listar Cursos
                        System.out.println("Cursos Disponíveis:");
                        List<Cursos> cursos = cursoDAO.listarCursos();
                        for (Cursos curso : cursos) {
                            System.out.println(curso);
                        }
                    }
                    case 2 -> {
                        // Listar Unidades Curriculares
                        System.out.println("Unidades Curriculares Disponíveis:");
                        List<UnidadeCurricular> ucs = unidadeCurricularDAO.listarUnidadesCurriculares();
                        for (UnidadeCurricular uc : ucs) {
                            System.out.println(uc);
                        }
                    }
                    case 3 -> {
                        // Associar Unidade Curricular a Curso
                        System.out.println("Cursos Disponíveis:");
                        List<Cursos> cursos = cursoDAO.listarCursos();
                        for (Cursos curso : cursos) {
                            System.out.println(curso);
                        }

                        System.out.print("Digite o ID do curso: ");
                        int idCurso = scanner.nextInt();

                        System.out.println("Unidades Curriculares Disponíveis:");
                        List<UnidadeCurricular> ucs = unidadeCurricularDAO.listarUnidadesCurriculares();
                        for (UnidadeCurricular uc : ucs) {
                            System.out.println(uc);
                        }

                        System.out.print("Digite o ID da Unidade Curricular a associar: ");
                        int idUC = scanner.nextInt();

                        cursoUnidadeCurricularDAO.associarCursoUnidadeCurricular(idCurso, idUC);
                        System.out.println("Unidade Curricular associada ao curso com sucesso!");
                    }
                    case 4 -> {
                        // Atualizar Unidade Curricular
                        System.out.println("Unidades Curriculares Disponíveis:");
                        List<UnidadeCurricular> ucs = unidadeCurricularDAO.listarUnidadesCurriculares();
                        for (UnidadeCurricular uc : ucs) {
                            System.out.println(uc);
                        }

                        System.out.print("Digite o ID da Unidade Curricular a atualizar: ");
                        int idUC = scanner.nextInt();
                        scanner.nextLine(); // Consumir nova linha

                        System.out.print("Digite o novo nome da Unidade Curricular: ");
                        String novoNome = scanner.nextLine();

                        String novoTipoAvaliacao;
                        while (true) {
                            System.out.print("Digite o novo tipo de avaliação (MISTA ou CONTINUA): ");
                            novoTipoAvaliacao = scanner.nextLine().toUpperCase();
                            if (novoTipoAvaliacao.equals("MISTA") || novoTipoAvaliacao.equals("CONTINUA")) {
                                break;
                            } else {
                                System.out.println("Tipo de avaliação inválido. Tente novamente.");
                            }
                        }

                        unidadeCurricularDAO.atualizarUnidadeCurricular(idUC, novoNome, novoTipoAvaliacao);
                        System.out.println("Unidade Curricular atualizada com sucesso!");
                    }
                    case 5 -> {
                        // Sair do programa
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida! Tente novamente.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
        }
    }
}
