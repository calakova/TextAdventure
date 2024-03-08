package at.bbrz;

import at.bbrz.armor.ArmorSet;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class Player {
    @Getter
    @Setter
    private String name;
    @Getter
    private int maxHP = 100;
    @Getter
    @Setter
    private int currentHP = maxHP;
    @Getter
    private ArmorSet armorSet;
    @Getter
    private Weapon weapon;
    private List<Item> items;
    private final Output outputHandler;
    private final Game game;

    public Player(String name,
                  ArmorSet armorSet,
                  Weapon weapon,
                  Output outputHandler,
                  Game game) {
        this.name = name;
        this.armorSet = armorSet;
        this.weapon = weapon;
        this.outputHandler = outputHandler;
        this.game = game;
    }

    public void takeDamage(int rawDamage) {
        int playerDefence = armorSet.getDefence();
        int actualDamage = rawDamage - playerDefence;
        if (actualDamage < 0) actualDamage = 0;
        this.setCurrentHP(currentHP - actualDamage);

        outputHandler.printLine(name + " took " + actualDamage + " damage!", "purple");

        if (currentHP <= 0) {
            die();
        }

        outputHandler.printLine(currentHP + "/" + maxHP + " HP", "purple");
        outputHandler.emptyLine();
    }

    public void die() {
        outputHandler.printLine("You died!", "red");
        game.setGameRunning(false);
    }
}
