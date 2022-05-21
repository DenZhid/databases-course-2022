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
import entities.GameToGenre;
import entities.Genre;
import entities.Order;
import entities.OrderToGame;
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
        generatorChooser.setCustomersIds(insertCustomers(customersSize));
        generatorChooser.setGenresIds(insertGenres(genresSize));
        generatorChooser.setPublishersIdsAndDates(insertPublishers(publishersSize));
        generatorChooser.setDevelopersIdsAndDates(insertDevelopers(developersSize));
        generatorChooser.setGamesIdsAndDates(insertGames(gamesSize));
        generatorChooser.setOrdersIds(insertOrders(ordersSize));
        insertReviews(reviewsSize);
        insertGameToGenre(gamesSize);
        insertOrderToGame(ordersSize);
    }

    private List<Long> insertCustomers(int customerSize) throws SQLException {
        List<Object> customers = generatorChooser.getCustomers().generateMultiple(customerSize);
        List<Long> resultIds = new ArrayList<>();
        for (Object o : customers) {
            Customer customer = (Customer) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO customer (password, email, login) VALUES (?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setString(1, customer.getPassword());
            preparedStatement.setString(2, customer.getEmail());
            preparedStatement.setString(3, customer.getLogin());
            preparedStatement.executeUpdate();
            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    resultIds.add(generatedIds.getLong(1));
                }
            }
        }
        return resultIds;
    }

    private List<Long> insertGenres(int genresSize) throws SQLException {
        List<Object> genres = generatorChooser.getGenres().generateMultiple(genresSize);
        List<Long> resultIds = new ArrayList<>();
        for (Object o : genres) {
            Genre genre = (Genre) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO genre (popularity, name) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setInt(1, genre.getPopularity());
            preparedStatement.setString(2, genre.getName());
            preparedStatement.executeUpdate();
            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    resultIds.add(generatedIds.getLong(1));
                }
            }
        }
        return resultIds;
    }

    private Map<Long, Publisher> insertPublishers(int publishersSize) throws SQLException {
        List<Object> publishers = generatorChooser.getPublishers().generateMultiple(publishersSize);
        Map<Long, Publisher> resultIdsAndDates = new HashMap<>();
        for (Object o : publishers) {
            Publisher publisher = (Publisher) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO publisher (name, country, found_date, game_count) VALUES (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setString(1, publisher.getName());
            preparedStatement.setString(2, publisher.getCountry());
            preparedStatement.setDate(3, Date.valueOf(publisher.getFoundDate()));
            preparedStatement.setInt(4, publisher.getGameCount());
            preparedStatement.executeUpdate();
            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    resultIdsAndDates.put(generatedIds.getLong(1), publisher);
                }
            }
        }
        return resultIdsAndDates;
    }

    private Map<Long, Developer> insertDevelopers(int developersSize) throws SQLException {
        List<Object> developers = generatorChooser.getDevelopers().generateMultiple(developersSize);
        Map<Long, Developer> resultIdsAndDates = new HashMap<>();
        for (Object o : developers) {
            Developer developer = (Developer) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO developer (publisher_id, name, country, found_date, game_count) " +
                                    "VALUES (?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setLong(1, developer.getPublisherId());
            preparedStatement.setString(2, developer.getName());
            preparedStatement.setString(3, developer.getCountry());
            preparedStatement.setDate(4, Date.valueOf(developer.getFoundDate()));
            preparedStatement.setInt(5, developer.getGameCount());
            preparedStatement.executeUpdate();
            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    resultIdsAndDates.put(generatedIds.getLong(1), developer);
                }
            }
        }
        return resultIdsAndDates;
    }

    private Map<Long, LocalDate> insertGames(int gamesSize) throws SQLException {
        List<Object> games = generatorChooser.getGames().generateMultiple(gamesSize);
        Map<Long, LocalDate> resultIdsAndDates = new HashMap<>();
        for (Object o : games) {
            Game game = (Game) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO game (developer_id, publisher_id, name, release_date, price, description)" +
                                    " VALUES (?, ?, ?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setLong(1, game.getDeveloperId());
            preparedStatement.setLong(2, game.getPublisherId());
            preparedStatement.setString(3, game.getName());
            preparedStatement.setDate(4, Date.valueOf(game.getReleaseDate()));
            preparedStatement.setDouble(5, game.getPrice());
            preparedStatement.setString(6, game.getDescription());
            preparedStatement.executeUpdate();
            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    resultIdsAndDates.put(generatedIds.getLong(1), game.getReleaseDate());
                }
            }
        }
        return resultIdsAndDates;
    }

    private List<Long> insertOrders(int ordersSize) throws SQLException {
        List<Object> orders = generatorChooser.getOrders().generateMultiple(ordersSize);
        List<Long> resultIds = new ArrayList<>();
        for (Object o : orders) {
            Order order = (Order) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO purchase (customer_id, date) VALUES (?, ?)",
                            Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setLong(1, order.getCustomerId());
            preparedStatement.setDate(2, Date.valueOf(order.getDate()));
            preparedStatement.executeUpdate();
            try (ResultSet generatedIds = preparedStatement.getGeneratedKeys()) {
                if (generatedIds.next()) {
                    resultIds.add(generatedIds.getLong(1));
                }
            }
        }
        return resultIds;
    }

    private void insertReviews(int reviewsSize) throws SQLException {
        List<Object> reviews = generatorChooser.getReviews().generateMultiple(reviewsSize);
        for (Object o : reviews) {
            Review review = (Review) o;
            PreparedStatement preparedStatement =
                    connection.prepareStatement(
                            "INSERT INTO review (customer_id, game_id, rate, review_text) VALUES (?, ?, ?, ?)",
                            Statement.RETURN_GENERATED_KEYS
                    );
            preparedStatement.setLong(1, review.getCustomerId()); // Продумать логику добавления
            preparedStatement.setLong(2, review.getGameId()); // Продумать логику добавления
            preparedStatement.setInt(3, review.getRate());
            preparedStatement.setString(4, review.getText());
            preparedStatement.executeUpdate();
        }
    }

    private void insertGameToGenre(int gamesSize) throws SQLException {
        List<Object> gamesToGenres = generatorChooser.getGameToGenre().generateMultiple(gamesSize);
        gamesToGenres.addAll(generatorChooser.getGameToGenre().generateAdditionalMultiply(gamesSize / 2));
        for (Object gamesToGenre : gamesToGenres) {
            GameToGenre gameToGenre = (GameToGenre) gamesToGenre;
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO game_to_genre (genre_id, game_id) VALUES (?, ?)"
            );
            preparedStatement.setLong(1, gameToGenre.getGenreId());
            preparedStatement.setLong(2, gameToGenre.getGameId());
            preparedStatement.executeUpdate();
        }
    }

    private void insertOrderToGame(int ordersSize) throws SQLException {
        List<Object> ordersToGames = generatorChooser.getOrderToGame().generateMultiple(ordersSize);
        ordersToGames.addAll(generatorChooser.getOrderToGame().generateAdditionalMultiply(ordersSize / 2));
        for (Object ordersToGame : ordersToGames) {
            OrderToGame orderToGame = (OrderToGame) ordersToGame;
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO purchase_to_game (purchase_id, game_id) VALUES (?, ?)"
            );
            preparedStatement.setLong(1, orderToGame.getOrderId());
            preparedStatement.setLong(2, orderToGame.getGameId());
            preparedStatement.executeUpdate();
        }
    }
}
