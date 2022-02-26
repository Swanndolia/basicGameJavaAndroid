package dev.swanndolia.idleasciimmorpg.overlays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.DefaultCharacter;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.userinterface.Fight;
import dev.swanndolia.idleasciimmorpg.userinterface.Menu;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.tools.activity.ActivityLauncher;

public class EnemyKilledOverlay {
    public void EnemyKilledOverlay(Context context, Player player, DefaultCharacter enemyEncountered) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.enemy_drops_layout);

        final Integer[] totalValue = {0};

        LinearLayout itemListHolder = (LinearLayout) dialog.findViewById(R.id.itemListHolder);
        TextView enemyKilledTitle = (TextView) dialog.findViewById(R.id.enemyKilledTitle);
        enemyKilledTitle.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
        TextView rewardsList = (TextView) dialog.findViewById(R.id.rewardsList);
        rewardsList.setText("You've won " + enemyEncountered.getExpReward() + " experience");

        Button backMenu = (Button) dialog.findViewById(R.id.backToMenuBtn);
        backMenu.setOnClickListener(view -> {
            player.addInventory(enemyEncountered.getInventory());
            new ActivityLauncher().ActivityLauncher(context, Menu.class, player);
        });

        Button exploreMore = (Button) dialog.findViewById(R.id.exploreAgainBtn);
        exploreMore.setOnClickListener(view -> {
            player.addInventory(enemyEncountered.getInventory());
            new ActivityLauncher().ActivityLauncher(context, Fight.class, player);
        });

        Button sellAllLoot = (Button) dialog.findViewById(R.id.sellAllLootBtn);
        Button takeAllLoot = (Button) dialog.findViewById(R.id.takeAllLootBtn);
        takeAllLoot.setOnClickListener(view -> {
            player.addInventory(enemyEncountered.getInventory());
            int amountToRemove = enemyEncountered.getInventory().size();
            enemyEncountered.setInventory(new HashMap<>());
            itemListHolder.removeViews(0, amountToRemove);
            takeAllLoot.setVisibility(View.GONE);
            sellAllLoot.setVisibility(View.GONE);
        });

        Integer finalTotalValue = totalValue[0];
        sellAllLoot.setOnClickListener(view -> {
            player.addCryptoCoins(finalTotalValue);
            int amountToRemove = enemyEncountered.getInventory().size();
            enemyEncountered.setInventory(new HashMap<>());
            itemListHolder.removeViews(0, amountToRemove);
            sellAllLoot.setVisibility(View.GONE);
            takeAllLoot.setVisibility(View.GONE);
        });

        for (Item item : enemyEncountered.getInventory()) {
            totalValue[0] += item.getSellValue();
            // used to setup double buttons don't touch this fucking shit stay in loop to avoid already have parent
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout doubleButton = (LinearLayout) layoutInflater.inflate(R.layout.take_or_sell_buttons, null);

            Button takeOneItem = doubleButton.findViewById(R.id.buttonLeft);
            Button sellOneItem = doubleButton.findViewById(R.id.buttonRight);
            takeOneItem.setText("Take:" + item.getName());
            takeOneItem.setOnClickListener(view -> {
                totalValue[0] -= item.getSellValue();
                enemyEncountered.removeInventory(item);
                player.addInventory(item);
                itemListHolder.removeView(doubleButton);
                if (itemListHolder.getChildCount() == 0) {
                    sellAllLoot.setVisibility(View.GONE);
                    takeAllLoot.setVisibility(View.GONE);
                }
            });
            sellOneItem.setText("Sell for: " + item.getSellValue());
            sellOneItem.setOnClickListener(view -> {
                totalValue[0] -= item.getSellValue();
                enemyEncountered.removeInventory(item);
                player.addCryptoCoins(item.getSellValue());
                itemListHolder.removeView(doubleButton);
                if (itemListHolder.getChildCount() == 0) {
                    sellAllLoot.setVisibility(View.GONE);
                    takeAllLoot.setVisibility(View.GONE);
                }
            });
            itemListHolder.addView(doubleButton);
        }
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (((Activity) context).hasWindowFocus()) {
            dialog.show();
        }
    }
}