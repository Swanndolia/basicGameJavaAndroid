package dev.swanndolia.idleasciimmorpg.interfaces;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;

public class CompareAndEquip extends AppCompatActivity {
    Player player;
    Button equippedItemBtn;
    LinearLayout parentLayout;

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
        } else if (!slot.equals("None")) {
            equippedItemBtn.setText("No " + slot + " Equipped");
            parentLayout.addView(equippedItemBtn);
        }
        loadInventoryList(slot);
    }

    private void loadInventoryList(String slot) {
        for (Item item : player.getInventory()) {
            System.out.println(item.getSlot());
            System.out.println(player.getEquippedItem(slot) != item);
            if (item.getSlot().equals(slot) && player.getEquippedItem(slot) != item) {
                Button itemListBtn = new Button(this);
                itemListBtn.setTextSize(20);
                itemListBtn.setText(item.getName());
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
                parentLayout.addView(itemListBtn);
            }
        }
    }

    private void addEventListenerEquippedItem(String slot) {
        equippedItemBtn.setText("Equipped: " + player.getEquippedItem(slot).getName());
        equippedItemBtn.setOnClickListener(new View.OnClickListener() {
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
}