package at.bbrz.items;

import lombok.Getter;

@Getter
public class Item {
    protected final String name;
    protected final String description;

    public Item(String name, String description) {
        this.name = name;
        this.description = description;
    }
}
