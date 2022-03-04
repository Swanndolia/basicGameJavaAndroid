package dev.swanndolia.idlemmorpg.items.armor.torso;

import java.io.Serializable;

public class BonesArmor extends TorsoSlot implements Serializable {
    public BonesArmor() {
        this.setName("Bones Armor");
        this.setDesc("An armor made with animals bones");
        this.setSellValue(5);
        this.setDodgeChance(5);
        this.setWeight(1);
        this.setProtection(2);
    }
}
