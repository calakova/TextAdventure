package at.bbrz.items;

import lombok.Getter;

@Getter
public class Weapon extends Item {
    private final int attack;

    public Weapon(String name, String description, int attack) {
        super(name, description);
        this.attack = attack;
    }
}
