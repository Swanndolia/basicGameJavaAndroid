package dev.swanndolia.idlemmorpg.location.fields;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.location.fields.enemylist.Chicken;
import dev.swanndolia.idlemmorpg.location.fields.enemylist.Fox;
import dev.swanndolia.idlemmorpg.location.fields.enemylist.Rat;

public class GenerateEnemy {
    public DefaultCharacter GenerateEnemy(String location, Player player) {
        List<DefaultCharacter> enemyList = new ArrayList<DefaultCharacter>();
        enemyList.add(new Chicken().Chicken(player));
        enemyList.add(new Fox().Fox(player));
        enemyList.add(new Rat().Rat(player));
        DefaultCharacter randomEnemy = enemyList.get(new Random().nextInt(enemyList.size()));
        return randomEnemy;
    }
}
