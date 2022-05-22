package entities;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Game {

    private final long id;
    private final long developerId;
    private final long publisherId;
    private final String developerName; // Для использования Dao
    private final String publisherName; // Для использования Dao
    private final String name;
    private final LocalDate releaseDate;
    private final float price;
    private final String description;
    private final List<String> listOfGenres = new ArrayList<>(); //  Для использования Dao

    public Game(
            long id,
            long developerId,
            long publisherId,
            String developerName,
            String publisherName,
            String name,
            LocalDate releaseDate,
            float price,
            String description
    ) {
        this.id = id;
        this.developerId = developerId;
        this.publisherId = publisherId;
        this.developerName = developerName;
        this.publisherName = publisherName;
        this.name = name;
        this.releaseDate = releaseDate;
        this.price = price;
        this.description = description;
    }

    // Используется генератором, поля id, developerName, publisherName не важны
    public Game(
            long developerId,
            long publisherId,
            String name,
            LocalDate releaseDate,
            float price,
            String description
    ) {
        this.id = 0;
        this.developerId = developerId;
        this.publisherId = publisherId;
        this.developerName = "";
        this.publisherName = "";
        this.name = name;
        this.releaseDate = releaseDate;
        this.price = price;
        this.description = description;
    }

    public void addToListOfGenres(String genreName) {
        listOfGenres.add(genreName);
    }

    public long getDeveloperId() {
        return developerId;
    }

    public long getPublisherId() {
        return publisherId;
    }

    public String getName() {
        return name;
    }

    public LocalDate getReleaseDate() {
        return releaseDate;
    }

    public float getPrice() {
        return price;
    }

    public String getDescription() {
        return description;
    }
}
