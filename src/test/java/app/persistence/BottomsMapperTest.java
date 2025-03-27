package app.persistence;

import app.entities.Bottom;
import app.exceptions.DatabaseException;
import app.test.SetupDatabase;
import org.junit.jupiter.api.*;

import java.sql.SQLException;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@Tag("IntegrationTest")
class BottomsMapperTest {

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
    void getBottoms() throws DatabaseException {
        List<Bottom> bottoms = new ArrayList<>();
        bottoms.add(new Bottom("Chocolate", 5.00));
        bottoms.add(new Bottom("Vanilla", 5.00));
        bottoms.add(new Bottom("Nutmeg", 5.00));
        bottoms.add(new Bottom("Pistacio", 6.00));
        bottoms.add(new Bottom("Almond", 7.00));

        assertEquals(bottoms, BottomsMapper.getBottoms());
    }
}