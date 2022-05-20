package entities;

public class Genre {

    private final String name;
    private final int popularity;

    public Genre(String name, int popularity) {
        this.name = name;
        this.popularity = popularity;
    }

    public String getName() {
        return name;
    }

    public int getPopularity() {
        return popularity;
    }
}
