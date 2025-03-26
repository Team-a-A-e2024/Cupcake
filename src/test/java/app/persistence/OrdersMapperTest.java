package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.test.SetupDatabase;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationTest")
class OrdersMapperTest {
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
    void createOrder() throws DatabaseException {
        // Arrange
        Order expected = new Order(11,1, "Chocolate", "Chocolate", 1, false);

        // Act
        Order actual = OrdersMapper.createOrder(expected);

        // Assert
        assertEquals(expected, actual);
    }
}