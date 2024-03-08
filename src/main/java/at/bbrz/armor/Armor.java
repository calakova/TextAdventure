package at.bbrz.armor;

import lombok.Getter;

@Getter
public class Armor {
    protected String name;
    protected int defence;

    protected Armor(String name, int defence) {
        this.name = name;
        this.defence = defence;
    }
}
