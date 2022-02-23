package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.drawable.AnimationDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

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
    AlertDialog dialog;
    LinearLayout parentLayout;
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

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        String location = (String) bundle.getSerializable("location");
        enemyEncountered = new GenerateEnemy().GenerateEnemy(location, player);
        setContentView(R.layout.activity_fight);

        parentLayout = (LinearLayout) findViewById(R.id.parentLayout);
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
        updatePlayerInfos();

        enemyHp.setMax(enemyEncountered.getHp());
        enemyHp.setProgress(enemyEncountered.getHp());
        updateEnemyInfos();

        runAnimation(R.drawable.idle);

        Item basicWeapon = player.getEquippedItem("Basic Weapon");
        Item specialWeapon = player.getEquippedItem("Special Weapon");
        Item ultimateWeapon = player.getEquippedItem("Ultimate Weapon");

        if (basicWeapon != null) {
            basicAttackBtn.setText(basicWeapon.getName() + " " + basicWeapon.getDamage() + " dmg");
            basicAttackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    enemyAttackStep(playerAttackStep(basicWeapon));
                }
            });
        }
        if (specialWeapon != null) {
            specialAttackBtn.setText(specialWeapon.getName() + " " + specialWeapon.getDamage() + " dmg");
            specialAttackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (player.getInventory().contains(new SpecialAmmunition().SpecialAmmunition())) {
                        enemyAttackStep(playerAttackStep(specialWeapon));
                    } else {
                        Toast.makeText(Fight.this, "You don't have any special ammunition", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }
        if (ultimateWeapon != null) {
            ultimateAttackBtn.setText(ultimateWeapon.getName() + " " + ultimateWeapon.getDamage() + " dmg");
            ultimateAttackBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (player.getInventory().contains(new UltimateAmmunition().UltimateAmmunition())) {
                        enemyAttackStep(playerAttackStep(ultimateWeapon));
                    } else {
                        Toast.makeText(Fight.this, "You don't have any ultimate ammunition", Toast.LENGTH_SHORT).show();
                    }

                }
            });
        }
    }


    private void updatePlayerInfos() {
        playerHp.setProgress(player.getHp());
        playerTextView.setText(player.getName() + " lvl " + player.getLevel() + " HP: " + player.getHp());
        if (player.getHp() <= 0) {
            playerTextView.setText(player.getName() + " has been killed by a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
            playerKilledStep();
        }
        player.savePlayer();
    }

    private void playerKilledStep() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        LinearLayout deadPlayerLayout = new LinearLayout(this);
        deadPlayerLayout.setOrientation(LinearLayout.VERTICAL);

        Integer lostExp = (int) player.getExp() / 10;
        Integer lostCryptoCoins = (int) player.getCryptoCoins() / 5;
        player.setExp(player.getExp() - lostExp);
        player.setCryptoCoins(player.getCryptoCoins() - lostCryptoCoins);

        TextView deadTitleView = new TextView(this);
        deadTitleView.setText(player.getName() + " has been killed by a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
        deadTitleView.setTextSize(22);
        deadTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        deadTitleView.setTextColor(getColor(R.color.white));
        deadPlayerLayout.addView(deadTitleView);

        LinearLayout lostListLayout = new LinearLayout(this);
        lostListLayout.setOrientation(LinearLayout.VERTICAL);
        TextView lostCryptoCoinsTextView = new TextView(this);
        lostCryptoCoinsTextView.setTextColor(getColor(R.color.white));
        lostCryptoCoinsTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        lostCryptoCoinsTextView.setText("You've lost " + lostCryptoCoins + " Crypto-coins");
        TextView lostExpTextView = new TextView(this);
        lostExpTextView.setTextColor(getColor(R.color.white));
        lostExpTextView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        lostExpTextView.setText("You've lost " + lostExp + " exp");

        lostListLayout.addView(lostExpTextView);
        lostListLayout.addView(lostCryptoCoinsTextView);
        deadPlayerLayout.addView(lostListLayout);
        deadPlayerLayout.setBackgroundColor(getColor(com.google.android.material.R.color.design_default_color_error));

        builder.setView(deadPlayerLayout);
        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                player.setHp(player.getMaxHp());
                dialog.dismiss();
                onBackPressed();
            }
        });

        builder.show();
    }

    private void updateEnemyInfos() {
        enemyHp.setProgress(enemyEncountered.getHp());
        enemyTextView.setText(enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel() + " HP: " + enemyEncountered.getHp());
        if (enemyEncountered.getHp() <= 0) {
            enemyTextView.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
            enemyKilledStep();
        }
    }

    private void enemyKilledStep() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        dialog = builder.create();
        LinearLayout dropListLayout = new LinearLayout(this);
        dropListLayout.setOrientation(LinearLayout.VERTICAL);

        player.setExp(player.getExp() + enemyEncountered.getExpReward());
        player.checkLevelUp();
        TextView dropTitleView = new TextView(this);
        dropTitleView.setText("Good job " + player.getName() + " on killing a " + enemyEncountered.getName() + " lvl " + enemyEncountered.getLevel());
        dropTitleView.setTextSize(22);
        dropTitleView.setTextAlignment(View.TEXT_ALIGNMENT_CENTER);
        dropListLayout.addView(dropTitleView);
        final Integer[] totalValue = {0};

        for (Item item : enemyEncountered.getInventory()) {
            totalValue[0] += item.getSellValue();
            Button takeOneItem = new Button(this);
            Button sellOneItem = new Button(this);
            LinearLayout actionItemHolderLayout = new LinearLayout(this);

            takeOneItem.setText("Take:" + item.getName());
            takeOneItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalValue[0] -= item.getSellValue();
                    enemyEncountered.removeInventory(item);
                    player.addInventory(item);
                    dropListLayout.removeView(actionItemHolderLayout);

                }
            });

            sellOneItem.setText("Sell for: " + item.getSellValue());
            sellOneItem.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    totalValue[0] -= item.getSellValue();
                    enemyEncountered.removeInventory(item);
                    player.addCryptoCoins(item.getSellValue());
                    dropListLayout.removeView(actionItemHolderLayout);
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
                Integer amountToRemove = enemyEncountered.getInventory().size();
                enemyEncountered.setInventory(new HashMap<>());
                dropListLayout.removeViews(0, amountToRemove + 2);
            }
        });

        Button sellAllLoot = new Button(this);
        sellAllLoot.setText("Sell All Loot");
        Integer finalTotalValue = totalValue[0];
        sellAllLoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                player.addCryptoCoins(finalTotalValue);
                Integer ammountToRemove = enemyEncountered.getInventory().size();
                enemyEncountered.setInventory(new HashMap<>());
                dropListLayout.removeViews(0, ammountToRemove + 2);
            }
        });
        actionAllItemsLayout.addView(takeAllLoot);
        actionAllItemsLayout.addView(sellAllLoot);
        actionAllItemsLayout.setOrientation(LinearLayout.HORIZONTAL);
        LinearLayout exploreMoreOrBackMenu = new LinearLayout(this);
        exploreMoreOrBackMenu.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT));

        Button backMenu = new Button(this);
        backMenu.setText("Back to Menu");
        backMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        Button exploreMore = new Button(this);
        exploreMore.setText("Explore Again !");
        exploreMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Fight.this, Fight.class);
                intent.putExtra("player", player);
                startActivity(intent);
                finish();
            }
        });

        exploreMoreOrBackMenu.addView(backMenu);
        exploreMoreOrBackMenu.addView(exploreMore);
        exploreMoreOrBackMenu.setOrientation(LinearLayout.HORIZONTAL);

        dropListLayout.addView(actionAllItemsLayout);
        dropListLayout.addView(exploreMoreOrBackMenu);


        builder.setOnCancelListener(new DialogInterface.OnCancelListener() {
            @Override
            public void onCancel(DialogInterface dialogInterface) {
                dialog.dismiss();
                onBackPressed();
            }
        });
        builder.setView(dropListLayout);
        builder.show();
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
                    updatePlayerInfos();
                } else {
                    basicAttackBtn.setEnabled(false);
                    specialAttackBtn.setEnabled(false);
                    ultimateAttackBtn.setEnabled(false);
                    updateEnemyInfos();
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
                            updatePlayerInfos();
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
        animHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                runAnimation(animationId);
            }
        }, waitPlayerAnimEnd);
    }

    @Override
    public void onBackPressed() {
        if (enemyEncountered.getHp() > 0 && player.getHp() > 0) {
            Integer losingCryptoCoins = (int) player.getCryptoCoins() / 10;
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            AlertDialog dialog = builder.create();
            builder.setTitle("Are you sure you want to flee ?\nYou'll lose " + losingCryptoCoins + " Cryptocoins !");
            builder.setPositiveButton(android.R.string.ok,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            player.setCryptoCoins((player.getCryptoCoins() - losingCryptoCoins));
                            dialog.dismiss();
                            Intent intent = new Intent(Fight.this, Menu.class);
                            intent.putExtra("player", player);
                            startActivity(intent);
                            finish();
                        }
                    });
            builder.setNegativeButton(android.R.string.cancel,
                    new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            dialog.dismiss();
                        }
                    });

            builder.show();
        } else {
            if (dialog != null) {
                dialog.dismiss();
            }
            Intent intent = new Intent(Fight.this, Menu.class);
            intent.putExtra("player", player);
            startActivity(intent);
            finish();
        }
    }
}
