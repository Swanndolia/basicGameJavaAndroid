package dev.swanndolia.idleasciimmorpg.userinterface;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Town extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        setContentView(R.layout.activity_town);

        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());
        final Button healerBtn = findViewById(R.id.healerBtn);
        final Button marketBtn = findViewById(R.id.marketPlaceButton);

        int healCost = player.getMaxHp() * player.getLevel() / 3;
        String healerSentence = player.getHp() > player.getMaxHp() * 0.75 ? "Sure you need my services ? (" + player.getHp() + "/" + player.getMaxHp() + ")" : "Lemme see ! You're hurt ! (" + player.getHp() + "/" + player.getMaxHp() + ")";
        marketBtn.setOnClickListener(view -> {
            Intent intent = new Intent(Town.this, MarketPlace.class);
            intent.putExtra("player", player);
            startActivity(intent);
            finish();
        });
        healerBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Town.this);
            builder.setTitle(healerSentence + "\n" + "It will cost you: " + healCost);
            builder.setPositiveButton(android.R.string.ok,
                    (dialog, id) -> {
                        if (player.getCryptoCoins() >= healCost) {
                            player.setCryptoCoins((player.getCryptoCoins() - healCost));
                            dialog.dismiss();
                            Intent intent = new Intent(Town.this, Menu.class);
                            intent.putExtra("player", player);
                            startActivity(intent);
                            finish();
                        } else {
                            Toast.makeText(Town.this, "You don't have enough money", Toast.LENGTH_SHORT).show();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,
                    (dialog, id) -> dialog.dismiss());
            builder.show();
        });
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
        Intent intent = new Intent(Town.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
    }
}
