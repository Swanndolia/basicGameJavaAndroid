package dev.swanndolia.idlemmorpg.items.armor.legs;

import java.io.Serializable;

import dev.swanndolia.idlemmorpg.items.armor.torso.TorsoSlot;

public class BonesLegs extends TorsoSlot implements Serializable {
    public BonesLegs() {
        this.setName("Bones Legs");
        this.setDesc("An armor made with animals bones");
        this.setSellValue(5);
        this.setDodgeChance(5);
        this.setWeight(1);
        this.setProtection(2);
    }
}
