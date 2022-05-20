package entities;

public class OrderToGame {

    private final long orderId;
    private final long gameId;

    public OrderToGame(long orderId, long gameId) {
        this.orderId = orderId;
        this.gameId = gameId;
    }

    public long getOrderId() {
        return orderId;
    }

    public long getGameId() {
        return gameId;
    }
}
