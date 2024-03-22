package at.bbrz;

import at.bbrz.commands.Command;
import org.apache.commons.collections4.map.CaseInsensitiveMap;

import java.util.HashMap;
import java.util.Map;

public class CommandHandler {
    private final Map<String, Command> commands = new CaseInsensitiveMap<>();
    private final Output outputHandler;

    public CommandHandler(Output outputHandler) {
        this.outputHandler = outputHandler;
    }

    public void runCommand(String input) {
        if (input == null) {
            outputHandler.printLine("Null input!", "red");
            outputHandler.emptyLine();
            return;
        }
        input = input.trim().toLowerCase();
        String[] inputArray = input.split(" ");

        if (inputArray.length > 2) {
            outputHandler.printLine("Too many parameters!", "red");
            outputHandler.emptyLine();
            return;
        }

        String command = inputArray[0];
        String parameter = "";
        if (inputArray.length == 2) {
            parameter = inputArray[1];
        }

        if (commands.containsKey(inputArray[0])) {
            commands.get(command).run(parameter);
            return;
        }

        outputHandler.printLine("Invalid command!", "red");
        outputHandler.emptyLine();
    }

    public void addCommand(String commandName, Command command) {
        commands.put(commandName, command);
    }
}
