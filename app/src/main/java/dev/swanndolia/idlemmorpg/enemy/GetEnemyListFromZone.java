package dev.swanndolia.idlemmorpg.enemy;

import java.util.List;

import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemy.fields.EnemyListFields;

public class GetEnemyListFromZone {
    List enemyList;

    public GetEnemyListFromZone(String location, Player player) {
        switch (location) {
            default:
                enemyList = new EnemyListFields(player).getEnemyList();
                break;
        }
    }

    public List getEnemyList() {
        return enemyList;
    }
}
