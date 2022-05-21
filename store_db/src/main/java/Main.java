import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Locale;

public class Main {

    public static void main(String[] args) {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }

        try {
            Connection connection = DriverManager.getConnection(
                    "jdbc:postgresql://localhost/computer_games_store",
                    "postgres",
                    "den2001s"
            );
            Initiator initiator = new Initiator(Locale.forLanguageTag("ru"), connection);
            initiator.insertRandomData(
                    10,
                    8,
                    3,
                    5,
                    10,
                    4,
                    5
            );
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
