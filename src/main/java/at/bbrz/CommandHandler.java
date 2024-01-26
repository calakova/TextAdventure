package at.bbrz;

import java.util.Map;

public class CommandHandler {
    private String input;
    private Map<String, Runnable> commands = Map.of(
            "move", new Move()
    );

    //TODO: foreach that checks if input has corresponding command in commands Map
}
