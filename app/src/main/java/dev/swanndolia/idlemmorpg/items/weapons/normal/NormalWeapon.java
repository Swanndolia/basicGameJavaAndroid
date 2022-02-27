package dev.swanndolia.idlemmorpg.items.weapons.normal;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.items.weapons.Weapon;

public class NormalWeapon extends Weapon implements Serializable {
    public NormalWeapon() {
        this.setSlot("Basic Weapon");
        this.setIcon(R.drawable.sword);
    }
}
