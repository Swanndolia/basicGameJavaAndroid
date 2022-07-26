package dev.swanndolia.idlemmorpg.ui.overlays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.DefaultCharacter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.firebase.GetRgbFromRarity;
import dev.swanndolia.idlemmorpg.ui.main.Fight;
import dev.swanndolia.idlemmorpg.ui.main.Menu;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;

public class EnemyKilledOverlay {
    public void EnemyKilledOverlay(Context context, Player player, DefaultCharacter enemyEncountered) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.overlay_enemy_drops);

        final Integer[] totalValue = {0};

        LinearLayout itemListHolder = dialog.findViewById(R.id.itemListHolder);
        TextView enemyKilledTitle = dialog.findViewById(R.id.enemyKilledTitle);
        enemyKilledTitle.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
        TextView rewardsList = dialog.findViewById(R.id.rewardsList);
        rewardsList.setText("You've won " + enemyEncountered.getExpReward() + " experience");

        Button backMenu = dialog.findViewById(R.id.backToMenuBtn);
        backMenu.setOnClickListener(view -> {
            player.addInventory(enemyEncountered.getInventory());
            new ActivityLauncher(context, Menu.class, player);
        });

        Button exploreMore = dialog.findViewById(R.id.exploreAgainBtn);
        exploreMore.setOnClickListener(view -> {
            player.addInventory(enemyEncountered.getInventory());
            new ActivityLauncher(context, Fight.class, player);
        });

        Button sellAllLoot = dialog.findViewById(R.id.sellAllLootBtn);
        Button takeAllLoot = dialog.findViewById(R.id.takeAllLootBtn);
        ImageView iconSellAllItem = dialog.findViewById(R.id.iconSellItem);
        ImageView iconAllItem = dialog.findViewById(R.id.iconItem);
        //iconItem.setImageResource(item.getIcon());
        iconAllItem.setImageResource(R.drawable.icon_inventory);
        takeAllLoot.setOnClickListener(view -> {
            player.addInventory(enemyEncountered.getInventory());
            int amountToRemove = enemyEncountered.getInventory().size();
            enemyEncountered.setInventory(new HashMap<>());
            itemListHolder.removeViews(0, amountToRemove);
            removeButtonAndIcon(sellAllLoot, iconSellAllItem, takeAllLoot, iconAllItem);

        });

        Integer finalTotalValue = totalValue[0];
        iconSellAllItem.setImageResource(R.drawable.icon_coins);
        sellAllLoot.setOnClickListener(view -> {
            player.addCoins(finalTotalValue);
            int amountToRemove = enemyEncountered.getInventory().size();
            enemyEncountered.setInventory(new HashMap<>());
            itemListHolder.removeViews(0, amountToRemove);
            removeButtonAndIcon(sellAllLoot, iconSellAllItem, takeAllLoot, iconAllItem);
        });


        for (Item item : enemyEncountered.getInventory()) {
            totalValue[0] += item.getSellValue();
            // used to setup double buttons don't touch this fucking shit stay in loop to avoid already have parent
            LayoutInflater layoutInflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            LinearLayout rewardAction = (LinearLayout) layoutInflater.inflate(R.layout.take_or_sell_buttons, null);
            rewardAction.setGravity(Gravity.CENTER_VERTICAL);
            ImageView iconItem = rewardAction.findViewById(R.id.iconItem);
            Button takeOneItem = rewardAction.findViewById(R.id.buttonLeft);
            ImageView iconSellItem = rewardAction.findViewById(R.id.iconSellItem);
            Button sellOneItem = rewardAction.findViewById(R.id.buttonRight);
            //iconItem.setImageResource(item.getIcon());
            iconItem.setImageResource(R.drawable.icon_inventory);
            iconSellItem.setImageResource(R.drawable.icon_coins);
            takeOneItem.setAllCaps(false);
            takeOneItem.setText(item.getName());
            takeOneItem.setTextColor(new GetRgbFromRarity().GetRgbFromRarity(item.getRarity()));
            takeOneItem.setOnClickListener(view -> {
                totalValue[0] -= item.getSellValue();
                enemyEncountered.removeInventory(item);
                player.addInventory(item);
                itemListHolder.removeView(rewardAction);
                if (itemListHolder.getChildCount() == 0) {
                    removeButtonAndIcon(sellAllLoot, iconSellAllItem, takeAllLoot, iconAllItem);
                }
            });
            sellOneItem.setText("Sell for: " + item.getSellValue());
            sellOneItem.setOnClickListener(view -> {
                totalValue[0] -= item.getSellValue();
                enemyEncountered.removeInventory(item);
                player.addCoins(item.getSellValue());
                itemListHolder.removeView(rewardAction);
                if (itemListHolder.getChildCount() == 0) {
                    removeButtonAndIcon(sellAllLoot, iconSellAllItem, takeAllLoot, iconAllItem);
                }
            });
            itemListHolder.addView(rewardAction);
        }
        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (((Activity) context).hasWindowFocus()) {
            dialog.show();
        }
    }
    public void removeButtonAndIcon(Button sellAllLoot, ImageView iconSellAllItem, Button iconAllItem, ImageView takeAllLoot){
        sellAllLoot.setVisibility(View.GONE);
        iconSellAllItem.setVisibility(View.GONE);
        iconAllItem.setVisibility(View.GONE);
        takeAllLoot.setVisibility(View.GONE);
    }
}