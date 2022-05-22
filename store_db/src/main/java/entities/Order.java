package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Order {

    private final long customerId;
    private final List<String> listOfGames = new ArrayList<>();
    private final LocalDate date;

    public Order(long customerId, LocalDate date) {
        this.customerId = customerId;
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

    public List<String> getListOfGames() {
        return listOfGames;
    }
}
