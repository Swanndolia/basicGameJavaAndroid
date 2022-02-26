package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.drops.Drops;
import dev.swanndolia.idleasciimmorpg.items.rarity.Common;

public class Feather extends Drops implements Serializable, Common {
    public Feather Feather() {
        this.setName("Feather");
        this.setDesc("A bit of blood maculate it");
        this.setSellValue(3);
        this.Common(this);
        return this;
    }
}
