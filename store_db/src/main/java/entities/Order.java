package entities;

import java.sql.Date;
import java.time.LocalDate;

public class Order {

    private final long customerId;
    private final LocalDate date;

    public Order(long customerId, LocalDate date) {
        this.customerId = customerId;
        this.date = date;
    }

    public long getCustomerId() {
        return customerId;
    }

    public LocalDate getDate() {
        return date;
    }
}
