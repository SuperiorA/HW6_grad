package org.example;

import lombok.Getter;
import lombok.Setter;
import java.util.*;

@Getter
@Setter
public class Game {
    private int totalGames;
    private int winsWithSwitch;
    private int winsWithoutSwitch;
    private Map<Integer, Boolean> resultsWithSwitch = new HashMap<>();
    private Map<Integer, Boolean> resultsWithoutSwitch = new HashMap<>();

    public void runSimulation(int numberOfGames) {
        this.totalGames = numberOfGames;
        Random random = new Random();

        for (int i = 0; i < numberOfGames; i++) {
            // Инициализация игры: за одной из 3 дверей автомобиль
            int carPosition = random.nextInt(3);

            // Игрок делает первоначальный выбор
            int initialChoice = random.nextInt(3);

            // Ведущий открывает дверь с козой
            int openedDoor = getOpenedDoor(carPosition, initialChoice);

            // Игрок решает не менять выбор
            boolean winWithoutSwitch = initialChoice == carPosition;
            resultsWithoutSwitch.put(i, winWithoutSwitch);
            if (winWithoutSwitch) winsWithoutSwitch++;

            // Игрок решает изменить выбор
            int switchedChoice = getSwitchedChoice(initialChoice, openedDoor);
            boolean winWithSwitch = switchedChoice == carPosition;
            resultsWithSwitch.put(i, winWithSwitch);
            if (winWithSwitch) winsWithSwitch++;
        }
    }

    private int getOpenedDoor(int carPosition, int initialChoice) {
        Random random = new Random();
        int openedDoor;
        do {
            openedDoor = random.nextInt(3);
        } while (openedDoor == carPosition || openedDoor == initialChoice);
        return openedDoor;
    }

    private int getSwitchedChoice(int initialChoice, int openedDoor) {
        for (int i = 0; i < 3; i++) {
            if (i != initialChoice && i != openedDoor) {
                return i;
            }
        }
        return -1; // fallback (never happens)
    }

    public void printStatistics() {
        System.out.println("Статистика после " + totalGames + " игр:");
        System.out.printf("Побед при смене выбора: %d (%.2f%%)%n",
                winsWithSwitch, (double)winsWithSwitch/totalGames*100);
        System.out.printf("Побед без смены выбора: %d (%.2f%%)%n",
                winsWithoutSwitch, (double)winsWithoutSwitch/totalGames*100);

        System.out.println("\nПримеры первых 10 игр:");
        System.out.println("Игра\tСмена\tБез смены");
        for (int i = 0; i < 10 && i < totalGames; i++) {
            System.out.printf("%d\t%s\t%s%n",
                    i+1,
                    resultsWithSwitch.get(i) ? "Победа" : "Поражение",
                    resultsWithoutSwitch.get(i) ? "Победа" : "Поражение");
        }
    }
}