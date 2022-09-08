package dev.swanndolia.idlemmorpg.ui.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;

public class Inventory extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        setContentView(R.layout.activity_inventory);

        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());

        final Button basicWeaponBtn = findViewById(R.id.basicWeaponBtn);
        final Button specialWeaponBtn = findViewById(R.id.rangedWeaponBtn);
        final Button armsBtn = findViewById(R.id.armsBtn);
        final Button capeBtn = findViewById(R.id.capeBtn);
        final Button bootsBtn = findViewById(R.id.bootsBtn);
        final Button glovesBtn = findViewById(R.id.glovesBtn);
        final Button helmetBtn = findViewById(R.id.helmetBtn);
        final Button legsBtn = findViewById(R.id.legsBtn);
        final Button lRingBtn = findViewById(R.id.lRingBtn);
        final Button rRingBtn = findViewById(R.id.rRingBtn);
        final Button neckBtn = findViewById(R.id.neckBtn);
        final Button torsoBtn = findViewById(R.id.torsoBtn);
        final Button miscBtn = findViewById(R.id.miscBtn);

        basicWeaponBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Basic Weapon"));
        specialWeaponBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Special Weapon"));
        armsBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Arms"));
        bootsBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Boots"));
        capeBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Cape"));
        glovesBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Gloves"));
        helmetBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Helmet"));
        legsBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Legs"));
        lRingBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Ring"));
        rRingBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Ring"));
        neckBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Neck"));
        torsoBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Torso"));
        miscBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "None"));
    }

    @Override
    public void onBackPressed() {
        new ActivityLauncher(this, Menu.class, player);
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
}
