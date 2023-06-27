package dev.swanndolia.idlemmorpg.items.weapons.melee;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.items.weapons.Weapon;

public class MeleeWeapon extends Weapon implements Serializable {
    public MeleeWeapon() {
        this.setSlot("Melee Weapon");
        this.setIcon(R.drawable.icon_melee_weapon);
    }
}
