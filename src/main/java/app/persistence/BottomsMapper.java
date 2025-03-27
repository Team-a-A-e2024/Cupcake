package app.persistence;

import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class BottomsMapper {

    private final ConnectionPool connectionPool;

    public BottomsMapper(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    public Map<String, Integer> getAllBottoms() throws DatabaseException {
        Map<String, Integer> bottoms = new HashMap<>();
        String sql = "SELECT * FROM bottoms";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("bottom");
                int price = rs.getInt("price");
                bottoms.put(name, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get bottoms from database");
        }
        return bottoms;
    }
}