package dao;

import modelo.Curso;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursoDAO {

    private Connection conexao;

    public CursoDAO(Connection conexao) {
        this.conexao = conexao;
    }

    // Create
    public void inserirCurso(Curso curso) throws SQLException {
        String sql = "INSERT INTO CURSO (nome, numero_alunos) VALUES (?, ?)";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getNumeroAlunos());
            stmt.executeUpdate();
        }
    }

    // Read (listar todos os cursos)
    public List<Curso> listarCursos() throws SQLException {
        List<Curso> cursos = new ArrayList<>();
        String sql = "SELECT * FROM CURSO";
        try (Statement stmt = conexao.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {
            while (rs.next()) {
                Curso curso = new Curso(
                        rs.getInt("id"),
                        rs.getString("nome"),
                        rs.getInt("numero_alunos")
                );
                cursos.add(curso);
            }
        }
        return cursos;
    }

    // Update
    public void atualizarCurso(Curso curso) throws SQLException {
        String sql = "UPDATE CURSO SET nome = ?, numero_alunos = ? WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setString(1, curso.getNome());
            stmt.setInt(2, curso.getNumeroAlunos());
            stmt.setInt(3, curso.getId());
            stmt.executeUpdate();
        }
    }

    // Delete
    public void deletarCurso(int id) throws SQLException {
        String sql = "DELETE FROM CURSO WHERE id = ?";
        try (PreparedStatement stmt = conexao.prepareStatement(sql)) {
            stmt.setInt(1, id);
            stmt.executeUpdate();
        }
    }
}
