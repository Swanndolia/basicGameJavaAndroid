package dev.swanndolia.idlemmorpg.tools.activity;

import android.app.Dialog;
import android.content.Context;
import android.widget.TextView;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;

public class Tutorial {
    public Tutorial(Context context, Player player) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.overlay_tutorial);
        TextView tutoTextView = dialog.findViewById(R.id.tutorialTextView);
        tutoTextView.setText("Hello " + player.getName() +
                "\nWelcome to my lil indie game" +
                "\nI have no pretension making anything good" +
                "\nBut it's a good way to have some fun for me" +
                "\nHope you will enjoy it as much as i do :D");
        dialog.show();
    }
}
