package dev.swanndolia.idlemmorpg.items.drops.fields;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Common;

public class Feather extends Drops implements Serializable, Common {
    public Feather() {
        this.setName("Feather");
        this.setDesc("A bit of blood maculate it");
        this.setSellValue(3);
        this.Common(this);
    }
}
