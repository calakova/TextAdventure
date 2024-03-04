package at.bbrz;

import lombok.Setter;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    @Setter
    private String command;
    private final Map<String, Command> commands = new HashMap<>();
    private final Output outputHandler;

    public CommandHandler(Output outputHandler) {
        this.outputHandler = outputHandler;
    }

    public void runCommand() {
        if (commands.containsKey(command)) {
            commands.get(command).run();
            return;
        }

        outputHandler.printLine("Invalid command!", "red");
        outputHandler.emptyLine();
    }

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }
}
