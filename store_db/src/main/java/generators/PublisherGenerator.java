package generators;

import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import entities.Publisher;

public class PublisherGenerator extends Generator {

    private final Faker faker;
    private int gameCount;

    public PublisherGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Publisher generate() {
        int gamesReleased = random.nextInt(gameCount / 2);
        if (gamesReleased == 0) {
            gamesReleased = gameCount / 2;
        }
        gameCount -= gamesReleased;
        return new Publisher(
                faker.company().name(),
                faker.country().name(),
                // Примерно 22 года до сегодняшней даты
                faker.date().past(8030, TimeUnit.DAYS).toInstant().atZone(ZoneId.of("ECT")).toLocalDate(),
                gameCount
        );
    }

    @Override
    public List<Object> generateMultiple(int number) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < number; i++) {
            result.add(generate());
        }
        Publisher lastPublisher = (Publisher) result.get(result.size() - 1);
        result.remove(result.size() - 1);
        // Нужно, чтобы убедиться, что все создаваемые игры получат своего издателя
        lastPublisher.setGameCount(lastPublisher.getGameCount() + gameCount);
        result.add(lastPublisher);
        return result;
    }


    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
}
