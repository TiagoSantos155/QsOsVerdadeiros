package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection {

    private static Connection connection;
    private static final String URL = "jdbc:h2:~/test;AUTO_SERVER=TRUE"; // Banco de dados em memória
    private static final String USER = "sa"; // Usuário padrão
    private static final String PASSWORD = ""; // Senha padrão (vazio)

    public static Connection getConnection() {
        if (connection == null) {
            try {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
                System.out.println("Conexão com o banco de dados H2 estabelecida.");
            } catch (SQLException e) {
                e.printStackTrace();
                System.out.println("Não foi possível conectar ao banco de dados.");
                System.exit(1);
            }
        }
        return connection;
    }
}