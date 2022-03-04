package dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor;

import dev.swanndolia.idlemmorpg.items.armor.legs.BonesLegs;
import dev.swanndolia.idlemmorpg.items.drops.fields.Bones;
import dev.swanndolia.idlemmorpg.recipes.Recipe;

public class BonesLegsRecipe extends Recipe {
    public BonesLegsRecipe() {
        this.addRequiredItems(new Bones(), 5);
        this.addRecipeResult(new BonesLegs());
    }
}
