package at.bbrz.commands;

import at.bbrz.items.armor.ArmorSet;
import at.bbrz.Output;
import at.bbrz.Player;
import at.bbrz.items.Weapon;

public class Stats implements Command {
    private final Player player;
    private final ArmorSet armorSet;
    private final Weapon weapon;
    private final Output outputHandler;

    public Stats(Player player, ArmorSet armorSet, Weapon weapon, Output outputHandler) {
        this.player = player;
        this.armorSet = armorSet;
        this.weapon = weapon;
        this.outputHandler = outputHandler;
    }

    @Override
    public void run(String parameter) {
        outputHandler.printLine("Name: " + player.getName(), "green");
        outputHandler.printLine("HP: " + player.getCurrentHP()
                + "/" + player.getMaxHP(), "green");
        outputHandler.printLine("Defence: " + armorSet.getDefence(), "green");
        outputHandler.printLine("Attack: " + weapon.getAttack(), "green");
        outputHandler.emptyLine();
    }
}
