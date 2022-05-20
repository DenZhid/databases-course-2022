package generators;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class GameToGenreGenerator extends Generator {

    private List<Long> genresId;
    private Map<Long, LocalDate> gamesIdAndDates;

    public GameToGenreGenerator(Random random) {
        super(random);
    }

    @Override
    protected Object generate() {
        return null; // TODO
    }

    public void setGenresId(List<Long> genresId) {
        this.genresId = genresId;
    }

    public void setGamesIdAndDates(Map<Long, LocalDate> gamesIdAndDates) {
        this.gamesIdAndDates = gamesIdAndDates;
    }
}
