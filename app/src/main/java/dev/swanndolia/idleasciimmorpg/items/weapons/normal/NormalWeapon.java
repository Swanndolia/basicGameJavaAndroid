package dev.swanndolia.idleasciimmorpg.items.weapons.normal;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.weapons.Weapon;

public class NormalWeapon  extends Weapon implements Serializable {
    public NormalWeapon(){
        this.setSlot("Basic Weapon");
    }
}
