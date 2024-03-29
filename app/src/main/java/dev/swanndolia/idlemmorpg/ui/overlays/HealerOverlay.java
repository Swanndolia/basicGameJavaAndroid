package dev.swanndolia.idlemmorpg.ui.overlays;

import android.app.Dialog;
import android.content.Context;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;

public class HealerOverlay {
    public HealerOverlay(Player player, Context context) {

        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.overlay_healer);

        int healCost = player.getMaxHp() * player.getLevel() / 3;
        String healerSentence = player.getHp() > player.getMaxHp() * 0.75 ? "Sure you need my services ? (" + player.getHp() + "/" + player.getMaxHp() + ")" : "Lemme see ! You're hurt ! (" + player.getHp() + "/" + player.getMaxHp() + ")";

        TextView healerText = dialog.findViewById(R.id.healerText);
        healerText.setText(healerSentence + "\n" + "It will cost you: " + healCost);
        Button cancelHealBtn = dialog.findViewById(R.id.cancelHealBtn);
        cancelHealBtn.setOnClickListener(view -> dialog.dismiss());
        Button acceptHealBtn = dialog.findViewById(R.id.acceptHealBtn);

        acceptHealBtn.setOnClickListener(view -> { //todo check why it dont save on firebase
            if (player.getCoins() >= healCost) {
                player.setCoins(player.getCoins() - healCost);
                player.setHp(player.getMaxHp());
                player.savePlayer();
                dialog.dismiss();
                Toast.makeText(context, "You've been successfully healed to max HP", Toast.LENGTH_SHORT).show();
            } else {
                dialog.dismiss();
                Toast.makeText(context, "You don't have enough money to heal yourself", Toast.LENGTH_SHORT).show();
            }
        });
        dialog.show();
    }
}
