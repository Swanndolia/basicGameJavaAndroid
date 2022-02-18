package dev.swanndolia.idleasciimmorpg.items.armor.torso;

import java.io.Serializable;

public class BasicTunic extends TorsoSlot implements Serializable {
    public BasicTunic BasicTunic() {
        BasicTunic item = new BasicTunic();
        item.setName("Basic Tunic");
        item.setDesc("A common tunic");
        item.setSellValue(5);
        item.setDodgeChance(5);
        item.setWeight(1);
        item.setProtection(1);
        return item;
    }
}
