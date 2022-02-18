package dev.swanndolia.idleasciimmorpg.items.weapons.normal;

import java.io.Serializable;

public class BasicPistol extends NormalWeapon implements Serializable {
    public BasicPistol BasicPistol() {
        BasicPistol item = new BasicPistol();
        item.setName("Basic Pistol");
        item.setDesc("A very basic pistol");
        item.setCritChance(0);
        item.setAccuracy(40);
        item.setDamage(1);
        item.setSellValue(5);
        return item;
    }
}
