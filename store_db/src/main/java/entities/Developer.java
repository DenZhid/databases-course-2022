package entities;

import java.time.LocalDate;

public class Developer extends Creator {
    private final long publisherId;

    public Developer(long publisherId, String name, String country, LocalDate foundDate, int gameCount) {
        super(name, country, foundDate, gameCount);
        this.publisherId = publisherId;
    }

    public long getPublisherId() {
        return publisherId;
    }
}
