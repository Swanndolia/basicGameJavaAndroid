package dev.swanndolia.idlemmorpg.characters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.armor.torso.BasicTunic;
import dev.swanndolia.idlemmorpg.items.weapons.normal.BrokenSword;
import dev.swanndolia.idlemmorpg.items.weapons.ranged.BrokenBow;
import dev.swanndolia.idlemmorpg.tools.player.ForceSaveInventoryList;

public class Player implements Serializable {

    String name;
    Integer hp;
    Integer mp;
    Integer maxMp;
    Integer level;
    Integer arrow;
    Integer cryptoCoins;
    Integer luck;
    Integer maxHp;
    Integer evade;
    Integer exp;
    Integer nextLevelExp;
    List<Item> inventory;
    List<Item> equippedInventory;

    public Player() {
    }

    public Player(String name) {
        this.name = name;
        this.hp = 100;
        this.mp = 100;
        this.maxHp = 100;
        this.maxMp = 100;
        this.level = 1;
        this.exp = 0;
        this.nextLevelExp = 8;
        this.arrow = 0;
        this.cryptoCoins = 0;
        this.luck = 0;
        this.evade = 0;
        this.inventory = new ArrayList<Item>();
        this.equippedInventory = new ArrayList<Item>();
        this.giveAndEquipBasicStuff();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getHp() {
        return hp;
    }

    public void setHp(Integer hp) {
        this.hp = hp;
    }

    public Integer getMaxHp() {
        return this.maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getMp() {
        return mp;
    }

    public void setMp(Integer mp) {
        this.mp = mp;
    }

    public Integer getMaxMp() {
        return maxMp;
    }

    public void setMaxMp(Integer maxMp) {
        this.maxMp = maxMp;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public void addExp(Integer exp) {
        this.exp += exp;
    }

    public Integer getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(Integer nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public void checkLevelUp() {
        if (this.getExp() >= this.getNextLevelExp()) {
            this.nextLevelExp = (int) (this.nextLevelExp + this.nextLevelExp * 1.7);
            this.level += 1;
            this.maxHp += this.level;
            this.hp = this.maxHp;
            this.savePlayer();
        }
    }

    public void addCryptoCoins(Integer cryptoCoins) {
        this.cryptoCoins += cryptoCoins;
        this.savePlayer();
    }


    public Integer getSpecialAmmo() {
        return arrow;
    }

    public void setSpecialAmmo(Integer specialAmmo) {
        this.arrow = specialAmmo;

    }

    public Integer getCryptoCoins() {
        return cryptoCoins;
    }

    public void setCryptoCoins(Integer cryptoCoins) {
        this.cryptoCoins = cryptoCoins;

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
        return this.inventory;
    }

    public List<Item> getEquippedInventory() {
        return equippedInventory;
    }

    public void addInventory(List<Item> items) {
        this.inventory.addAll(items);
        this.savePlayer();
    }

    public void addInventory(Item item, Integer integer) {
        for (int i = 0; i < integer; i++) {
            this.addInventory(item);
        }
    }

    public void addInventory(Item item) {
        this.inventory.add(item);
        this.savePlayer();
    }

    public void addEquippedInventory(Item item) {
        this.equippedInventory.add(item);
        this.savePlayer();
    }

    public void removeInventory(Item item) {
        this.inventory.remove(item);
        this.savePlayer();
    }

    public void removeInventory(Item item, Integer integer) {

        for (int i = 0; i < integer; i++) {
            this.removeInventory(item);
        }
    }

    public void removeEquippedInventory(Item item) {
        this.equippedInventory.remove(item);
        this.savePlayer();
    }

    public void equipItem(Item itemToEquip) {
        this.removeInventory(itemToEquip);
        this.addEquippedInventory(itemToEquip);
        this.savePlayer();
    }

    public void unequippItem(Item itemToEquip) {
        this.removeEquippedInventory(itemToEquip);
        this.addInventory(itemToEquip);
        this.savePlayer();
    }

    public Item getEquippedItem(String slot) {
        if (this.equippedInventory != null) {
            for (Item item : this.getEquippedInventory()) {
                if (item.getSlot().equals(slot)) {
                    return item;
                }
            }
        }
        return null;
    }

    private void giveAndEquipBasicStuff() {
        this.addInventory(new ForceSaveInventoryList());
        this.addEquippedInventory(new ForceSaveInventoryList());
        this.addEquippedInventory(new BrokenSword());
        this.addEquippedInventory(new BrokenBow());
        this.addEquippedInventory(new BasicTunic());
        this.savePlayer();
    }

    public void savePlayer() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(this.getName()).child("player").setValue(this);
    }
}
