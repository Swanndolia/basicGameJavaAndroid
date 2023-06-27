package dev.swanndolia.idlemmorpg.ui.main;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ProgressBar;

import androidx.appcompat.app.AppCompatActivity;

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
        setContentView(R.layout.activity_inventory);

        final Button meleeWeaponBtn = findViewById(R.id.meleeWeaponBtn);
        final Button rangedWeaponBtn = findViewById(R.id.rangedWeaponBtn);
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

        meleeWeaponBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Melee Weapon"));
        rangedWeaponBtn.setOnClickListener(v -> new ActivityLauncher(this, CompareAndEquip.class, player, "slot", "Ranged Weapon"));
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
}
