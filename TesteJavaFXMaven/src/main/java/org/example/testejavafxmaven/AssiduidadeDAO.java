package org.example.testejavafxmaven;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class AssiduidadeDAO extends GenericDAO<Assiduidade> {

    @Override
    protected Assiduidade mapResultSetToEntity(ResultSet rs) throws SQLException {
        return new Assiduidade(
                rs.getInt("id_assiduidade"),
                rs.getInt("id_avaliacao"),
                rs.getString("obrigatorio")
        );
    }

    public void adicionarAssiduidade(int idAvaliacao, String obrigatorio) throws SQLException {
        String query = "INSERT INTO Assiduidade (id_avaliacao, obrigatorio) VALUES (?, ?)";
        executeUpdate(query, idAvaliacao, obrigatorio);
    }

    public void atualizarAssiduidade(int idAssiduidade, String obrigatorio) throws SQLException {
        String query = "UPDATE Assiduidade SET obrigatorio = ? WHERE id_assiduidade = ?";
        executeUpdate(query, obrigatorio, idAssiduidade);
    }

    public void deletarAssiduidade(int idAssiduidade) throws SQLException {
        String query = "DELETE FROM Assiduidade WHERE id_assiduidade = ?";
        executeUpdate(query, idAssiduidade);
    }

    public List<Assiduidade> listarAssiduidade() throws SQLException {
        String query = "SELECT * FROM Assiduidade";
        return findAll(query);
    }
}
