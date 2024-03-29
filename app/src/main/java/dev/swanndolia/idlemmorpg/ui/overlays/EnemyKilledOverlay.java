package dev.swanndolia.idlemmorpg.ui.overlays;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
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
import dev.swanndolia.idlemmorpg.characters.DefaultEncounter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.firebase.GetRgbFromRarity;
import dev.swanndolia.idlemmorpg.ui.main.Fight;
import dev.swanndolia.idlemmorpg.ui.main.Menu;

public class EnemyKilledOverlay {
    public EnemyKilledOverlay(Context context, Player player, DefaultEncounter enemyEncountered, String location) {
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
            player.addItemListToInventory(enemyEncountered.getInventory());
            player.savePlayer();
            dialog.dismiss();
            new ActivityLauncher(context, Menu.class, player);
        });

        Button exploreMore = dialog.findViewById(R.id.exploreAgainBtn);
        exploreMore.setOnClickListener(view -> {
            player.addItemListToInventory(enemyEncountered.getInventory());
            player.savePlayer();
            dialog.dismiss();
            new ActivityLauncher(context, Fight.class, player, "location", location);
        });

        Button sellAllLoot = dialog.findViewById(R.id.sellAllLootBtn);
        Button takeAllLoot = dialog.findViewById(R.id.takeAllLootBtn);
        ImageView iconSellAllItem = dialog.findViewById(R.id.iconSellItem);
        ImageView iconAllItem = dialog.findViewById(R.id.iconItem);
        //iconItem.setImageResource(item.getIcon());
        iconAllItem.setImageResource(R.drawable.z_icon_inventory);
        takeAllLoot.setOnClickListener(view -> {
            player.addItemListToInventory(enemyEncountered.getInventory());
            int amountToRemove = enemyEncountered.getInventory().size();
            enemyEncountered.setInventory(new HashMap<>());
            itemListHolder.removeViews(0, amountToRemove);
            player.savePlayer();
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
            iconItem.setImageResource(R.drawable.z_icon_inventory);
            iconSellItem.setImageResource(R.drawable.z_icon_coins);
            takeOneItem.setAllCaps(false);
            takeOneItem.setText(item.getName());
            takeOneItem.setTextColor(new GetRgbFromRarity().getRgbFromRarity(item.getRarity()));
            takeOneItem.setOnClickListener(view -> {
                totalValue[0] -= item.getSellValue();
                enemyEncountered.removeInventory(item);
                player.addItemToInventory(item);
                itemListHolder.removeView(rewardAction);
                if (itemListHolder.getChildCount() == 0) {
                    removeButtonAndIcon(sellAllLoot, iconSellAllItem, takeAllLoot, iconAllItem);
                }
                player.savePlayer();
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
                player.savePlayer();
            });
            itemListHolder.addView(rewardAction);
        }

        Integer finalTotalValue = totalValue[0];
        iconSellAllItem.setImageResource(R.drawable.z_icon_coins);
        sellAllLoot.setOnClickListener(view -> {
            player.addCoins(finalTotalValue);
            int amountToRemove = enemyEncountered.getInventory().size();
            enemyEncountered.setInventory(new HashMap<>());
            itemListHolder.removeViews(0, amountToRemove);
            removeButtonAndIcon(sellAllLoot, iconSellAllItem, takeAllLoot, iconAllItem);
            player.savePlayer();
        });

        dialog.setCancelable(false);
        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        if (((Activity) context).hasWindowFocus()) {
            dialog.show();
        }
    }

    public void removeButtonAndIcon(Button sellAllLoot, ImageView iconSellAllItem, Button iconAllItem, ImageView takeAllLoot) {
        sellAllLoot.setVisibility(View.GONE);
        iconSellAllItem.setVisibility(View.GONE);
        iconAllItem.setVisibility(View.GONE);
        takeAllLoot.setVisibility(View.GONE);
    }
}