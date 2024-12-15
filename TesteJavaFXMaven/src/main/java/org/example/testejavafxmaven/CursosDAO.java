package org.example.testejavafxmaven;

import org.example.testejavafxmaven.Cursos;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CursosDAO extends GenericDAO<Cursos> {

    @Override
    protected Cursos mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Cursos(rs.getInt("id_curso"), rs.getString("nome"));
    }

    public void save(Cursos curso) {
        String query = "INSERT INTO Cursos (nome) VALUES (?)";
        executeUpdate(query, curso.getNome());
    }

    public void update(Cursos curso) {
        String query = "UPDATE Cursos SET nome = ? WHERE id_curso = ?";
        executeUpdate(query, curso.getNome(), curso.getId());
    }

    public void delete(int id) {
        String query = "DELETE FROM Cursos WHERE id_curso = ?";
        executeUpdate(query, id);
    }

    public void adicionarCurso(String nome) throws SQLException {
        String query = "INSERT INTO Cursos (nome) VALUES (?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setString(1, nome);
            stmt.executeUpdate();
        }
    }

    public List<Cursos> listarCursos() throws SQLException {
        String query = "SELECT * FROM Cursos";
        List<Cursos> cursos = new ArrayList<>();
        try (Connection conn = DataBaseConnection.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {
            while (rs.next()) {
                cursos.add(new Cursos(rs.getInt("id_curso"), rs.getString("nome")));
            }
        }
        return cursos;
    }
}