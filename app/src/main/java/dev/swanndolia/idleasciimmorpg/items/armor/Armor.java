package dev.swanndolia.idleasciimmorpg.items.armor;

import dev.swanndolia.idleasciimmorpg.items.Item;

public class Armor extends Item {

    public Armor(){
        this.setEquipped(false);
    }

    Integer dodgeChance;
    Integer protection;
    Integer weight;

    public Integer getDodgeChance() {
        return dodgeChance;
    }

    public void setDodgeChance(Integer dodgeChance) {
        this.dodgeChance = dodgeChance;
    }

    public Integer getProtection() {
        return protection;
    }

    public void setProtection(Integer protection) {
        this.protection = protection;
    }

    public Integer getWeight() {
        return weight;
    }

    public void setWeight(Integer weight) {
        this.weight = weight;
    }
}
