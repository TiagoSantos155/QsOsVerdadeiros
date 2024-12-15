package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SalaDAO extends GenericDAO<Sala> {

    @Override
    protected Sala mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Sala(
                rs.getInt("id_sala"),
                rs.getString("nome"),
                rs.getInt("capacidade"),
                rs.getInt("computadores")
        );
    }

    public void inserirSala(Sala sala) throws SQLException {
        String query = "INSERT INTO Salas (nome, capacidade, computadores) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sala.getNome());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala.getComputadores());
            stmt.executeUpdate();
        }
    }

    public void atualizarSala(Sala sala) throws SQLException {
        String query = "UPDATE Salas SET nome = ?, capacidade = ?, computadores = ? WHERE id_sala = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, sala.getNome());
            stmt.setInt(2, sala.getCapacidade());
            stmt.setInt(3, sala.getComputadores());
            stmt.setInt(4, sala.getId());
            stmt.executeUpdate();
        }
    }

    public void deletarSala(int idSala) throws SQLException {
        String query = "DELETE FROM Salas WHERE id_sala = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idSala);
            stmt.executeUpdate();
        }
    }

    public List<Sala> listarSalas() throws SQLException {
        String query = "SELECT * FROM Salas";
        List<Sala> salas = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                salas.add(new Sala(
                        rs.getInt("id_sala"),
                        rs.getString("nome"),
                        rs.getInt("capacidade"),
                        rs.getInt("computadores")
                ));
            }
        }
        return salas;
    }

}
