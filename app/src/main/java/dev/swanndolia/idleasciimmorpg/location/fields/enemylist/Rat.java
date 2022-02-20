package dev.swanndolia.idleasciimmorpg.location.fields.enemylist;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idleasciimmorpg.characters.DefaultCharacter;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.Egg;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.Feather;
import dev.swanndolia.idleasciimmorpg.items.drops.fields.chickendrops.RawChicken;

public class Rat extends DefaultCharacter {
    public Rat Rat(Player player) {
        Rat enemy = new Rat();
        enemy.setName("Rat");
        enemy.setDesc("I should avoid getting bitten");
        enemy.setLevel((int) (new Random().nextInt((int) ((player.getLevel() * 1.4 - player.getLevel() * 0.6) + 1)) + (player.getLevel() * 0.6) + 1));
        enemy.setEvade((int) Math.round(1 + enemy.getLevel() * 1.5));
        enemy.setLuck((int) Math.round(1 + enemy.getLevel() * 1.5));
        enemy.setHp((int) Math.round(3 + enemy.getLevel() * 2.5));
        enemy.setDamage((int) Math.round(1 + enemy.getLevel() * 3.2));
        enemy.setExpReward((int) Math.round(1 + enemy.getLevel() * 1.2));
        enemy.setAccuracy(80);
        enemy.setCritChance(5);
        enemy.setCritMultiplier(1.5);
        enemy.setInventory(generateInventory());
        return enemy;
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new Feather().Feather(), 300);
        inventory.put(new Egg().Egg(), 100);
        inventory.put(new RawChicken().RawChicken(), 700);
        return inventory;
    } //do fight part and leveling system
}
