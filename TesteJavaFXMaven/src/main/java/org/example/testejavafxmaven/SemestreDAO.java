package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class SemestreDAO {

    public void salvarSemestres(String inicioSemestre1, String fimSemestre1, String inicioSemestre2, String fimSemestre2) {
        String sql = "INSERT INTO semestres (inicio_semestre1, fim_semestre1, inicio_semestre2, fim_semestre2) VALUES (?, ?, ?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, inicioSemestre1);
            pstmt.setString(2, fimSemestre1);
            pstmt.setString(3, inicioSemestre2);
            pstmt.setString(4, fimSemestre2);

            pstmt.executeUpdate();
            System.out.println("Semestres adicionados com sucesso à base de dados.");

        } catch (SQLException e) {
            System.err.println("Erro ao salvar os semestres na base de dados: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
