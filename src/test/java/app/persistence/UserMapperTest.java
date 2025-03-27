package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import app.test.SetupDatabase;
import org.junit.jupiter.api.*;
import java.sql.SQLException;
import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {

    @BeforeAll
    public static void beforeAll(){
        try {
            SetupDatabase.createTables();
        }
        catch (DatabaseException e) {
            fail(e.getMessage());
        }
    }

    @BeforeEach
    void beforeEach() {
        try {
            SetupDatabase.seedTables();
        }
        catch (DatabaseException e) {
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
        app.entities.User expected = new app.entities.User(6,"test@test.test","1234", null, 200d);

        // Act
        User actual = UsersMapper.addUserByObject(expected);

        // Assert
        assertEquals(expected, actual);
    }
}