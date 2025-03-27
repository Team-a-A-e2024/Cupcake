package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class OrdersMapper {
    private static ConnectionPool connectionPool;

    public static void setConnectionPool(ConnectionPool connectionPool) {
        OrdersMapper.connectionPool = connectionPool;
    }

    public static Order createOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, topping, bottom, amount, is_processed) " + "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (Connection connection = connectionPool.getConnection(); PreparedStatement ps = connection.prepareStatement(sql);) {
            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getTopping());
            ps.setString(3, order.getBottom());
            ps.setInt(4, order.getAmount());
            ps.setBoolean(5, order.isProcessed());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    return new Order(id, order.getUserId(), order.getTopping(), order.getBottom(), order.getAmount(), order.isProcessed());
                } else {
                    throw new DatabaseException("Failed to place order");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static List<Order> getOrders() throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String topping = rs.getString("topping");
                String bottom = rs.getString("bottom");
                int amount = rs.getInt("amount");
                boolean isProcessed = rs.getBoolean("is_processed");
                orders.add(new Order(id, userId, topping, bottom, amount, isProcessed));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return orders;
    }

    public static Map<String, Order> getCustomerAndOrder() throws DatabaseException {
        Map<String, Order> customerAndOrders = new HashMap<>();

        String sql = "SELECT u.email, o.* " +
                "FROM users u " +
                "INNER JOIN orders o " +
                "ON o.user_id = u.id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ResultSet rs = ps.executeQuery();

            while(rs.next()) {
                String user = rs.getString("email");
                int id = rs.getInt("id");
                int userId = rs.getInt("user_id");
                String topping = rs.getString("topping");
                String bottom = rs.getString("bottom");
                int amount = rs.getInt("amount");
                boolean isProcessed = rs.getBoolean("is_processed");
                customerAndOrders.put(user, new Order(id, userId, topping, bottom, amount, isProcessed));
            }

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return customerAndOrders;
    }
}
