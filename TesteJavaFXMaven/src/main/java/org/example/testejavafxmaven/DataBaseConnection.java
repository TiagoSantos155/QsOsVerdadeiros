package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE"; // Banco de dados H2
    private static final String USER = "sa"; // Usuário padrão
    private static final String PASSWORD = ""; // Senha padrão (vazio)

    /**
     * Obtém uma nova conexão com o banco de dados.
     *
     * @return Connection - objeto de conexão com o banco de dados.
     */
    public static Connection getConnection() {
        try {
            return DriverManager.getConnection(URL, USER, PASSWORD);
        } catch (SQLException e) {
            System.err.println("Não foi possível conectar ao banco de dados.");
            e.printStackTrace();
            throw new RuntimeException(e); // Lançar exceção para tratamento em níveis superiores
        }
    }

    /**
     * Inicializa o esquema do banco de dados, criando as tabelas necessárias caso ainda não existam.
     */
    public static void inicializarSchema() {
        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {

            // Tabela `utilizadores`
            String sqlUtilizadores = """
                CREATE TABLE IF NOT EXISTS utilizadores (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(100) NOT NULL,
                    email VARCHAR(100) UNIQUE NOT NULL,
                    senha VARCHAR(100) NOT NULL
                );
            """;
            stmt.execute(sqlUtilizadores);

            // Tabela `semestres`
            String sqlSemestres = """
                CREATE TABLE IF NOT EXISTS semestres (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    inicio_semestre1 DATE NOT NULL,
                    fim_semestre1 DATE NOT NULL,
                    inicio_semestre2 DATE NOT NULL,
                    fim_semestre2 DATE NOT NULL
                );
            """;
            stmt.execute(sqlSemestres);

            // Tabela `epocas`
            String sqlEpocas = """
                CREATE TABLE IF NOT EXISTS epocas (
                    id INT AUTO_INCREMENT PRIMARY KEY,
                    tipo VARCHAR(50) NOT NULL,
                    inicio DATE NOT NULL,
                    fim DATE NOT NULL
                );
            """;
            stmt.execute(sqlEpocas);

            // Tabela `Cursos`
            String sqlCursos = """
                CREATE TABLE IF NOT EXISTS Cursos (
                    id_curso INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL
                );
            """;
            stmt.execute(sqlCursos);

            // Tabela `UnidadesCurriculares`
            String sqlUnidadesCurriculares = """
                CREATE TABLE IF NOT EXISTS UnidadesCurriculares (
                    id_uc INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(255) NOT NULL,
                    tipo_avaliacao ENUM('MISTA', 'CONTINUA') NOT NULL,
                    numero_alunos INT NOT NULL
                );
            """;
            stmt.execute(sqlUnidadesCurriculares);

            String sqlSalas = """
                CREATE TABLE IF NOT EXISTS Salas (
                    id_sala INT AUTO_INCREMENT PRIMARY KEY,
                    nome VARCHAR(50) NOT NULL,
                    capacidade INT NOT NULL,
                    computadores INT NOT NULL -- Número de computadores na sala
                );
            """;
            stmt.execute(sqlSalas);

            // Tabela `CursosUnidadesCurriculares`
            String sqlCursosUnidadesCurriculares = """
                CREATE TABLE IF NOT EXISTS CursosUnidadesCurriculares (
                    id_curso INT NOT NULL,
                    id_uc INT NOT NULL,
                    PRIMARY KEY (id_curso, id_uc),
                    FOREIGN KEY (id_curso) REFERENCES Cursos(id_curso),
                    FOREIGN KEY (id_uc) REFERENCES UnidadesCurriculares(id_uc)
                );
            """;
            stmt.execute(sqlCursosUnidadesCurriculares);

            // Tabela `Avaliacoes`
            String sqlAvaliacoes = """
                CREATE TABLE IF NOT EXISTS Avaliacoes (
                    id_avaliacao INT AUTO_INCREMENT PRIMARY KEY,
                    tipo_avaliacao ENUM('TESTE', 'TESTE_FINAL', 'TRABALHO', 'ENTREGA_TRABALHO', 'ENTREGA_TRABALHO_GRUPO', 'APRESENTACAO_TRABALHO',
                                         'APRESENTACAO_TRABALHO_GRUPO', 'MONOGRAFIA', 'EXERCICIO_PRATICO', 'PITCH', 'EXAME_FINAL', 'PROVA_ORAL') NOT NULL,
                    ponderacao DECIMAL(5,2) NOT NULL,
                    data_hora TIMESTAMP NOT NULL,
                    sala VARCHAR(50),
                    horario_habitual BOOLEAN DEFAULT FALSE,
                    requer_computador BOOLEAN DEFAULT FALSE,
                    id_uc INT NOT NULL,
                    id_epoca INT,
                    FOREIGN KEY (id_uc) REFERENCES UnidadesCurriculares(id_uc),
                    FOREIGN KEY (id_epoca) REFERENCES epocas(id)
                );
            """;
            stmt.execute(sqlAvaliacoes);

            // Tabela `AlocacoesSalas`
            String sqlAlocacoesSalas = """
                CREATE TABLE IF NOT EXISTS AlocacoesSalas (
                    id_alocacao INT AUTO_INCREMENT PRIMARY KEY,
                    id_avaliacao INT NOT NULL,
                    id_sala INT NOT NULL,
                    FOREIGN KEY (id_avaliacao) REFERENCES Avaliacoes(id_avaliacao),
                    FOREIGN KEY (id_sala) REFERENCES Salas(id_sala)
                );
            """;
            stmt.execute(sqlAlocacoesSalas);

            // Tabela `Assiduidade`
            String sqlAssiduidade = """
                CREATE TABLE IF NOT EXISTS Assiduidade (
                    id_assiduidade INT AUTO_INCREMENT PRIMARY KEY,
                    id_avaliacao INT NOT NULL,
                    obrigatorio ENUM('SIM', 'NAO') NOT NULL,
                    FOREIGN KEY (id_avaliacao) REFERENCES Avaliacoes(id_avaliacao)
                );
            """;
            stmt.execute(sqlAssiduidade);

            System.out.println("Esquema do banco de dados inicializado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o esquema do banco de dados.");
            e.printStackTrace();
        }
    }
}