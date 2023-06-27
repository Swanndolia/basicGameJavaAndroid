package dev.swanndolia.idlemmorpg.items.drops.fields;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.drops.Drops;

public class Egg extends Drops implements Serializable {
    public Egg(){
        this.setName("Egg");
        this.setDesc("Should i throw it in someone face ?");
        this.setSellValue(8);
        this.setRarity("D");
    }
}