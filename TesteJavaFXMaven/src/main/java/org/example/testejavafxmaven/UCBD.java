package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UCBD implements UCDAO {

    @Override
    public void salvarUC(String nome, Integer cursoId) {
        String sql = "INSERT INTO UCs (nome, curso_id) VALUES (?, ?)";

        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nome);
            if (cursoId != null) {
                stmt.setInt(2, cursoId);  // Associa a UC ao curso se o cursoId não for null
            } else {
                stmt.setNull(2, java.sql.Types.INTEGER);  // Caso não tenha um curso associado, setamos null
            }
            stmt.executeUpdate();

            System.out.println("UC " + nome + " adicionada com sucesso.");
        } catch (SQLException e) {
            System.err.println("Erro ao adicionar UC.");
            e.printStackTrace();
        }
    }

    @Override
    public List<UC> buscarUcsPorCurso(int cursoId) {
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
    @Override
    public List<UC> buscarTodasUCs() {
        List<UC> ucs = new ArrayList<>();
        String sql = "SELECT * FROM ucs";

        // Usando try-with-resources para garantir o fechamento correto dos recursos.
        try (Connection conn = DataBaseConnection.getConnection();
             PreparedStatement pstmt = conn.prepareStatement(sql);
             ResultSet rs = pstmt.executeQuery()) {

            while (rs.next()) {
                int id = rs.getInt("id");
                String nome = rs.getString("nome");
                UC uc = new UC(id, nome);  // Criando o objeto UC com os dados do ResultSet
                ucs.add(uc);  // Adicionando à lista de UCs
            }

        } catch (SQLException e) {
            System.err.println("Erro ao buscar UCs no banco de dados: " + e.getMessage());
            e.printStackTrace();
        }

        return ucs;
    }


}
