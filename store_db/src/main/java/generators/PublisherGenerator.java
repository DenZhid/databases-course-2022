package generators;

import java.time.ZoneId;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import entities.Publisher;

public class PublisherGenerator extends Generator {

    private final Faker faker;

    public PublisherGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Publisher generate() {
        return new Publisher(
                faker.letterify(createTemplate(getLength(50, 10))),
                faker.country().name(),
                // Примерно 22 года до сегодняшней даты
                faker.date().past(8030, 365, TimeUnit.DAYS).toInstant().atZone(ZoneId.systemDefault()).toLocalDate(),
                random.nextInt(51)
        );
    }
}
