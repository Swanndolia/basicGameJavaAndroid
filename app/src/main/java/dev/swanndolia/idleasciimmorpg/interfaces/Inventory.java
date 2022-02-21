package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Inventory extends AppCompatActivity {
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_inventory);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");

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

        basicWeaponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Basic Weapon");
                startActivity(intent);
                finish();
            }
        });
        specialWeaponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Special Weapon");
                startActivity(intent);
                finish();
            }
        });
        ultimateWeaponBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Ultimate Weapon");
                startActivity(intent);
                finish();
            }
        });
        armsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Arms");
                startActivity(intent);
                finish();
            }
        });
        bootsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Boots");
                startActivity(intent);
                finish();
            }
        });
        capeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Cape");
                startActivity(intent);
                finish();
            }
        });
        glovesBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Gloves");
                startActivity(intent);
                finish();
            }
        });
        helmetBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Helmet");
                startActivity(intent);
                finish();
            }
        });
        legsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Legs");
                startActivity(intent);
                finish();
            }
        });
        lRingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Ring");
                startActivity(intent);
                finish();
            }
        });
        rRingBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Ring");
                startActivity(intent);
                finish();
            }
        });
        neckBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Neck");
                startActivity(intent);
                finish();
            }
        });
        torsoBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "Torso");
                startActivity(intent);
                finish();
            }
        });
        miscBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Inventory.this, CompareAndEquip.class);
                intent.putExtra("player", player);
                intent.putExtra("slot", "None");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Inventory.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
    }
}
