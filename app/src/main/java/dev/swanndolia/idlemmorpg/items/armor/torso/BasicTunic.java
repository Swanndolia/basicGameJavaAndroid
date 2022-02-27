package dev.swanndolia.idlemmorpg.items.armor.torso;

import java.io.Serializable;

public class BasicTunic extends TorsoSlot implements Serializable {
    public BasicTunic() {
        this.setName("Basic Tunic");
        this.setDesc("A common tunic");
        this.setSellValue(5);
        this.setDodgeChance(5);
        this.setWeight(1);
        this.setProtection(1);
    }
}
