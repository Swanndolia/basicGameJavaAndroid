package dev.swanndolia.idlemmorpg.recipes;

import java.util.ArrayList;
import java.util.List;

import dev.swanndolia.idlemmorpg.items.Item;

public class Recipe {
    public Recipe(){
        this.requiredItems = new ArrayList<Item>();
        this.recipeResult = new ArrayList<Item>();
    }
    List<Item> requiredItems;
    List<Item> recipeResult;

    public List<Item> getRequiredItems() {
        return requiredItems;
    }

    public void setRequiredItems(List<Item> requiredItems) {
        this.requiredItems = requiredItems;
    }
    public void addRequiredItems(Item requiredItem) {
        this.requiredItems.add(requiredItem);
    }
    public void addRequiredItems(Item requiredItem, int amount) {
        for (int i = 0; i < amount; i++) {
            this.requiredItems.add(requiredItem);
        }
        this.requiredItems.add(requiredItem);
    }

    public List<Item> getRecipeResult() {
        return recipeResult;
    }

    public void setRecipeResult(List<Item> recipeResult) {
        this.recipeResult = recipeResult;
    }
    public void addRecipeResult(Item recipeResult) {
        this.recipeResult.add(recipeResult);
    }

}
