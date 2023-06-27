package dev.swanndolia.idlemmorpg.ui.main;

import android.app.Dialog;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.content.res.AppCompatResources;

import java.text.MessageFormat;
import java.util.Random;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.DefaultEncounter;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.enemy.GenerateEnemy;
import dev.swanndolia.idlemmorpg.enemy.GetEnemyListFromZone;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.items.weapons.ranged.Arrow;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.animations.CustomAnimationDrawableNew;
import dev.swanndolia.idlemmorpg.ui.overlays.EnemyKilledOverlay;

public class Fight extends AppCompatActivity {
    ImageView playerAnimationView;
    TextView playerTextView;
    TextView enemyTextView;
    Button meleeAttackBtn;
    Button rangedAttackBtn;
    Button fleeBtn;
    Button bagBtn;
    DefaultEncounter enemyEncountered;
    Player player;
    ProgressBar playerHp;
    ProgressBar enemyHp;
    String location;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        location = (String) bundle.getSerializable("location");
        player = (Player) bundle.getSerializable("player");
        enemyEncountered = new GenerateEnemy(new GetEnemyListFromZone(location, player).getEnemyList()).getRandomEnemy();
        setContentView(R.layout.activity_fight);

        playerAnimationView = findViewById(R.id.playerAnim);
        playerTextView = findViewById(R.id.playerInfo);
        playerHp = findViewById(R.id.playerHp);
        enemyTextView = findViewById(R.id.enemyInfo);
        enemyHp = findViewById(R.id.enemyHp);
        meleeAttackBtn = findViewById(R.id.meleeWeaponBtn);
        rangedAttackBtn = findViewById(R.id.rangedWeaponBtn);
        fleeBtn = findViewById(R.id.fleeBtn);
        bagBtn = findViewById(R.id.bagBtn);
        playerHp.setMax(player.getMaxHp());
        playerHp.setProgress(player.getHp());
        updatePlayerInfo();

        enemyHp.setMax(enemyEncountered.getHp());
        enemyHp.setProgress(enemyEncountered.getHp());
        updateEnemyInfo();

        runAnimation(R.drawable.idle);

        Item meleeWeapon = player.getEquippedItem("Melee Weapon");
        Item rangedWeapon = player.getEquippedItem("Ranged Weapon");

        fleeBtn.setOnClickListener(v -> onBackPressed());

        if (meleeWeapon != null) {
            meleeAttackBtn.setText(MessageFormat.format("{0} ({1})", meleeWeapon.getName(), meleeWeapon.getDamage()));
            meleeAttackBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(this, meleeWeapon.getIcon()), null, null, null);
            meleeAttackBtn.setOnClickListener(v -> enemyAttackStep(playerAttackStep(meleeWeapon)));
        }
        if (rangedWeapon != null) {
            rangedAttackBtn.setText(MessageFormat.format("{0} ({1})", rangedWeapon.getName(), rangedWeapon.getDamage()));
            rangedAttackBtn.setCompoundDrawablesRelativeWithIntrinsicBounds(AppCompatResources.getDrawable(this, rangedWeapon.getIcon()), null, null, null);
            rangedAttackBtn.setOnClickListener(v -> {
                if (player.getInventory().contains(new Arrow())) {
                    enemyAttackStep(playerAttackStep(rangedWeapon));
                } else {
                    Toast.makeText(Fight.this, "You don't have any arrow", Toast.LENGTH_SHORT).show();
                }
            });
        }

    }

    private void updatePlayerInfo() {
        playerHp.setProgress(player.getHp());
        playerTextView.setText(MessageFormat.format("{0} lvl {1} HP: {2}", player.getName(), player.getLevel(), player.getHp()));
        if (player.getHp() <= 0) {
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.overlay_player_dead);
            Integer lostExp = player.getExp() / 10;
            Integer lostCryptoCoins = player.getCoins() / 5;
            player.setExp(player.getExp() - lostExp);
            player.setCoins(player.getCoins() - lostCryptoCoins);
            TextView deadPlayerText = dialog.findViewById(R.id.deadPlayerText);
            deadPlayerText.setText(MessageFormat.format("{0} has been killed by a {1} lvl {2}", player.getName(), enemyEncountered.getName(), enemyEncountered.getLevel()));
            TextView lostCryptoCoinsText = dialog.findViewById(R.id.lostCryptoCoinsText);
            lostCryptoCoinsText.setText(MessageFormat.format("You''ve lost {0} Crypto-coins", lostCryptoCoins));
            TextView lostExpTextView = dialog.findViewById(R.id.lostExpText);
            lostExpTextView.setText(MessageFormat.format("You''ve lost {0} exp", lostExp));
            player.setHp(player.getMaxHp());
            dialog.show();
            dialog.setOnCancelListener(
                    dialogInterface -> new ActivityLauncher(this, Menu.class, player)
            );
        }
        player.savePlayer();
    }

    private void updateEnemyInfo() {
        enemyHp.setProgress(enemyEncountered.getHp());
        enemyTextView.setText(MessageFormat.format("{0} lvl {1} HP: {2}", enemyEncountered.getName(), enemyEncountered.getLevel(), enemyEncountered.getHp()));
        if (enemyEncountered.getHp() <= 0) {
            new EnemyKilledOverlay(Fight.this, player, enemyEncountered, location);
            player.addExp(enemyEncountered.getExpReward());
            player.savePlayer();
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

    public Integer runAnimation(Integer animation) {//todo remove animation from fight class for cleanup
        CustomAnimationDrawableNew cad = new CustomAnimationDrawableNew((AnimationDrawable) AppCompatResources.getDrawable(this, animation)) {
            @Override
            public void onAnimationStart() {
                if (animation == R.drawable.idle) {
                    meleeAttackBtn.setEnabled(true);
                    rangedAttackBtn.setEnabled(true);
                } else {
                    meleeAttackBtn.setEnabled(false);
                    rangedAttackBtn.setEnabled(false);
                }
            }

            @Override
            public void onAnimationFinish() {
                if (animation == R.drawable.attack_crit || animation == R.drawable.attack) {
                    updateEnemyInfo();
                }
                if (animation == R.drawable.hurt) {
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
    public void onBackPressed() {//todo improve this
        if (enemyEncountered.getHp() > 0 && player.getHp() > 0) {
            Integer losingCryptoCoins = player.getCoins() / 10;
            Dialog dialog = new Dialog(this);
            dialog.setContentView(R.layout.overlay_flee_confirm);
            Button confirmFlee = dialog.findViewById(R.id.confirmFlee);
            confirmFlee.setOnClickListener(view -> {
                player.setCoins(player.getCoins() - losingCryptoCoins);
                dialog.dismiss();
                new ActivityLauncher(this, Menu.class, player);
            });
            Button stopFlee = dialog.findViewById(R.id.stopFlee);
            stopFlee.setOnClickListener(view -> dialog.dismiss());
            dialog.show();
        } else {
            new ActivityLauncher(this, Menu.class, player);
        }
    }
}

