package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;

public class Egg extends Drops implements Serializable {
    public Egg Egg(){
        this.setName("Egg");
        this.setDesc("Should i throw it in someone face ?");
        this.setSellValue(8);
        return this;
    }
}