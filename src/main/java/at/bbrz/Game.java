package at.bbrz;

import at.bbrz.items.*;
import at.bbrz.commands.*;
import at.bbrz.items.armor.*;
import lombok.Getter;
import lombok.Setter;

public class Game extends Publisher {
    @Getter
    private Room currentRoom;
    @Getter
    private Player player;
    private final CommandHandler commandHandler;
    @Getter
    private final Output outputHandler;
    @Getter
    private final Input inputHandler;
    private final JSONLoader JSONLoader;
    private Item[] items;
    private Armor[] armors;
    private Weapon[] weapons;
    private Room[] rooms;
    @Setter
    private boolean gameRunning = true;

    public Game(Output outputHandler,
                Input inputHandler,
                CommandHandler commandHandler,
                JSONLoader JSONLoader) {
        this.outputHandler = outputHandler;
        this.inputHandler = inputHandler;
        this.commandHandler = commandHandler;
        this.JSONLoader = JSONLoader;
    }

    public void setCurrentRoom(Room currentRoom) {
        this.currentRoom = currentRoom;
        notifySubscribers(currentRoom);
    }

    public void init() {
        loadJSONData();
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

    private void loadJSONData() {
        // TODO: Figure out how to work with these arrays
        items = JSONLoader.loadItems(
                "C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                        "\\src\\main\\resources\\json\\items.json");
        armors = JSONLoader.loadArmor(
                "C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                        "\\src\\main\\resources\\json\\armor.json");
        weapons = JSONLoader.loadWeapons(
                "C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                        "\\src\\main\\resources\\json\\weapons.json");
        rooms = JSONLoader.loadRooms(
                "C:\\Users\\BBRZ\\IdeaProjects\\TextAdventure" +
                        "\\src\\main\\resources\\json\\rooms.json");
    }

    private void askForEnter() {
        outputHandler.printLine("Press Enter to start the game!", "green");
        inputHandler.getNextLine();
    }

    private void setupPlayer() {
        outputHandler.printLine("What's your name?", "yellow");
        String name = inputHandler.getNextLine();
        ArmorSet armorSet = new ArmorSet(
                new HeadArmor("Hat",
                        "A hat that has withstood the test of time. \n" +
                                "Offers little to no defensive value.",
                        1),
                new TorsoArmor("Jacket",
                        "A somewhat new wool jacket.",
                        2),
                new LegArmor("Linen Pants",
                        "A nice pair of linen pants.",
                        1),
                new FootArmor("Leather Shoes",
                        "A worn pair of leather shoes.",
                        1));
        this.player = new Player(
                name,
                armorSet,
                new Weapon("Shortsword",
                        "A shortwsword. \nRather weak, but better than nothing",
                        3),
                outputHandler,
                this);
        outputHandler.emptyLine();
    }

    private void setupRooms() {
        for (Room room : rooms) {
            room.setGame(this);
        }
        Room home = rooms[0];
        Room woods = rooms[1];
        Room deepWoods = rooms[2];
        Room swamp = rooms[3];
        Room hills = rooms[4];

        home.addItem(new Item("Torch",
                "An unlit torch. \n" +
                "Use your Ignite spell to light it!"));
        home.addItem(new Item("Health Potion",
                "A blood red potion. \n" +
                        "Use it to restore 50 HP"));

        this.currentRoom = home;
    }

    /*
    private void addExitsToRooms(Room[] rooms) {
        Room home = rooms[0];
        Room woods = rooms[1];
        Room deepWoods = rooms[2];
        Room swamp = rooms[3];
        Room hills = rooms[4];

        home.addExit("E", woods);
        woods.addExit("W", home);
        woods.addExit("N", deepWoods);
        woods.addExit("S", swamp);
        woods.addExit("E", hills);
        deepWoods.addExit("S", woods);
        swamp.addExit("N", woods);
        hills.addExit("W", woods);
    }

     */

    private void setupCommands() {
        commandHandler.addCommand("exit", new Exit(this));
        commandHandler.addCommand("stats",
                new Stats(player, player.getArmorSet(), player.getWeapon(), outputHandler));
        commandHandler.addCommand("suicide", new Suicide(player));

        Move move = new Move(this, currentRoom, outputHandler);
        commandHandler.addCommand("move", move);
        subscribe(move);
        Look look = new Look(currentRoom, outputHandler);
        commandHandler.addCommand("look", look);
        subscribe(look);
    }
}
