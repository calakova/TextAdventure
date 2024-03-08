package at.bbrz.commands;

import at.bbrz.Output;
import at.bbrz.Room;

import java.util.ArrayList;

public class Look implements Command {
    private Room room;
    private Output outputHandler;

    public Look(Room room, Output outputHandler) {
        this.room = room;
        this.outputHandler = outputHandler;
    }

    @Override
    public void run() {
        String itemsString = String.join(", ", room.getListOfItems());
        outputHandler.printLine(itemsString, "cyan");
        outputHandler.emptyLine();
    }
}
