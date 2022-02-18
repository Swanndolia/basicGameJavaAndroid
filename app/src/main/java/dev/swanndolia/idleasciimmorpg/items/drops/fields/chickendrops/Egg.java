package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;

public class Egg extends Drops implements Serializable {
    public Egg Egg(){
        Egg item = new Egg();
        item.setName("Egg");
        item.setDesc("Should i throw it in someone face ?");
        item.setSellValue(8);
        return item;
    }
}