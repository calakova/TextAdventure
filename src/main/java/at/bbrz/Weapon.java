package at.bbrz;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
public class Weapon {
    private String name;
    @Getter
    private int attack;
}
