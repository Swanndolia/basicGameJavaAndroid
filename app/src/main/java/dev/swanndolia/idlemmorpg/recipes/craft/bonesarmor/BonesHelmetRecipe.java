package dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor;

import dev.swanndolia.idlemmorpg.items.armor.helmet.BonesHelmet;
import dev.swanndolia.idlemmorpg.items.drops.commons.Bones;
import dev.swanndolia.idlemmorpg.recipes.Recipe;

public class BonesHelmetRecipe extends Recipe {
    public BonesHelmetRecipe(){
        this.addRequiredItems(new Bones(), 5);
        this.addRecipeResult(new BonesHelmet());
    }
}
