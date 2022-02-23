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

        final Button healerBtn = findViewById(R.id.fieldsBtn);

        Integer healCost = player.getMaxHp() * player.getLevel() / 3;
        String healerSentence = player.getHp() > player.getMaxHp() * 0.75 ? "You sure you need my services ? " : "I see you've been hurt !";

        healerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Town.this);
                AlertDialog dialog = builder.create();
                builder.setTitle("Hello " + player.getName() + ",\n" + healerSentence + "\n" + "It will cost you: " + healCost);
                builder.setPositiveButton(android.R.string.ok,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                if(player.getCryptoCoins() >= healCost) {
                                    player.setCryptoCoins((player.getCryptoCoins() - healCost));
                                    dialog.dismiss();
                                    Intent intent = new Intent(Town.this, Menu.class);
                                    intent.putExtra("player", player);
                                    startActivity(intent);
                                    finish();
                                }
                                else{
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
