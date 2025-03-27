package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class OrdersMapper {

    public List<Order> getUnprocessedOrders(ConnectionPool connectionPool, int userId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? AND is_processed = false";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                String topping = rs.getString("topping");
                String bottom = rs.getString("bottom");
                int amount = rs.getInt("amount");
                boolean isProcessed = rs.getBoolean("is_processed");

                orders.add(new Order(id, userId, topping, bottom, amount, isProcessed));

            }
        } catch (SQLException e) {
            throw new DatabaseException(e, "Could not get orders from database");
        }
        return orders;
    }
}