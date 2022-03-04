package dev.swanndolia.idlemmorpg.ui.overlays;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.tools.music.BackgroundMusicService;
import dev.swanndolia.idlemmorpg.ui.main.Login;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.ui.main.Menu;

public class SettingsOverlay {

    public SettingsOverlay(Context context, SharedPreferences sharedPreferences) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.overlay_settings);
        dialog.setTitle("Customize your settings here !");

        SeekBar soundSeekBar = dialog.findViewById(R.id.volumeMusicBar);
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
                Intent musicService = new Intent(context, BackgroundMusicService.class);
                if (progress == 0) {
                    context.stopService(musicService);
                } else if (progress == 100) {
                    context.startService(musicService);
                }
            }
        });

        Button disconnectBtn = dialog.findViewById(R.id.disconnectBtn);
        disconnectBtn.setOnClickListener(view -> {
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.clear();
            editor.apply();
            dialog.dismiss();
            new ActivityLauncher(context, Login.class);
        });

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
}
