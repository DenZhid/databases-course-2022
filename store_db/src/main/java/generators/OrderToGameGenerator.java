package generators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class OrderToGameGenerator extends Generator {

    private List<Long> ordersIds;
    private Map<Long, LocalDate> gameIdsAndDates;

    public OrderToGameGenerator(Random random) {
        super(random);
    }

    // TODO
    @Override
    protected Object generate() {
        return null;
    }

    public void setOrdersIds(List<Long> ordersIds) {
        this.ordersIds = ordersIds;
    }

    public void setGameIdsAndDates(Map<Long, LocalDate> gameIdsAndDates) {
        this.gameIdsAndDates = gameIdsAndDates;
    }
}
