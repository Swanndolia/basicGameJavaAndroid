package dev.swanndolia.idlemmorpg.items.drops.fields;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;

public class Feather extends Drops implements Serializable {
    public Feather() {
        this.setName("Feather");
        this.setDesc("A bit of blood maculate it");
        this.setSellValue(3);
        this.setRarity("D");
    }
}
