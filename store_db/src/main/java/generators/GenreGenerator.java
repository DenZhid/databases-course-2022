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

    // Используем жанры книг, как жанр игр
    @Override
    public Genre generate() {
        return new Genre(faker.book().genre(), random.nextInt(101));
    }
}
