package dao;

import modelo.Semestre;
import org.example.testejavafxmaven.DataBaseConnection;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class SemestreDAO {
    private final Connection connection;

    public SemestreDAO() {
        this.connection = DataBaseConnection.getConnection(); // Usar a conexão da classe DataBaseConnection
    }

    public void adicionarSemestre(Semestre semestre) throws SQLException {
        String sql = "INSERT INTO Semestre (inicio, fim) VALUES (?, ?)";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(semestre.getInicio()));
            stmt.setDate(2, Date.valueOf(semestre.getFim()));
            stmt.executeUpdate();
        }
    }

    public List<Semestre> listarSemestres() throws SQLException {
        String sql = "SELECT * FROM Semestre";
        List<Semestre> semestres = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                semestres.add(new Semestre(
                        rs.getInt("id"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate()
                ));
            }
        }
        return semestres;
    }

    public boolean salvar(Semestre semestre) {
        try {
            adicionarSemestre(semestre);
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao salvar o semestre: " + e.getMessage());
            return false;
        }
    }

    public List<Semestre> listarTodos() {
        String sql = "SELECT * FROM Semestre";
        List<Semestre> semestres = new ArrayList<>();
        try (Statement stmt = connection.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                semestres.add(new Semestre(
                        rs.getInt("id"),
                        rs.getDate("inicio").toLocalDate(),
                        rs.getDate("fim").toLocalDate()
                ));
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar semestres: " + e.getMessage());
        }
        return semestres;
    }

    public boolean atualizar(Semestre semestre) {
        String sql = "UPDATE Semestre SET inicio = ?, fim = ? WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setDate(1, Date.valueOf(semestre.getInicio()));
            stmt.setDate(2, Date.valueOf(semestre.getFim()));
            stmt.setInt(3, semestre.getId());
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao atualizar o semestre: " + e.getMessage());
            return false;
        }
    }

    public boolean excluir(int id) {
        String sql = "DELETE FROM Semestre WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
            return true;
        } catch (SQLException e) {
            System.err.println("Erro ao excluir o semestre: " + e.getMessage());
            return false;
        }
    }

    public Semestre buscarPorId(int id) {
        String sql = "SELECT * FROM Semestre WHERE id = ?";
        try (PreparedStatement stmt = connection.prepareStatement(sql)) {
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return new Semestre(
                            rs.getInt("id"),
                            rs.getDate("inicio").toLocalDate(),
                            rs.getDate("fim").toLocalDate()
                    );
                }
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar semestre por ID: " + e.getMessage());
        }
        return null;
    }
}
