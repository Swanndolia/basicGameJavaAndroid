package dev.swanndolia.idlemmorpg.tools.activity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import java.util.UUID;

import dev.swanndolia.idlemmorpg.tools.services.BackgroundMusicService;
import dev.swanndolia.idlemmorpg.tools.services.DisconnectPlayerAfterTaskKill;
import dev.swanndolia.idlemmorpg.ui.main.Login;
import dev.swanndolia.idlemmorpg.ui.main.Register;

public class BootStrap extends AppCompatActivity {

    Intent musicService;
    SharedPreferences sharedPreferences;

    UUID uuid;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {

        sharedPreferences = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
        musicService = new Intent(BootStrap.this, BackgroundMusicService.class);
        startService(musicService);

        if (sharedPreferences.getBoolean("stayLogin", true)) {
            Intent intent = new Intent(BootStrap.this, Login.class);
            intent.putExtra("autologin", true);
            intent.putExtra("username", sharedPreferences.getString("username", ""));
            intent.putExtra("password", sharedPreferences.getString("password", ""));
            startActivity(intent);
            finish();
        } else if (!sharedPreferences.getString("username", "").equals("")) {
            Intent intent = new Intent(BootStrap.this, Login.class);
            intent.putExtra("username", sharedPreferences.getString("username", ""));
            intent.putExtra("autologin", false);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(BootStrap.this, Register.class);
            intent.putExtra("autologin", false);
            startActivity(intent);
            finish();
        }
        super.onResume();
    }
}
