package dev.swanndolia.idlemmorpg.ui.main;

import android.content.Context;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.activity.Tutorial;
import dev.swanndolia.idlemmorpg.ui.overlays.ChatOverlay;
import dev.swanndolia.idlemmorpg.ui.overlays.SettingsOverlay;

public class Menu extends AppCompatActivity {
    Player player;
    ProgressBar playerExp;
    ProgressBar playerHp;
    ProgressBar playerStamina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        if (bundle.getSerializable("tutorial") != null && (Boolean) bundle.getSerializable("tutorial")) {
            new Tutorial(this, player);
        }
        makePlayerAlwaysUpdated();

        setContentView(R.layout.activity_menu);
        setContentView(R.layout.activity_menu);

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
        final ImageButton chatBtn = findViewById(R.id.chatBtn);

        exploreBtn.setOnClickListener(v -> new ActivityLauncher(this, Explore.class, player));
        inventoryBtn.setOnClickListener(v -> new ActivityLauncher(this, Inventory.class, player));
        craftBtn.setOnClickListener(v -> new ActivityLauncher(this, Craft.class, player));
        smeltBtn.setOnClickListener(v -> new ActivityLauncher(this, Smelt.class, player));
        tinkerBtn.setOnClickListener(v -> new ActivityLauncher(this, Tinker.class, player));
        fuseBtn.setOnClickListener(v -> new ActivityLauncher(this, Fuse.class, player));
        townBtn.setOnClickListener(v -> new ActivityLauncher(this, Town.class, player));
        inventoryBtn.setOnClickListener(v -> new ActivityLauncher(this, Inventory.class, player));
        profileBtn.setOnClickListener(v -> new ActivityLauncher(this, Profile.class, player));
        bestiaryBtn.setOnClickListener(v -> new ActivityLauncher(this, Bestiary.class, player));
        guildBtn.setOnClickListener(v -> new ActivityLauncher(this, Guild.class, player));
        storeBtn.setOnClickListener(v -> new ActivityLauncher(this, Store.class, player));
        settingsBtn.setOnClickListener(v -> new SettingsOverlay(this, getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE)));
        chatBtn.setOnClickListener(v -> new ChatOverlay(this, player));
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