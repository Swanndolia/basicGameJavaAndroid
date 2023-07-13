package dev.swanndolia.idlemmorpg;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idlemmorpg.tools.services.BackgroundMusicService;
import dev.swanndolia.idlemmorpg.ui.main.Login;
import dev.swanndolia.idlemmorpg.ui.main.Register;

public class MainActivity extends AppCompatActivity {

    Intent musicService;
    SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {

        sharedPreferences = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
        musicService = new Intent(MainActivity.this, BackgroundMusicService.class);
        startService(musicService);

        if (sharedPreferences.getBoolean("stayLogin", true)) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.putExtra("autologin", true);
            intent.putExtra("username", sharedPreferences.getString("username", ""));
            intent.putExtra("password", sharedPreferences.getString("password", ""));
            startActivity(intent);
            finish();
        } else if (!sharedPreferences.getString("username", "").equals("")) {
            Intent intent = new Intent(MainActivity.this, Login.class);
            intent.putExtra("username", sharedPreferences.getString("username", ""));
            intent.putExtra("autologin", false);
            startActivity(intent);
            finish();
        } else {
            Intent intent = new Intent(MainActivity.this, Register.class);
            intent.putExtra("autologin", false);
            startActivity(intent);
            finish();
        }
        super.onResume();
    }
}
