package at.bbrz.commands;

import at.bbrz.Game;
import at.bbrz.Output;
import at.bbrz.Room;
import at.bbrz.Subscriber;

import java.util.ArrayList;

public class Move implements Command, Subscriber {
    private final Game game;
    private Room currentRoom;
    private final Output outputHandler;

    public Move(Game game, Room currentRoom, Output outputHandler) {
        this.game = game;
        this.currentRoom = currentRoom;
        this.outputHandler = outputHandler;
    }

    @Override
    public void run(String direction) {
        ArrayList<String> availableDirections = currentRoom.getExitDirectionsArrayList();

        if ((direction != null) && isDirectionValid(direction, availableDirections)) {
            changeRoom(direction);
            return;
        }
        outputHandler.printLine("Invalid direction!", "red");
        outputHandler.emptyLine();
    }

    @Override
    public void update(Room room) {
        currentRoom = room;
    }

    private boolean isDirectionValid(String input, ArrayList<String> directions) {
        return directions.stream().anyMatch(input::equalsIgnoreCase);
    }

    private void changeRoom(String direction) {
        Room nextRoom = currentRoom.getExitFor(direction);
        game.setCurrentRoom(nextRoom);
    }
}
