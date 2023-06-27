package dev.swanndolia.idlemmorpg.tools.player;

import java.io.Serializable;

public class PlayerStats implements Serializable {
    Integer enemyKilled;
    Integer legendaryEnemyKilled;
    Integer deaths;
    Integer uniqueItemFound;
    Integer legendaryItemFound;
    Integer questCompleted;
    Integer coinGained;
    Integer itemBoughtMarket;
    Integer itemSoldMarket;

    public PlayerStats() {
        this.enemyKilled = 0;
        this.legendaryEnemyKilled = 0;
        this.deaths = 0;
        this.uniqueItemFound = 0;
        this.legendaryItemFound = 0;
        this.questCompleted = 0;
        this.coinGained = 0;
        this.itemBoughtMarket = 0;
        this.itemSoldMarket = 0;
    }

    public Integer getEnemyKilled() {
        return enemyKilled;
    }

    public void setEnemyKilled(int enemyKilled) {
        this.enemyKilled = enemyKilled;
    }

    public Integer getLegendaryEnemyKilled() {
        return legendaryEnemyKilled;
    }

    public void setLegendaryEnemyKilled(int legendaryEnemyKilled) {
        this.legendaryEnemyKilled = legendaryEnemyKilled;
    }

    public Integer getDeaths() {
        return deaths;
    }

    public void setDeaths(int deaths) {
        this.deaths = deaths;
    }

    public Integer getUniqueItemFound() {
        return uniqueItemFound;
    }

    public void setUniqueItemFound(int uniqueItemFound) {
        this.uniqueItemFound = uniqueItemFound;
    }

    public Integer getLegendaryItemFound() {
        return legendaryItemFound;
    }

    public void setLegendaryItemFound(int legendaryItemFound) {
        this.legendaryItemFound = legendaryItemFound;
    }

    public Integer getQuestCompleted() {
        return questCompleted;
    }

    public void setQuestCompleted(int questCompleted) {
        this.questCompleted = questCompleted;
    }

    public Integer getMoneyGained() {
        return coinGained;
    }

    public void setMoneyGained(int moneyGained) {
        this.coinGained = moneyGained;
    }

    public Integer getItemBoughtMarket() {
        return itemBoughtMarket;
    }

    public void setItemBoughtMarket(int itemBoughtMarket) {
        this.itemBoughtMarket = itemBoughtMarket;
    }

    public Integer getItemSoldMarket() {
        return itemSoldMarket;
    }

    public void setItemSoldMarket(int itemSoldMarket) {
        this.itemSoldMarket = itemSoldMarket;
    }
}
