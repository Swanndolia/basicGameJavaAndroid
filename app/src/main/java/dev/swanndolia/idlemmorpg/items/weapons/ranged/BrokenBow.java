package dev.swanndolia.idlemmorpg.items.weapons.ranged;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.items.rarity.Broken;

public class BrokenBow extends RangedWeapon implements Serializable, Broken {
    public BrokenBow() {
        this.setName("Broken Bow");
        this.setDesc("A Broken bow, Still use arrows");
        this.setCritChance(30);
        this.setAccuracy(75);
        this.setDamage(3);
        this.setSellValue(25);
        this.setCritMultiplier(3.0);
        this.calculateCritDamage();
        this.Broken(this);
        this.setIcon(R.drawable.bow);
    }
}
