package generators;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import entities.Order;

public class OrderGenerator extends Generator {

    private Faker faker;
    private Map<Long, LocalDate> gamesIdsAndDates;
    private List<Long> customersIds;

    public OrderGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Order generate() {
        return new Order(
                customersIds.get(random.nextInt(customersIds.size())),
                faker.date()
                        .past(183, TimeUnit.DAYS)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate()
        );
    }

    public void setGamesIdsAndDates(Map<Long, LocalDate> gamesIdsAndDates) {
        this.gamesIdsAndDates = gamesIdsAndDates;
    }

    public void setCustomersIds(List<Long> customersIds) {
        this.customersIds = customersIds;
    }
}
