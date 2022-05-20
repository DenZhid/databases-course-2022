import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import entities.Customer;
import entities.Developer;
import entities.Game;
import entities.Genre;
import entities.Order;
import entities.Publisher;
import entities.Review;
import generators.GeneratorChooser;

public class Initiator {

    private final Connection connection;
    private final GeneratorChooser generatorChooser;

    public Initiator(Locale locale, Connection connection) {
        this.generatorChooser = new GeneratorChooser(new Random(), new Faker(locale));
        this.connection = connection;
    }

    public void insertRandomData(
            int customersSize,
            int genresSize,
            int publishersSize,
            int developersSize,
            int gamesSize,
            int ordersSize,
            int reviewsSize
    ) throws SQLException {
        generatorChooser.setGameCount(gamesSize);
        generatorChooser.setCustomersIds(insertCustomers(customersSize));
        generatorChooser.setGenresIds(insertGenres(genresSize));
        generatorChooser.setPublishersIdsAndDates(insertPublishers(publishersSize));
        generatorChooser.setDevelopersIdsAndDates(insertDevelopers(developersSize));
        generatorChooser.setGamesIdsAndDates(insertGames(gamesSize));
        generatorChooser.setOrdersIds(insertOrders(ordersSize));
        insertReviews(reviewsSize);
        insertGameToGenre();
        insertOrderToGenre();
    }

    private List<Long> insertCustomers(int customerSize) throws SQLException {
        List<Object> customers = generatorChooser.getCustomers().generateMultiple(customerSize);
        List<Long> resultIds = new ArrayList<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO customer (password, email, login) VALUES (?, ?, ?)");
        for (int i = 0; i < customerSize; i++) {
            Customer customer = (Customer) customers.get(i);
            preparedStatement.setString(1, customer.getPassword());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getLogin());
            preparedStatement.executeQuery();
            ResultSet generatedIds = preparedStatement.getGeneratedKeys();
            resultIds.add(generatedIds.getLong(1));
        }
        return resultIds;
    }

    private List<Long> insertGenres(int genresSize) throws SQLException {
        List<Object> genres = generatorChooser.getGenres().generateMultiple(genresSize);
        List<Long> resultIds = new ArrayList<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement("INSERT INTO genre (popularity, name) VALUES (?, ?)");
        for (int i = 0; i < genresSize; i++) {
            Genre genre = (Genre) genres.get(i);
            preparedStatement.setInt(1, genre.getPopularity());
            preparedStatement.setString(2, genre.getName());
            ResultSet generatedIds = preparedStatement.getGeneratedKeys();
            resultIds.add(generatedIds.getLong(1));
        }
        return resultIds;
    }

    private Map<Long, Publisher> insertPublishers(int publishersSize) throws SQLException {
        List<Object> publishers = generatorChooser.getPublishers().generateMultiple(publishersSize);
        Map<Long, Publisher> resultIdsAndDates = new HashMap<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO publisher (name, country, found_date, game_count) VALUES (?, ?, ?, ?)"
                );
        for (int i = 0; i < publishersSize; i++) {
            Publisher publisher = (Publisher) publishers.get(i);
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getCountry());
            preparedStatement.setDate(3, Date.valueOf(publisher.getFoundDate()));
            preparedStatement.setInt(4, publisher.getGameCount()); // Продумать логику добавления
            ResultSet resultSet = preparedStatement.executeQuery();
            resultIdsAndDates.put(resultSet.getLong(1), publisher);
        }
        return resultIdsAndDates;
    }

    private Map<Long, Developer> insertDevelopers(int developersSize) throws SQLException {
        List<Object> developers = generatorChooser.getDevelopers().generateMultiple(developersSize);
        Map<Long, Developer> resultIdsAndDates = new HashMap<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO developer (publisher_id, name, country, found_date, game_count) " +
                                "VALUES (?, ?, ?, ?, ?)"
                );
        for (int i = 0; i < developersSize; i++) {
            Developer developer = (Developer) developers.get(i);
            preparedStatement.setLong(1, developer.getPublisherId());
            preparedStatement.setString(2, developer.getName());
            preparedStatement.setString(3, developer.getCountry());
            preparedStatement.setDate(4, Date.valueOf(developer.getFoundDate()));
            preparedStatement.setInt(5, developer.getGameCount()); // Продумать логику добавления
            ResultSet resultSet = preparedStatement.executeQuery();
            resultIdsAndDates.put(resultSet.getLong(1), developer);
        }
        return resultIdsAndDates;
    }

    private Map<Long, LocalDate> insertGames(int gamesSize) throws SQLException {
        List<Object> games = generatorChooser.getGames().generateMultiple(gamesSize);
        Map<Long, LocalDate> resultIdsAndDates = new HashMap<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO game (developer_id, publisher_id, name, release_date, price, description)" +
                                " VALUES (?, ?, ?, ?, ?, ?)"
                );
        for (int i = 0; i < gamesSize; i++) {
            Game game = (Game) games.get(i);
            preparedStatement.setLong(1, game.getDeveloperId()); // Продумать логику добавления
            preparedStatement.setLong(2, game.getPublisherId()); // Продумать логику добавления
            preparedStatement.setString(3, game.getName());
            preparedStatement.setDate(4, java.sql.Date.valueOf(game.getReleaseDate())); // Продумать логику добавления
            preparedStatement.setDouble(5, game.getPrice());
            preparedStatement.setString(6, game.getDescription());
            ResultSet resultSet = preparedStatement.executeQuery();
            resultIdsAndDates.put(resultSet.getLong(1), game.getReleaseDate());
        }
        return resultIdsAndDates;
    }

    private List<Long> insertOrders(int ordersSize) throws SQLException {
        List<Object> orders = generatorChooser.getOrders().generateMultiple(ordersSize);
        List<Long> resultIds = new ArrayList<>();
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO purchase (customer_id, date) VALUES (?, ?)",
                        Statement.RETURN_GENERATED_KEYS);
        for (int i = 0; i < ordersSize; i++) {
            Order order = (Order) orders.get(i);
            preparedStatement.setLong(1, order.getCustomerId()); // Продумать логику добавления
            preparedStatement.setDate(2, ); // Продумать логику добавления
            ResultSet resultSet = preparedStatement.executeQuery();
            resultIds.add(resultSet.getLong(1));
        }
        return resultIds;
    }

    private void insertReviews(int reviewsSize) throws SQLException {
        List<Object> reviews = generatorChooser.getReviews().generateMultiple(reviewsSize);
        PreparedStatement preparedStatement =
                connection.prepareStatement(
                        "INSERT INTO review (customer_id, game_id, rate, review_text) VALUES (?, ?, ?, ?)",
                        Statement.RETURN_GENERATED_KEYS
                );
        for (int i = 0; i < reviewsSize; i++) {
            Review review = (Review) reviews.get(i);
            preparedStatement.setLong(1, review.getCustomerId()); // Продумать логику добавления
            preparedStatement.setLong(2, review.getGameId()); // Продумать логику добавления
            preparedStatement.setInt(3, review.getRate());
            preparedStatement.setString(4, review.getText());
            preparedStatement.executeQuery();
        }
    }

    private void insertGameToGenre() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement();
        preparedStatement.executeQuery();
    }

    private void insertOrderToGenre() throws SQLException {
        PreparedStatement preparedStatement = connection.prepareStatement();
        preparedStatement.executeQuery();
    }
}
