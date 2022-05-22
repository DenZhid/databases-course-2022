package testbenches;

import java.time.ZoneId;
import java.util.Locale;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import com.github.javafaker.Faker;

import dao.Dao;

public class TestebenchMixed {

    private long maxTimeSelection;
    private long minTimeSelection;
    private double averageTimeSelection;
    private long maxTimeUpdate;
    private long minTimeUpdate;
    private double averageTimeUpdate;
    private long maxTimeDelete;
    private long minTimeDelete;
    private double averageTimeDelete;
    private int deletionLeft;
    private int updateLeft;
    private int selectLeft;
    private final Random random = new Random();
    private final Faker faker = new Faker(Locale.forLanguageTag("ru"));

    public void mixedTest(
            int deletionRepeats,
            int updateRepeats,
            int selectRepeats,
            Dao dao,
            String daoName,
            int maxIdsForCustomer,
            int maxIdsForGames,
            int maxIdsForOrders
    ) {
        beforeStart(deletionRepeats, updateRepeats, selectRepeats);
        long startTestbenchTime = System.currentTimeMillis();
        while (deletionLeft != 0 && updateLeft != 0 && selectLeft != 0) {
            performAnyInteraction(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
        }
        if (deletionLeft == 0) {
            while (updateLeft != 0 && selectLeft != 0) {
                performSelectOrUpdate(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
            }
            if (updateLeft == 0) {
                while (selectLeft != 0) {
                    performSelect(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
                    selectLeft--;
                }
            } else {
                while (updateLeft != 0) {
                    performUpdate(dao, maxIdsForCustomer, maxIdsForOrders);
                    updateLeft--;
                }
            }
        } else if (updateLeft == 0) {
            while (deletionLeft != 0 && selectLeft != 0) {
                performSelectOrDelete(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
            }
            if (deletionLeft == 0) {
                while (selectLeft != 0) {
                    performSelect(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
                    selectLeft--;
                }
            } else {
                while (deletionLeft != 0) {
                    performDelete(dao, maxIdsForCustomer, maxIdsForOrders);
                    deletionLeft--;
                }
            }
        } else {
            while (deletionLeft != 0 && updateLeft != 0) {
                performUpdateOrDelete(dao, maxIdsForCustomer, maxIdsForOrders);
            }
            if (deletionLeft == 0) {
                while (updateLeft != 0) {
                    performUpdate(dao, maxIdsForCustomer, maxIdsForOrders);
                    updateLeft--;
                }
            } else {
                while (deletionLeft != 0) {
                    performDelete(dao, maxIdsForCustomer, maxIdsForOrders);
                    deletionLeft--;
                }
            }
        }
        long testbenchTime = System.currentTimeMillis() - startTestbenchTime;
        System.out.println(
                "Результаты для " + daoName + " для смешанного режима (чтений " + selectRepeats + ", обновлений "
                        + updateRepeats + ", удалений " + deletionRepeats + "):\n" +
                        "Чтения:\n" +
                        "Максимальное время: " + maxTimeSelection + " мс." + "\n" +
                        "Минимальное время: " + minTimeSelection + " мс." + "\n" +
                        "Суммарное время: " + averageTimeSelection + " мс." + "\n" +
                        "Среднее время: " + averageTimeSelection / selectRepeats + " мс." + "\n" +
                        "Обновления:\n" +
                        "Максимальное время: " + maxTimeUpdate + " мс." + "\n" +
                        "Минимальное время: " + minTimeUpdate + " мс." + "\n" +
                        "Суммарное время: " + averageTimeUpdate + " мс." + "\n" +
                        "Среднее время: " + averageTimeUpdate / updateRepeats + " мс." + "\n" +
                        "Удаления:\n" +
                        "Максимальное время: " + maxTimeDelete + " мс." + "\n" +
                        "Минимальное время: " + minTimeDelete + " мс." + "\n" +
                        "Суммарное время: " + averageTimeDelete + " мс." + "\n" +
                        "Среднее время: " + averageTimeDelete / deletionRepeats + " мс." + "\n" +
                        "Время теста: " + testbenchTime + " мс." + "\n"
        );
    }

    private void beforeStart(int deletionRepeats, int updateRepeats, int selectRepeats) {
        maxTimeSelection = 0;
        minTimeSelection = Long.MAX_VALUE;
        averageTimeSelection = 0.0;
        maxTimeUpdate = 0;
        minTimeUpdate = Long.MAX_VALUE;
        averageTimeUpdate = 0.0;
        maxTimeDelete = 0;
        minTimeDelete = Long.MAX_VALUE;
        averageTimeDelete = 0.0;
        deletionLeft = deletionRepeats;
        updateLeft = updateRepeats;
        selectLeft = selectRepeats;
    }

    private void performAnyInteraction(Dao dao, int maxIdsForCustomer, int maxIdsForGames, int maxIdsForOrders) {
        switch (random.nextInt(3)) {
            case 0:
                performSelect(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
                selectLeft--;
                break;
            case 1:
                performUpdate(dao, maxIdsForCustomer, maxIdsForOrders);
                updateLeft--;
                break;
            default:
                performDelete(dao, maxIdsForCustomer, maxIdsForOrders);
                deletionLeft--;
                break;
        }
    }

    private void performSelectOrUpdate(Dao dao, int maxIdsForCustomer, int maxIdsForGames, int maxIdsForOrders) {
        if (random.nextInt(2) == 0) {
            performSelect(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
            selectLeft--;
        } else {
            performUpdate(dao, maxIdsForCustomer, maxIdsForOrders);
            updateLeft--;
        }
    }

    private void performSelectOrDelete(Dao dao, int maxIdsForCustomer, int maxIdsForGames, int maxIdsForOrders) {
        if (random.nextInt(2) == 0) {
            performSelect(dao, maxIdsForCustomer, maxIdsForGames, maxIdsForOrders);
            selectLeft--;
        } else {
            performDelete(dao, maxIdsForCustomer, maxIdsForOrders);
            deletionLeft--;
        }
    }

    private void performUpdateOrDelete(Dao dao, int maxIdsForCustomer, int maxIdsForOrders) {
        if (random.nextInt(2) == 0) {
            performUpdate(dao, maxIdsForCustomer, maxIdsForOrders);
            updateLeft--;
        } else {
            performDelete(dao, maxIdsForCustomer, maxIdsForOrders);
            deletionLeft--;
        }
    }

    private void performSelect(Dao dao, int maxIdsForCustomer, int maxIdsForGames, int maxIdsForOrders) {
        int typeOfSelect = random.nextInt(3);
        long id;
        switch (typeOfSelect) {
            case 0:
                id = random.nextInt(maxIdsForCustomer);
                break;
            case 1:
                id = random.nextInt(maxIdsForGames);
                break;
            default:
                id = random.nextInt(maxIdsForOrders);
                break;
        }
        long startTime = System.currentTimeMillis();
        switch (typeOfSelect) {
            case 0:
                dao.getCustomerById(id);
                break;
            case 1:
                dao.getGameById(id);
                break;
            case 2:
                dao.getOrderById(id);
                break;
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        if (timeElapsed > maxTimeSelection) {
            maxTimeSelection = timeElapsed;
        }
        if (timeElapsed < minTimeSelection) {
            minTimeSelection = timeElapsed;
        }
        averageTimeSelection += timeElapsed;
    }

    private void performUpdate(Dao dao, int maxIdsForCustomer, int maxIdsForOrders) {
        int typeOfUpdate = random.nextInt(2);
        long id = (typeOfUpdate == 0) ? random.nextInt(maxIdsForCustomer) : random.nextInt(maxIdsForOrders);
        long startTime = System.currentTimeMillis();
        if (typeOfUpdate == 0) {
            dao.updateCustomerPassword(id, faker.bothify("?#?#?#????####?##???"));
        } else {
            dao.updateOrderDate(id,
                    faker.date()
                            .past(8030, 365, TimeUnit.DAYS)
                            .toInstant()
                            .atZone(ZoneId.systemDefault())
                            .toLocalDate()
            );
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        if (timeElapsed > maxTimeUpdate) {
            maxTimeUpdate = timeElapsed;
        }
        if (timeElapsed < minTimeUpdate) {
            minTimeUpdate = timeElapsed;
        }
        averageTimeUpdate += timeElapsed;
    }

    private void performDelete(Dao dao, int maxIdsForCustomer, int maxIdsForOrders) {
        int typeOfDelete = random.nextInt(2);
        long id = (typeOfDelete == 0) ? random.nextInt(maxIdsForCustomer) : random.nextInt(maxIdsForOrders);
        long startTime = System.currentTimeMillis();
        if (typeOfDelete == 0) {
            dao.deleteCustomerById(id);
        } else {
            dao.deleteOrderById(id);
        }
        long timeElapsed = System.currentTimeMillis() - startTime;
        if (timeElapsed > maxTimeDelete) {
            maxTimeDelete = timeElapsed;
        }
        if (timeElapsed < minTimeDelete) {
            minTimeDelete = timeElapsed;
        }
        averageTimeDelete += timeElapsed;
    }
}
