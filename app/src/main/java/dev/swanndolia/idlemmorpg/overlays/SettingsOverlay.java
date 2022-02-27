package dev.swanndolia.idlemmorpg.overlays;

import android.app.Dialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

import dev.swanndolia.idlemmorpg.userinterface.Login;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;

public class SettingsOverlay {
    public void SettingsOverlay(Context context, SharedPreferences sharedPreferences) {
        Dialog dialog = new Dialog(context);
        dialog.setTitle("Customize your settings here !");
        LinearLayout settingsOverlay = new LinearLayout(context);
        settingsOverlay.setOrientation(LinearLayout.VERTICAL);
        LinearLayout soundSettingsLayout = new LinearLayout(context);
        TextView soundTextView = new TextView(context);
        soundTextView.setText("Volume: ");
        SeekBar soundSeekBar = new SeekBar(context);
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                //send command to music service somehow
            }
        });
        soundSettingsLayout.addView(soundTextView);
        soundSettingsLayout.addView(soundSeekBar);
        settingsOverlay.addView(soundSettingsLayout);

        Button disconnectBtn = new Button(context);
        disconnectBtn.setText("DISCONNECT");
        disconnectBtn.setBackgroundColor(ContextCompat.getColor(context, com.google.android.material.R.color.design_default_color_error));
        Dialog finalDialog = dialog;
        disconnectBtn.setOnClickListener(view -> {

            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("stayLogin", false);
            editor.apply();
            finalDialog.dismiss();
            new ActivityLauncher().ActivityLauncher(context, Login.class);

        });
        TextView discordTextView = new TextView(context);
        discordTextView.setText("discord ic");
        TextView githubTextView = new TextView(context);
        githubTextView.setText("github là");
        TextView settings1TextView = new TextView(context);
        settings1TextView.setText("jsp encore ic");
        TextView settings2TextView = new TextView(context);
        settings2TextView.setText("jsp la nn plus");
        TextView settings3TextView = new TextView(context);
        settings3TextView.setText("et la nn plus");

        settingsOverlay.addView(discordTextView);
        settingsOverlay.addView(githubTextView);
        settingsOverlay.addView(settings1TextView);
        settingsOverlay.addView(settings2TextView);
        settingsOverlay.addView(settings3TextView);
        settingsOverlay.addView(disconnectBtn);
        dialog.setContentView(settingsOverlay);

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
}
