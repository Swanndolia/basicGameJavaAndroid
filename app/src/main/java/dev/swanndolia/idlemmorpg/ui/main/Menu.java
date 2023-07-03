package dev.swanndolia.idlemmorpg.ui.main;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
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

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.activity.Tutorial;
import dev.swanndolia.idlemmorpg.ui.overlays.ChatOverlay;
import dev.swanndolia.idlemmorpg.ui.overlays.SettingsOverlay;

//todo dungeons with character movement
//todo economy
//todo skill system (strength agi dex int const... with buff to hp stamina dmg / crit dodge acc)
//todo mini story
public class Menu extends AppCompatActivity {
    Player player;
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        if (bundle.getSerializable("tutorial") != null && (Boolean) bundle.getSerializable("tutorial")) {
            new Tutorial(this, player);
        }


        setContentView(R.layout.activity_menu);

        //TODO make bars work
        final ProgressBar playerExp = findViewById(R.id.playerExpBar);
        final ProgressBar playerHp = findViewById(R.id.playerHpBar);
        final ProgressBar playerStamina = findViewById(R.id.playerStaminaBar);

        playerExp.setMax(player.getNextLevelExp());
        playerHp.setMax(player.getMaxHp());
        playerStamina.setMax(player.getMaxStamina());

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            playerExp.setProgress(player.getExp(), true);
            playerHp.setProgress(player.getHp(), true);
            playerStamina.setProgress(player.getStamina(), true);
        } else {
            playerExp.setProgress(player.getExp());
            playerHp.setProgress(player.getHp());
            playerStamina.setProgress(player.getStamina());
        }

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
        settingsBtn.setOnClickListener(v -> new SettingsOverlay(this, getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE), player));
        chatBtn.setOnClickListener(v -> new ChatOverlay(this, player));
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

    @Override
    public void onStop() {
        databaseReference.child("users").child(player.getName()).child("onlineSessionID").setValue("");
        super.onStop();
    }

    @Override
    public void onRestart() {//todo trying to add generated token check to dont delog onstop but delog if saved token is different from previous one currently use one instead two tokens
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                SharedPreferences sharedPref = getSharedPreferences("SESSION_ID", Context.MODE_PRIVATE);


                if (snapshot.child("users").child(player.getName()).child("onlineSessionID").exists()) {
                    if (!snapshot.child("users").child(player.getName()).child("onlineSessionID").getValue().toString().equals(sharedPref.getString("id", "")))
                        new ActivityLauncher(Menu.this, Login.class);
                    Toast.makeText(Menu.this, snapshot.child("users").child(player.getName()).child("onlineSessionID").getValue().toString(), Toast.LENGTH_SHORT);
                } else {
                    databaseReference.child("users").child(player.getName()).child("onlineSessionID").setValue(sharedPref.getString("id", ""));
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        super.onRestart();
    }
}



