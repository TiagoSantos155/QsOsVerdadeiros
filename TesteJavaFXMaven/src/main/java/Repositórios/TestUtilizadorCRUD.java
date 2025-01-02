package Repositórios;

public class TestUtilizadorCRUD {
    public static void main(String[] args) {
        UtilizadorRepository repo = new UtilizadorRepository();

        // Create
        repo.create("João Silva", "Administrador");
        repo.create("Maria Oliveira", "CoordenadorCurso");

        // Read All
        System.out.println("Todos os utilizadores:");
        repo.readAll().forEach(System.out::println);

        // Read By ID
        System.out.println("Utilizador com ID 1:");
        System.out.println(repo.readById(1));

        // Update
        repo.update(1, "João Santos", "Administrador");

        // Delete
        repo.delete(2);

        // Read All (após alterações)
        System.out.println("Após alterações:");
        repo.readAll().forEach(System.out::println);
    }
}
