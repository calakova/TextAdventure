package at.bbrz.items.armor;

import at.bbrz.items.Item;
import lombok.Getter;

@Getter
public class Armor extends Item {
    protected final int defence;

    protected Armor(String name, String description, int defence) {
        super(name, description);
        this.defence = defence;
    }
}
