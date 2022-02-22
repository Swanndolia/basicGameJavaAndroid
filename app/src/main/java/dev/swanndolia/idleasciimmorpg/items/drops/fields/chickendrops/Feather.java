package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.drops.Drops;

public class Feather extends Drops implements Serializable {
    public Feather Feather() {
        this.setName("Feather");
        this.setDesc("A bit of blood maculate it");
        this.setSellValue(3);
        return this;
    }
}
