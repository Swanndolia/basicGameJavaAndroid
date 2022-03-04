package dev.swanndolia.idlemmorpg.items.armor.boots;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.armor.torso.TorsoSlot;

public class BonesBoots extends TorsoSlot implements Serializable {
    public BonesBoots() {
        this.setName("Bones Boots");
        this.setDesc("An armor made with animals bones");
        this.setSellValue(5);
        this.setDodgeChance(5);
        this.setWeight(1);
        this.setProtection(2);
    }
}
