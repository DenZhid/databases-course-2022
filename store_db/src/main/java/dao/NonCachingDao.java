package dao;

import java.time.LocalDate;
import java.util.List;

import entities.Customer;
import entities.Game;
import entities.Order;

public class NonCachingDao implements Dao {
    @Override
    public List<Customer> getAllCustomers() {
        return null;
    }

    @Override
    public Customer getCustomerById(long id) {
        return null;
    }

    @Override
    public List<Game> getGamesByGenre(String genreName) {
        return null;
    }

    @Override
    public List<Order> getOrderByDate(LocalDate date) {
        return null;
    }

    @Override
    public Customer addCustomer(Customer customer) {
        return null;
    }

    @Override
    public Game addGame(Game game) {
        return null;
    }

    @Override
    public void updateOrderDate(long id, LocalDate Date) {

    }

    @Override
    public void updateCustomerPassword(long id, String password) {

    }

    @Override
    public void deleteOrderById(long id) {

    }

    @Override
    public void deleteCustomerById(long id) {

    }
}
