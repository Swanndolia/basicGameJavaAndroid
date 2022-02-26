package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;
import dev.swanndolia.idleasciimmorpg.items.rarity.Common;

public class Egg extends Drops implements Serializable , Common {
    public Egg Egg(){
        this.setName("Egg");
        this.setDesc("Should i throw it in someone face ?");
        this.setSellValue(8);
        this.Common(this);
        return this;
    }
}