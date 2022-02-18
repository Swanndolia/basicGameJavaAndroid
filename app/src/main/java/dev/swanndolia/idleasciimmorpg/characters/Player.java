package dev.swanndolia.idleasciimmorpg.characters;

import android.os.Build;

import androidx.annotation.RequiresApi;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.armor.Armor;
import dev.swanndolia.idleasciimmorpg.items.armor.torso.BasicTunic;
import dev.swanndolia.idleasciimmorpg.items.weapons.Weapon;
import dev.swanndolia.idleasciimmorpg.items.weapons.normal.BasicPistol;


public class Player implements Serializable {

    String name;
    Integer hp;
    Integer level;
    Integer specialAmmo;
    Integer ultimateAmmo;
    Integer cryptoCoins;
    Integer luck;
    Integer evade;
    Integer exp;
    Integer nextLevelExp;
    List<Item> inventory;

    public Player Player(String name) {
        this.name = name;
        this.hp = 100;
        this.level = 1;
        this.exp = 0;
        this.nextLevelExp = 8;
        this.specialAmmo = 0;
        this.ultimateAmmo = 0;
        this.cryptoCoins = 0;
        this.luck = 0;
        this.evade = 0;
        this.inventory = new ArrayList<Item>();
        this.giveAndEquipBasicStuff();
        return this;
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

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;

    }
    public Integer getSpecialAmmo() {
        return specialAmmo;
    }

    public void setSpecialAmmo(Integer specialAmmo) {
        this.specialAmmo = specialAmmo;

    }

    public Integer getUltimateAmmo() {
        return ultimateAmmo;

    }

    public void setUltimateAmmo(Integer ultimateAmmo) {
        this.ultimateAmmo = ultimateAmmo;

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

    public Integer getExp() {
        return exp;
    }

    public void setExp(Integer exp) {
        this.exp = exp;
    }

    public Integer getNextLevelExp() {
        return nextLevelExp;
    }

    public void setNextLevelExp(Integer nextLevelExp) {
        this.nextLevelExp = nextLevelExp;
    }

    public List<Item> getInventory() {
        return this.inventory;
    }

    public Item getEquipedItem(String slot) {
        for (Item item : this.getInventory()) {
            if (item.isEquipped() && item.getSlot().equals(slot)) {
                return item;
            }
        }
        return null;
    }

    public void equipItem(Item itemToEquip) {
        for (Item item : this.getInventory()) {
            if (item != null && itemToEquip.equals(item)) {
                item.setEquipped(true);
                break;
            }
        }
        this.savePlayer();
    }

    public void unequipItem(String slot) {
        if (this.getEquipedItem(slot) != null) {
            this.getEquipedItem(slot).setEquipped(false);
        }
        this.savePlayer();
    }

    public void addInventory(Collection items) {
        this.inventory.addAll(items);

    }

    public void addInventory(Item item) {
        this.inventory.add(item);

        this.savePlayer();
    }

    public void removeInventory(Item item) {
        this.inventory.remove(item);
    }

    private void giveAndEquipBasicStuff() {
        this.addInventory(new BasicPistol().BasicPistol());
        this.addInventory(new BasicTunic().BasicTunic());
        for (Item item : this.getInventory()) {
            item.setEquipped(true);
        }
        this.savePlayer();
    }

    public void savePlayer() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").child(this.getName()).child("player").setValue(this);
    }
}
