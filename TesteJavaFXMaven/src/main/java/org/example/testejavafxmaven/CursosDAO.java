package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CursosDAO {

    // Método para buscar todos os cursos
    public List<Cursos> buscarTodosCursos() {
        List<Cursos> cursos = new ArrayList<>();
        String sql = "SELECT * FROM Cursos";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                List<UC> ucs = buscarUcsDoCurso(id);  // Buscar as UCs associadas ao curso
                Cursos curso = new Cursos(id, nome, ucs);
                cursos.add(curso);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cursos no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        return cursos;
    }

    // Método para buscar todas as UCs de um curso específico
    private List<UC> buscarUcsDoCurso(int cursoId) {
        List<UC> ucs = new ArrayList<>();
        String sql = "SELECT * FROM UCs WHERE curso_id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, cursoId);
            try (ResultSet rs = pstmt.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String nome = rs.getString("nome");
                    ucs.add(new UC(id, nome));
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar UCs do curso no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return ucs;
    }
}
