package app.persistence;

import app.entities.User;
import java.sql.*;

public class UserMapper {

    public static User getUserByEmailAndPassword(String email, String password, ConnectionPool connectionPool) {
        String sql = "SELECT * FROM users WHERE email = ? AND password = ?";

        try (Connection conn = connectionPool.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, email);
            ps.setString(2, password);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new User(
                        rs.getInt("id"),
                        rs.getString("role"),
                        rs.getString("email"),
                        rs.getString("password"),
                        rs.getDouble("credit")
                );
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public static void setConnectionPool(ConnectionPool connectionPool) {
    }
}
