package dev.swanndolia.idlemmorpg.ui.main;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;

public class Bestiary extends AppCompatActivity {
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        setContentView(R.layout.activity_bestiary);
    }

    public void onBackPressed() {
        new ActivityLauncher(this, Menu.class, player);
    }
}
