package org.example.testejavafxmaven;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public List<Integer> listarUnidadesPorCurso(int cursoId) throws SQLException {
        String query = "SELECT id_uc FROM CursoUnidadeCurricular WHERE id_curso = ?";
        List<Integer> unidadesAssociadas = new ArrayList<>();

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {
            stmt.setInt(1, cursoId);
            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    unidadesAssociadas.add(rs.getInt("id_uc"));
                }
            }
        }
        return unidadesAssociadas;
    }

    public void atualizarAssociacoes(int cursoId, List<UnidadeCurricular> ucs) throws SQLException {
        String deleteQuery = "DELETE FROM CursoUnidadeCurricular WHERE id_curso = ?";
        String insertQuery = "INSERT INTO CursoUnidadeCurricular (id_curso, id_uc) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection()) {
            conn.setAutoCommit(false); // Início da transação.

            // Remover associações antigas.
            try (PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery)) {
                deleteStmt.setInt(1, cursoId);
                deleteStmt.executeUpdate();
            }

            // Inserir novas associações.
            try (PreparedStatement insertStmt = conn.prepareStatement(insertQuery)) {
                for (UnidadeCurricular uc : ucs) {
                    insertStmt.setInt(1, cursoId);
                    insertStmt.setInt(2, uc.getId());
                    insertStmt.addBatch();
                }
                insertStmt.executeBatch();
            }

            conn.commit(); // Finalizar a transação.
        }
    }


}
