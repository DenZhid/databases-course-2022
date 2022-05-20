package entities;

import java.time.LocalDate;

public class Game {
    private final long developerId;
    private final long publisherId;
    private final String name;
    private final LocalDate releaseDate;
    private final float price;
    private final String description;

    public Game (
                long developerId,
                long publisherId,
                String name,
                LocalDate releaseDate,
                float price,
                String description
    ) {
        this.developerId = developerId;
        this.publisherId = publisherId;
        this.name = name;
        this.releaseDate = releaseDate;
        this.price = price;
        this.description = description;
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
