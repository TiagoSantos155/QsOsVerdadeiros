package Repositórios;

import org.example.testejavafxmaven.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoRepository {

    private static final String INSERT = "INSERT INTO Curso (nome, numero_alunos) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM Curso";
    private static final String SELECT_BY_ID = "SELECT * FROM Curso WHERE id = ?";
    private static final String UPDATE = "UPDATE Curso SET nome = ?, numero_alunos = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Curso WHERE id = ?";

    // Create (INSERT)
    public void create(String nome, int numeroAlunos) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {

            stmt.setString(1, nome);
            stmt.setInt(2, numeroAlunos);
            stmt.executeUpdate();
            System.out.println("Curso criado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read (SELECT ALL)
    public List<String> readAll() {
        List<String> cursos = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                cursos.add(rs.getInt("id") + " - " + rs.getString("nome") + " (Alunos: " + rs.getInt("numero_alunos") + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return cursos;
    }

    // Read (SELECT BY ID)
    public String readById(int id) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id") + " - " + rs.getString("nome") + " (Alunos: " + rs.getInt("numero_alunos") + ")";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Não encontrado
    }

    // Update
    public void update(int id, String nome, int numeroAlunos) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, nome);
            stmt.setInt(2, numeroAlunos);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Curso atualizado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Delete
    public void delete(int id) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(DELETE)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();
            System.out.println("Curso removido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
