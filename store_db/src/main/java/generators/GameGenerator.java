package generators;

import java.time.LocalDate;
import java.util.Map;
import java.util.Random;

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

        return new Game(
                0, // TODO
                0, // TODO
                faker.book().title(),
                new LocalDate(), // TODO
                (float) Math.round(random.nextFloat() * 100) / 100,
                faker.letterify(createTemplate(getLength(100000, 100)))
        );
    }

    public void setPublisherMap(Map<Long, Publisher> publisherMap) {
        this.publisherMap = publisherMap;
    }

    public void setDeveloperMap(Map<Long, Developer> developerMap) {
        this.developerMap = developerMap;
    }
}
