package dev.swanndolia.idlemmorpg.ui.main;

import android.graphics.Color;
import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.firebase.GetRgbFromRarity;
import dev.swanndolia.idlemmorpg.tools.player.ListToMapInventory;

public class CompareAndEquip extends AppCompatActivity {
    Player player;
    Button equippedItemBtn;
    LinearLayout parentLayout;
    String slot;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        slot = (String) bundle.getSerializable("slot");
        setContentView(R.layout.activity_compare);



        parentLayout = findViewById(R.id.parentLayout);
        equippedItemBtn = new Button(this);
        equippedItemBtn.setTextSize(20);
        if (!slot.equals("None")) {
            parentLayout.addView(equippedItemBtn);
        }
        equippedItemBtn.setTextColor(Color.BLACK);
        equippedItemBtn.setText("No " + slot + " Equipped");
        if (player.getEquippedItem(slot) != null) {
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
                itemListBtn.setTextColor(new GetRgbFromRarity().getRgbFromRarity(entry.getKey().getRarity()));
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
        equippedItemBtn.setTextColor(new GetRgbFromRarity().getRgbFromRarity(player.getEquippedItem(slot).getRarity()));
        equippedItemBtn.setOnClickListener(view -> {
            if (player.getEquippedItem(slot) != null) {
                equippedItemBtn.setText("No " + slot + " Equipped");
                player.unequippItem(player.getEquippedItem(slot));
                parentLayout.removeView(button);
                equippedItemBtn.setTextColor(Color.BLACK);
                loadInventoryMapped(player.getEquippedItem(slot));
            }
        });
    }

    @Override
    public void onBackPressed() {
        new ActivityLauncher(this, Inventory.class, player);
    }
}