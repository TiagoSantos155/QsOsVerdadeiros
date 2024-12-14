package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosBD implements CursosDAO {

    @Override
    public void salvarCurso(String nome) {
        String sql = "INSERT INTO Cursos (nome) VALUES (?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
            System.out.println("Curso salvo com sucesso: " + nome);
        } catch (SQLException e) {
            System.err.println("Erro ao salvar curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public List<Cursos> buscarTodosCursos() {
        List<Cursos> cursos = new ArrayList<>();
        String sql = "SELECT * FROM Cursos";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                List<UC> ucs = buscarUcsDoCurso(id);
                Cursos curso = new Cursos(id, nome);
                cursos.add(curso);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar cursos no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }
        return cursos;
    }

    @Override
    public Cursos buscarCursoPorId(int id) {
        String sql = "SELECT * FROM Cursos WHERE id = ?";
        Cursos curso = null;

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            try (ResultSet rs = pstmt.executeQuery()) {
                if (rs.next()) {
                    String nome = rs.getString("nome");
                    List<UC> ucs = buscarUcsDoCurso(id);
                    curso = new Cursos(id, nome);
                }
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar curso pelo ID: " + e.getMessage());
            e.printStackTrace();
        }
        return curso;
    }

    @Override
    public void atualizarCurso(Cursos curso) {
        String sql = "UPDATE Cursos SET nome = ? WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setString(1, curso.getNome());
            pstmt.setInt(2, curso.getId());
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Curso atualizado com sucesso: " + curso.getNome());
            } else {
                System.out.println("Nenhum curso encontrado com ID: " + curso.getId());
            }

        } catch (SQLException e) {
            System.err.println("Erro ao atualizar curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

    @Override
    public void excluirCurso(int id) {
        String sql = "DELETE FROM Cursos WHERE id = ?";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql)) {

            pstmt.setInt(1, id);
            int rowsAffected = pstmt.executeUpdate();
            if (rowsAffected > 0) {
                System.out.println("Curso excluído com sucesso. ID: " + id);
            } else {
                System.out.println("Nenhum curso encontrado com ID: " + id);
            }

        } catch (SQLException e) {
            System.err.println("Erro ao excluir curso: " + e.getMessage());
            e.printStackTrace();
        }
    }

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
