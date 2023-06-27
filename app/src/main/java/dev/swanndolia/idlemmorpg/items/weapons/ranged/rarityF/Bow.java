package dev.swanndolia.idlemmorpg.items.weapons.ranged.rarityF;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.items.weapons.ranged.RangedWeapon;

public class Bow extends RangedWeapon implements Serializable {
    public Bow() {
        this.setName("Broken Bow");
        this.setDesc("A Broken bow, Still use arrows");
        this.setCritChance(30);
        this.setAccuracy(75);
        this.setDamage(3);
        this.setSellValue(25);
        this.setCritMultiplier(3.0);
        this.calculateCritDamage();
        this.setRarity("F");
        this.setIcon(R.drawable.icon_ranged_weapon);
    }
}
