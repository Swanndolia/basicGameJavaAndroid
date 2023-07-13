package dev.swanndolia.idlemmorpg.items.weapons.melee.rarityF;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.items.weapons.melee.MeleeWeapon;

public class Sword extends MeleeWeapon implements Serializable {
    public Sword() {
        this.setName("Broken Sword");
        this.setDesc("A very rustic Sword");
        this.setCritChance(5);
        this.setAccuracy(85);
        this.setDamage(2);
        this.setSellValue(5);
        this.setCritMultiplier(2.0);
        this.calculateCritDamage();
        this.setRarity("F");
        this.setIcon(R.drawable.z_icon_melee_weapon);
    }
}
