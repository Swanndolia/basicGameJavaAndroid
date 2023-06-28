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

    public ActivityLauncher(Context fromActivity, Class<?> nextActivity, Player player) {
        Intent intent = new Intent(fromActivity, nextActivity);
        intent.putExtra("player", player);
        fromActivity.startActivity(intent);
        ((Activity) fromActivity).finish();
    }

    public ActivityLauncher(Context previousActivity, Class<?> nextActivity, Player player, String extraName, String extraMeta) {
        Intent intent = new Intent(previousActivity, nextActivity);
        intent.putExtra("player", player);
        intent.putExtra(extraName, extraMeta);
        previousActivity.startActivity(intent);
        ((Activity) previousActivity).finish();
    }
}
