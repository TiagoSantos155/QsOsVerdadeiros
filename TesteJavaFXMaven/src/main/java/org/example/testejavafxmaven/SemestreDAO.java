package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class SemestreDAO {
    public void salvar(Semestre semestre) {
        String sql = """
            INSERT INTO semestres (inicio_semestre1, fim_semestre1, inicio_semestre2, fim_semestre2)
            VALUES (?, ?, ?, ?)
        """;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, semestre.getInicioSemestre1());
            stmt.setString(2, semestre.getFimSemestre1());
            stmt.setString(3, semestre.getInicioSemestre2());
            stmt.setString(4, semestre.getFimSemestre2());
            stmt.executeUpdate();
            System.out.println("Semestre salvo com sucesso!");
        } catch (SQLException e) {
            System.err.println("Erro ao salvar semestre.");
            e.printStackTrace();
        }
    }

    public Semestre buscarUltimoSemestre() {
        String sql = "SELECT * FROM semestres ORDER BY id DESC LIMIT 1";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            if (rs.next()) {
                Semestre semestre = new Semestre();
                semestre.definirSemestre1(rs.getString("inicio_semestre1"), rs.getString("fim_semestre1"));
                semestre.definirSemestre2(rs.getString("inicio_semestre2"), rs.getString("fim_semestre2"));
                return semestre;
            }
        } catch (SQLException e) {
            System.err.println("Erro ao buscar último semestre.");
            e.printStackTrace();
        }
        return null;
    }
}
