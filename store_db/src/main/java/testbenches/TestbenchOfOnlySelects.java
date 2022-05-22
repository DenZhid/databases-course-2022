package testbenches;

import java.util.Random;

import dao.Dao;

public class TestbenchOfOnlySelects {

    public static void getCustomerByIdTest(int repeats, Dao dao, String daoName, int idsCount) {
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        double averageTime = 0.0;
        long startTestbenchTime = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < repeats; i++) {
            long id = random.nextInt(idsCount);
            long startTime = System.currentTimeMillis();
            dao.getCustomerById(id);
            long timeElapsed = System.currentTimeMillis() - startTime;
            if (timeElapsed > maxTime) {
                maxTime = timeElapsed;
            }
            if (timeElapsed < minTime) {
                minTime = timeElapsed;
            }
            averageTime += timeElapsed;
        }
        long testbenchTime = System.currentTimeMillis() - startTestbenchTime;
        System.out.println(
                "Результаты для " + daoName + " для метода getCustomerById:\n" +
                        "Максимальное время: " + maxTime + " мс." + "\n" +
                        "Минимальное время: " + minTime + " мс." + "\n" +
                        "Среднее время: " + averageTime / repeats + " мс." + "\n" +
                        "Время теста: " + testbenchTime + " мс." + "\n"
        );
    }

    public static void getGameByIdTest(int repeats, Dao dao, String daoName, int idsCount) {
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        double averageTime = 0.0;
        long startTestbenchTime = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < repeats; i++) {
            long id = random.nextInt(idsCount);
            long startTime = System.currentTimeMillis();
            dao.getGameById(id);
            long timeElapsed = System.currentTimeMillis() - startTime;
            if (timeElapsed > maxTime) {
                maxTime = timeElapsed;
            }
            if (timeElapsed < minTime) {
                minTime = timeElapsed;
            }
            averageTime += timeElapsed;
        }
        long testbenchTime = System.currentTimeMillis() - startTestbenchTime;
        System.out.println(
                "Результаты для " + daoName + " для метода getGameById:\n" +
                        "Максимальное время: " + maxTime + " мс." + "\n" +
                        "Минимальное время: " + minTime + " мс." + "\n" +
                        "Среднее время: " + averageTime / repeats + " мс." + "\n" +
                        "Время теста: " + testbenchTime + " мс." + "\n"
        );
    }

    public static void getOrderByIdTest(int repeats, Dao dao, String daoName, int idsCount) {
        long maxTime = 0;
        long minTime = Long.MAX_VALUE;
        double averageTime = 0.0;
        long startTestbenchTime = System.currentTimeMillis();
        Random random = new Random();
        for (int i = 0; i < repeats; i++) {
            long id = random.nextInt(idsCount);
            long startTime = System.currentTimeMillis();
            dao.getOrderById(id);
            long timeElapsed = System.currentTimeMillis() - startTime;
            if (timeElapsed > maxTime) {
                maxTime = timeElapsed;
            }
            if (timeElapsed < minTime) {
                minTime = timeElapsed;
            }
            averageTime += timeElapsed;
        }
        long testbenchTime = System.currentTimeMillis() - startTestbenchTime;
        System.out.println(
                "Результаты для " + daoName + " для метода getOrderById:\n" +
                        "Максимальное время: " + maxTime + " мс." + "\n" +
                        "Минимальное время: " + minTime + " мс." + "\n" +
                        "Среднее время: " + averageTime / repeats + " мс." + "\n" +
                        "Время теста: " + testbenchTime + " мс." + "\n"
        );
    }
}
