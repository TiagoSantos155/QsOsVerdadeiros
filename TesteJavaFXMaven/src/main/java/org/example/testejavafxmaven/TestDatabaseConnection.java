package org.example.testejavafxmaven;

import java.sql.Connection;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        Connection conn = DataBaseConnection.getConnection();
        if (conn != null) {
            System.out.println("Conexão com a base de dados testada com sucesso!");
        }
    }
}
