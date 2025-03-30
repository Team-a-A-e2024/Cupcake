package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;
import app.test.SetupDatabase;
import org.junit.jupiter.api.*;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

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

    @Test
    void getOrders() throws DatabaseException {
        List<Order> orders = new ArrayList<>();
        orders.add(new Order(1, 1, "Chocolate", "Chocolate", 1, false));
        orders.add(new Order(2, 1, "Blueberry", "Vanilla", 1, true));
        orders.add(new Order(3, 2, "Raspberry", "Nutmeg", 1, false));
        orders.add(new Order(4, 2, "Crispy", "Pistacio", 1, true));
        orders.add(new Order(5, 3, "Strawberry", "Almond", 1, false));
        orders.add(new Order(6, 3, "Rum/Raisin", "Chocolate", 1, true));
        orders.add(new Order(7, 4, "Orange", "Vanilla", 1, false));
        orders.add(new Order(8, 4, "Lemon", "Nutmeg", 1, true));
        orders.add(new Order(9, 5, "Blue cheese", "Pistacio", 1, false));
        orders.add(new Order(10, 5, "Chocolate", "Almond", 1, true));

        assertEquals(orders, OrdersMapper.getOrders());
    }

    @Test
    void removeOrder() throws DatabaseException {
        assertTrue(OrdersMapper.removeOrderById(1));
        assertFalse(OrdersMapper.removeOrderById(1));
    }
}