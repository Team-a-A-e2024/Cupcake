package app.persistence;

import app.entities.Order;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.*;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationTest")
class OrdersMapperTest {
    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "cupcake";

    private static final ConnectionPool connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB);

    @BeforeAll
    public static void beforeAll() {
        OrdersMapper.setConnectionPool(connectionPool);

        try {
            Connection connection = connectionPool.getConnection();

            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DROP TABLE IF EXISTS test.users CASCADE");
                stmt.execute("DROP TABLE IF EXISTS test.toppings CASCADE");
                stmt.execute("DROP TABLE IF EXISTS test.bottoms CASCADE");
                stmt.execute("DROP TABLE IF EXISTS test.orders CASCADE");

                stmt.execute("DROP SEQUENCE IF EXISTS test.orders_id_seq");
                stmt.execute("DROP SEQUENCE IF EXISTS test.users_id_seq");

                stmt.execute("CREATE TABLE test.users AS (SELECT * FROM public.users) WITH NO DATA");
                stmt.execute("CREATE TABLE test.toppings AS (SELECT * FROM public.toppings) WITH NO DATA");
                stmt.execute("CREATE TABLE test.bottoms AS (SELECT * FROM public.bottoms) WITH NO DATA");
                stmt.execute("CREATE TABLE test.orders AS (SELECT * FROM public.orders) WITH NO DATA");

                stmt.execute("CREATE SEQUENCE test.orders_id_seq");
                stmt.execute("ALTER TABLE test.orders ALTER COLUMN id SET DEFAULT nextval('test.orders_id_seq')");
                stmt.execute("CREATE SEQUENCE test.users_id_seq");
                stmt.execute("ALTER TABLE test.users ALTER COLUMN id SET DEFAULT nextval('test.users_id_seq')");
            }
            catch (SQLException e) {
                fail(e.getMessage());
            }

        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection connection = connectionPool.getConnection()) {
            try (Statement stmt = connection.createStatement()) {
                stmt.execute("DELETE FROM test.users CASCADE");
                stmt.execute("DELETE FROM test.toppings CASCADE");
                stmt.execute("DELETE FROM test.bottoms CASCADE");
                stmt.execute("DELETE FROM test.orders CASCADE");

                stmt.execute("SELECT setval('test.orders_id_seq', 1, false)");
                stmt.execute("SELECT setval('test.users_id_seq', 1, false)");

                stmt.execute("INSERT INTO test.users (id, email, password, role, credit) " +
                        "VALUES " +
                        "(DEFAULT, 'admin@olsker.dk', 'Cupc4k3!', 'admin', 999999999), " +
                        "(DEFAULT, 'cph-ab632@cphbusiness.dk', 'Test1', null, 0), " +
                        "(DEFAULT, 'cph-ea178@cphbusiness.dk', 'Test2', null, 0), " +
                        "(DEFAULT, 'cph-fb157@cphbusiness.dk', 'Test3', null, 0), " +
                        "(DEFAULT, 'cph-ta241@cphbusiness.dk', 'Test4', null, 0);");

                stmt.execute("INSERT INTO test.toppings (topping, price) " +
                        "VALUES " +
                        "('Chocolate', 5.00), " +
                        "('Blueberry', 5.00), " +
                        "('Raspberry', 5.00), " +
                        "('Crispy', 6.00), " +
                        "('Strawberry', 6.00), " +
                        "('Rum/Raisin', 7.00), " +
                        "('Orange', 8.00), " +
                        "('Lemon', 8.00), " +
                        "('Blue cheese', 9.00);");

                stmt.execute("INSERT INTO test.bottoms (bottom, price) " +
                        "VALUES " +
                        "('Chocolate', 5.00), " +
                        "('Vanilla', 5.00), " +
                        "('Nutmeg', 5.00), " +
                        "('Pistacio', 6.00), " +
                        "('Almond', 7.00);");

                stmt.execute("INSERT INTO test.orders (id, user_id, topping, bottom, amount, is_processed) " +
                        "VALUES " +
                        "(DEFAULT, 1, 'Chocolate', 'Chocolate', 1, false), " +
                        "(DEFAULT, 1, 'Blueberry', 'Vanilla', 1, true), " +
                        "(DEFAULT, 2, 'Raspberry', 'Nutmeg', 1, false), " +
                        "(DEFAULT, 2, 'Crispy', 'Pistacio', 1, true), " +
                        "(DEFAULT, 3, 'Strawberry', 'Almond', 1, false), " +
                        "(DEFAULT, 3, 'Rum/Raisin', 'Chocolate', 1, true), " +
                        "(DEFAULT, 4, 'Orange', 'Vanilla', 1, false), " +
                        "(DEFAULT, 4, 'Lemon', 'Nutmeg', 1, true), " +
                        "(DEFAULT, 5, 'Blue cheese', 'Pistacio', 1, false), " +
                        "(DEFAULT, 5, 'Chocolate', 'Almond', 1, true);");

                stmt.execute("SELECT setval('test.orders_id_seq', COALESCE((SELECT MAX(id) FROM test.orders)+1, 1), false)");
                stmt.execute("SELECT setval('test.users_id_seq', COALESCE((SELECT MAX(id) FROM test.users)+1, 1), false)");

            } catch (SQLException e) {
                fail(e.getMessage());
            }
        } catch (SQLException e) {
            fail(e.getMessage());
        }
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection());
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