package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class OrdersMapper {
    private static ConnectionPool connectionPool;

    public static void setConnectionPool(ConnectionPool connectionPool) {
        OrdersMapper.connectionPool = connectionPool;
    }

    public static Order createOrder(Order order) throws DatabaseException {
        String sql = "INSERT INTO orders (user_id, topping, bottom, amount, is_processed) " +
                "VALUES (?, ?, ?, ?, ?) RETURNING id";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql);
                ) {
            ps.setInt(1, order.getUserId());
            ps.setString(2, order.getTopping());
            ps.setString(3, order.getBottom());
            ps.setInt(4, order.getAmount());
            ps.setBoolean(5, order.isProcessed());

            try (var rs = ps.executeQuery()) {
                if (rs.next()) {
                    int id = rs.getInt("id");

                    return new Order(
                            id,
                            order.getUserId(),
                            order.getTopping(),
                            order.getBottom(),
                            order.getAmount(),
                            order.isProcessed()
                    );
                }
                else {
                    throw new DatabaseException("Failed to place order");
                }
            }
        } catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }
    }
}
