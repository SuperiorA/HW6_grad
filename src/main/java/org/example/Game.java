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
    private Map<Integer, String> resultsWithSwitch = new HashMap<>();
    private Map<Integer, String> resultsWithoutSwitch = new HashMap<>();

    public void runSimulation(int numberOfGames) {
        this.totalGames = numberOfGames;
        Random random = new Random();

        for (int i = 0; i < numberOfGames; i++) {
            int carPosition = random.nextInt(3);
            int initialChoice = random.nextInt(3);
            int openedDoor = getOpenedDoor(carPosition, initialChoice);
            if (initialChoice == carPosition) {
                resultsWithoutSwitch.put(i, "ААААВТОМОБИЛЬ");
                winsWithoutSwitch++;
            }

            int switchedChoice = getSwitchedChoice(initialChoice, openedDoor);
            if (switchedChoice == carPosition) {
                resultsWithSwitch.put(i, "ААААВТОМОБИЛЬ");
                winsWithSwitch++;
            }
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
        return -1;
    }

    public void printStatistics() {
        System.out.println("\nПримеры первых 20 игр:");
        System.out.println("Игра\tСмена\tБез смены");

        for (int i = 0; i < 20 && i < totalGames; i++) {
            String resultWithSwitch = resultsWithSwitch.get(i);
            String resultWithoutSwitch = resultsWithoutSwitch.get(i);

            System.out.printf("%d\t%-12s\t%-12s%n",
                    i + 1,
                    resultWithSwitch != null ? resultWithSwitch : "Коза",
                    resultWithoutSwitch != null ? resultWithoutSwitch : "Коза");
        }

        System.out.println("\nОбщая статистика:");
        System.out.printf("Побед при смене выбора: %d (%.2f%%)\n",
                winsWithSwitch, (double)winsWithSwitch/totalGames*100);
        System.out.printf("Побед без смены выбора: %d (%.2f%%)\n",
                winsWithoutSwitch, (double)winsWithoutSwitch/totalGames*100);
    }
}