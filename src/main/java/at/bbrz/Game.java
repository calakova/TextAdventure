package at.bbrz;

import lombok.Getter;
import lombok.Setter;

import java.util.Scanner;

@Getter
public class Game {
    @Setter
    private Room currentRoom;
    private Player player;
    private Scanner scanner = new Scanner(System.in);

    public Game() {
        init();
        askForEnter();
        run();
    }

    private void init() {
        // TODO: ask for player input for the name
        player = new Player("PLACEHOLDER");

        Room home = new Room("Home", "A cozy hut in the middle of the woods.");
        Room woods = new Room("The Woods", "The woods outside of your home.");
        Room deepWoods = new Room("Deep Woods", "The deepest parts of the woods.");
        Room swamp = new Room("The Swamp", "A treacherous swamp lies before you.");
        Room hills = new Room("The Hills", "A hilly bit of land.");

        home.addExit("E", woods);
        woods.addExit("W", home);
        woods.addExit("N", deepWoods);
        woods.addExit("S", swamp);
        woods.addExit("E", hills);
        deepWoods.addExit("S", woods);
        swamp.addExit("N", woods);
        hills.addExit("W", woods);

        currentRoom = home;
    }

    private void askForEnter() {
        System.out.println("Press Enter to start the game!");
        scanner.nextLine();
    }

    private void run() {
        System.out.println(currentRoom.getName());
        System.out.println(currentRoom.getDescription());
        System.out.println("What do you want to do?");
        String input = scanner.nextLine();
        // TODO: use input to check which command to run

        // TODO: implement a way to quit the game
        run();
    }
}
