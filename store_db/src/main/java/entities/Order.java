package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final long id; // Для использования Dao
    private final long customerId;
    private final String customerLogin; // Для использования Dao
    private final List<String> listOfGames = new ArrayList<>(); // Для использования Dao
    private final LocalDate date;

    public Order(long id, long customerId, String customerLogin, LocalDate date) {
        this.id = id;
        this.customerId = customerId;
        this.customerLogin = customerLogin;
        this.date = date;
    }

    // Используется генератором, поля id, customerLogin не важны
    public Order(long customerId, LocalDate date) {
        this.id = 0;
        this.customerId = customerId;
        this.customerLogin = "";
        this.date = date;
    }

    public void addToListOfGames(String gameName) {
        listOfGames.add(gameName);
    }

    public long getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }
}
