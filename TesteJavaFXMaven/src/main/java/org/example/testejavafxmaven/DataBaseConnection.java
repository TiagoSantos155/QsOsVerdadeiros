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

            // Tabelas
            stmt.execute("CREATE TABLE IF NOT EXISTS Utilizador (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('Administrador', 'CoordenadorCurso'))" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Curso (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL UNIQUE," +
                    "numero_alunos INT NOT NULL CHECK (numero_alunos > 0)" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS UC (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Curso_UC (" +
                    "curso_id INT NOT NULL," +
                    "uc_id INT NOT NULL," +
                    "PRIMARY KEY (curso_id, uc_id)," +
                    "FOREIGN KEY (curso_id) REFERENCES Curso(id)," +
                    "FOREIGN KEY (uc_id) REFERENCES UC(id)" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS MapaAvaliacao (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "utilizador_id INT NOT NULL," +
                    "curso_id INT NOT NULL," +
                    "FOREIGN KEY (utilizador_id) REFERENCES Utilizador(id)," +
                    "FOREIGN KEY (curso_id) REFERENCES Curso(id)" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Semestre (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "inicio DATE NOT NULL," +
                    "fim DATE NOT NULL" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Epoca (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "tipo VARCHAR(50) NOT NULL CHECK (tipo IN ('Normal', 'Recurso', 'Especial'))," +
                    "inicio DATE NOT NULL," +
                    "fim DATE NOT NULL," +
                    "semestre_id INT NOT NULL," +
                    "FOREIGN KEY (semestre_id) REFERENCES Semestre(id)" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Sala (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "nome VARCHAR(255) NOT NULL," +
                    "capacidade INT NOT NULL CHECK (capacidade > 0)," +
                    "numero_computadores INT NOT NULL" +
                    ");");

            stmt.execute("CREATE TABLE IF NOT EXISTS Avaliacao (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "tipo VARCHAR(255) NOT NULL," +
                    "ponderacao DECIMAL(5, 2) NOT NULL CHECK (ponderacao BETWEEN 0 AND 100)," +
                    "data_hora TIMESTAMP NOT NULL," +
                    "sala_id INT," +
                    "requere_computador BOOLEAN NOT NULL," +
                    "uc_id INT NOT NULL," +
                    "FOREIGN KEY (sala_id) REFERENCES Sala(id)," +
                    "FOREIGN KEY (uc_id) REFERENCES UC(id)" +
                    ");");

            System.out.println("Esquema do banco de dados inicializado com sucesso.");

        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o esquema do banco de dados.");
            e.printStackTrace();
        }
    }
}
