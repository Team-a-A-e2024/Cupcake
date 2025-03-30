package app.persistence;

import app.entities.Order;
import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UsersMapper {
    private static ConnectionPool connectionPool;

    public static void setConnectionPool(ConnectionPool connectionPool) {
        UsersMapper.connectionPool = connectionPool;
    }

    public static User addUserByObject(User user) throws DatabaseException
    {
        String sql = "insert into users (email,password,role,credit) VALUES (?,?,?,?) returning id ;";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1,user.getEmail());
                ps.setString(2,user.getPassword());
                ps.setString(3,user.getRole());
                ps.setDouble(4,user.getCredit());
                try (var rs = ps.executeQuery()) {
                    if (rs.next()) {
                        int id = rs.getInt("id");

                        return new User(
                                id,
                                user.getEmail(),
                                user.getPassword(),
                                user.getRole(),
                                user.getCredit()
                        );
                    }
                    else {
                        throw new DatabaseException("Failed to insert user");
                    }
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }
    public static User getUserByEmailAndPassword(String email, String password) throws DatabaseException{
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDouble("credit")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }

        return null;
    }

    public static User getUserByEmail(String email) throws DatabaseException{
        String sql = "SELECT * FROM users WHERE email = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getString("role"),
                        rs.getDouble("credit")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }

        return null;
    }

    public static List<User> getUsersAndOrders() throws DatabaseException {
        String sql = "SELECT u.email, u.credit, o.* " +
                "FROM users u " +
                "INNER JOIN orders o " +
                "ON o.user_id = u.id;";
        List<User> users = new ArrayList<>();
        User user = null;

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ResultSet rs = ps.executeQuery();

            while (rs.next()) {
                if (user == null || rs.getInt("user_id") != user.getId()) {
                    user = new User(
                            rs.getInt("user_id"),
                            rs.getString("email"),
                            rs.getDouble("credit"),
                            new ArrayList<>()
                    );

                    users.add(user);
                }

                user.addOrder(new Order(
                        rs.getInt("id"),
                        rs.getInt("user_id"),
                        rs.getString("topping"),
                        rs.getString("bottom"),
                        rs.getInt("amount"),
                        rs.getBoolean("is_processed")
                ));
            }

        } catch (SQLException e) {
            e.printStackTrace();
            throw new DatabaseException(e.getMessage());
        }

        return users;
    }
}
