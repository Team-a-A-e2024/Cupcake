package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    public static List<Order> getUnprocessedOrders(int userId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT * FROM orders WHERE user_id = ? AND is_processed = false";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setInt(1, userId);

            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    int id = rs.getInt("id");
                    String topping = rs.getString("topping");
                    String bottom = rs.getString("bottom");
                    int amount = rs.getInt("amount");
                    boolean isProcessed = rs.getBoolean("is_processed");

                    orders.add(new Order(id, userId, topping, bottom, amount, isProcessed));
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return orders;
    }

    public static List<Order> getOrdersWithPrice(int userId) throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        String sql = "SELECT o.*, t.topping, t.price AS topping_price, b.bottom, b.price AS bottom_price\n" +
                "FROM orders o \n" +
                "JOIN bottoms b ON o.bottom = b.bottom\n" +
                "JOIN toppings t ON o.topping = t.topping\n" +
                "WHERE user_id = ? AND is_processed = false";
        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, userId);
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                int id = rs.getInt("id");

                String topping = rs.getString("topping");
                String bottom = rs.getString("bottom");
                int amount = rs.getInt("amount");
                boolean isProcessed = rs.getBoolean("is_processed");
                double price = rs.getDouble("topping_price");
                price += rs.getDouble("bottom_price");
                orders.add(new Order(id, userId, topping, bottom, amount, isProcessed, price));
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
        return orders;
    }

    public static boolean removeOrderById(int id) throws DatabaseException {
        String sql = "DELETE FROM Orders WHERE id = ?";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
        ) {
            ps.setInt(1, id);
            return ps.executeUpdate() == 1;

        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }

    public static boolean updateProcessStatus(boolean processedStatus, int id) throws DatabaseException {
        String sql = "UPDATE orders SET is_processed = ? WHERE id = ?;";

        try (Connection connection = connectionPool.getConnection();
             PreparedStatement ps = connection.prepareStatement(sql)) {

            ps.setBoolean(1, processedStatus);
            ps.setInt(2, id);

            int rowsAffected = ps.executeUpdate();

            if (rowsAffected == 0) {
                throw new DatabaseException("Could not update the processed status in orders");
            }else return true;

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }
    }
}