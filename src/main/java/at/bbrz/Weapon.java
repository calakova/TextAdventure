package at.bbrz;

import lombok.Getter;

public class Weapon extends Item {
    @Getter
    private final int attack;

    public Weapon(String name, String description, int attack) {
        super(name, description);
        this.attack = attack;
    }
}
