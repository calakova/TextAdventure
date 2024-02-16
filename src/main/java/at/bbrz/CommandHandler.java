package at.bbrz;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    @Setter
    private String command;
    private final Map<String, Runnable> commands = new HashMap<>();
    private final OutputHandler outputHandler;

    public CommandHandler(Game game) {
        this.outputHandler = game.getOutputHandler();

        commands.put("move", new Move(game));
        commands.put("exit", new Exit(game));
    }

    public void runCommand() {
        if (commands.containsKey(command)) {
            commands.get(command).run();
            return;
        }

        outputHandler.printLine("Invalid command!");
        outputHandler.emptyLine();
    }
}
