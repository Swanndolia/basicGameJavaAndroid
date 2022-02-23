package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Map;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
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
            generateEquippeItemButton(null);
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
                    generateEquippeItemButton(itemListBtn);
                }

                if (!entry.getKey().getSlot().equals("None")) {
                    itemListBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
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

                                generateEquippeItemButton(itemListBtn);
                            }
                        }
                    });
                }
                if (checkEquippedItem == null || checkEquippedItem != entry.getKey()) {
                    parentLayout.addView(itemListBtn);
                }
            }
        }
    }

    private void generateEquippeItemButton(Button button) {
        equippedItemBtn.setText("Equipped: " + player.getEquippedItem(slot).getName());
        equippedItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (player.getEquippedItem(slot) != null) {
                    equippedItemBtn.setText("No " + slot + " Equipped");
                    player.unequippItem(player.getEquippedItem(slot));
                    parentLayout.removeView(button);
                    loadInventoryMapped(player.getEquippedItem(slot));
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CompareAndEquip.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
    }
}