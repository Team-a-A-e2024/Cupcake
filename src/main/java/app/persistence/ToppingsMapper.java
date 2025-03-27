package app.persistence;

import app.exceptions.DatabaseException;

import java.awt.image.DataBufferInt;
import java.sql.*;
import java.util.HashMap;
import java.util.Map;

public class ToppingsMapper {

    public Map<String, Integer> getAllToppings(ConnectionPool connectionPool) throws DatabaseException {
        Map<String, Integer> toppings = new HashMap<>();
        String sql = "SELECT * FROM toppings";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)){
             ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String name = rs.getString("topping");
                int price = rs.getInt("price");
                toppings.put(name, price);
            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get toppings from database");
        }
        return toppings;
    }
}
