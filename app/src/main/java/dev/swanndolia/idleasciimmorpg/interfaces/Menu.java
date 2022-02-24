package dev.swanndolia.idleasciimmorpg.interfaces;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.SeekBar;
import android.widget.TextView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Menu extends AppCompatActivity {
    AlertDialog dialog;
    Player player;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();

        setContentView(R.layout.activity_menu);
        setContentView(R.layout.activity_menu);

        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setMax(player.getNextLevelExp());
        expProgressBar.setProgress(player.getExp());

        final Button exploreBtn = findViewById(R.id.exploreBtn);
        final Button craftBtn = findViewById(R.id.craftBtn);
        final Button smeltBtn = findViewById(R.id.smeltBtn);
        final Button tinkerBtn = findViewById(R.id.tinkerBtn);
        final Button fuseBtn = findViewById(R.id.fuseBtn);
        final Button townBtn = findViewById(R.id.townBtn);
        final Button inventoryBtn = findViewById(R.id.inventoryBtn);
        final Button profileBtn = findViewById(R.id.profileBtn);
        final Button guildBtn = findViewById(R.id.guildBtn);
        final Button bestiaryBtn = findViewById(R.id.bestiaryBtn);
        final Button storeBtn = findViewById(R.id.storeBtn);
        final ImageButton settingsBtn = findViewById(R.id.settingsBtn);



        exploreBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Explore.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        inventoryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Inventory.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        craftBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Craft.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        smeltBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Smelt.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });

        tinkerBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Tinker.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        fuseBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Fuse.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        townBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Town.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        inventoryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Inventory.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        profileBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Profile.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        bestiaryBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Bestiary.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        guildBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Guild.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });
        storeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Menu.this, Store.class);
            intent.putExtra("player", player);
            startActivity(intent);
        });


        settingsBtn.setOnClickListener(v -> {
            AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
            builder.setTitle("Customize your settings here !");
            builder.setView(generateSettingsOverlay());
            dialog = builder.create();
            dialog.show();
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


    public LinearLayout generateSettingsOverlay() {
        LinearLayout settingsOverlay = new LinearLayout(this);
        settingsOverlay.setOrientation(LinearLayout.VERTICAL);
        LinearLayout soundSettingsLayout = new LinearLayout(this);
        TextView soundTextView = new TextView(this);
        soundTextView.setText("Volume: ");
        SeekBar soundSeekBar = new SeekBar(this);
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                //send command to music service somehow
            }
        });
        soundSettingsLayout.addView(soundTextView);
        soundSettingsLayout.addView(soundSeekBar);
        settingsOverlay.addView(soundSettingsLayout);

        Button disconnectBtn = new Button(this);
        disconnectBtn.setText("DISCONNECT");
        disconnectBtn.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_error));
        disconnectBtn.setOnClickListener(view -> {
            SharedPreferences sharedPreferences = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putBoolean("stayLogin", false);
            editor.apply();
            dialog.dismiss();
            Intent intent = new Intent(Menu.this, Login.class);
            startActivity(intent);
            finish();
        });
        TextView discordTextView = new TextView(this);
        discordTextView.setText("discord ic");
        TextView githubTextView = new TextView(this);
        githubTextView.setText("github là");
        TextView settings1TextView = new TextView(this);
        settings1TextView.setText("jsp encore ic");
        TextView settings2TextView = new TextView(this);
        settings2TextView.setText("jsp la nn plus");
        TextView settings3TextView = new TextView(this);
        settings3TextView.setText("et la nn plus");

        settingsOverlay.addView(discordTextView);
        settingsOverlay.addView(githubTextView);
        settingsOverlay.addView(settings1TextView);
        settingsOverlay.addView(settings2TextView);
        settingsOverlay.addView(settings3TextView);


        settingsOverlay.addView(disconnectBtn);
        return settingsOverlay;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to close the game ?");
        builder.setPositiveButton(android.R.string.ok,
                (dialog, id) -> {
                    dialog.dismiss();
                    System.exit(0);
                });
        builder.setNegativeButton(android.R.string.cancel,
                (dialog, id) -> dialog.dismiss());
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}