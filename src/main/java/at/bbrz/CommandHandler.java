package at.bbrz;

import at.bbrz.commands.Command;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands = new HashMap<>();
    private final Output outputHandler;

    public CommandHandler(Output outputHandler) {
        this.outputHandler = outputHandler;
    }

    public void runCommand(String input) {
        if (commands.containsKey(input)) {
            commands.get(input).run();
            return;
        }

        outputHandler.printLine("Invalid command!", "red");
        outputHandler.emptyLine();
    }

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }
}
