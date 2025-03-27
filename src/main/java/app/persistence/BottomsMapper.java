package app.persistence;

import app.entities.Bottom;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BottomsMapper {
    private static ConnectionPool connectionPool;

    public static void setConnectionPool(ConnectionPool connectionPool) {
        BottomsMapper.connectionPool = connectionPool;
    }

    public static List<Bottom> getBottoms() throws DatabaseException {
        List<Bottom> bottoms = new ArrayList<>();
        String sql = "SELECT * FROM bottoms";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String bottom = rs.getString("bottom");
                double price = rs.getDouble("price");
                bottoms.add(new Bottom(bottom, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return bottoms;
    }
}