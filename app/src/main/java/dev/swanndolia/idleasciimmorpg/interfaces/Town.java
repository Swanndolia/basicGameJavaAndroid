package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Town extends AppCompatActivity {
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");

        setContentView(R.layout.activity_town);

        final Button healerBtn = findViewById(R.id.healerBtn);

        Integer healCost = player.getMaxHp() * player.getLevel() / 3;
        String healerSentence = player.getHp() > player.getMaxHp() * 0.75 ? "Sure you need my services ? (" + player.getHp() + "/" + player.getMaxHp() + ")" : "Lemme see ! You're hurt ! (" + player.getHp() + "/" + player.getMaxHp() + ")";

        healerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Town.this);
                AlertDialog dialog = builder.create();
                builder.setTitle(healerSentence + "\n" + "It will cost you: " + healCost);
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
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
                            }
                        });
                builder.setNegativeButton(android.R.string.cancel,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.dismiss();
                            }
                        });
                builder.show();
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
