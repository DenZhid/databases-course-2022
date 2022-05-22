package dao;

import java.sql.Connection;
import java.time.LocalDate;
import java.util.List;

import cache.LRUCache;
import entities.Customer;
import entities.Game;
import entities.Order;

public class CachedDao extends NonCachingDao {

    private final LRUCache<Long, Customer> customerCache;
    private final LRUCache<Long, Game> gameCache;
    private final LRUCache<Long, Order> orderCache;

    public CachedDao(Connection connection, int maxCacheSizeForOneTable) {
        super(connection);
        this.customerCache = new LRUCache<>(maxCacheSizeForOneTable);
        this.gameCache = new LRUCache<>(maxCacheSizeForOneTable);
        this.orderCache = new LRUCache<>(maxCacheSizeForOneTable);
    }

    @Override
    public Customer getCustomerById(long id) {
        Customer result = customerCache.get(id);
        if (result != null) {
            return result;
        }
        result = super.getCustomerById(id);
        if (result == null) {
            return null;
        }
        customerCache.put(id, result);
        return result;
    }

    @Override
    public Game getGameById(long id) {
        Game result = gameCache.get(id);
        if (result != null) {
            return result;
        }
        result = super.getGameById(id);
        if (result == null) {
            return null;
        }
        gameCache.put(id, result);
        return result;
    }

    @Override
    public Order getOrderById(long id) {
        Order result = orderCache.get(id);
        if (result != null) {
            return result;
        }
        result = super.getOrderById(id);
        if (result == null) {
            return null;
        }
        orderCache.put(id, result);
        return result;
    }

    @Override
    public void updateOrderDate(long id, LocalDate date) {
        super.updateOrderDate(id, date);
        orderCache.remove(id);
    }

    @Override
    public void updateCustomerPassword(long id, String password) {
        super.updateCustomerPassword(id, password);
        customerCache.remove(id);
    }

    @Override
    public void deleteOrderById(long id) {
        orderCache.remove(id);
        super.deleteOrderById(id);
    }

    @Override
    public void deleteCustomerById(long id) {
        customerCache.remove(id);
        super.deleteCustomerById(id);
    }
}
