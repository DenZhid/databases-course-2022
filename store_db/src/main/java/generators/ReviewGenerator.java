package generators;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import entities.Review;

public class ReviewGenerator extends Generator {

    private final Faker faker;
    private final Map<Long, List<Long>> usedGames = new HashMap<>();
    private List<Long> customersIds;
    private Map<Long, LocalDate> gamesIdsAndDates;

    public ReviewGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Review generate() {
        long customerId = customersIds.get(random.nextInt(customersIds.size()));
        while (usedGames.get(customerId) != null && usedGames.get(customerId).size() == gamesIdsAndDates.size()) {
            customerId = customersIds.get(random.nextInt(customersIds.size()));
        }
        long gameId = createRandomGameId();
        while (usedGames.get(customerId) != null && usedGames.get(customerId).contains(gameId)) {
            gameId = createRandomGameId();
        }
        return new Review(
                customerId,
                gameId,
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

    private long createRandomGameId() {
        Iterator<Map.Entry<Long, LocalDate>> entriesIterator = gamesIdsAndDates.entrySet().iterator();
        int numberOfEntry = random.nextInt(gamesIdsAndDates.size());
        for (int i = 0; i < numberOfEntry - 1; i++) {
            entriesIterator.next();
        }
        return entriesIterator.next().getKey();
    }

    @Override
    protected String createTemplate(int length) {
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            if (random.nextBoolean()) {
                sb.append('?');
            }
        }
        return sb.toString();
    }
}
