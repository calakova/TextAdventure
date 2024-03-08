package at.bbrz.armor;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class ArmorSet {
    private HeadArmor headArmor;
    private TorsoArmor torsoArmor;
    private LegArmor legArmor;
    private FootArmor footArmor;

    public int getDefence() {
        return headArmor.getDefence()
                + torsoArmor.getDefence()
                + legArmor.getDefence()
                + footArmor.getDefence();
    }
}
