package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UCDAO {

    public void salvarUC(String nomeUC, int cursoId) {
        String sql = "INSERT INTO UCs (nome, curso_id) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, nomeUC);
            pstmt.setInt(2, cursoId);
            pstmt.executeUpdate();
            System.out.println("Unidade Curricular '" + nomeUC + "' associada ao curso de ID " + cursoId + " com sucesso!");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar a unidade curricular '" + nomeUC + "' na base de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
