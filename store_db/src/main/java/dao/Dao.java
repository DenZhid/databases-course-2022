package dao;

import java.time.LocalDate;

import entities.Customer;
import entities.Game;
import entities.Order;

public interface Dao {

    Customer getCustomerById(long id);

    Game getGameById(long id);

    Order getOrderById(long id);

    void updateOrderDate(long id, LocalDate date);

    void updateCustomerPassword(long id, String password);

    void deleteOrderById(long id);

    void deleteCustomerById(long id);
}
