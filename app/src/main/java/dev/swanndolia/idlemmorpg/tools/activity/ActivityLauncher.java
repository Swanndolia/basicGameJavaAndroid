package dev.swanndolia.idlemmorpg.tools.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import dev.swanndolia.idlemmorpg.characters.Player;

public class ActivityLauncher {
    public void ActivityLauncher(Context fromActivity, Class<?> toActivity) {
        Intent intent = new Intent(fromActivity, toActivity);
        fromActivity.startActivity(intent);
        ((Activity)fromActivity).finish();
    }
    public void ActivityLauncher(Context fromActivity, Class<?> toActivity, Player player) {
        Intent intent = new Intent(fromActivity, toActivity);
        intent.putExtra("player", player);
        fromActivity.startActivity(intent);
        ((Activity)fromActivity).finish();
    }
    public void ActivityLauncher(Context fromActivity, Class<?> toActivity, Player player, String slot) {
        Intent intent = new Intent(fromActivity, toActivity);
        intent.putExtra("player", player);
        intent.putExtra("slot", slot);
        fromActivity.startActivity(intent);
        ((Activity)fromActivity).finish();
    }
}
