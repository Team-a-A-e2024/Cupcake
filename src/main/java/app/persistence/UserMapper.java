package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserMapper {

    public static void addUserByObject(ConnectionPool connectionPool, User user) throws DatabaseException
    {
        String sql = "insert into users VALUES (?,?,?,?,?); ;";
        try (Connection connection = connectionPool.getConnection())
        {
            try (PreparedStatement ps = connection.prepareStatement(sql))
            {

                ResultSet rs = ps.executeQuery();

            }
        }
        catch (SQLException ex)
        {
            throw new DatabaseException(ex, "Could not get users from database");
        }
    }
}
