package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;

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
                        throw new DatabaseException("Failed to place order");
                    }
                }
            }
        }
        catch (SQLException e)
        {
            throw new DatabaseException(e.getMessage());
        }
    }

}
