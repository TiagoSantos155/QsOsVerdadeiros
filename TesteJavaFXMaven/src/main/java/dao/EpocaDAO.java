package dao;

import modelo.Epoca;
import org.example.testejavafxmaven.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EpocaDAO {
    private final Connection connection;

    public EpocaDAO() {
        this.connection = DataBaseConnection.getConnection(); // Conexão vinda da classe DataBaseConnection
    }

    public void adicionarEpoca(Epoca epoca) throws SQLException {
        String sql = "INSERT INTO Epoca (tipo, inicio, fim, semestre_id) VALUES (?, ?, ?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, epoca.getTipo());
            stmt.setDate(2, Date.valueOf(epoca.getInicio()));
            stmt.setDate(3, Date.valueOf(epoca.getFim()));
            stmt.setInt(4, epoca.getSemestreId());
            stmt.executeUpdate();
        }
    }

    public List<Epoca> listarEpocas(int semestreId) throws SQLException {
        String sql = "SELECT * FROM Epoca WHERE semestre_id = ?";
        List<Epoca> epocas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, semestreId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    epocas.add(new Epoca(
                            rs.getInt("id"),
                            rs.getString("tipo"),
                            rs.getDate("inicio").toLocalDate(),
                            rs.getDate("fim").toLocalDate(),
                            rs.getInt("semestre_id")
                    ));
                }
            }
        }
        return epocas;
    }

    public boolean salvar(Epoca epoca) {
        try {
            adicionarEpoca(epoca);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar a época: " + e.getMessage());
            return false;
        }
    }

    public List<Epoca> listarTodos() {
        String sql = "SELECT * FROM Epoca";
        List<Epoca> epocas = new ArrayList<>();
        try (PreparedStatement stmt = connection.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                epocas.add(new Epoca(
                        rs.getInt("id"),
                        rs.getString("tipo"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate(),
                        rs.getInt("semestre_id")
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar épocas: " + e.getMessage());
        }
        return epocas;
    }

    public boolean atualizar(Epoca epoca) {
        String sql = "UPDATE Epoca SET tipo = ?, inicio = ?, fim = ?, semestre_id = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setString(1, epoca.getTipo());
            stmt.setDate(2, Date.valueOf(epoca.getInicio()));
            stmt.setDate(3, Date.valueOf(epoca.getFim()));
            stmt.setInt(4, epoca.getSemestreId());
            stmt.setInt(5, epoca.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar a época: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Epoca WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir a época: " + e.getMessage());
            return false;
        }
    }

    public Epoca buscarPorId(int id) {
        String sql = "SELECT * FROM Epoca WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Epoca(
                            rs.getInt("id"),
                            rs.getString("tipo"),
                            rs.getDate("inicio").toLocalDate(),
                            rs.getDate("fim").toLocalDate(),
                            rs.getInt("semestre_id")
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar época por ID: " + e.getMessage());
        }
        return null;
    }
}
