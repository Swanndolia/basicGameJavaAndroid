package dev.swanndolia.idlemmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;
import dev.swanndolia.idlemmorpg.items.rarity.Common;

public class Egg extends Drops implements Serializable , Common {
    public Egg(){
        this.setName("Egg");
        this.setDesc("Should i throw it in someone face ?");
        this.setSellValue(8);
        this.Common(this);
    }
}