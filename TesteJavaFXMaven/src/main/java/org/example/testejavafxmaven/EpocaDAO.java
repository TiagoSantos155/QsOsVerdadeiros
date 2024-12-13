package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class EpocaDAO {
    public void salvar(String tipo, String inicio, String fim) {
        String sql = """
            INSERT INTO epocas (tipo, inicio, fim)
            VALUES (?, ?, ?)
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, tipo);
            stmt.setString(2, inicio);
            stmt.setString(3, fim);
            stmt.executeUpdate();
            System.out.println("Época salva com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar época.");
            e.printStackTrace();
        }
    }

    public String listarEpocas() {
        StringBuilder sb = new StringBuilder();
        String sql = "SELECT * FROM epocas";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                sb.append("Tipo: ").append(rs.getString("tipo")).append("\n")
                        .append("Início: ").append(rs.getString("inicio")).append("\n")
                        .append("Fim: ").append(rs.getString("fim")).append("\n\n");
            }
        } catch (SQLException e) {
            System.err.println("Erro ao listar épocas.");
            e.printStackTrace();
        }
        return sb.toString();
    }
}
