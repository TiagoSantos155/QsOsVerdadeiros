package Repositórios;

import org.example.testejavafxmaven.DataBaseConnection;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizadorRepository {

    private static final String INSERT = "INSERT INTO Utilizador (nome, tipo) VALUES (?, ?)";
    private static final String SELECT_ALL = "SELECT * FROM Utilizador";
    private static final String SELECT_BY_ID = "SELECT * FROM Utilizador WHERE id = ?";
    private static final String UPDATE = "UPDATE Utilizador SET nome = ?, tipo = ? WHERE id = ?";
    private static final String DELETE = "DELETE FROM Utilizador WHERE id = ?";

    // Create (INSERT)
    public void create(String nome, String tipo) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(INSERT)) {

            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            stmt.executeUpdate();
            System.out.println("Utilizador criado com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    // Read (SELECT ALL)
    public List<String> readAll() {
        List<String> utilizadores = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(SELECT_ALL)) {

            while (rs.next()) {
                utilizadores.add(rs.getInt("id") + " - " + rs.getString("nome") + " (" + rs.getString("tipo") + ")");
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return utilizadores;
    }

    // Read (SELECT BY ID)
    public String readById(int id) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(SELECT_BY_ID)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("id") + " - " + rs.getString("nome") + " (" + rs.getString("tipo") + ")";
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null; // Não encontrado
    }

    // Update
    public void update(int id, String nome, String tipo) {
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(UPDATE)) {

            stmt.setString(1, nome);
            stmt.setString(2, tipo);
            stmt.setInt(3, id);
            stmt.executeUpdate();
            System.out.println("Utilizador atualizado com sucesso!");

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
            System.out.println("Utilizador removido com sucesso!");

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
