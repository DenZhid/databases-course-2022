package generators;

import java.util.Random;

import com.github.javafaker.Faker;

import entities.Genre;

public class GenreGenerator extends Generator {

    private final Faker faker;

    public GenreGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    public Genre generate() {
        String template = createTemplate(getLength(25, 10));
        return new Genre(faker.letterify(template), random.nextInt(101));
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
