package org.example.testejavafxmaven;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.SQLException;
import java.sql.Timestamp;
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

        Scanner scanner = new Scanner(System.in);

        // DAOs para cursos/unidades curriculares
        CursosDAO cursoDAO = new CursosDAO();
        UnidadeCurricularDAO unidadeCurricularDAO = new UnidadeCurricularDAO();
        CursoUnidadeCurricularDAO cursoUnidadeCurricularDAO = new CursoUnidadeCurricularDAO();

        // DAOs para salas/avaliações
        SalaDAO salaDAO = new SalaDAO();
        AvaliacoesDAO avaliacoesDAO = new AvaliacoesDAO();

        try {
            // Inicialização do banco de dados e dados iniciais
            DataBaseConnection.inicializarSchema();
            inicializarDados(cursoDAO, unidadeCurricularDAO);

            // Launch JavaFX
            launch(args);

            while (true) {
                System.out.println("\n=== Menu Principal ===");
                System.out.println("1. Gerenciar Cursos e Unidades Curriculares");
                System.out.println("2. Gerenciar Salas e Avaliações");
                System.out.println("0. Sair");
                System.out.print("Escolha uma opção: ");

                int opcao = scanner.nextInt();
                switch (opcao) {
                    case 1 -> menuCursos(scanner, cursoDAO, unidadeCurricularDAO, cursoUnidadeCurricularDAO);
                    case 2 -> menuSalasAvaliacoes(scanner, salaDAO, avaliacoesDAO, unidadeCurricularDAO);
                    case 0 -> {
                        System.out.println("Saindo...");
                        return;
                    }
                    default -> System.out.println("Opção inválida. Tente novamente.");
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro no banco de dados: " + e.getMessage());
        }
    }

    private static void menuCursos(Scanner scanner, CursosDAO cursoDAO, UnidadeCurricularDAO unidadeCurricularDAO, CursoUnidadeCurricularDAO cursoUnidadeCurricularDAO) throws SQLException {
        while (true) {
            System.out.println("\n=== Menu de Cursos e Unidades Curriculares ===");
            System.out.println("1. Listar Cursos");
            System.out.println("2. Listar Unidades Curriculares");
            System.out.println("3. Associar Unidade Curricular a Curso");
            System.out.println("4. Editar Unidade Curricular");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            scanner.nextLine(); // Consumir nova linha

            switch (opcao) {
                case 1 -> listarCursos(cursoDAO);
                case 2 -> listarUnidadesCurriculares(unidadeCurricularDAO);
                case 3 -> associarCursoUnidadeCurricular(scanner, cursoDAO, unidadeCurricularDAO, cursoUnidadeCurricularDAO);
                case 4 -> atualizarUnidadeCurricular(scanner, unidadeCurricularDAO);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void menuSalasAvaliacoes(Scanner scanner, SalaDAO salaDAO, AvaliacoesDAO avaliacoesDAO, UnidadeCurricularDAO unidadeCurricularDAO) {
        while (true) {
            System.out.println("\n=== Menu de Salas e Avaliações ===");
            System.out.println("1. Gerenciar Salas");
            System.out.println("2. Gerenciar Avaliações");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");

            int opcao = scanner.nextInt();
            switch (opcao) {
                case 1 -> menuSalas(scanner, salaDAO);
                case 2 -> menuAvaliacoes(scanner, avaliacoesDAO, unidadeCurricularDAO);
                case 0 -> {
                    return;
                }
                default -> System.out.println("Opção inválida. Tente novamente.");
            }
        }
    }

    private static void listarCursos(CursosDAO cursoDAO) throws SQLException {
        System.out.println("\nCursos Disponíveis:");
        List<Cursos> cursos = cursoDAO.listarCursos();
        cursos.forEach(System.out::println);
    }

    private static void listarUnidadesCurriculares(UnidadeCurricularDAO unidadeCurricularDAO) throws SQLException {
        System.out.println("\nUnidades Curriculares Disponíveis:");
        List<UnidadeCurricular> ucs = unidadeCurricularDAO.listarUnidadesCurriculares();
        ucs.forEach(System.out::println);
    }

    private static void associarCursoUnidadeCurricular(Scanner scanner, CursosDAO cursoDAO, UnidadeCurricularDAO unidadeCurricularDAO, CursoUnidadeCurricularDAO cursoUnidadeCurricularDAO) throws SQLException {
        listarCursos(cursoDAO);
        System.out.print("Digite o ID do curso: ");
        int idCurso = scanner.nextInt();

        listarUnidadesCurriculares(unidadeCurricularDAO);
        System.out.print("Digite o ID da Unidade Curricular a associar: ");
        int idUC = scanner.nextInt();

        cursoUnidadeCurricularDAO.associarCursoUnidadeCurricular(idCurso, idUC);
        System.out.println("Unidade Curricular associada ao curso com sucesso!");
    }

    private static void atualizarUnidadeCurricular(Scanner scanner, UnidadeCurricularDAO unidadeCurricularDAO) throws SQLException {
        listarUnidadesCurriculares(unidadeCurricularDAO);

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

        System.out.print("Digite o novo número de alunos da Unidade Curricular: ");
        int numeroAlunos = scanner.nextInt();

        unidadeCurricularDAO.atualizarUnidadeCurricular(idUC, novoNome, novoTipoAvaliacao, numeroAlunos);
        System.out.println("Unidade Curricular atualizada com sucesso!");
    }

    private static void menuSalas(Scanner scanner, SalaDAO salaDAO) {
        while (true) {
            System.out.println("\n=== Menu de Salas ===");
            System.out.println("1. Listar Salas");
            System.out.println("2. Adicionar Sala");
            System.out.println("3. Atualizar Sala");
            System.out.println("4. Remover Sala");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            try {
                switch (opcao) {
                    case 1:
                        List<Sala> salas = salaDAO.listarSalas();
                        salas.forEach(System.out::println);
                        break;
                    case 2:
                        System.out.print("Nome da sala: ");
                        String nome = scanner.next();
                        System.out.print("Capacidade: ");
                        int capacidade = scanner.nextInt();
                        System.out.print("Número de computadores: ");
                        int computadores = scanner.nextInt();
                        Sala novaSala = new Sala(0, nome, capacidade, computadores);
                        salaDAO.inserirSala(novaSala);
                        System.out.println("Sala adicionada com sucesso!");
                        break;
                    case 3:
                        System.out.print("ID da sala a atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        System.out.print("Novo nome: ");
                        String novoNome = scanner.next();
                        System.out.print("Nova capacidade: ");
                        int novaCapacidade = scanner.nextInt();
                        System.out.print("Novo número de computadores: ");
                        int novosComputadores = scanner.nextInt();
                        Sala salaAtualizada = new Sala(idAtualizar, novoNome, novaCapacidade, novosComputadores);
                        salaDAO.atualizarSala(salaAtualizada);
                        System.out.println("Sala atualizada com sucesso!");
                        break;
                    case 4:
                        System.out.print("ID da sala a remover: ");
                        int idRemover = scanner.nextInt();
                        salaDAO.deletarSala(idRemover);
                        System.out.println("Sala removida com sucesso!");
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao processar a operação: " + e.getMessage());
            }
        }
    }

    private static void menuAvaliacoes(Scanner scanner, AvaliacoesDAO avaliacoesDAO, UnidadeCurricularDAO unidadeCurricularDAO) {
        while (true) {
            System.out.println("\n=== Menu de Avaliações ===");
            System.out.println("1. Listar Avaliações");
            System.out.println("2. Adicionar Avaliação");
            System.out.println("3. Atualizar Avaliação");
            System.out.println("4. Remover Avaliação");
            System.out.println("5. Filtrar Avaliações por Computador");
            System.out.println("6. Filtrar Avaliações por Horário Habitual");
            System.out.println("0. Voltar ao Menu Principal");
            System.out.print("Escolha uma opção: ");
            int opcao = scanner.nextInt();

            try {
                switch (opcao) {
                    case 1:
                        List<Avaliacoes> avaliacoes = avaliacoesDAO.listarAvaliacoes();
                        avaliacoes.forEach(System.out::println);
                        break;
                    case 2:
                        // Selecionar Unidade Curricular
                        listarUnidadesCurriculares(unidadeCurricularDAO);
                        System.out.print("ID da UC: ");
                        int idUc = scanner.nextInt();

                        // Selecionar Época
                        System.out.print("ID da época (ou 0 para nenhum): ");
                        Integer idEpoca = scanner.nextInt();
                        if (idEpoca == 0) idEpoca = null;

                        // Preencher detalhes da avaliação
                        System.out.print("Tipo de avaliação: ");
                        String tipoAvaliacao = scanner.next();
                        System.out.print("Ponderação: ");
                        double ponderacao = scanner.nextDouble();
                        System.out.print("Data e hora (YYYY-MM-DD HH:MM:SS): ");
                        scanner.nextLine(); // Consumir o restante da linha
                        Timestamp dataHora = Timestamp.valueOf(scanner.nextLine());
                        System.out.print("Sala: ");
                        String sala = scanner.next();
                        System.out.print("É horário habitual? (true/false): ");
                        boolean horarioHabitual = scanner.nextBoolean();
                        System.out.print("Requer computador? (true/false): ");
                        boolean requerComputador = scanner.nextBoolean();

                        Avaliacoes novaAvaliacao = new Avaliacoes(0, tipoAvaliacao, ponderacao, dataHora, sala, horarioHabitual, requerComputador, idUc, idEpoca);
                        avaliacoesDAO.inserirAvaliacao(novaAvaliacao);
                        System.out.println("Avaliação adicionada com sucesso!");
                        break;
                    case 3:
                        System.out.print("ID da avaliação a atualizar: ");
                        int idAtualizar = scanner.nextInt();
                        System.out.print("Novo tipo de avaliação: ");
                        String novoTipoAvaliacao = scanner.next();
                        System.out.print("Nova ponderação: ");
                        double novaPonderacao = scanner.nextDouble();
                        System.out.print("Nova data e hora (YYYY-MM-DD HH:MM:SS): ");
                        scanner.nextLine();
                        Timestamp novaDataHora = Timestamp.valueOf(scanner.nextLine());
                        System.out.print("Nova sala: ");
                        String novaSala = scanner.next();
                        System.out.print("É horário habitual? (true/false): ");
                        boolean novoHorarioHabitual = scanner.nextBoolean();
                        System.out.print("Requer computador? (true/false): ");
                        boolean novoRequerComputador = scanner.nextBoolean();
                        System.out.print("Novo ID da UC: ");
                        int novoIdUc = scanner.nextInt();
                        System.out.print("Novo ID da época (ou 0 para nenhum): ");
                        Integer novoIdEpoca = scanner.nextInt();
                        if (novoIdEpoca == 0) novoIdEpoca = null;
                        Avaliacoes avaliacaoAtualizada = new Avaliacoes(idAtualizar, novoTipoAvaliacao, novaPonderacao, novaDataHora, novaSala, novoHorarioHabitual, novoRequerComputador, novoIdUc, novoIdEpoca);
                        avaliacoesDAO.atualizarAvaliacao(avaliacaoAtualizada);
                        System.out.println("Avaliação atualizada com sucesso!");
                        break;
                    case 4:
                        System.out.print("ID da avaliação a remover: ");
                        int idRemover = scanner.nextInt();
                        avaliacoesDAO.deletarAvaliacao(idRemover);
                        System.out.println("Avaliação removida com sucesso!");
                        break;
                    case 5:
                        System.out.print("Filtrar por avaliações que requerem computador (true/false): ");
                        boolean filtroComputador = scanner.nextBoolean();
                        List<Avaliacoes> filtro1 = avaliacoesDAO.filtrarPorNecessidadeComputador(filtroComputador);
                        filtro1.forEach(System.out::println);
                        break;
                    case 6:
                        System.out.print("Filtrar por avaliações em horário habitual (true/false): ");
                        boolean filtroHorario = scanner.nextBoolean();
                        List<Avaliacoes> filtro2 = avaliacoesDAO.filtrarPorHorarioHabitual(filtroHorario);
                        filtro2.forEach(System.out::println);
                        break;
                    case 0:
                        return;
                    default:
                        System.out.println("Opção inválida. Tente novamente.");
                }
            } catch (SQLException e) {
                System.out.println("Erro ao processar a operação: " + e.getMessage());
            }
        }
    }

    private static void inicializarDados(CursosDAO cursoDAO, UnidadeCurricularDAO unidadeCurricularDAO) throws SQLException {
        // Criação inicial de cursos
        cursoDAO.adicionarCurso("Engenharia Informática");
        cursoDAO.adicionarCurso("Gestão de Empresas");
        cursoDAO.adicionarCurso("Design Gráfico");
        cursoDAO.adicionarCurso("Matemática Aplicada");
        cursoDAO.adicionarCurso("Biologia");

        // Criação inicial de unidades curriculares
        unidadeCurricularDAO.adicionarUnidadeCurricular("Programação", "MISTA", 30);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Algoritmos", "MISTA", 25);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Bases de Dados", "CONTINUA", 20);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Marketing", "CONTINUA", 40);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Gestão de Projetos", "MISTA", 35);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Biologia Molecular", "MISTA", 15);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Estatística", "CONTINUA", 25);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Matemática Discreta", "CONTINUA", 30);
        unidadeCurricularDAO.adicionarUnidadeCurricular("Design Digital", "MISTA", 20);
    }
}
