package dev.swanndolia.idlemmorpg.characters;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.swanndolia.idlemmorpg.items.Item;

public class DefaultEncounter implements Serializable {
    String name;
    String desc;
    Integer hp;
    Integer level;
    Integer luck;
    Integer evade;
    Integer damage;
    Integer accuracy;
    Integer expReward;
    Integer critChance;
    Double critMultiplier;
    List<Item> inventory;

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

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public Integer getLuck() {
        return luck;
    }

    public void setLuck(Integer luck) {
        this.luck = luck;
    }

    public Integer getEvade() {
        return evade;
    }

    public void setEvade(Integer evade) {
        this.evade = evade;
    }

    public List<Item> getInventory() {
        return inventory;
    }

    public void setInventory(Map<Item, Integer> map) {
        List<Item> inventory = new ArrayList<Item>();
        for (Item key : map.keySet()) {
            int random = (int) (Math.random() * 1000);
            if (map.get(key) <= random) {
                inventory.add(key);
            }
        }
        this.inventory = inventory;
    }

    public Integer getDamage() {
        return damage;
    }

    protected void setDamage(int damage) {
        this.damage = damage;
    }

    public Integer getCritDamage() {
        if (this.damage != null && this.critMultiplier != null) {
            return (int) (this.damage * this.critMultiplier);
        }
        return null;
    }

    public Double getCritMultiplier() {
        return this.critMultiplier;
    }

    public void setCritMultiplier(Double critMultiplier) {
        this.critMultiplier = critMultiplier;
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

    public Integer getExpReward() {
        return expReward;
    }

    public void setExpReward(Integer expReward) {
        this.expReward = expReward;
    }

    public void removeInventory(Item item) {
        this.inventory.remove(item);
    }
}