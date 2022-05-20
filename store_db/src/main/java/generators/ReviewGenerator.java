package generators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import entities.Review;

public class ReviewGenerator extends Generator {

    private final Faker faker;
    private List<Long> customersIds;
    private Map<Long, LocalDate> gamesIdsAndDates;

    public ReviewGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Review generate() {
        return new Review(
                0,
                0,
                random.nextInt(11),
                faker.letterify(createTemplate(getLength(100000, 100)))
        );
    }

    public void setCustomersIds(List<Long> customersIds) {
        this.customersIds = customersIds;
    }

    public void setGamesIdsAndDates(Map<Long, LocalDate> gamesIdsAndDates) {
        this.gamesIdsAndDates = gamesIdsAndDates;
    }
}
