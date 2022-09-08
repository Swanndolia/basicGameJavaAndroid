package dev.swanndolia.idlemmorpg.enemy;

import java.util.List;

import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemy.fields.EnemyListFields;
import dev.swanndolia.idlemmorpg.enemy.tests.EnemyListTest;

public class GetEnemyListFromZone {
    List enemyList;

    public GetEnemyListFromZone(String location, Player player) {
        switch (location) {
            case "fields":
                enemyList = new EnemyListFields(player).getEnemyList();
                break;
            default:
                enemyList = new EnemyListTest(player).getEnemyList();
                break;
        }
    }

    public List getEnemyList() {
        return enemyList;
    }
}
