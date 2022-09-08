package dev.swanndolia.idlemmorpg.enemyzones.tests.enemylist;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.drops.commons.BrokenRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.commons.CommonRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.commons.EpicRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.commons.LegendaryRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.commons.RareRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.commons.UncommonRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.commons.UniqueRarityLoot;
import dev.swanndolia.idlemmorpg.items.drops.fields.Egg;
import dev.swanndolia.idlemmorpg.items.drops.fields.Feather;
import dev.swanndolia.idlemmorpg.items.drops.fields.RawChicken;

public class TestEnemy2 extends DefaultCharacter implements Serializable {
    public TestEnemy2(int playerLevel) {
        this.setName("Test Enemy 2");
        this.setDesc("It don't look so scary");
        this.setLevel((int) (new Random().nextInt((int) ((playerLevel * 1.4 - playerLevel * 0.6) + 1)) + (playerLevel * 0.6) + 1));
        this.setEvade(0);
        this.setLuck((int) Math.round(1 + this.getLevel() * 0.2));
        this.setHp(1);
        this.setExpReward((int) Math.round(1 + this.getLevel() * 1.2));
        this.setDamage((int) Math.round(1 + this.getLevel() * 2.2));
        this.setAccuracy(90);
        this.setCritChance(2);
        this.setCritMultiplier(2.0);
        this.setDamage(this.getLevel() * 2);
        this.setInventory(generateInventory());
    }

    private Map<Item, Integer> generateInventory() {
        Map<Item, Integer> inventory = new HashMap<Item, Integer>();
        inventory.put(new BrokenRarityLoot(), 200);
        inventory.put(new CommonRarityLoot(), 300);
        inventory.put(new UncommonRarityLoot(), 400);
        inventory.put(new RareRarityLoot(), 500);
        inventory.put(new EpicRarityLoot(), 600);
        inventory.put(new LegendaryRarityLoot(), 700);
        inventory.put(new UniqueRarityLoot(), 800);
        return inventory;
    }
}
