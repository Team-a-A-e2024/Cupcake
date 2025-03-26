package app.persistence;

import app.entities.User;
import app.exceptions.DatabaseException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class UserMapperTest {


    private static final String USER = "postgres";
    private static final String PASSWORD = "postgres";
    private static final String URL = "jdbc:postgresql://localhost:5432/%s?currentSchema=test";
    private static final String DB = "cupcake";
    private static ConnectionPool connectionPool;

    @BeforeAll
    public static void setUpClass() {

        connectionPool = ConnectionPool.getInstance(USER, PASSWORD, URL, DB); //creates a db object
        try (Connection testConnection = connectionPool.getConnection()) //tries to connect to a databse through the object
        {
            try (Statement stmt = testConnection.createStatement()) //tries to create a statement to execute sql queries
            {
                try {
                    stmt.execute("DROP TABLE IF EXISTS test.users CASCADE;\n");
                    stmt.execute("DROP TABLE IF EXISTS test.toppings CASCADE;");
                    stmt.execute("DROP TABLE IF EXISTS test.bottoms CASCADE;");
                    stmt.execute("DROP TABLE IF EXISTS test.orders CASCADE;\n");

                    stmt.execute("CREATE TABLE IF NOT EXISTS test.users\n" +
                            "(\n" +
                            "    id serial NOT NULL,\n" +
                            "    email character varying(64) NOT NULL,\n" +
                            "    password character varying(64) NOT NULL,\n" +
                            "    role character varying(64),\n" +
                            "    credit double precision NOT NULL,\n" +
                            "    PRIMARY KEY (id)\n" +
                            ");");
                    stmt.execute("CREATE TABLE IF NOT EXISTS test.toppings\n" +
                            "(\n" +
                            "    topping character varying(64) NOT NULL,\n" +
                            "    price integer NOT NULL,\n" +
                            "    PRIMARY KEY (topping)\n" +
                            ");");
                    stmt.execute("CREATE TABLE IF NOT EXISTS test.bottoms\n" +
                            "(\n" +
                            "    bottom character varying(64) NOT NULL,\n" +
                            "    price integer NOT NULL,\n" +
                            "    PRIMARY KEY (bottom)\n" +
                            ");");
                    stmt.execute("CREATE TABLE IF NOT EXISTS test.orders\n" +
                            "(\n" +
                            "    id serial NOT NULL,\n" +
                            "    user_id integer NOT NULL,\n" +
                            "    topping character varying(64) NOT NULL,\n" +
                            "    bottom character varying(64) NOT NULL,\n" +
                            "    amount integer NOT NULL,\n" +
                            "    is_processed boolean NOT NULL,\n" +
                            "    PRIMARY KEY (id)\n" +
                            ");");
                    stmt.execute("ALTER TABLE IF EXISTS test.orders\n" +
                            "    ADD FOREIGN KEY (user_id)\n" +
                            "    REFERENCES public.users (id) MATCH SIMPLE\n" +
                            "    ON UPDATE NO ACTION\n" +
                            "    ON DELETE NO ACTION\n" +
                            "    NOT VALID;");
                    stmt.execute("ALTER TABLE IF EXISTS test.orders\n" +
                            "    ADD FOREIGN KEY (topping)\n" +
                            "    REFERENCES public.toppings (topping) MATCH SIMPLE\n" +
                            "    ON UPDATE NO ACTION\n" +
                            "    ON DELETE NO ACTION\n" +
                            "    NOT VALID;");
                    stmt.execute("ALTER TABLE IF EXISTS test.orders\n" +
                            "    ADD FOREIGN KEY (bottom)\n" +
                            "    REFERENCES public.bottoms (bottom) MATCH SIMPLE\n" +
                            "    ON UPDATE NO ACTION\n" +
                            "    ON DELETE NO ACTION\n" +
                            "    NOT VALID;");

                } catch (Throwable var6) {
                    if (stmt != null) {
                        try {
                            stmt.close();
                        } catch (Throwable var5) {
                            var6.addSuppressed(var5);
                        }
                    }
                    throw var6;
                }
            }
        } catch (SQLException throwables) //if an sql error happens
        {
            System.out.println(throwables.getMessage());
            fail("Database connection failed");
        }
    }

    @BeforeEach
    void setUp() {
        try (Connection testConnection = connectionPool.getConnection()) { //connects to database
            try (Statement stmt = testConnection.createStatement()) { //tries to create a statement
                stmt.execute("DELETE FROM test.orders");
                stmt.execute("DELETE FROM test.users");
                stmt.execute("DELETE FROM test.toppings");
                stmt.execute("DELETE FROM test.bottoms");



                stmt.execute("INSERT INTO test.users (email, password, role, credit)\n" +
                        "VALUES\n" +
                        "\t('admin@olsker.dk', 'Cupc4k3!', 'admin', 999999999),\n" +
                        "\t('cph-ab632@cphbusiness.dk', 'Test1', null, 0),\n" +
                        "\t('cph-ea178@cphbusiness.dk', 'Test2', null, 0),\n" +
                        "\t('cph-fb157@cphbusiness.dk', 'Test3', null, 0),\n" +
                        "\t('cph-ta241@cphbusiness.dk', 'Test4', null, 0);");
                stmt.execute("INSERT INTO test.toppings (topping, price)\n" +
                        "VALUES\n" +
                        "\t('Chocolate', 5.00),\n" +
                        "\t('Blueberry', 5.00),\n" +
                        "\t('Raspberry', 5.00),\n" +
                        "\t('Crispy', 6.00),\n" +
                        "\t('Strawberry', 6.00),\n" +
                        "\t('Rum/Raisin', 7.00),\n" +
                        "\t('Orange', 8.00),\n" +
                        "\t('Lemon', 8.00),\n" +
                        "\t('Blue cheese', 9.00);");
                stmt.execute("INSERT INTO test.bottoms (bottom, price)\n" +
                        "VALUES \n" +
                        "\t('Chocolate', 5.00),\n" +
                        "\t('Vanilla', 5.00),\n" +
                        "\t('Nutmeg', 5.00),\n" +
                        "\t('Pistacio', 6.00),\n" +
                        "\t('Almond', 7.00);");
                stmt.execute("INSERT INTO test.orders (user_id, topping, bottom, amount, is_processed)\n" +
                        "VALUES\n" +
                        "\t(1, 'Chocolate', 'Chocolate', 1, false),\n" +
                        "\t(1, 'Blueberry', 'Vanilla', 1, true),\n" +
                        "\t(2, 'Raspberry', 'Nutmeg', 1, false),\n" +
                        "\t(2, 'Crispy', 'Pistacio', 1, true),\n" +
                        "\t(3, 'Strawberry', 'Almond', 1, false),\n" +
                        "\t(3, 'Rum/Raisin', 'Chocolate', 1, true),\n" +
                        "\t(4, 'Orange', 'Vanilla', 1, false),\n" +
                        "\t(4, 'Lemon', 'Nutmeg', 1, true),\n" +
                        "\t(5, 'Blue cheese', 'Pistacio', 1, false),\n" +
                        "\t(5, 'Chocolate', 'Almond', 1, true);");
            }
        } catch (SQLException throwables) {
            fail("Database connection failed");
        }
    }

    @Test
    void testConnection() throws SQLException {
        assertNotNull(connectionPool.getConnection()); //if it runs it works
    }

    @Test
    void testinsert() throws DatabaseException {
        User u1 = new User("test4@test.test", "1234", null, 1234d);
        UserMapper.addUserByObject(connectionPool, u1);
        Assertions.assertNotNull(u1);
    }
}