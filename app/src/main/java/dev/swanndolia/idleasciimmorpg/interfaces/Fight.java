package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import java.util.Random;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.DefaultCharacter;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.location.fields.GenerateEnemy;
import dev.swanndolia.idleasciimmorpg.tools.animations.CustomAnimationDrawableNew;

public class Fight extends AppCompatActivity {

    AlertDialog.Builder builderDeadPlayer;
    AlertDialog.Builder builderDeadEnemy;
    LinearLayout dropListLayout;
    LinearLayout deadPlayerLayout;
    LinearLayout parentLayout;
    LinearLayout buttonListLayout;
    ImageView animationView;
    TextView playerTextView;
    TextView enemyTextView;
    Button basicAttackBtn;
    Button specialAttackBtn;
    Button ultimateAttackBtn;
    DefaultCharacter enemyEncountered;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        String location = (String) bundle.getSerializable("location");

        setContentView(R.layout.activity_fight);

        builderDeadPlayer = new AlertDialog.Builder(this);
        builderDeadEnemy = new AlertDialog.Builder(this);
        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);

        dropListLayout = new LinearLayout(this);
        dropListLayout.setOrientation(LinearLayout.VERTICAL);
        deadPlayerLayout = new LinearLayout(this);
        deadPlayerLayout.setOrientation(LinearLayout.VERTICAL);
        buttonListLayout = new LinearLayout(this);
        buttonListLayout.setOrientation(LinearLayout.VERTICAL);
        animationView = new ImageView(this);
        playerTextView = new TextView(this);
        enemyTextView = new TextView(this);
        basicAttackBtn = new Button(this);
        specialAttackBtn = new Button(this);
        ultimateAttackBtn = new Button(this);
        enemyEncountered = new GenerateEnemy().GenerateEnemy(location, player);

        basicAttackBtn.setTextSize(20);
        basicAttackBtn.setText("No Basic Weapon ");
        buttonListLayout.addView(basicAttackBtn);
        specialAttackBtn.setTextSize(20);
        specialAttackBtn.setText("No Special Weapon ");
        buttonListLayout.addView(specialAttackBtn);
        ultimateAttackBtn.setTextSize(20);
        ultimateAttackBtn.setText("No Ultimate Weapon ");
        buttonListLayout.addView(ultimateAttackBtn);

        Item basicWeapon = player.getEquippedItem("Basic Weapon");
        Item specialWeapon = player.getEquippedItem("Special Weapon");
        Item ultimateWeapon = player.getEquippedItem("Ultimate Weapon");

        enemyTextView.setTextSize(20);
        enemyTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        enemyTextView.setLines(1);
        parentLayout.addView(enemyTextView);
        updateEnemyTextView();
        playerTextView.setTextSize(20);
        playerTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        playerTextView.setLines(1);
        parentLayout.addView(playerTextView);
        updatePlayerTextView();


        animationView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        parentLayout.addView(animationView);
        runAnimation(R.drawable.idle);

        parentLayout.addView(buttonListLayout);

        if (basicWeapon != null) {
            addClickListenerAttackBtn(basicWeapon, basicAttackBtn);
        }
        if (specialWeapon != null) {
            addClickListenerAttackBtn(specialWeapon, specialAttackBtn);
        }
        if (ultimateWeapon != null) {
            addClickListenerAttackBtn(ultimateWeapon, ultimateAttackBtn);
        }
    }

    private void updatePlayerTextView() {
        playerTextView.setText(player.getName() + " lvl " + player.getLevel() + " HP: " + player.getHp());
        if (player.getHp() <= 0) {
            enemyTextView.setText(player.getName() + " has been killed by a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
            playerKilledStep();
        }
        player.savePlayer();
    }

    private void playerKilledStep() {
        Integer lostExp = (int) player.getExp() / 10;
        Integer lostCryptoCoins = (int) player.getCryptoCoins() / 10;
        player.setExp(player.getExp() - lostExp);
        player.setCryptoCoins(player.getCryptoCoins() - lostCryptoCoins);

        parentLayout.removeView(buttonListLayout);
        TextView deadTitleView = new TextView(this);
        deadTitleView.setText(player.getName() + " has been killed by a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
        deadTitleView.setTextSize(22);
        deadTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        deadPlayerLayout.addView(deadTitleView);

        builderDeadPlayer.setView(deadPlayerLayout);
        AlertDialog deadDialog = builderDeadPlayer.create();

        LinearLayout lostListLayout = new LinearLayout(this);
        TextView lostCryptoCoinsTextView = new TextView(this);
        lostCryptoCoinsTextView.setText("You've lost " + lostCryptoCoins + " Crypto-coins");
        TextView lostExpTextView = new TextView(this);
        lostExpTextView.setText("You've lost " + lostExp + " exp");

        lostListLayout.setOrientation(LinearLayout.HORIZONTAL);
        lostListLayout.addView(lostExpTextView);
        lostListLayout.addView(lostCryptoCoinsTextView);
        deadPlayerLayout.addView(lostListLayout);

        System.out.println(deadPlayerLayout.getParent());
        deadDialog.show();
    }

    private void updateEnemyTextView() {
        enemyTextView.setText(enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel() + " HP: " + enemyEncountered.getHp());
        if (enemyEncountered.getHp() <= 0) {
            enemyTextView.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
            enemyKilledStep();
        }
    }

    private void enemyKilledStep() {
        player.setExp(player.getExp() + enemyEncountered.getExpReward());
        player.checkLevelUp();
        parentLayout.removeView(buttonListLayout);
        TextView dropTitleView = new TextView(this);
        dropTitleView.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
        dropTitleView.setTextSize(22);
        dropTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dropListLayout.addView(dropTitleView);
        Integer totalValue = 0;

        builderDeadEnemy.setView(dropListLayout);
        AlertDialog dialog = builderDeadEnemy.create();

        for (Item item : enemyEncountered.getInventory()) {
            totalValue += item.getSellValue();
            Button takeOneItem = new Button(this);
            Button sellOneItem = new Button(this);
            LinearLayout actionItemHolderLayout = new LinearLayout(this);

            sellOneItem.setText("Sell for: " + item.getSellValue());
            sellOneItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.addCryptoCoins(item.getSellValue());
                    dropListLayout.removeView(actionItemHolderLayout);
                    if (dropListLayout.getChildCount() == 2) {
                        dialog.dismiss();
                    }
                }
            });

            takeOneItem.setText("Take:" + item.getName());
            takeOneItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    player.addInventory(item);
                    dropListLayout.removeView(actionItemHolderLayout);
                    if (dropListLayout.getChildCount() == 2) {
                        dialog.dismiss();
                    }
                }
            });

            actionItemHolderLayout.addView(takeOneItem);
            actionItemHolderLayout.addView(sellOneItem);
            actionItemHolderLayout.setOrientation(LinearLayout.HORIZONTAL);
            dropListLayout.addView(actionItemHolderLayout);
        }

        LinearLayout actionAllItemsLayout = new LinearLayout(this);
        Button takeAllLoot = new Button(this);
        takeAllLoot.setText("Take All Loot");
        takeAllLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addInventory(enemyEncountered.getInventory());
                dialog.dismiss();
            }
        });

        Button sellAllLoot = new Button(this);
        sellAllLoot.setText("Sell All Loot");
        Integer finalTotalValue = totalValue;
        sellAllLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCryptoCoins(finalTotalValue);
                dialog.dismiss();
            }
        });

        actionAllItemsLayout.setOrientation(LinearLayout.HORIZONTAL);
        actionAllItemsLayout.addView(takeAllLoot);
        actionAllItemsLayout.addView(sellAllLoot);
        dropListLayout.addView(actionAllItemsLayout);

        dialog.show();
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
                    updatePlayerTextView();
                } else {
                    basicAttackBtn.setEnabled(false);
                    specialAttackBtn.setEnabled(false);
                    ultimateAttackBtn.setEnabled(false);
                    updateEnemyTextView();
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
                            updatePlayerTextView();
                            runAnimation(R.drawable.death);
                        }
                    }
                }
            }
        };
        cad.stop();
        animationView.setImageDrawable(cad);
        cad.start();
        return cad.getTotalDuration();
    }

    private void scheduleAnim(Integer waitPlayerAnimEnd, Integer animationId) {
        Handler animHandler = new Handler();
        animHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runAnimation(animationId);
            }
        }, waitPlayerAnimEnd);
    }

    private void addClickListenerAttackBtn(Item weapon, Button attackButton) {
        attackButton.setText(weapon.getName() + " " + weapon.getDamage() + " dmg");
        attackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                enemyAttackStep(playerAttackStep(weapon));
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Fight.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
    }
}
