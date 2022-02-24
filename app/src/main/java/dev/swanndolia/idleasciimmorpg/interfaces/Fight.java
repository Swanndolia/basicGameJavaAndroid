package dev.swanndolia.idleasciimmorpg.interfaces;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
import java.util.HashMap;
import java.util.Random;
import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.DefaultCharacter;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.items.weapons.special.SpecialAmmunition;
import dev.swanndolia.idleasciimmorpg.items.weapons.ultimate.UltimateAmmunition;
import dev.swanndolia.idleasciimmorpg.location.fields.GenerateEnemy;
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
            basicAttackBtn.setText(basicWeapon.getName() + " " + basicWeapon.getDamage() + " dmg");
            basicAttackBtn.setOnClickListener(v -> enemyAttackStep(playerAttackStep(basicWeapon)));
        }
        if (specialWeapon != null) {
            specialAttackBtn.setText(specialWeapon.getName() + " " + specialWeapon.getDamage() + " dmg");
            specialAttackBtn.setOnClickListener(v -> {
                if (player.getInventory().contains(new SpecialAmmunition().SpecialAmmunition())) {
                    enemyAttackStep(playerAttackStep(specialWeapon));
                } else {
                    Toast.makeText(Fight.this, "You don't have any special ammunition", Toast.LENGTH_SHORT).show();
                }
            });
        }
        if (ultimateWeapon != null) {
            ultimateAttackBtn.setText(ultimateWeapon.getName() + " " + ultimateWeapon.getDamage() + " dmg");
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
        playerTextView.setText(player.getName() + " lvl " + player.getLevel() + " HP: " + player.getHp());
        if (player.getHp() <= 0) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.player_dead_layout);
            Integer lostExp = (int) player.getExp() / 10;
            Integer lostCryptoCoins = (int) player.getCryptoCoins() / 5;
            player.setExp(player.getExp() - lostExp);
            player.setCryptoCoins(player.getCryptoCoins() - lostCryptoCoins);
            TextView deadPlayerText = (TextView) dialog.findViewById(R.id.deadPlayerText);
            deadPlayerText.setText(player.getName() + " has been killed by a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
            TextView lostCryptoCoinsText = (TextView) dialog.findViewById(R.id.lostCryptoCoinsText);
            lostCryptoCoinsText.setText("You've lost " + lostCryptoCoins + " Crypto-coins");
            TextView lostExpTextView = (TextView) dialog.findViewById(R.id.lostExpText);
            lostExpTextView.setText("You've lost " + lostExp + " exp");
            dialog.show();
            dialog.setOnCancelListener(
                    dialogInterface -> onBackPressed()
            );
        }
        player.savePlayer();
    }

    private void updateEnemyInfo() {
        enemyHp.setProgress(enemyEncountered.getHp());
        enemyTextView.setText(enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel() + " HP: " + enemyEncountered.getHp());
        if (enemyEncountered.getHp() <= 0) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.enemy_drops_layout);
            player.setExp(player.getExp() + enemyEncountered.getExpReward());
            player.checkLevelUp();
            expProgressBar.setProgress(player.getExp());
            expProgressBar.setMax(player.getNextLevelExp());
            final Integer[] totalValue = {0};

            LinearLayout itemListHolder = (LinearLayout) dialog.findViewById(R.id.itemListHolder);
            TextView enemyKilledTitle = (TextView) dialog.findViewById(R.id.enemyKilledTitle);
            enemyKilledTitle.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());

            Button backMenu = (Button) dialog.findViewById(R.id.backToMenuBtn);
            backMenu.setOnClickListener(view -> {
                player.addInventory(enemyEncountered.getInventory());
                onBackPressed();
            });

            Button exploreMore = (Button) dialog.findViewById(R.id.exploreAgainBtn);
            exploreMore.setOnClickListener(view -> {
                player.addInventory(enemyEncountered.getInventory());
                Intent intent = new Intent(Fight.this, Fight.class);
                intent.putExtra("player", player);
                startActivity(intent);
                finish();
            });

            Button sellAllLoot = (Button) dialog.findViewById(R.id.sellAllLootBtn);
            Button takeAllLoot = (Button) dialog.findViewById(R.id.takeAllLootBtn);
            takeAllLoot.setOnClickListener(view -> {
                player.addInventory(enemyEncountered.getInventory());
                int amountToRemove = enemyEncountered.getInventory().size();
                enemyEncountered.setInventory(new HashMap<>());
                itemListHolder.removeViews(0, amountToRemove);
                takeAllLoot.setVisibility(View.GONE);
                sellAllLoot.setVisibility(View.GONE);
            });

            Integer finalTotalValue = totalValue[0];
            sellAllLoot.setOnClickListener(view -> {
                player.addCryptoCoins(finalTotalValue);
                int amountToRemove = enemyEncountered.getInventory().size();
                enemyEncountered.setInventory(new HashMap<>());
                itemListHolder.removeViews(0, amountToRemove);
                sellAllLoot.setVisibility(View.GONE);
                takeAllLoot.setVisibility(View.GONE);
            });

            for (Item item : enemyEncountered.getInventory()) {
                totalValue[0] += item.getSellValue();
                // used to setup double buttons don't touch this fucking shit stay in loop to avoid already have parent
                LayoutInflater layoutInflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                LinearLayout doubleButton = (LinearLayout) layoutInflater.inflate(R.layout.take_or_sell_buttons, null);

                Button takeOneItem = doubleButton.findViewById(R.id.buttonLeft);
                Button sellOneItem = doubleButton.findViewById(R.id.buttonRight);
                takeOneItem.setText("Take:" + item.getName());
                takeOneItem.setOnClickListener(view -> {
                    totalValue[0] -= item.getSellValue();
                    enemyEncountered.removeInventory(item);
                    player.addInventory(item);
                    itemListHolder.removeView(doubleButton);
                    if(itemListHolder.getChildCount() == 0){
                        sellAllLoot.setVisibility(View.GONE);
                        takeAllLoot.setVisibility(View.GONE);
                    }
                });
                sellOneItem.setText("Sell for: " + item.getSellValue());
                sellOneItem.setOnClickListener(view -> {
                    totalValue[0] -= item.getSellValue();
                    enemyEncountered.removeInventory(item);
                    player.addCryptoCoins(item.getSellValue());
                    itemListHolder.removeView(doubleButton);
                    if(itemListHolder.getChildCount() == 0){
                        sellAllLoot.setVisibility(View.GONE);
                        takeAllLoot.setVisibility(View.GONE);
                    }
                });
                itemListHolder.addView(doubleButton);
            }
            dialog.setCancelable(false);
            dialog.show();
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
                    updatePlayerInfo();
                } else {
                    basicAttackBtn.setEnabled(false);
                    specialAttackBtn.setEnabled(false);
                    ultimateAttackBtn.setEnabled(false);
                    updateEnemyInfo();
                }
            }

            @Override
            public void onAnimationFinish() {
                if (animation != R.drawable.idle) {
                    this.stop();
                    if (animation != R.drawable.death) {
                        if (player.getHp() > 0) {
                            runAnimation(R.drawable.idle);
                        } else {
                            updatePlayerInfo();
                            runAnimation(R.drawable.death);
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
            if (player.getHp() <= 0) {
                player.setHp(player.getMaxHp());
            }
            Intent intent = new Intent(Fight.this, Menu.class);
            intent.putExtra("player", player);
            startActivity(intent);
            finish();
        }
    }
    private void makePlayerAlwaysUpdated() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        databaseReference.child("users").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                player = snapshot.child(player.getName()).child("player").getValue(Player.class);
                updatePlayerInfo();
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });
    }
}

