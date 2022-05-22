package dao;

import java.time.LocalDate;
import java.util.List;

import entities.Customer;
import entities.Game;
import entities.Order;

public interface Dao {

    List<Customer> getAllCustomers();

    Customer getCustomerById(long id);

    List<Game> getGamesByGenre(String genreName);

    List<Order> getOrderByDate(LocalDate date);

    Customer addCustomer(Customer customer);

    Game addGame(Game game);

    void updateOrderDate(long id, LocalDate Date);

    void updateCustomerPassword(long id, String password);

    void deleteOrderById(long id);

    void deleteCustomerById(long id);
}
