package at.bbrz;

import java.util.Arrays;

public class Move implements Command {
    private final Game game;
    private Room currentRoom;
    private final OutputHandler outputHandler;
    private final InputHandler inputHandler;

    public Move(Game game, Room currentRoom, OutputHandler outputHandler, InputHandler inputHandler) {
        this.game = game;
        this.currentRoom = currentRoom;
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
    }

    @Override
    public void run() {
        String[] inputAndAvailableDirections = askDirection();
        String input = inputAndAvailableDirections[0];
        String availableDirections = inputAndAvailableDirections[1];

        if (isDirectionValid(input, availableDirections)) {
            changeRoom(input);
            return;
        }
        outputHandler.printLine("Invalid direction!", "red");
        outputHandler.emptyLine();
    }

    private String[] askDirection() {
        String directions = currentRoom.getExitDirections();
        outputHandler.printLine("Which direction do you want to go in?", "yellow");
        outputHandler.printLine("Available directions: " + directions, "blue");
        String input = inputHandler.getNextLine();
        outputHandler.emptyLine();
        return new String[]{input, directions};
    }

    private boolean isDirectionValid(String input, String directions) {
        String[] directionArr = directions.split(", ");
        return Arrays.asList(directionArr).contains(input);
    }

    /* TODO: Maybe find a way to not have the same property in two separate classes
    (assuming that doing so doesn't break unit tests */
    private void changeRoom(String direction) {
        Room nextRoom = currentRoom.getExitFor(direction);
        game.setCurrentRoom(nextRoom);
        this.currentRoom = nextRoom;
    }
}
