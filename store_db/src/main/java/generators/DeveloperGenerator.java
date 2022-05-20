package generators;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import entities.Developer;
import entities.Publisher;

public class DeveloperGenerator extends Generator {

    private final Faker faker;
    private Map<Long, Publisher> publishersMap;
    private int gameCount;

    public DeveloperGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Developer generate() {
        Iterator<Map.Entry<Long, Publisher>> entriesIterator = publishersMap.entrySet().iterator();
        int numberOfEntry = random.nextInt(publishersMap.size());
        for (int i = 0; i < numberOfEntry - 1; i++) {
            entriesIterator.next();
        }
        Map.Entry<Long, Publisher> foundEntry = entriesIterator.next();
        return new Developer(
                foundEntry.getKey(),
                faker.company().name(), // Используем случайное имя компании
                faker.country().name(),
                // Примерно 22 года до сегодняшней даты
                faker.date().past(8030, TimeUnit.DAYS).toInstant().atZone(ZoneId.of("ECT")).toLocalDate(),
                gameCount // Подсчитываем количество игр позже
        );
    }

    public void setPublishersMap(Map<Long, Publisher> publishersMap) {
        this.publishersMap = publishersMap;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
}
