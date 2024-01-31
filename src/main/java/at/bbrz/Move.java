package at.bbrz;

import java.util.Map;
import java.util.Scanner;

public class Move implements Runnable {
    private final Game game;
    private final Scanner scanner = new Scanner(System.in);

    public Move(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        String directions = game.getCurrentRoom().getExitDirections();
        System.out.println("Which direction do you want to go in?\n" +
                "Available directions: " + directions);
        String input = scanner.nextLine();
        System.out.println();

        Room nextRoom = game.getCurrentRoom().getExits().get(input);
        game.setCurrentRoom(nextRoom);
    }
}
