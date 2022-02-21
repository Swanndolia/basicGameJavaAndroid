package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;

public class CompareAndEquip extends AppCompatActivity {
    Player player;
    Button equippedItemBtn;
    LinearLayout parentLayout;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        String slot = (String) bundle.getSerializable("slot");

        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        equippedItemBtn = new Button(this);
        equippedItemBtn.setTextSize(20);

        if (player.getEquippedItem(slot) != null) {
            addEventListenerEquippedItem(slot);
        } else {
            equippedItemBtn.setText("No " + slot + " Equipped");
        }
        if (!slot.equals("None")) {
            parentLayout.addView(equippedItemBtn);
        }
        loadInventoryList(slot);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    private void loadInventoryList(String slot) {
        for (Item item : player.getInventory()) {
            if (item.getSlot().equals(slot) && player.getEquippedItem(slot) != item) {
                Button itemListBtn = new Button(this);
                itemListBtn.setTextSize(20);
                itemListBtn.setText(item.getName());
                if (!item.getSlot().equals("None")) {
                    itemListBtn.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            // show item full params + ask equip
                            if (player.getEquippedItem(slot) != null) {
                                player.getEquippedItem(slot).setEquipped(false);
                            }
                            player.equipItem(item);
                            addEventListenerEquippedItem(slot);
                            parentLayout.removeView(itemListBtn);
                        }
                    });
                }
                parentLayout.addView(itemListBtn);
            }
        }
    }

    private void addEventListenerEquippedItem(String slot) {
        equippedItemBtn.setText("Equipped: " + player.getEquippedItem(slot).getName());
        equippedItemBtn.setOnClickListener(new View.OnClickListener() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onClick(View view) {
                equippedItemBtn.setText("No " + slot + " Equipped");
                if (player.getEquippedItem(slot) != null) {
                    player.unequipItem(slot);
                    loadInventoryList(slot);
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