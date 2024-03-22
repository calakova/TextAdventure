package at.bbrz.commands;

import at.bbrz.Output;
import at.bbrz.Room;
import at.bbrz.Subscriber;

public class Look implements Command, Subscriber {
    private Room room;
    private final Output outputHandler;

    public Look(Room room, Output outputHandler) {
        this.room = room;
        this.outputHandler = outputHandler;
    }

    @Override
    public void run(String parameter) {
        String itemsString = String.join(", ", room.getListOfItems());

        if (itemsString.isEmpty()) {
            outputHandler.printLine("No items found in this room!", "red");
            outputHandler.emptyLine();
        }

        outputHandler.printLine(itemsString, "cyan");
        outputHandler.emptyLine();
    }

    @Override
    public void update(Room currentRoom) {
        room = currentRoom;
    }
}
