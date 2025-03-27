package app.persistence;

import app.exceptions.DatabaseException;
import app.models.User;
import org.junit.jupiter.api.*;
import app.test.SetupDatabase;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions .*;


    class UserMapperTest {

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

        @Test
        void testConnection() throws SQLException {
            assertNotNull(SetupDatabase.getConnectionPool().getConnection());
        }

        @Test
        void testLoginValidUser() throws DatabaseException {
            String email = "cph-ab632@cphbusiness.dk";
            String password = "Test1";

            User user = UserMapper.getUserByEmailAndPassword(email, password, SetupDatabase.getConnectionPool());

            assertNotNull(user);
            assertEquals(email, user.getEmail());
            assertNull(user.getRole());
            assertEquals(0, user.getCredit());
        }

        @Test
        void testLoginWrongPassword() throws DatabaseException {
            String email = "admin@olsker.dk";
            String wrongPassword = "WrongPass";

            User user = UserMapper.getUserByEmailAndPassword(email, wrongPassword, SetupDatabase.getConnectionPool());

            assertNull(user);
        }

        @Test
        void testLoginUnknownUser() throws DatabaseException {
            String email = "not@existing.dk";
            String password = "whatever";

            User user = UserMapper.getUserByEmailAndPassword(email, password, SetupDatabase.getConnectionPool());

            assertNull(user);
        }
    }
