package dev.swanndolia.idleasciimmorpg.items.weapons.ultimate;

import dev.swanndolia.idleasciimmorpg.items.Item;

public class UltimateAmmunition extends Item {

    public UltimateAmmunition UltimateAmmunition() {
        this.setName("Ultimate Ammunition");
        this.setDesc("Ammunition for ultimate weapon");
        this.setSellValue(2);
        return this;
    }
}