package at.bbrz;

public class Main {
    public static void main(String[] args) {
        OutputHandler outputHandler = new OutputHandler();
        InputHandler inputHandler = new InputHandler();
        CommandHandler commandHandler = new CommandHandler(outputHandler);
        Game game = new Game(outputHandler, inputHandler, commandHandler);
        game.init();
    }
}