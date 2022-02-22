package dev.swanndolia.idleasciimmorpg.items.weapons.special;

import java.io.Serializable;

public class StandardPistol extends SpecialWeapon implements Serializable {
    public StandardPistol StandardPistol() {
        this.setName("Standard Pistol");
        this.setDesc("A classic pistol, watch your ammo");
        this.setCritChance(30);
        this.setAccuracy(75);
        this.setDamage(3);
        this.setSellValue(25);
        this.setCritMultiplier(3.0);
        this.calculateCritDamage();
        return this;
    }
}
