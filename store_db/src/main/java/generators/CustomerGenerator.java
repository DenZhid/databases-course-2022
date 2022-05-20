package generators;

import java.util.Random;

import com.github.javafaker.Faker;

import entities.Customer;

public class CustomerGenerator extends Generator {

    private final Faker faker;

    public CustomerGenerator(Random random, Faker faker) {
        super(random);
        this.faker = faker;
    }

    @Override
    protected Customer generate() {
        String templateLogin = createTemplate(getLength(100, 3));
        String templatePassword = createTemplate(getLength(25, 6));
        String templateEmail = createTemplate(getLength(100, 10));
        switch (random.nextInt(3)) {
            case 0:
                templateEmail += "@mail.ru";
                break;
            case 1:
                templateEmail += "@gmail.com";
                break;
            default:
                templateEmail += "yandex.ru";
                break;
        }
        return new Customer(faker.bothify(templateLogin), faker.bothify(templatePassword),faker.bothify(templateEmail));
    }
}
