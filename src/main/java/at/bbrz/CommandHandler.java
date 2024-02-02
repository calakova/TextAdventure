package at.bbrz;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    @Setter
    private String command;
    private final Map<String, Runnable> commands = new HashMap<>();

    public CommandHandler(Game game) {
        commands.put("move", new Move(game));
        commands.put("exit", new Exit(game));
    }

    public void runCommand() {
        if (commands.containsKey(command)) {
            commands.get(command).run();
            return;
        }

        System.out.println("Invalid command!");
    }
}
