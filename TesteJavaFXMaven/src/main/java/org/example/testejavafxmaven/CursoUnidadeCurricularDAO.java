package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CursoUnidadeCurricularDAO extends GenericDAO<CursoUnidadeCurricular> {

    @Override
    protected CursoUnidadeCurricular mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new CursoUnidadeCurricular(
                rs.getInt("id_curso"),
                rs.getInt("id_uc")
        );
    }

    public void save(CursoUnidadeCurricular cursoUc) {
        String query = "INSERT INTO CursosUnidadesCurriculares (id_curso, id_uc) VALUES (?, ?)";
        executeUpdate(query, cursoUc.getIdCurso(), cursoUc.getIdUnidadeCurricular());
    }

    public void delete(int idCurso, int idUc) {
        String query = "DELETE FROM CursosUnidadesCurriculares WHERE id_curso = ? AND id_uc = ?";
        executeUpdate(query, idCurso, idUc);
    }

    public void associarCursoUnidadeCurricular(int idCurso, int idUC) throws SQLException {
        String query = "INSERT INTO CursosUnidadesCurriculares (id_curso, id_uc) VALUES (?, ?)";
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, idCurso);
            stmt.setInt(2, idUC);
            stmt.executeUpdate();
        }
    }
}
