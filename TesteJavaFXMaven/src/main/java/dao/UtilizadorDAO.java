package dao;

import modelo.Utilizador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UtilizadorDAO {
    private final Connection conexao;

    public UtilizadorDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Criar um novo utilizador
    public void salvar(Utilizador utilizador) throws SQLException {
        String sql = "INSERT INTO Utilizadores (nome, tipo, email, senha) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getTipo());
            stmt.setString(3, utilizador.getEmail());
            stmt.setString(4, utilizador.getSenha()); // Nota: Em produção, as senhas devem ser armazenadas com hash
            stmt.executeUpdate();
        }
    }

    // Atualizar utilizador
    public void atualizar(Utilizador utilizador) throws SQLException {
        String sql = "UPDATE Utilizadores SET nome = ?, tipo = ?, email = ?, senha = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, utilizador.getNome());
            stmt.setString(2, utilizador.getTipo());
            stmt.setString(3, utilizador.getEmail());
            stmt.setString(4, utilizador.getSenha());
            stmt.setInt(5, utilizador.getId());
            stmt.executeUpdate();
        }
    }

    // Excluir utilizador
    public void deletar(int id) throws SQLException {
        String sql = "DELETE FROM Utilizadores WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }

    // Listar todos os utilizadores
    public List<Utilizador> listar() throws SQLException {
        List<Utilizador> utilizadores = new ArrayList<>();
        String sql = "SELECT * FROM Utilizadores";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Utilizador u = new Utilizador(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getString("tipo"),
                        rs.getString("email"),
                        rs.getString("senha")
                );
                utilizadores.add(u);
            }
        }
        return utilizadores;
    }

    // Buscar utilizador por e-mail
    public Utilizador buscarPorEmail(String email) throws SQLException {
        String sql = "SELECT * FROM Utilizadores WHERE email = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, email);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Utilizador(
                            rs.getInt("id"),
                            rs.getString("nome"),
                            rs.getString("tipo"),
                            rs.getString("email"),
                            rs.getString("senha")
                    );
                }
            }
        }
        return null;
    }
}
