package org.example.testejavafxmaven;

import org.example.testejavafxmaven.Cursos;
import java.sql.ResultSet;
import java.sql.SQLException;

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
}