package Repositórios;

import org.example.testejavafxmaven.DataBaseConnection;

public class TestCursoCRUD {
    public static void main(String[] args) {
        DataBaseConnection.inicializarSchema();
        CursoRepository repo = new CursoRepository();

        // Create
        repo.create("Engenharia Informática", 120);
        repo.create("Engenharia Civil", 80);

        // Read All
        System.out.println("Todos os cursos:");
        repo.readAll().forEach(System.out::println);

        // Read By ID
        System.out.println("Curso com ID 1:");
        System.out.println(repo.readById(1));

        // Update
        repo.update(1, "Engenharia Informática Avançada", 130);

        // Delete
        repo.delete(2);

        // Read All (após alterações)
        System.out.println("Após alterações:");
        repo.readAll().forEach(System.out::println);
    }
}
