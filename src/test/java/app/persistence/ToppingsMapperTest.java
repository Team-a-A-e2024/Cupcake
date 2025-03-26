package app.persistence;

import app.entities.Topping;
import app.exceptions.DatabaseException;
import app.test.SetupDatabase;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationTest")
class ToppingsMapperTest {
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
    void getToppings() throws DatabaseException {
        List<Topping> toppings = new ArrayList<>();
        toppings.add(new Topping("Chocolate", 5.00));
        toppings.add(new Topping("Blueberry", 5.00));
        toppings.add(new Topping("Raspberry", 5.00));
        toppings.add(new Topping("Crispy", 6.00));
        toppings.add(new Topping("Strawberry", 6.00));
        toppings.add(new Topping("Rum/Raisin", 7.00));
        toppings.add(new Topping("Orange", 8.00));
        toppings.add(new Topping("Lemon", 8.00));
        toppings.add(new Topping("Blue cheese", 9.00));

        assertEquals(toppings, ToppingsMapper.getToppings());
    }
}