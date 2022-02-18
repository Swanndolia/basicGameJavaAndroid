package dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops;

import java.io.Serializable;

import dev.swanndolia.idleasciimmorpg.items.drops.Drops;

public class Feather extends Drops implements Serializable {
    public Feather Feather(){
        Feather item = new Feather();
        item.setName("Feather");
        item.setDesc("A bit of blood maculate it");
        item.setSellValue(3);
        return item;
    }
}
