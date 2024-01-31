package at.bbrz;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Getter
public class Room {
    private final Game game;
    private final String name;
    private final String description;
    private final Map<String, Room> exits;
    private List<Item> items;

    public Room(String name, String description, Game game) {
        this.game = game;
        this.name = name;
        this.description = description;
        this.exits = new HashMap<>();
    }

    public void addExit(String name, Room exit) {
        exits.put(name, exit);
    }

    public void addItem(Item item) {
        items.add(item);
    }

    public String getExitDirections() {
        StringBuilder directions = new StringBuilder();
        for (Map.Entry<String, Room> pair : game.getCurrentRoom().getExits().entrySet()) {
            directions.append(pair.getKey())
                    .append(", ");
        }
        directions.setLength(directions.length() - 2);
        return directions.toString();
    }
}
