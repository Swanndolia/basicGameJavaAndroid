package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;

public class CompareAndEquip extends AppCompatActivity {
    LinearLayout equippedLayout;
    LinearLayout itemListLayout;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_compare);

        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        String slot = (String) bundle.getSerializable("slot");

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
        Button equippedItemBtn = new Button(this);

        equippedItemBtn.setTextSize(20);

        if (player.getEquipedItem(slot) != null) {
            addEventListenerEquipedItem(equippedItemBtn, slot, parentLayout);
        } else {
            equippedItemBtn.setText("No " + slot + " Equipped");
        }
        parentLayout.addView(equippedItemBtn);

        loadIventoryList(slot, parentLayout, equippedItemBtn);
    }

    private void loadIventoryList(String slot, LinearLayout parentLayout, Button equippedItemBtn) {
        for (Item item : player.getInventory()) {
            if (item.getSlot().equals(slot) && player.getEquipedItem(slot) != item) {
                Button itemListBtn = new Button(this);
                itemListBtn.setTextSize(20);
                itemListBtn.setText(item.getName());
                itemListBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        // show item full params + ask equip
                        if (player.getEquipedItem(slot) != null) {
                            player.getEquipedItem(slot).setEquipped(false);
                        }
                        player.equipItem(item);
                        addEventListenerEquipedItem(equippedItemBtn, slot, parentLayout);
                        parentLayout.removeView(itemListBtn);
                    }
                });
                parentLayout.addView(itemListBtn);
            }
        }
    }

    private void addEventListenerEquipedItem(Button equippedItemBtn, String slot, LinearLayout parentLayout) {
        equippedItemBtn.setText("Equipped: " + player.getEquipedItem(slot).getName());
        equippedItemBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                equippedItemBtn.setText("No " + slot + " Equipped");
                if (player.getEquipedItem(slot) != null) {
                    player.unequipItem(slot);
                    loadIventoryList(slot, parentLayout, equippedItemBtn);
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (android.os.Build.VERSION.SDK_INT < android.os.Build.VERSION_CODES.ECLAIR
                && keyCode == KeyEvent.KEYCODE_BACK
                && event.getRepeatCount() == 0) {
            // Take care of calling this method on earlier versions of
            // the platform where it doesn't exist.
            onBackPressed();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(CompareAndEquip.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
        return;
    }
}