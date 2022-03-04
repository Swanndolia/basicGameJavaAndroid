package dev.swanndolia.idlemmorpg.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.armor.boots.BonesBoots;
import dev.swanndolia.idlemmorpg.recipes.Recipe;
import dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor.BonesBootsRecipe;
import dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor.BonesHelmetRecipe;
import dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor.BonesLegsRecipe;
import dev.swanndolia.idlemmorpg.recipes.craft.bonesarmor.BonesTorsoRecipe;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.player.ListToMapInventory;

public class Craft extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        setContentView(R.layout.activity_craft);

        expProgressBar = findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());

        List<Recipe> recipeList = new ArrayList<>();
        recipeList.add(new BonesHelmetRecipe());
        recipeList.add(new BonesTorsoRecipe());
        recipeList.add(new BonesLegsRecipe());
        recipeList.add(new BonesBootsRecipe());

        LinearLayout recipeListLayout = findViewById(R.id.recipeList);
        LayoutInflater inflater = getLayoutInflater();


        for (Recipe recipe : recipeList) {
            View recipeLayout = inflater.inflate(R.layout.recipe_layout, recipeListLayout, false);
            Button resultBtn = recipeLayout.findViewById(R.id.craftBtn);
            TextView recipeText = recipeLayout.findViewById(R.id.recipeText);
            Map<Item, Integer> mappedRequirements = new ListToMapInventory().ListToMapInventory(recipe.getRequiredItems());
            for (Map.Entry<Item, Integer> entry : mappedRequirements.entrySet()) {
                recipeText.setText(recipeText.getText() + " " + entry.getKey().getName() + " x " + entry.getValue());
            }
            Map<Item, Integer> mappedResult = new ListToMapInventory().ListToMapInventory(recipe.getRecipeResult());
            for (Map.Entry<Item, Integer> entry : mappedResult.entrySet()) {
                resultBtn.setText(resultBtn.getText() + " " + entry.getKey().getName() + " x " + entry.getValue());
            }
            resultBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    for()//check for each item required if present in inventory if yes check if present entry.value amount of time
                        // try removeitem x ammount  if NPE not enough in inv
                    player.addInventory(recipe.getRecipeResult());
                }
            });
            recipeListLayout.addView(recipeLayout);
        }
    }

    @Override
    public void onBackPressed() {
        new ActivityLauncher(this, Menu.class, player);
    }

    private void makePlayerAlwaysUpdated() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player = snapshot.child(player.getName()).child("player").getValue(Player.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}