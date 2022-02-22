package dev.swanndolia.idleasciimmorpg.items.weapons.normal;

import java.io.Serializable;

public class BrokenSword extends NormalWeapon implements Serializable {
    public BrokenSword BrokenSword() {
        this.setName("Broken Sword");
        this.setDesc("A very rustic Sword");
        this.setCritChance(5);
        this.setAccuracy(85);
        this.setDamage(2);
        this.setSellValue(5);
        this.setCritMultiplier(2.0);
        this.calculateCritDamage();
        return this;
    }
}
