package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.tools.music.BackgroundMusicService;


public class BootStrap extends AppCompatActivity {

    Intent musicService;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
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
    }
    @Override
    public void onBackPressed() {
        Toast.makeText(BootStrap.this, "Back Button is being Pressed!", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }

    @Override
    public void onPause() {
        super.onPause();
        Toast.makeText(BootStrap.this, "Back Button is being Pressed!", Toast.LENGTH_SHORT).show();

        super.onBackPressed();
    }

}
