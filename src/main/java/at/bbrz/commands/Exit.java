package at.bbrz.commands;

import at.bbrz.Game;

public class Exit implements Command{
    private final Game game;

    public Exit(Game game) {
        this.game = game;
    }

    @Override
    public void run() {
        game.setGameRunning(false);
    }
}
