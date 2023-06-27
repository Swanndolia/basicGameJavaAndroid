package dev.swanndolia.idlemmorpg.ui.main;

import android.os.Bundle;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;

public class Profile extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        setContentView(R.layout.activity_profile);

        TextView playerName = findViewById(R.id.playerNameStats);
        playerName.setText(player.getName());
        TextView playerCoin = findViewById(R.id.playerCoinsStats);
        playerCoin.setText(player.getCoins().toString());
        TextView playerArrow = findViewById(R.id.playerArrowsStats);
        playerArrow.setText(player.getArrow().toString());
        TextView playerDeaths = findViewById(R.id.playerDeathsStats);
        playerDeaths.setText(player.getDeaths().toString());

        TextView playerExp = findViewById(R.id.playerExpStats);
        playerExp.setText(MessageFormat.format("Level: {0}({1} / {2})", player.getLevel().toString(), player.getExp().toString(), player.getNextLevelExp().toString()));

        TextView playerHp = findViewById(R.id.playerHpStats);
        playerHp.setText(MessageFormat.format("{0} / {1}", player.getHp().toString(), player.getMaxHp().toString()));
        TextView playerMaxMp = findViewById(R.id.playerMpStats);
        playerMaxMp.setText(MessageFormat.format("{0} / {1}", player.getMp().toString(), player.getMaxMp().toString()));

        //TODO
        player.getItemBoughtMarket();
        player.getItemSoldMarket();
        player.getLegendaryEnemyKilled();
        player.getLegendaryItemFound();
        player.getUniqueItemFound();
        player.getEnemyKilled();
        player.getMoneyGained();
        player.getQuestCompleted();


    }

    private void makePlayerAlwaysUpdated() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player = snapshot.child(player.getName()).child("player").getValue(Player.class);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }

    @Override
    public void onBackPressed() {
        new ActivityLauncher(this, Menu.class, player);
    }
}