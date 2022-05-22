import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

import dao.CachedDao;
import dao.NonCachingDao;
import testbenches.TestbenchOfOnlySelects;
import testbenches.TestebenchMixed;

public class Main {

    private static final String NON_CASHING = "не кэшируемого dao";
    private static final String CASHING = "кэшируемого dao";
    private static final String URL = "jdbc:postgresql://localhost/computer_games_store";
    private static final String USER = "postgres";
    private static final String PASSWORD = "den2001s";
    private static final String DRIVER_NAME = "org.postgresql.Driver";

    public static void main(String[] args) {
        try {
            Class.forName(DRIVER_NAME);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        try {
            Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
            fillUpDBWithGenerator(connection);
            startTesting(connection);
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void startTesting(Connection connection) {
        TestbenchOfOnlySelects.getCustomerByIdTest(
                5000,
                new NonCachingDao(connection),
                NON_CASHING,
                1005
        );
        TestbenchOfOnlySelects.getOrderByIdTest(
                5000,
                new NonCachingDao(connection),
                NON_CASHING,
                2505
        );
        TestbenchOfOnlySelects.getGameByIdTest(
                5000,
                new NonCachingDao(connection),
                NON_CASHING,
                1006
        );

        TestbenchOfOnlySelects.getCustomerByIdTest(
                5000,
                new CachedDao(connection, 500),
                CASHING,
                1005
        );
        TestbenchOfOnlySelects.getOrderByIdTest(
                5000,
                new CachedDao(connection, 500),
                CASHING,
                2505
        );
        TestbenchOfOnlySelects.getGameByIdTest(
                5000,
                new CachedDao(connection, 500),
                CASHING,
                1006
        );

        TestebenchMixed testebenchMixed = new TestebenchMixed();
        testebenchMixed.mixedTest(
                1000,
                1500,
                2500,
                new NonCachingDao(connection),
                NON_CASHING,
                1005,
                2505,
                1006
        );

        testebenchMixed.mixedTest(
                1000,
                1500,
                2500,
                new CachedDao(connection, 500),
                CASHING,
                1005,
                2505,
                1006
        );
    }

    private static void fillUpDBWithGenerator(Connection connection) throws SQLException {
        Initiator initiator = new Initiator(Locale.forLanguageTag("ru"), connection);
        initiator.insertRandomData(
                1000,
                50,
                100,
                100,
                1000,
                2500,
                500
        );
    }
}
