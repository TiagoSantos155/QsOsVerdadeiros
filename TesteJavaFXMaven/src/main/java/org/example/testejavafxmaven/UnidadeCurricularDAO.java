package org.example.testejavafxmaven;

import org.example.testejavafxmaven.UnidadeCurricular;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UnidadeCurricularDAO extends GenericDAO<UnidadeCurricular> {

    @Override
    protected UnidadeCurricular mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new UnidadeCurricular(
                rs.getInt("id_uc"),
                rs.getString("nome"),
                rs.getString("tipo_avaliacao"),
                rs.getInt("numero_alunos")
        );
    }

    public void save(UnidadeCurricular uc) {
        String query = "INSERT INTO UnidadesCurriculares (nome, tipo_avaliacao) VALUES (?, ?)";
        executeUpdate(query, uc.getNome(), uc.getTipoAvaliacao());
    }

    public void update(UnidadeCurricular uc) {
        String query = "UPDATE UnidadesCurriculares SET nome = ?, tipo_avaliacao = ? WHERE id_uc = ?";
        executeUpdate(query, uc.getNome(), uc.getTipoAvaliacao(), uc.getId());
    }

    public void delete(int id) {
        String query = "DELETE FROM UnidadesCurriculares WHERE id_uc = ?";
        executeUpdate(query, id);
    }

    public void adicionarUnidadeCurricular(String nome, String tipoAvaliacao, int numeroAlunos) throws SQLException {
        String query = "INSERT INTO UnidadesCurriculares (nome, tipo_avaliacao, numero_alunos) VALUES (?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, tipoAvaliacao);
            stmt.setInt(3, numeroAlunos);
            stmt.executeUpdate();
        }
    }

    public void atualizarUnidadeCurricular(int id, String nome, String tipoAvaliacao, int numeroAlunos) throws SQLException {
        String query = "UPDATE UnidadesCurriculares SET nome = ?, tipo_avaliacao = ?, numero_alunos = ? WHERE id_uc = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.setString(2, tipoAvaliacao);
            stmt.setInt(3, numeroAlunos);
            stmt.setInt(4, id);
            stmt.executeUpdate();
        }
    }

    public List<UnidadeCurricular> listarUnidadesCurriculares() throws SQLException {
        String query = "SELECT * FROM UnidadesCurriculares";
        List<UnidadeCurricular> unidades = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                unidades.add(mapResultSetToEntity(rs));
            }
        }
        return unidades;
    }

}