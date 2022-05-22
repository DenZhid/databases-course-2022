package generators;

import java.time.Instant;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import entities.Developer;
import entities.Game;
import entities.Publisher;

public class GameGenerator extends Generator {

    private final Faker faker;
    private Map<Long, Publisher> publisherMap;
    private Map<Long, Developer> developerMap;

    public GameGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Game generate() {
        long[] ids = createIds();
        LocalDate developerDate = developerMap.get(ids[0]).getFoundDate();
        LocalDate publisherFoundDate = publisherMap.get(ids[1]).getFoundDate();
        Instant atMostDate;
        if (developerDate.isBefore(publisherFoundDate)) {
            atMostDate = publisherFoundDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        } else {
            atMostDate = developerDate.atStartOfDay(ZoneId.systemDefault()).toInstant();
        }
        long atMostDateFromJavaEpoch = atMostDate.getEpochSecond();
        long nowFromJavaEpoch = Instant.now().getEpochSecond();
        int atMost = (int) ((nowFromJavaEpoch - atMostDateFromJavaEpoch) / 86400); // в сутках
        return new Game(
                ids[0],
                ids[1],
                faker.letterify(createTemplate(getLength(50, 10))),
                faker.date()
                        .past(atMost + 365, TimeUnit.DAYS)
                        .toInstant()
                        .atZone(ZoneId.systemDefault())
                        .toLocalDate(),
                random.nextInt(1000000) / 100f,
                faker.letterify(createTemplate(getLength(1000, 100)))
        );
    }

    public void setPublisherMap(Map<Long, Publisher> publisherMap) {
        this.publisherMap = publisherMap;
    }

    public void setDeveloperMap(Map<Long, Developer> developerMap) {
        this.developerMap = developerMap;
    }

    private long[] createIds() {
        Iterator<Map.Entry<Long, Developer>> developersEntriesIterator = developerMap.entrySet().iterator();
        int numberOfEntry = random.nextInt(developerMap.size());
        for (int i = 0; i < numberOfEntry - 1; i++) {
            developersEntriesIterator.next();
        }
        // Разработчик мог работать с другим издателем раньше
        if (random.nextBoolean()) {
            return new long[]{
                    developersEntriesIterator.next().getKey(),
                    developersEntriesIterator.next().getValue().getPublisherId()
            };
        }
        Iterator<Map.Entry<Long, Publisher>> publishersEntriesIterator = publisherMap.entrySet().iterator();
        numberOfEntry = random.nextInt(publisherMap.size());
        for (int i = 0; i < numberOfEntry - 1; i++) {
            publishersEntriesIterator.next();
        }
        return new long[]{developersEntriesIterator.next().getKey(), publishersEntriesIterator.next().getKey()};
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
