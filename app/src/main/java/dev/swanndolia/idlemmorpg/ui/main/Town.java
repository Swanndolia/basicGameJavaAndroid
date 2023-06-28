package dev.swanndolia.idlemmorpg.ui.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.ui.overlays.HealerOverlay;

public class Town extends AppCompatActivity {
    Player player;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        setContentView(R.layout.activity_town);

        Button healerBtn = findViewById(R.id.healerBtn);
        Button marketBtn = findViewById(R.id.marketPlaceButton);

        marketBtn.setOnClickListener(view -> {
            new ActivityLauncher(this, MarketPlace.class, player);
        });
        healerBtn.setOnClickListener(v -> {
            new HealerOverlay(player, this);
        });
    }
    @Override
    public void onBackPressed() {
        new ActivityLauncher(this, Menu.class, player);
    }
}
