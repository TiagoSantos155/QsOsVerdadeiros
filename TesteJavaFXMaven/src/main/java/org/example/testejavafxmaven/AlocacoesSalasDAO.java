package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AlocacoesSalasDAO extends GenericDAO<AlocacoesSalas> {

    @Override
    protected AlocacoesSalas mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new AlocacoesSalas(
                rs.getInt("id_alocacao"),
                rs.getInt("id_avaliacao"),
                rs.getInt("id_sala")
        );
    }

    public void inserirAlocacao(AlocacoesSalas alocacao) throws SQLException {
        String query = "INSERT INTO AlocacoesSalas (id_avaliacao, id_sala) VALUES (?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, alocacao.getIdAvaliacao());
            stmt.setInt(2, alocacao.getIdSala());
            stmt.executeUpdate();
        }
    }

    public void deletarAlocacao(int idAlocacao) throws SQLException {
        String query = "DELETE FROM AlocacoesSalas WHERE id_alocacao = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAlocacao);
            stmt.executeUpdate();
        }
    }

    public List<AlocacoesSalas> listarAlocacoes() throws SQLException {
        String query = "SELECT * FROM AlocacoesSalas";
        List<AlocacoesSalas> alocacoes = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                alocacoes.add(new AlocacoesSalas(
                        rs.getInt("id_alocacao"),
                        rs.getInt("id_avaliacao"),
                        rs.getInt("id_sala")
                ));
            }
        }
        return alocacoes;
    }

}
