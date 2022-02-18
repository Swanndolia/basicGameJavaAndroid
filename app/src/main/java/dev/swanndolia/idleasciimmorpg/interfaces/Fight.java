package dev.swanndolia.idleasciimmorpg.interfaces;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Character;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.weapons.Weapon;
import dev.swanndolia.idleasciimmorpg.items.weapons.normal.BasicPistol;
import dev.swanndolia.idleasciimmorpg.items.weapons.normal.NormalWeapon;
import dev.swanndolia.idleasciimmorpg.location.fields.GenerateEnemy;

public class Fight extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        Player player = (Player) bundle.getSerializable("player");
        String location = (String) bundle.getSerializable("location");

        Character enemyEncountered = new GenerateEnemy().GenerateEnemy(location, player);

        setContentView(R.layout.activity_fight);

        LinearLayout parentLayout = (LinearLayout) findViewById(R.id.parentLayout);

        TextView enemyTextView = new TextView(this);
        enemyTextView.setText(enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel() + " HP: " + enemyEncountered.getHp() );
        enemyTextView.setTextSize(20);
        enemyTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        enemyTextView.setLines(1);

        parentLayout.addView(enemyTextView);

        Button basicAttackBtn = new Button(this);
        basicAttackBtn.setTextSize(20);

        Item basicWeapon = player.getEquipedItem("Basic Weapon");
        Item specialWeapon = player.getEquipedItem("Special Weapon");
        Item ultimateWeapon = player.getEquipedItem("Ultimate Weapon");

        if (basicWeapon != null) {
            basicAttackBtn.setText(basicWeapon.getName() + " " + basicWeapon.getDamage() + " dmg");
        } else {
            basicAttackBtn.setText("No Basic Weapon ");
        }
        parentLayout.addView(basicAttackBtn);

        Button specialAttackBtn = new Button(this);
        specialAttackBtn.setTextSize(20);
        if (specialWeapon != null) {
            specialAttackBtn.setText(specialWeapon.getName() + " " + specialWeapon.getDamage() + " dmg");
        } else {
            specialAttackBtn.setText("No Special Weapon");
        }
        parentLayout.addView(specialAttackBtn);

        Button ultimateAttackBtn = new Button(this);
        ultimateAttackBtn.setTextSize(20);
        if (ultimateWeapon != null) {
            ultimateAttackBtn.setText(ultimateWeapon.getName() + " " + ultimateWeapon.getDamage() + " dmg");
        } else {
            ultimateAttackBtn.setText("No Ultimate Weapon");
        }
        parentLayout.addView(ultimateAttackBtn);
    }
}