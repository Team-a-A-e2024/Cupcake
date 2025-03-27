package app.persistence;

import app.entities.Topping;
import app.exceptions.DatabaseException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ToppingsMapper {
    private static ConnectionPool connectionPool;

    public static void setConnectionPool(ConnectionPool connectionPool) {
        ToppingsMapper.connectionPool = connectionPool;
    }

    public static List<Topping> getToppings() throws DatabaseException {
        List<Topping> toppings = new ArrayList<>();
        String sql = "SELECT * FROM toppings";

        try (
                Connection connection = connectionPool.getConnection();
                PreparedStatement ps = connection.prepareStatement(sql)
        ) {
            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                String topping = rs.getString("topping");
                double price = rs.getDouble("price");
                toppings.add(new Topping(topping, price));
            }
        }
        catch (SQLException e) {
            throw new DatabaseException(e.getMessage());
        }

        return toppings;
    }
}