package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizadorBD implements UtilizadorDAO {

    @Override
    public void salvar(Utilizador utilizador) {
        String sql = "INSERT INTO utilizadores (nome, email, senha) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getEmail());
            stmt.setString(3, utilizador.getSenha());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao salvar utilizador: " + utilizador.getEmail());
            e.printStackTrace();
        }
    }

    @Override
    public List<Utilizador> buscarTodos() {
        List<Utilizador> utilizadores = new ArrayList<>();
        String sql = "SELECT * FROM utilizadores";
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utilizador utilizador = new Admin(
                        rs.getString("nome"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                utilizadores.add(utilizador);
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar utilizadores.");
            e.printStackTrace();
        }
        return utilizadores;
    }

    @Override
    public Utilizador buscarPorEmail(String email) {
        String sql = "SELECT * FROM utilizadores WHERE email = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Admin(
                            rs.getString("nome"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar utilizador com email: " + email);
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void atualizar(Utilizador utilizador) {
        String sql = "UPDATE utilizadores SET nome = ?, senha = ? WHERE email = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getSenha());
            stmt.setString(3, utilizador.getEmail());
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar utilizador: " + utilizador.getEmail());
            e.printStackTrace();
        }
    }

    @Override
    public void excluir(String email) {
        String sql = "DELETE FROM utilizadores WHERE email = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, email);
            stmt.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Erro ao excluir utilizador com email: " + email);
            e.printStackTrace();
        }
    }
}
