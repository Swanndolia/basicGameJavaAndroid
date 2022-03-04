package dev.swanndolia.idlemmorpg.items.armor.helmet;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.armor.torso.TorsoSlot;

public class BonesHelmet extends TorsoSlot implements Serializable {
    public BonesHelmet() {
        this.setName("Bones Helmet");
        this.setDesc("An armor made with animals bones");
        this.setSellValue(5);
        this.setDodgeChance(5);
        this.setWeight(1);
        this.setProtection(2);
    }
}
