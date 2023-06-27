package dev.swanndolia.idlemmorpg.items.drops.fields;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;

public class RawChicken extends Drops implements Serializable {
    public RawChicken(){
        this.setName("Raw Chicken");
        this.setDesc("Don't look so good for now, i should cook it first");
        this.setSellValue(1);
        this.setRarity("D");
    }
}
