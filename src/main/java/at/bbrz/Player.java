package at.bbrz;

import lombok.Setter;

import java.util.List;

public class Player {
    @Setter
    private String name;
    private List<Item> items;

    public Player(String name) {
        this.name = name;
    }
}
