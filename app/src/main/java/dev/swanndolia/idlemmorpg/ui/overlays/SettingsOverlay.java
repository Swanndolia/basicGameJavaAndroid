package dev.swanndolia.idlemmorpg.ui.overlays;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.view.Window;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.SeekBar;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.services.BackgroundMusicService;
import dev.swanndolia.idlemmorpg.ui.main.Login;

public class SettingsOverlay {

    public SettingsOverlay(Context context, SharedPreferences sharedPreferences, Player player) {
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
                Intent musicService = new Intent(context, BackgroundMusicService.class);
                if (progress == 0) { //todo make sound level instead on off
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
            DatabaseReference usersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
            usersDatabase.child(player.getName()).child("lastSessionID").setValue("");
            usersDatabase.child(player.getName()).child("online").setValue(false);
            new ActivityLauncher(context, Login.class);
        });

        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);

        dialog.show();
    }
}
