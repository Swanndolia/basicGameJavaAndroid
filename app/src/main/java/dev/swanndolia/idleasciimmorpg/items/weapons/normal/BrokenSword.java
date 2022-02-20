package dev.swanndolia.idleasciimmorpg.items.weapons.normal;

import java.io.Serializable;

public class BrokenSword extends NormalWeapon implements Serializable {
    public BrokenSword BrokenSword() {
        BrokenSword item = new BrokenSword();
        item.setName("Broken Sword");
        item.setDesc("A very rustic Sword");
        item.setCritChance(0);
        item.setAccuracy(70);
        item.setDamage(1);
        item.setSellValue(5);
        item.setCritMultiplier(2.0);
        item.calculateCritDamage();
        return item;
    }
}
