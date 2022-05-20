package generators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entities.Order;

public class OrderGenerator extends Generator {

    private Map<Long, LocalDate> gamesIdsAndDates;
    private List<Long> customersIds;

    public OrderGenerator(Random random) {
        super(random);
    }

    // TODO
    @Override
    protected Order generate() {
        return new Order(0, new LocalDate());
    }

    public void setGamesIdsAndDates(Map<Long, LocalDate> gamesIdsAndDates) {
        this.gamesIdsAndDates = gamesIdsAndDates;
    }

    public void setCustomersIds(List<Long> customersIds) {
        this.customersIds = customersIds;
    }
}
