package dev.swanndolia.idleasciimmorpg.characters;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.swanndolia.idleasciimmorpg.items.Item;

public class Character {
    String name;
    String desc;
    Integer hp;
    Integer level;
    Integer luck;
    Integer evade;
    Integer damage;
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

    protected void setDamage(int damage) {
        this.damage = damage;
    }
    protected Integer getDamage() {
        return damage;
    }
}