package dev.swanndolia.idleasciimmorpg.items;

import java.io.Serializable;

public class Item implements Serializable {

    public String name;
    public String desc;
    public String slot;
    public Integer dodgeChance;
    public Integer protection;
    public Integer weight;
    public Integer sellValue;
    public Boolean equipped;
    public Integer damage;
    public Integer critChance;
    public Integer critDamage;
    public Integer accuracy;
    public Double critMultiplier;

    public Item() {
        this.slot = "None";
        this.equipped = false;
    }

    public String getSlot() {
        return slot;
    }

    public void setSlot(String slot) {
        this.slot = slot;
    }

    public Integer getDamage() {
        return damage;
    }

    public void setDamage(Integer damage) {
        this.damage = damage;
    }

    public Integer getCritChance() {
        return critChance;
    }

    public void setCritChance(Integer critChance) {
        this.critChance = critChance;
    }

    public Integer getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(Integer accuracy) {
        this.accuracy = accuracy;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getSellValue() {
        return sellValue;
    }

    public void setSellValue(Integer sellValue) {
        this.sellValue = sellValue;
    }

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

    public Boolean isEquipped() {
        return equipped;
    }

    public void setEquipped(Boolean equipped) {
        this.equipped = equipped;
    }

    public void setCritMultiplier(Double critMultiplier) {
        this.critMultiplier = critMultiplier;
    }

    public Double getCritMultiplier() {
        return critMultiplier;
    }

    public void calculateCritDamage() {
        this.critDamage = (int) (this.damage * this.critMultiplier);
    }

    public Integer getCritDamage() {
        return critDamage;
    }
}
