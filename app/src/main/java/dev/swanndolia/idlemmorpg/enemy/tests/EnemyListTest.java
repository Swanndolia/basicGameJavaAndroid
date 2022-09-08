package dev.swanndolia.idlemmorpg.enemy.tests;

import java.util.ArrayList;
import java.util.List;

import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemy.tests.enemylist.TestEnemy;
import dev.swanndolia.idlemmorpg.enemy.tests.enemylist.TestEnemy2;
import dev.swanndolia.idlemmorpg.enemy.tests.enemylist.TestEnemy3;

public class EnemyListTest {
    public List<DefaultCharacter> EnemyListTest(Player player) {
        List<DefaultCharacter> enemyList = new ArrayList<DefaultCharacter>();
        enemyList.add(new TestEnemy(player.getLevel()));
        enemyList.add(new TestEnemy2(player.getLevel()));
        enemyList.add(new TestEnemy3(player.getLevel()));
        return enemyList;
    }
     }
