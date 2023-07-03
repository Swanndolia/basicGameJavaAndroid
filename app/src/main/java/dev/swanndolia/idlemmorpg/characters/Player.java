package dev.swanndolia.idlemmorpg.characters;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.weapons.melee.rarityF.Sword;
import dev.swanndolia.idlemmorpg.tools.player.ForceSaveInventoryList;
import dev.swanndolia.idlemmorpg.tools.player.PlayerStats;

public class Player extends PlayerStats implements Serializable {

    String name;
    Integer hp;
    Integer stamina;
    Integer maxStamina;
    Integer level;
    Integer arrow;
    Integer coins;
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
        this.stamina = 100;
        this.maxHp = 100;
        this.maxStamina = 100;
        this.level = 1;
        this.exp = 0;
        this.nextLevelExp = 8;
        this.arrow = 0;
        this.coins = 0;
        this.luck = 0;
        this.evade = 0;
        this.inventory = new ArrayList<Item>();
        this.equippedInventory = new ArrayList<Item>();
        this.giveAndEquipBasicStuff();
        this.savePlayer();
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
        return maxHp;
    }

    public void setMaxHp(Integer maxHp) {
        this.maxHp = maxHp;
    }

    public Integer getStamina() {
        return stamina;
    }

    public void setStamina(Integer stamina) {
        this.stamina = stamina;
    }

    public Integer getMaxStamina() {
        return maxStamina;
    }

    public void setMaxStamina(Integer maxStamina) {
        this.maxStamina = maxStamina;
    }

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public void addExp(Integer exp) {
        this.exp += exp;
        this.checkLevelUp();
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
            this.nextLevelExp = (int) (this.nextLevelExp + this.nextLevelExp * 1.1);
            this.level += 1;
            this.maxHp += this.level;
            this.hp = this.maxHp;
            this.maxStamina += this.level;
            this.stamina = this.maxStamina;
            this.savePlayer();
        }
    }

    public void addCoins(Integer cryptoCoins) {
        this.coins += cryptoCoins;
    }

    public Integer getArrow() {
        return arrow;
    }

    public void setArrow(Integer arrow) {
        this.arrow = arrow;
    }

    public Integer getCoins() {
        return coins;
    }

    public void setCoins(Integer coins) {
        this.coins = coins;

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

    public void addItemListToInventory(List<Item> items) {
        this.inventory.addAll(items);
    }

    public void addItemsToInventory(Item item, Integer integer) {
        for (int i = 0; i < integer; i++) {
            this.inventory.add(item);
        }
        this.savePlayer();
    }

    public void addItemToInventory(Item item) {
        this.inventory.add(item);
    }

    public void addItemToEquippedInventory(Item item) {
        this.equippedInventory.add(item);
    }

    public void removeItemFromInventory(Item item) {
        this.inventory.remove(item);
        this.savePlayer();
    }

    public void removeItemFromInventory(Item item, Integer integer) {
        for (int i = 0; i < integer; i++) {
            this.inventory.remove(item);
        }
        this.savePlayer();
    }

    public void equipItem(Item itemToEquip) {
        this.removeItemFromInventory(itemToEquip);
        this.addItemToEquippedInventory(itemToEquip);
        this.savePlayer();
    }

    public void unequippItem(Item itemToUnequipp) {
        this.equippedInventory.remove(itemToUnequipp);
        this.addItemToInventory(itemToUnequipp);
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
        this.addItemToInventory(new ForceSaveInventoryList());
        this.addItemToEquippedInventory(new ForceSaveInventoryList());
        this.addItemToEquippedInventory(new Sword());
    }

    public void savePlayer() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(this.getName()).child("player").setValue(this);
    }
}
