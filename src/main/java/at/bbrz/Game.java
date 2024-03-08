package at.bbrz;

import at.bbrz.armor.*;
import at.bbrz.commands.Exit;
import at.bbrz.commands.Move;
import at.bbrz.commands.Stats;
import at.bbrz.commands.Suicide;
import lombok.Getter;
import lombok.Setter;

public class Game {
    @Getter
    @Setter
    private Room currentRoom;
    @Getter
    private Player player;
    private CommandHandler commandHandler;
    @Getter
    private final Output outputHandler;
    @Getter
    private final Input inputHandler;
    @Setter
    private boolean gameRunning = true;

    public Game(Output outputHandler, Input inputHandler, CommandHandler commandHandler) {
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
        this.commandHandler = commandHandler;
    }

    public void init() {
        askForEnter();
        setupPlayer();
        setupRooms();
        setupCommands();
        run();
    }

    private void run() {
        while (this.gameRunning) {
            outputHandler.printLine(currentRoom.getName());
            outputHandler.printLine(currentRoom.getDescription());
            outputHandler.printLine("What do you want to do?", "yellow");
            String input = inputHandler.getNextLine();
            outputHandler.emptyLine();
            commandHandler.runCommand(input);
        }
    }

    private void askForEnter() {
        outputHandler.printLine("Press Enter to start the game!", "green");
        inputHandler.getNextLine();
    }

    private void setupPlayer() {
        outputHandler.printLine("What's your name?", "yellow");
        String name = inputHandler.getNextLine();
        ArmorSet armorSet = new ArmorSet(
                new HeadArmor("Hat", 1),
                new TorsoArmor("Jacket", 2),
                new LegArmor("Linen Pants", 1),
                new FootArmor("Leather Shoes", 1));
        this.player = new Player(
                name,
                armorSet,
                new Weapon("Shortsword", 3),
                this.outputHandler,
                this);
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

    private void setupCommands() {
        this.commandHandler = new CommandHandler(outputHandler);
        commandHandler.addCommand("move",
                new Move(this, currentRoom, outputHandler, inputHandler));
        commandHandler.addCommand("exit", new Exit(this));
        commandHandler.addCommand("stats",
                new Stats(player, player.getArmorSet(), player.getWeapon(), outputHandler));
        commandHandler.addCommand("suicide", new Suicide(player));
    }
}
