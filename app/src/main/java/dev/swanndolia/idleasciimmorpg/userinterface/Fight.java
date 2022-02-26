package dev.swanndolia.idleasciimmorpg.userinterface;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.Random;
import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.DefaultCharacter;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.weapons.special.SpecialAmmunition;
import dev.swanndolia.idleasciimmorpg.items.weapons.ultimate.UltimateAmmunition;
import dev.swanndolia.idleasciimmorpg.location.fields.GenerateEnemy;
import dev.swanndolia.idleasciimmorpg.overlays.EnemyKilledOverlay;
import dev.swanndolia.idleasciimmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idleasciimmorpg.tools.animations.CustomAnimationDrawableNew;

public class Fight extends AppCompatActivity {
    ImageView playerAnimationView;
    TextView playerTextView;
    TextView enemyTextView;
    Button basicAttackBtn;
    Button specialAttackBtn;
    Button ultimateAttackBtn;
    DefaultCharacter enemyEncountered;
    Player player;
    ProgressBar playerHp;
    ProgressBar enemyHp;
    ProgressBar expProgressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        String location = (String) bundle.getSerializable("location");
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        enemyEncountered = new GenerateEnemy().GenerateEnemy(location, player);
        setContentView(R.layout.activity_fight);

        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());

        playerAnimationView = (ImageView) findViewById(R.id.playerAnim);
        playerTextView = (TextView) findViewById(R.id.playerInfo);
        playerHp = (ProgressBar) findViewById(R.id.playerHp);
        enemyTextView = (TextView) findViewById(R.id.enemyInfo);
        enemyHp = (ProgressBar) findViewById(R.id.enemyHp);
        basicAttackBtn = (Button) findViewById(R.id.basicWeaponBtn);
        specialAttackBtn = (Button) findViewById(R.id.specialWeaponBtn);
        ultimateAttackBtn = (Button) findViewById(R.id.ultimateWeaponBtn);

        playerHp.setMax(player.getMaxHp());
        playerHp.setProgress(player.getHp());
        updatePlayerInfo();

        enemyHp.setMax(enemyEncountered.getHp());
        enemyHp.setProgress(enemyEncountered.getHp());
        updateEnemyInfo();

        runAnimation(R.drawable.idle);

        Item basicWeapon = player.getEquippedItem("Basic Weapon");
        Item specialWeapon = player.getEquippedItem("Special Weapon");
        Item ultimateWeapon = player.getEquippedItem("Ultimate Weapon");

        if (basicWeapon != null) {
            basicAttackBtn.setText(MessageFormat.format("{0} {1} dmg", basicWeapon.getName(), basicWeapon.getDamage()));
            basicAttackBtn.setOnClickListener(v -> enemyAttackStep(playerAttackStep(basicWeapon)));
        }
        if (specialWeapon != null) {
            specialAttackBtn.setText(MessageFormat.format("{0} {1} dmg", specialWeapon.getName(), specialWeapon.getDamage()));
            specialAttackBtn.setOnClickListener(v -> {
                if (player.getInventory().contains(new SpecialAmmunition().SpecialAmmunition())) {
                    enemyAttackStep(playerAttackStep(specialWeapon));
                } else {
                    Toast.makeText(Fight.this, "You don't have any special ammunition", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (ultimateWeapon != null) {
            ultimateAttackBtn.setText(MessageFormat.format("{0} {1} dmg", ultimateWeapon.getName(), ultimateWeapon.getDamage()));
            ultimateAttackBtn.setOnClickListener(v -> {
                if (player.getInventory().contains(new UltimateAmmunition().UltimateAmmunition())) {
                    enemyAttackStep(playerAttackStep(ultimateWeapon));
                } else {
                    Toast.makeText(Fight.this, "You don't have any ultimate ammunition", Toast.LENGTH_SHORT).show();
                }

            });
        }
    }

    private void updatePlayerInfo() {
        playerHp.setProgress(player.getHp());
        playerTextView.setText(MessageFormat.format("{0} lvl {1} HP: {2}", player.getName(), player.getLevel(), player.getHp()));
        if (player.getHp() <= 0) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.player_dead_layout);
            Integer lostExp = (int) player.getExp() / 10;
            Integer lostCryptoCoins = (int) player.getCryptoCoins() / 5;
            player.setExp(player.getExp() - lostExp);
            player.setCryptoCoins(player.getCryptoCoins() - lostCryptoCoins);
            TextView deadPlayerText = (TextView) dialog.findViewById(R.id.deadPlayerText);
            deadPlayerText.setText(MessageFormat.format("{0} has been killed by a {1} lvl {2}", player.getName(), enemyEncountered.getName(), enemyEncountered.getLevel()));
            TextView lostCryptoCoinsText = (TextView) dialog.findViewById(R.id.lostCryptoCoinsText);
            lostCryptoCoinsText.setText(MessageFormat.format("You''ve lost {0} Crypto-coins", lostCryptoCoins));
            TextView lostExpTextView = (TextView) dialog.findViewById(R.id.lostExpText);
            lostExpTextView.setText(MessageFormat.format("You''ve lost {0} exp", lostExp));
            player.setHp(player.getMaxHp());
            dialog.show();
            dialog.setOnCancelListener(
                    dialogInterface -> new ActivityLauncher().ActivityLauncher(this, Menu.class, player)
            );
        }
        player.savePlayer();
    }

    private void updateEnemyInfo() {
        enemyHp.setProgress(enemyEncountered.getHp());
        enemyTextView.setText(MessageFormat.format("{0} lvl {1} HP: {2}", enemyEncountered.getName(), enemyEncountered.getLevel(), enemyEncountered.getHp()));
        if (enemyEncountered.getHp() <= 0) {
            new EnemyKilledOverlay().EnemyKilledOverlay(Fight.this, player, enemyEncountered);
        }
    }

    public Integer playerAttackStep(Item weapon) {
        Integer waitTimeBeforeEnemyAnim;

        if (weapon.getAccuracy() - enemyEncountered.getEvade() > new Random().nextInt(100)) {
            if (weapon.getCritChance() > new Random().nextInt(100)) {
                waitTimeBeforeEnemyAnim = runAnimation(R.drawable.attack_crit);
                enemyEncountered.setHp(enemyEncountered.getHp() - weapon.getCritDamage());
            } else {
                waitTimeBeforeEnemyAnim = runAnimation(R.drawable.attack);
                enemyEncountered.setHp(enemyEncountered.getHp() - weapon.getDamage());
            }
        } else {
            if (weapon.getCritChance() > new Random().nextInt(100)) {
                waitTimeBeforeEnemyAnim = runAnimation(R.drawable.attack_crit);
            } else {
                waitTimeBeforeEnemyAnim = runAnimation(R.drawable.attack);
            }
        }
        return waitTimeBeforeEnemyAnim;
    }

    public void enemyAttackStep(Integer waitPlayerAnimEnd) {
        if (enemyEncountered.getHp() > 0) {
            if (enemyEncountered.getAccuracy() - player.getEvade() > new Random().nextInt(100)) {
                scheduleAnim(waitPlayerAnimEnd, R.drawable.hurt);
                if (enemyEncountered.getCritChance() > new Random().nextInt(100)) {
                    player.setHp(player.getHp() - enemyEncountered.getCritDamage());
                } else {
                    player.setHp(player.getHp() - enemyEncountered.getDamage());
                }
            } else {
                scheduleAnim(waitPlayerAnimEnd, R.drawable.evade);
            }
        }
    }

    public Integer runAnimation(Integer animation) {
        CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew((AnimationDrawable) ContextCompat.getDrawable(this, animation)) {
            @Override
            public void onAnimationStart() {
                if (animation == R.drawable.idle) {
                    basicAttackBtn.setEnabled(true);
                    specialAttackBtn.setEnabled(true);
                    ultimateAttackBtn.setEnabled(true);
                } else {
                    basicAttackBtn.setEnabled(false);
                    specialAttackBtn.setEnabled(false);
                    ultimateAttackBtn.setEnabled(false);
                }
            }

            @Override
            public void onAnimationFinish() {
                if(animation == R.drawable.attack_crit || animation == R.drawable.attack){
                    updateEnemyInfo();
                }
                if(animation == R.drawable.hurt){
                    updatePlayerInfo();
                }
                if (animation != R.drawable.idle) {
                    this.stop();
                    if (animation != R.drawable.death) {
                        if (player.getHp() > 0) {
                            runAnimation(R.drawable.idle);
                        } else {
                            this.stop();
                            runAnimation(R.drawable.death);
                            updatePlayerInfo();
                        }
                    }
                }
            }
        };
        cad.stop();
        playerAnimationView.setImageDrawable(cad);
        cad.start();
        return cad.getTotalDuration();
    }

    private void scheduleAnim(Integer waitPlayerAnimEnd, Integer animationId) {
        Handler animHandler = new Handler();
        animHandler.postDelayed(() -> runAnimation(animationId), waitPlayerAnimEnd);
    }

    @Override
    public void onBackPressed() {
        if (enemyEncountered.getHp() > 0 && player.getHp() > 0) {
            Integer losingCryptoCoins = (int) player.getCryptoCoins() / 10;
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.flee_confirm_layout);
            Button confirmFlee = dialog.findViewById(R.id.confirmFlee);
            confirmFlee.setOnClickListener(view -> {
                player.setCryptoCoins(player.getCryptoCoins() - losingCryptoCoins);
                dialog.dismiss();
                Intent intent = new Intent(Fight.this, Menu.class);
                intent.putExtra("player", player);
                startActivity(intent);
                finish();
            });
            Button stopFlee = dialog.findViewById(R.id.stopFlee);
            stopFlee.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } else {
            new ActivityLauncher().ActivityLauncher(this, Menu.class, player);
        }
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

