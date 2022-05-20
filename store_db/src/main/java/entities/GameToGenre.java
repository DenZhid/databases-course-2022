package entities;

public class GameToGenre {

    private final long gameId;
    private final long genreId;

    public GameToGenre(long gameId, long genreId) {
        this.gameId = gameId;
        this.genreId = genreId;
    }

    public long getGameId() {
        return gameId;
    }

    public long getGenreId() {
        return genreId;
    }
}
