package dev.swanndolia.idleasciimmorpg.userinterface;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Map;
import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idleasciimmorpg.tools.player.ListToMapInventory;

public class CompareAndEquip extends AppCompatActivity {
    Player player;
    Button equippedItemBtn;
    LinearLayout parentLayout;
    String slot;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        slot = (String) bundle.getSerializable("slot");
        setContentView(R.layout.activity_compare);

        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());

        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        equippedItemBtn = new Button(this);
        equippedItemBtn.setTextSize(20);
        if (!slot.equals("None")) {
            parentLayout.addView(equippedItemBtn);
        }

        equippedItemBtn.setText("No " + slot + " Equipped");
        if(player.getEquippedItem(slot) != null){
            generateEquippedItemButton(null);
        }

        loadInventoryMapped(null);
    }

    private void loadInventoryMapped(Item checkEquippedItem) {
        Map<Item, Integer> mappedInventory = new ListToMapInventory().ListToMapInventory(player.getInventory());
        System.out.println(mappedInventory);
        for (Map.Entry<Item, Integer> entry : mappedInventory.entrySet()) {
            if (entry.getKey().getSlot().equals(slot)) {

                Button itemListBtn = new Button(this);
                itemListBtn.setTextSize(20);
                itemListBtn.setText(entry.getKey().getName() + " x " + entry.getValue());

                if (player.getEquippedItem(slot) != null && player.getEquippedItem(slot).equals(entry.getKey())) {
                    generateEquippedItemButton(itemListBtn);
                }

                if (!entry.getKey().getSlot().equals("None")) {
                    itemListBtn.setOnClickListener(view -> {
                        if (player.getEquippedItem(slot) == null || !player.getEquippedItem(slot).equals(entry.getKey())) {
                            if (player.getEquippedItem(slot) != null) {
                                player.unequippItem(player.getEquippedItem(slot));
                            }
                            player.equipItem(entry.getKey());
                            entry.setValue(entry.getValue() - 1);
                            if (entry.getValue() == 0) {
                                parentLayout.removeView(itemListBtn);
                            }

                            itemListBtn.setText(entry.getKey().getName() + " x " + entry.getValue());

                            generateEquippedItemButton(itemListBtn);
                        }
                    });
                }
                if (checkEquippedItem == null || checkEquippedItem != entry.getKey()) {
                    parentLayout.addView(itemListBtn);
                }
            }
        }
    }

    private void generateEquippedItemButton(Button button) {
        equippedItemBtn.setText("Equipped: " + player.getEquippedItem(slot).getName());
        equippedItemBtn.setOnClickListener(view -> {
            if (player.getEquippedItem(slot) != null) {
                equippedItemBtn.setText("No " + slot + " Equipped");
                player.unequippItem(player.getEquippedItem(slot));
                parentLayout.removeView(button);
                loadInventoryMapped(player.getEquippedItem(slot));
            }
        });
    }

    @Override
    public void onBackPressed() {
        new ActivityLauncher().ActivityLauncher(this, Inventory.class, player);
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