package org.example.testejavafxmaven;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public abstract class GenericDAO<T> {
    protected Connection getConnection() {
        return DataBaseConnection.getConnection();
    }

    protected abstract T mapResultSetToEntity(ResultSet rs) throws SQLException;

    public List<T> findAll(String query, Object... params) {
        List<T> entities = new ArrayList<>();
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                while (rs.next()) {
                    entities.add(mapResultSetToEntity(rs));
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return entities;
    }

    public void executeUpdate(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }
            stmt.executeUpdate();

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public T findOne(String query, Object... params) {
        try (Connection conn = getConnection();
             PreparedStatement stmt = conn.prepareStatement(query)) {

            for (int i = 0; i < params.length; i++) {
                stmt.setObject(i + 1, params[i]);
            }

            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    return mapResultSetToEntity(rs);
                }
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
