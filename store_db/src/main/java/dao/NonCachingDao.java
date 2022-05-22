package dao;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import entities.Customer;
import entities.Game;
import entities.Order;

public class NonCachingDao implements Dao {

    private final Connection connection;

    public NonCachingDao(Connection connection) {
        this.connection = connection;
    }

    // Имитация реального запроса
    @Override
    public Customer getCustomerById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * " +
                            "FROM customer " +
                            "WHERE id = ?"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    String login = resultSet.getString("login");
                    String password = resultSet.getString("password");
                    String email = resultSet.getString("email");
                    return new Customer(id, login, password, email);
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    // Имитация реального запроса
    @Override
    public Game getGameById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT *, d.name as developer_name, p.name as publisher_name, gen.name as genre_name " +
                            "FROM game g " +
                            "JOIN developer d on g.developer_id = d.id " +
                            "JOIN publisher p on g.publisher_id = p.id " +
                            "JOIN game_to_genre gtg on g.id = gtg.game_id " +
                            "JOIN genre gen on gtg.genre_id = gen.id " +
                            "WHERE g.id = ?"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long developerId = resultSet.getLong("developer_id");
                    long publisherId = resultSet.getLong("publisher_id");
                    String name = resultSet.getString("name");
                    LocalDate releaseDate = resultSet.getDate("release_date").toLocalDate();
                    float price = resultSet.getFloat("price");
                    String description = resultSet.getString("description");
                    String developerName = resultSet.getString("developer_name");
                    String publisherName = resultSet.getString("publisher_name");

                    Game game = new Game(
                            id,
                            developerId,
                            publisherId,
                            developerName,
                            publisherName,
                            name,
                            releaseDate,
                            price,
                            description);

                    game.addToListOfGenres(resultSet.getString("genre_name"));
                    while (resultSet.next()) {
                        game.addToListOfGenres(resultSet.getString("genre_name"));
                    }
                    return game;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    // Имитация реального запроса
    @Override
    public Order getOrderById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT *, c.login as customer_login, g.name as game_name " +
                            "FROM purchase p " +
                            "JOIN customer c on c.id = p.customer_id " +
                            "JOIN purchase_to_game ptg on p.id = ptg.purchase_id " +
                            "JOIN game g on ptg.game_id = g.id " +
                            "WHERE p.id = ?"
            );
            preparedStatement.setLong(1, id);
            try (ResultSet resultSet = preparedStatement.executeQuery()) {
                if (resultSet.next()) {
                    long customerId = resultSet.getLong("customer_id");
                    LocalDate date = resultSet.getDate("date").toLocalDate();
                    String customerLogin = resultSet.getString("customer_login");

                    Order order = new Order(id, customerId, customerLogin, date);

                    order.addToListOfGames(resultSet.getString("game_name"));
                    while (resultSet.next()) {
                        order.addToListOfGames(resultSet.getString("game_name"));
                    }
                    return order;
                }
            }
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
        return null;
    }

    @Override
    public void updateOrderDate(long id, LocalDate date) {
        try {
            PreparedStatement preparedUpdateStatement = connection.prepareStatement(
                    "UPDATE purchase SET date = ? WHERE id = ?"
            );
            preparedUpdateStatement.setDate(1, Date.valueOf(date));
            preparedUpdateStatement.setLong(2, id);
            preparedUpdateStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void updateCustomerPassword(long id, String password) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "UPDATE customer SET password = ? WHERE id = ? "
            );
            preparedStatement.setString(1, password);
            preparedStatement.setLong(2, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteOrderById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM purchase WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }

    @Override
    public void deleteCustomerById(long id) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement("DELETE FROM customer WHERE id = ?");
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            throw new IllegalStateException(e);
        }
    }
}
