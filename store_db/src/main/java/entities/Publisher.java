package entities;

import java.time.LocalDate;

/* Данный класс несёт лишь семантический смысл. У Publisher и Developer много похожих полей и удобно вынести их
/* в отдельный абстрактный класс Creator. При этом наследовать Developer от Publisher будет неправильно с точки зрения
/* семантики (Разработчик не есть потомок Издателя). */
public class Publisher extends Creator {

    public Publisher(String name, String country, LocalDate foundDate, int gameCount) {
        super(name, country, foundDate, gameCount);
    }
}
