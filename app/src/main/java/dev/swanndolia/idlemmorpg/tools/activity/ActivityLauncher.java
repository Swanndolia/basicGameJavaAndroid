package dev.swanndolia.idlemmorpg.tools.activity;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;

import dev.swanndolia.idlemmorpg.characters.Player;

public class ActivityLauncher {
    public ActivityLauncher(Context previousActivity, Class<?> nextActivity) {
        Intent intent = new Intent(previousActivity, nextActivity);
        previousActivity.startActivity(intent);
        ((Activity) previousActivity).finish();
    }
//TODO finish make player set to disconnected after leaving menu
    public ActivityLauncher(Context previousActivity, Class<?> nextActivity, Player player) {
        Intent intent = new Intent(previousActivity, nextActivity);
        intent.putExtra("player", player);
        previousActivity.startActivity(intent);
        ((Activity) previousActivity).finish();
    }

    public ActivityLauncher(Context previousActivity, Class<?> nextActivity, Player player, String extraName, String extraMeta) {
        Intent intent = new Intent(previousActivity, nextActivity);
        intent.putExtra("player", player);
        intent.putExtra(extraName, extraMeta);
        previousActivity.startActivity(intent);
        ((Activity) previousActivity).finish();
    }
}
