package entities;

import java.time.LocalDate;

abstract class Creator {

    private final String name;
    private final String country;
    private final LocalDate foundDate;
    private int gameCount;

    public Creator(String name, String country, LocalDate foundDate, int gameCount) {
        this.name = name;
        this.country = country;
        this.foundDate = foundDate;
        this.gameCount = gameCount;
    }

    public String getName() {
        return name;
    }

    public String getCountry() {
        return country;
    }

    public LocalDate getFoundDate() {
        return foundDate;
    }

    public int getGameCount() {
        return gameCount;
    }

    public void setGameCount(int gameCount) {
        this.gameCount = gameCount;
    }
}
