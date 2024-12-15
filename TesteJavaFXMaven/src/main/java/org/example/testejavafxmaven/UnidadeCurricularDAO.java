package org.example.testejavafxmaven;

import org.example.testejavafxmaven.UnidadeCurricular;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UnidadeCurricularDAO extends GenericDAO<UnidadeCurricular> {

    @Override
    protected UnidadeCurricular mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new UnidadeCurricular(
                rs.getInt("id_uc"),
                rs.getString("nome"),
                rs.getString("tipo_avaliacao")
        );
    }

    public void save(UnidadeCurricular uc) {
        String query = "INSERT INTO UnidadesCurriculares (nome, tipo_avaliacao) VALUES (?, ?)";
        executeUpdate(query, uc.getNome(), uc.getTipoAvaliacao());
    }

    public void update(UnidadeCurricular uc) {
        String query = "UPDATE UnidadesCurriculares SET nome = ?, tipo_avaliacao = ? WHERE id_uc = ?";
        executeUpdate(query, uc.getNome(), uc.getTipoAvaliacao(), uc.getId());
    }

    public void delete(int id) {
        String query = "DELETE FROM UnidadesCurriculares WHERE id_uc = ?";
        executeUpdate(query, id);
    }
}