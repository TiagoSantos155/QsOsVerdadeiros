package org.example.testejavafxmaven;

public class TestDatabaseConnection {
    public static void main(String[] args) {
        // Inicializa o esquema do banco de dados (cria as tabelas)
        DataBaseConnection.inicializarSchema();

        // Testa a conexão com o banco de dados
        if (DataBaseConnection.getConnection() != null) {
            System.out.println("Conexão com a base de dados testada com sucesso!");
        }
    }
}
