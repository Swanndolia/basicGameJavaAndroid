package dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor;

import dev.swanndolia.idlemmorpg.items.armor.boots.BonesBoots;
import dev.swanndolia.idlemmorpg.items.drops.allLocations.Bones;
import dev.swanndolia.idlemmorpg.recipes.Recipe;

public class BonesBootsRecipe extends Recipe {
    public BonesBootsRecipe() {
        this.addRequiredItems(new Bones(), 5);
        this.addRecipeResult(new BonesBoots());
    }
}
