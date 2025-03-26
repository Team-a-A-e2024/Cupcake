package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static void addUserByObject(ConnectionPool connectionPool, User user) throws DatabaseException
    {
        String sql = "insert into users (email,password,role,credit) VALUES (?,?,?,?); ;";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {
                ps.setString(1,user.getEmail());
                ps.setString(2,user.getPassword());
                ps.setString(3,user.getRole());
                ps.setDouble(4,user.getCredit());
                ResultSet rs = ps.executeQuery();
            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not insert user into database");
        }
    }

}
