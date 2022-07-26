package dev.swanndolia.idlemmorpg.enemyzones.tests;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemyzones.tests.enemylist.TestEnemy;
import dev.swanndolia.idlemmorpg.enemyzones.tests.enemylist.TestEnemy2;
import dev.swanndolia.idlemmorpg.enemyzones.tests.enemylist.TestEnemy3;

public class GenerateEnemy {
    public DefaultCharacter GenerateEnemy(String location, Player player) {
        List<DefaultCharacter> enemyList = new ArrayList<DefaultCharacter>();
        enemyList.add(new TestEnemy(player.getLevel()));
        enemyList.add(new TestEnemy2(player.getLevel()));
        enemyList.add(new TestEnemy3(player.getLevel()));
        DefaultCharacter randomEnemy = enemyList.get(new Random().nextInt(enemyList.size()));
        return randomEnemy;
    }
     }
