package at.bbrz.commands;

import at.bbrz.Player;

import java.util.List;

public class Suicide implements Command {
    private final Player player;

    public Suicide(Player player) {
        this.player = player;
    }

    @Override
    public void run() {
        player.die();
    }
}
