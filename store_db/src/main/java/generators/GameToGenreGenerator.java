package generators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entities.GameToGenre;

public class GameToGenreGenerator extends Generator {

    private final Map<Long, List<Long>> usedGenresIds = new HashMap<>();
    private List<Long> genresIds;
    private Map<Long, LocalDate> gamesIdAndDates;
    private int currentGameId = 0;

    public GameToGenreGenerator(Random random) {
        super(random);
    }

    // Используем этот метод для генерации хотя бы одного жанра для каждой игры
    @Override
    protected Object generate() {
        Iterator<Map.Entry<Long, LocalDate>> entriesIterator = gamesIdAndDates.entrySet().iterator();
        for (int i = 0; i < currentGameId - 1; i++) {
            entriesIterator.next();
        }
        long gameId = entriesIterator.next().getKey();
        long genreId = genresIds.get(random.nextInt(genresIds.size()));
        usedGenresIds.computeIfAbsent(gameId, k -> new ArrayList<>()).add(genreId);
        currentGameId++;
        return new GameToGenre(gameId, genreId);
    }

    public List<Object> generateAdditionalMultiply(int size) {
        List<Object> result = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            result.add(generateAdditional());
        }
        return result;
    }

    // Используем этот метод для генерации дополнительных жанров для игр
    private Object generateAdditional() {
        long gameId = createRandomGameId();
        while (usedGenresIds.get(gameId) != null && usedGenresIds.get(gameId).size() == genresIds.size()) {
            gameId = createRandomGameId();
        }
        long genreId = genresIds.get(random.nextInt(genresIds.size()));
        while (usedGenresIds.get(gameId) != null && usedGenresIds.get(gameId).contains(genreId)) {
            genreId = genresIds.get(random.nextInt(genresIds.size()));
        }
        return new GameToGenre(gameId, genreId);
    }

    public void setGenresIds(List<Long> genresIds) {
        this.genresIds = genresIds;
    }

    public void setGamesIdAndDates(Map<Long, LocalDate> gamesIdAndDates) {
        this.gamesIdAndDates = gamesIdAndDates;
    }

    private long createRandomGameId() {
        Iterator<Map.Entry<Long, LocalDate>> entriesIterator = gamesIdAndDates.entrySet().iterator();
        int numberOfEntry = random.nextInt(gamesIdAndDates.size());
        for (int i = 0; i < numberOfEntry - 1; i++) {
            entriesIterator.next();
        }
        return entriesIterator.next().getKey();
    }
}
