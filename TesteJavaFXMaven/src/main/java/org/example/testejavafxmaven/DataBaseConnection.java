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
     * Inicializa o esquema do banco de dados, criando a tabela `utilizadores` caso ela ainda não exista.
     */
    public static void inicializarSchema() {
        String sql = """
            CREATE TABLE IF NOT EXISTS utilizadores (
                id INT AUTO_INCREMENT PRIMARY KEY,
                nome VARCHAR(100) NOT NULL,
                email VARCHAR(100) UNIQUE NOT NULL,
                senha VARCHAR(100) NOT NULL
            );
        """;

        try (Connection conn = getConnection();
             Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
            System.out.println("Esquema do banco de dados inicializado com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao inicializar o esquema do banco de dados.");
            e.printStackTrace();
        }
    }
}
