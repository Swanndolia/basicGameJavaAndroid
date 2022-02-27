package dev.swanndolia.idlemmorpg.items.weapons.normal;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.rarity.Broken;

public class BrokenSword extends NormalWeapon implements Serializable, Broken {
    public BrokenSword() {
        this.setName("Broken Sword");
        this.setDesc("A very rustic Sword");
        this.setCritChance(5);
        this.setAccuracy(85);
        this.setDamage(2);
        this.setSellValue(5);
        this.setCritMultiplier(2.0);
        this.calculateCritDamage();
        this.Broken(this);
    }
}
