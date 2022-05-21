package generators;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Random;

import entities.OrderToGame;

public class OrderToGameGenerator extends Generator {

    private final Map<Long, List<Long>> usedGamesIds = new HashMap<>();
    private List<Long> ordersIds;
    private Map<Long, LocalDate> gamesIdAndDates;
    private int currentOrderId = 0;

    public OrderToGameGenerator(Random random) {
        super(random);
    }

    // Используем этот метод для генерации хотя бы одного жанра для каждой игры
    @Override
    protected Object generate() {
        long orderId = ordersIds.get(currentOrderId++);
        long gameId = createRandomGameId();
        usedGamesIds.computeIfAbsent(gameId, k -> new ArrayList<>()).add(gameId);
        return new OrderToGame(orderId, gameId);
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
        long orderId = ordersIds.get(random.nextInt(ordersIds.size()));
        while (usedGamesIds.get(orderId) != null && usedGamesIds.get(orderId).size() == gamesIdAndDates.size()) {
            orderId = ordersIds.get(random.nextInt(ordersIds.size()));
        }
        long gameId = createRandomGameId();
        while (usedGamesIds.get(orderId) != null && usedGamesIds.get(orderId).contains(gameId)) {
            gameId = createRandomGameId();
        }
        return new OrderToGame(orderId, gameId);
    }

    public void setOrdersIds(List<Long> ordersIds) {
        this.ordersIds = ordersIds;
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
