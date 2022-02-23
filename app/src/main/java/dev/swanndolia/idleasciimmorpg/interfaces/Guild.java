package dev.swanndolia.idleasciimmorpg.interfaces;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Guild extends AppCompatActivity {
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");

        setContentView(R.layout.activity_guild);
    }
}