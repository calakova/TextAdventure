package at.bbrz;

import java.util.Arrays;
import java.util.Scanner;

public class Move implements Runnable {
    private final Game game;
    private final OutputHandler outputHandler;
    private final InputHandler inputHandler;
    private final Scanner scanner = new Scanner(System.in);

    public Move(Game game) {
        this.game = game;
        this.outputHandler = game.getOutputHandler();
        this.inputHandler = game.getInputHandler();
    }

    @Override
    public void run() {
        while (true) {
            String[] inputAndAvailableDirections = askDirection();
            String input = inputAndAvailableDirections[0];
            String availableDirections = inputAndAvailableDirections[1];
            if (isDirectionValid(input, availableDirections)) {
                changeRoom(input);
                break;
            }
            outputHandler.printLine("Invalid direction!");
        }
    }

    private String[] askDirection() {
        String directions = game.getCurrentRoom().getExitDirections();
        outputHandler.printLine("Which direction do you want to go in?\n" +
                "Available directions: " + directions);
        String input = inputHandler.getNextLine();
        outputHandler.emptyLine();
        return new String[]{input, directions};
    }

    private boolean isDirectionValid(String input, String directions) {
        String[] directionArr = directions.split(", ");
        return Arrays.asList(directionArr).contains(input);
    }

    private void changeRoom(String direction) {
        Room nextRoom = game.getCurrentRoom().getExits().get(direction);
        game.setCurrentRoom(nextRoom);
    }
}
