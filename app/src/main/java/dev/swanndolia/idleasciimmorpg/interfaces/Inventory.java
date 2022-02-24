package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

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
        final Button specialWeaponBtn = findViewById(R.id.specialWeaponBtn);
        final Button ultimateWeaponBtn = findViewById(R.id.ultimateWeaponBtn);
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

        basicWeaponBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Basic Weapon");
            startActivity(intent);
            finish();
        });
        specialWeaponBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Special Weapon");
            startActivity(intent);
            finish();
        });
        ultimateWeaponBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Ultimate Weapon");
            startActivity(intent);
            finish();
        });
        armsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Arms");
            startActivity(intent);
            finish();
        });
        bootsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Boots");
            startActivity(intent);
            finish();
        });
        capeBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Cape");
            startActivity(intent);
            finish();
        });
        glovesBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Gloves");
            startActivity(intent);
            finish();
        });
        helmetBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Helmet");
            startActivity(intent);
            finish();
        });
        legsBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Legs");
            startActivity(intent);
            finish();
        });
        lRingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Ring");
            startActivity(intent);
            finish();
        });
        rRingBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Ring");
            startActivity(intent);
            finish();
        });
        neckBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Neck");
            startActivity(intent);
            finish();
        });
        torsoBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "Torso");
            startActivity(intent);
            finish();
        });
        miscBtn.setOnClickListener(v -> {
            Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
            intent.putExtra("player", player);
            intent.putExtra("slot", "None");
            startActivity(intent);
            finish();
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Inventory.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
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
