package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class AvaliacoesDAO extends GenericDAO<Avaliacoes> {

    @Override
    protected Avaliacoes mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Avaliacoes(
                rs.getInt("id_avaliacao"),
                rs.getString("tipo_avaliacao"),
                rs.getDouble("ponderacao"),
                rs.getTimestamp("data_hora"),
                rs.getString("sala"),
                rs.getBoolean("horario_habitual"),
                rs.getBoolean("requer_computador"),
                rs.getInt("id_uc"),
                rs.getObject("id_epoca", Integer.class) // Permite null
        );
    }

    // Inserir uma nova avaliação
    public void inserirAvaliacao(Avaliacoes avaliacao) throws SQLException {
        String query = "INSERT INTO Avaliacoes (tipo_avaliacao, ponderacao, data_hora, sala, horario_habitual, requer_computador, id_uc, id_epoca) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, avaliacao.getTipoAvaliacao());
            stmt.setDouble(2, avaliacao.getPonderacao());
            stmt.setTimestamp(3, avaliacao.getDataHora());
            stmt.setString(4, avaliacao.getSala());
            stmt.setBoolean(5, avaliacao.isHorarioHabitual());
            stmt.setBoolean(6, avaliacao.isRequerComputador());
            stmt.setInt(7, avaliacao.getIdUc());
            stmt.setObject(8, avaliacao.getIdEpoca(), Types.INTEGER); // Aceita null
            stmt.executeUpdate();
        }
    }

    // Atualizar uma avaliação existente
    public void atualizarAvaliacao(Avaliacoes avaliacao) throws SQLException {
        String query = "UPDATE Avaliacoes SET tipo_avaliacao = ?, ponderacao = ?, data_hora = ?, sala = ?, horario_habitual = ?, requer_computador = ?, id_uc = ?, id_epoca = ? WHERE id_avaliacao = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, avaliacao.getTipoAvaliacao());
            stmt.setDouble(2, avaliacao.getPonderacao()); // Corrigido para double
            stmt.setTimestamp(3, avaliacao.getDataHora());
            stmt.setString(4, avaliacao.getSala());
            stmt.setBoolean(5, avaliacao.isHorarioHabitual());
            stmt.setBoolean(6, avaliacao.isRequerComputador());
            stmt.setInt(7, avaliacao.getIdUc());
            stmt.setObject(8, avaliacao.getIdEpoca(), Types.INTEGER); // Aceita null
            stmt.setInt(9, avaliacao.getId());
            stmt.executeUpdate();
        }
    }

    // Deletar uma avaliação pelo ID
    public void deletarAvaliacao(int idAvaliacao) throws SQLException {
        String query = "DELETE FROM Avaliacoes WHERE id_avaliacao = ?";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idAvaliacao);
            stmt.executeUpdate();
        }
    }

    // Listar todas as avaliações
    public List<Avaliacoes> listarAvaliacoes() throws SQLException {
        String query = "SELECT * FROM Avaliacoes";
        List<Avaliacoes> avaliacoes = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                Avaliacoes avaliacao = new Avaliacoes(
                        rs.getInt("id_avaliacao"),
                        rs.getString("tipo_avaliacao"),
                        rs.getDouble("ponderacao"), // Corrigido para double
                        rs.getTimestamp("data_hora"), // Timestamp mantido
                        rs.getString("sala"),
                        rs.getBoolean("horario_habitual"),
                        rs.getBoolean("requer_computador"),
                        rs.getInt("id_uc"),
                        rs.getObject("id_epoca", Integer.class) // Permite null
                );
                avaliacoes.add(avaliacao);
            }
        }
        return avaliacoes;
    }

    // Filtrar avaliações por necessidade de computador
    public List<Avaliacoes> filtrarPorNecessidadeComputador(boolean requerComputador) throws SQLException {
        String query = "SELECT * FROM Avaliacoes WHERE requer_computador = ?";
        List<Avaliacoes> avaliacoes = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, requerComputador);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Avaliacoes avaliacao = mapResultSetToEntity(rs);
                    avaliacoes.add(avaliacao);
                }
            }
        }
        return avaliacoes;
    }

    // Filtrar avaliações por horário habitual
    public List<Avaliacoes> filtrarPorHorarioHabitual(boolean horarioHabitual) throws SQLException {
        String query = "SELECT * FROM Avaliacoes WHERE horario_habitual = ?";
        List<Avaliacoes> avaliacoes = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setBoolean(1, horarioHabitual);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    Avaliacoes avaliacao = mapResultSetToEntity(rs);
                    avaliacoes.add(avaliacao);
                }
            }
        }
        return avaliacoes;
    }
}
