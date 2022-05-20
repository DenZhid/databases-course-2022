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
                    "jdbc:postgresql://localhost:5432/computer_games_store",
                    "db_course",
                    "den2001s"
            );
            Initiator initiator = new Initiator(Locale.forLanguageTag("ru"), connection);
            initiator.insertRandomData();
            connection.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
