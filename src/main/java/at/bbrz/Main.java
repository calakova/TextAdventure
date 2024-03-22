package at.bbrz;

import com.google.gson.Gson;

public class Main {
    public static void main(String[] args) {
        OutputHandler outputHandler = new OutputHandler();
        InputHandler inputHandler = new InputHandler();
        CommandHandler commandHandler = new CommandHandler(outputHandler);
        Gson gson = new Gson();
        JSONLoader JSONLoader = new JSONLoader(outputHandler, gson);

        Game game = new Game(outputHandler, inputHandler, commandHandler, JSONLoader);
        game.init();
    }
}