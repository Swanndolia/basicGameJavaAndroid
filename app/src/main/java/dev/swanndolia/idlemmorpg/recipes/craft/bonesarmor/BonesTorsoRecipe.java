package dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor;

import dev.swanndolia.idlemmorpg.items.armor.torso.BonesArmor;
import dev.swanndolia.idlemmorpg.items.drops.allLocations.Bones;
import dev.swanndolia.idlemmorpg.recipes.Recipe;

public class BonesTorsoRecipe extends Recipe {
    public BonesTorsoRecipe(){
        this.addRequiredItems(new Bones(), 8);
        this.addRecipeResult(new BonesArmor());
    }
}
