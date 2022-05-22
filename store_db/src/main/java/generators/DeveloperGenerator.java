package generators;

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

    public DeveloperGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Developer generate() {
        return new Developer(
                createRandomPublisherId(),
                faker.letterify(createTemplate(getLength(50, 10))), // Используем случайное имя компании
                faker.country().name(),
                // Примерно 22 года до сегодняшней даты, но не меньше года
                faker.date().past(8030, 365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                random.nextInt(51)
        );
    }

    public void setPublishersMap(Map<Long, Publisher> publishersMap) {
        this.publishersMap = publishersMap;
    }

    // Назначаем случайного Publisher
    private long createRandomPublisherId() {
        Iterator<Map.Entry<Long, Publisher>> entriesIterator = publishersMap.entrySet().iterator();
        int numberOfEntry = random.nextInt(publishersMap.size());
        for (int i = 0; i < numberOfEntry - 1; i++) {
            entriesIterator.next();
        }
        return entriesIterator.next().getKey();
    }
}
