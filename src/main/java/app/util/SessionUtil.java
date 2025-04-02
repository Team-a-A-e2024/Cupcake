package app.util;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.persistence.UsersMapper;

public class SessionUtil {

    public static User UpdateUser(User user) {
        if (user == null) return null;

        try {
            user = UsersMapper.getUserByEmail(user.getEmail());
            return user;
        }
        catch (DatabaseException e) {
            e.printStackTrace();
        }

        return null;
    }
}
