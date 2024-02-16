package at.bbrz;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

public class Game {
    @Getter
    @Setter
    private Room currentRoom;
    @Getter
    private Player player;
    private CommandHandler commandHandler;
    @Getter
    private final OutputHandler outputHandler = new OutputHandler();
    @Getter
    private final InputHandler inputHandler = new InputHandler();
    @Setter
    private boolean gameRunning = true;

    public void init() {
        askForEnter();
        setupPlayer();
        setupRooms();

        this.commandHandler = new CommandHandler(this);

        run();
    }

    private void run() {
        while (this.gameRunning) {
            outputHandler.printLine(currentRoom.getName());
            outputHandler.printLine(currentRoom.getDescription());
            outputHandler.printLine("What do you want to do?");
            String input = inputHandler.getNextLine();
            outputHandler.emptyLine();
            commandHandler.setCommand(input);
            commandHandler.runCommand();
        }
    }

    private void askForEnter() {
        outputHandler.printLine(OutputColors.GREEN.label + "Press Enter to start the game!" + OutputColors.RESET.label);
        inputHandler.getNextLine();
    }

    private void setupPlayer() {
        outputHandler.printLine("What's your name?");
        String name = inputHandler.getNextLine();
        this.player = new Player(name);
        outputHandler.emptyLine();
    }

    private void setupRooms() {
        Room home = new Room("Home", "A cozy hut in the middle of the woods.", this);
        Room woods = new Room("The Woods", "The woods outside of your home.", this);
        Room deepWoods = new Room("Deep Woods", "The deepest parts of the woods.", this);
        Room swamp = new Room("The Swamp", "A treacherous swamp lies before you.", this);
        Room hills = new Room("The Hills", "A hilly bit of land.", this);

        home.addExit("E", woods);
        woods.addExit("W", home);
        woods.addExit("N", deepWoods);
        woods.addExit("S", swamp);
        woods.addExit("E", hills);
        deepWoods.addExit("S", woods);
        swamp.addExit("N", woods);
        hills.addExit("W", woods);

        this.currentRoom = home;
    }
}
