package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DataBaseConnection {

    private static Connection connection;
    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE"; // Banco de dados H2
    private static final String USER = "sa"; // Usuário padrão
    private static final String PASSWORD = ""; // Senha padrão (vazio)

    /**
     * Obtém a conexão com o banco de dados. Cria a conexão se ainda não estiver criada.
     *
     * @return Connection - objeto de conexão com o banco de dados.
     */
    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados H2 estabelecida.");
            } catch (SQLException e) {
                System.err.println("Não foi possível conectar ao banco de dados.");
                e.printStackTrace();
                System.exit(1); // Finaliza o programa em caso de erro crítico
            }
        }
        return connection;
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

    /**
     * Fecha a conexão com o banco de dados, se estiver aberta.
     */
    public static void closeConnection() {
        if (connection != null) {
            try {
                connection.close();
                connection = null;
                System.out.println("Conexão com o banco de dados H2 fechada.");
            } catch (SQLException e) {
                System.err.println("Erro ao fechar a conexão com o banco de dados.");
                e.printStackTrace();
            }
        }
    }
}
