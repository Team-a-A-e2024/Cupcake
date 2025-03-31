package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.entities.User;
import app.util.PasswordUtil;
import org.junit.jupiter.api.*;
import app.test.SetupDatabase;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationTest")
class UsersMapperTest {

    @BeforeAll
    public static void beforeAll() {
        try {
            SetupDatabase.createTables();
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    @BeforeEach
    void beforeEach() {
        try {
            SetupDatabase.seedTables();
        } catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(SetupDatabase.getConnectionPool().getConnection());
    }

    @Test
    void createUser() throws DatabaseException {
        // Arrange
        User expected = new User(6, "test", "test", null, 100);

        // Act
        User actual = UsersMapper.addUserByObject(expected);

        // Assert
        assertEquals(expected, actual);
    }

    @Test
    void getEmailValidUser() throws DatabaseException {
        String email = "cph-ab632@cphbusiness.dk";

        User user = UsersMapper.getUserByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertNull(user.getRole());
        assertEquals(0, user.getCredit());
    }

    @Test
    void getEmailInvalidUser() throws DatabaseException {
        String email = "fake";

        User user = UsersMapper.getUserByEmail(email);

        assertNull(user);
    }

    @Test
    void testLoginValidUser() throws DatabaseException {
        String email = "cph-ab632@cphbusiness.dk";
        String password = "Test1";

        User user = UsersMapper.getUserByEmail(email);

        assertNotNull(user);
        assertEquals(email, user.getEmail());
        assertNull(user.getRole());
        assertEquals(0, user.getCredit());
        assertEquals(password, user.getPassword());
    }

    @Test
    void testLoginWrongPassword() throws DatabaseException {
        String email = "admin@olsker.dk";
        String wrongPassword = "WrongPass";

        User user = UsersMapper.getUserByEmail(email);

        assertNotEquals(wrongPassword, user.getPassword());;
    }

    @Test
    void testLoginUnknownUser() throws DatabaseException {
        String email = "not@existing.dk";

        User user = UsersMapper.getUserByEmail(email);

        assertNull(user);
    }

    @Test
    void getUsersAndOrders() throws DatabaseException {
        List<User> expected = new ArrayList<>();
        expected.add(new User(1, "admin@olsker.dk", 999999999, new ArrayList<>()));
        expected.add(new User(2, "cph-ab632@cphbusiness.dk", 0, new ArrayList<>()));
        expected.add(new User(3, "cph-ea178@cphbusiness.dk", 0, new ArrayList<>()));
        expected.add(new User(4, "cph-fb157@cphbusiness.dk", 0, new ArrayList<>()));
        expected.add(new User(5, "cph-ta241@cphbusiness.dk", 0, new ArrayList<>()));
        expected.get(0).addOrder(new Order(1, 1, "Chocolate", "Chocolate", 1, false));
        expected.get(0).addOrder(new Order(2, 1, "Blueberry", "Vanilla", 1, true));
        expected.get(1).addOrder(new Order(3, 2, "Raspberry", "Nutmeg", 1, false));
        expected.get(1).addOrder(new Order(4, 2, "Crispy", "Pistacio", 1, true));
        expected.get(2).addOrder(new Order(5, 3, "Strawberry", "Almond", 1, false));
        expected.get(2).addOrder(new Order(6, 3, "Rum/Raisin", "Chocolate", 1, true));
        expected.get(3).addOrder(new Order(7, 4, "Orange", "Vanilla", 1, false));
        expected.get(3).addOrder(new Order(8, 4, "Lemon", "Nutmeg", 1, true));
        expected.get(4).addOrder(new Order(9, 5, "Blue cheese", "Pistacio", 1, false));
        expected.get(4).addOrder(new Order(10, 5, "Chocolate", "Almond", 1, true));

        List<User> actual = UsersMapper.getUsersAndOrders();

        assertEquals(expected, actual);
    }

    @Test
    void updateUserCredit() throws DatabaseException {
        User user = new User(1, "admin@olsker.dk", 500.0, new ArrayList<>());
        double expectedCredit = 750.0;
        user.setCredit(expectedCredit);

        UsersMapper.updateUserCredit(user);
        User updatedUser = UsersMapper.getUserByEmail(user.getEmail());

        assertEquals(expectedCredit, updatedUser.getCredit());
    }
}