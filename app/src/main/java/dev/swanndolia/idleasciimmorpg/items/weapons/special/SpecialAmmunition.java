package dev.swanndolia.idleasciimmorpg.items.weapons.special;

import dev.swanndolia.idleasciimmorpg.items.Item;

public class SpecialAmmunition extends Item {

    public SpecialAmmunition SpecialAmmunition() {
        this.setName("Special Ammunition");
        this.setDesc("Ammunition for special weapon");
        this.setSellValue(2);
        return this;
    }
}