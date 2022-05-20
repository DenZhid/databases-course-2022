package entities;

public class Review {
    private final long customerId;
    private final long gameId;
    private final int rate;
    private final String text;

    public Review(long customerId, long gameId, int rate, String text) {
        this.customerId = customerId;
        this.gameId = gameId;
        this.rate = rate;
        this.text = text;
    }

    public long getCustomerId() {
        return customerId;
    }

    public long getGameId() {
        return gameId;
    }

    public int getRate() {
        return rate;
    }

    public String getText() {
        return text;
    }
}
