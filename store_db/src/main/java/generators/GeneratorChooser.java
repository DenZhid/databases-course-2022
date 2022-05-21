package generators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.github.javafaker.Faker;

import entities.Developer;
import entities.Publisher;

public class GeneratorChooser {

    private final CustomerGenerator customers;
    private final DeveloperGenerator developers;
    private final GameGenerator games;
    private final GameToGenreGenerator gameToGenre;
    private final GenreGenerator genres;
    private final OrderGenerator orders;
    private final OrderToGameGenerator orderToGame;
    private final PublisherGenerator publishers;
    private final ReviewGenerator reviews;

    public GeneratorChooser(Random random, Faker faker) {
        this.customers = new CustomerGenerator(random, faker);
        this.developers = new DeveloperGenerator(random, faker);
        this.games = new GameGenerator(random, faker);
        this.gameToGenre = new GameToGenreGenerator(random);
        this.genres = new GenreGenerator(random, faker);
        this.orders = new OrderGenerator(random, faker);
        this.orderToGame = new OrderToGameGenerator(random);
        this.publishers = new PublisherGenerator(random, faker);
        this.reviews = new ReviewGenerator(random, faker);
    }

    public CustomerGenerator getCustomers() {
        return customers;
    }

    public DeveloperGenerator getDevelopers() {
        return developers;
    }

    public GameGenerator getGames() {
        return games;
    }

    public GameToGenreGenerator getGameToGenre() {
        return gameToGenre;
    }

    public GenreGenerator getGenres() {
        return genres;
    }

    public OrderGenerator getOrders() {
        return orders;
    }

    public OrderToGameGenerator getOrderToGame() {
        return orderToGame;
    }

    public PublisherGenerator getPublishers() {
        return publishers;
    }

    public ReviewGenerator getReviews() {
        return reviews;
    }

    public void setCustomersIds(List<Long> customersIds) {
        this.orders.setCustomersIds(customersIds);
        this.reviews.setCustomersIds(customersIds);
    }

    public void setGenresIds(List<Long> genresIds) {
        this.gameToGenre.setGenresIds(genresIds);
    }

    public void setPublishersIdsAndDates(Map<Long, Publisher> publisherMap) {
        this.developers.setPublishersMap(publisherMap);
        this.games.setPublisherMap(publisherMap);
    }

    public void setDevelopersIdsAndDates(Map<Long, Developer> developerMap) {
        this.games.setDeveloperMap(developerMap);
    }

    public void setGamesIdsAndDates(Map<Long, LocalDate> gamesIdsAndDates) {
        this.gameToGenre.setGamesIdAndDates(gamesIdsAndDates);
        this.reviews.setGamesIdsAndDates(gamesIdsAndDates);
        this.orders.setGamesIdsAndDates(gamesIdsAndDates);
        this.orderToGame.setGamesIdAndDates(gamesIdsAndDates);
    }

    public void setOrdersIds(List<Long> ordersIds) {
        this.orderToGame.setOrdersIds(ordersIds);
    }
}
