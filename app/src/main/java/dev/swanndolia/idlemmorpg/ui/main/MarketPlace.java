package dev.swanndolia.idlemmorpg.ui.main;

import android.app.Dialog;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;
import java.util.Map;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.items.Item;
import dev.swanndolia.idlemmorpg.tools.activity.ActivityLauncher;
import dev.swanndolia.idlemmorpg.tools.firebase.GetRgbFromRarity;
import dev.swanndolia.idlemmorpg.tools.market.ItemHolder;
import dev.swanndolia.idlemmorpg.tools.player.ForceSaveInventoryList;
import dev.swanndolia.idlemmorpg.tools.player.ListToMapInventory;

public class MarketPlace extends AppCompatActivity {
    Player player;
    LinearLayout marketItemListHolder;
    DatabaseReference databaseReference;
    Button sellOnMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_marketplace);
        sellOnMarket = findViewById(R.id.sellItemOnMarket);
        Button buyOnMarket = findViewById(R.id.buyItemOnMarket);

        marketItemListHolder = findViewById(R.id.marketItemListHolder);

        fetchItemOnMarket();

        sellOnMarket.setOnClickListener(view -> {
            marketItemListHolder.removeAllViews();
            sellOnMarket.setVisibility(View.GONE);
            buyOnMarket.setVisibility(View.VISIBLE);
            displayInventoryForSale(new ListToMapInventory().ListToMapInventory(player.getInventory()));
        });
        buyOnMarket.setOnClickListener(view -> {
            marketItemListHolder.removeAllViews();
            buyOnMarket.setVisibility(View.GONE);
            sellOnMarket.setVisibility(View.VISIBLE);
            fetchItemOnMarket();
        });
    }

    public void fetchItemOnMarket() {

        databaseReference.child("market").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //dumbass check to update market list view only if player is checking market and not inventory
                if (sellOnMarket.getVisibility() == View.VISIBLE) {
                    marketItemListHolder.removeAllViews();
                    for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                        ItemHolder itemHolder = dataSnapshot.getValue(ItemHolder.class);
                        Item item = itemHolder.getItem();
                        if (!itemHolder.getOwnerName().equals(player.getName())) {
                            Button marketItemListBtn = new Button(MarketPlace.this);
                            marketItemListBtn.setTextSize(20);
                            marketItemListBtn.setTextColor(new GetRgbFromRarity().getRgbFromRarity(item.getRarity()));
                            marketItemListBtn.setText(MessageFormat.format("{0} x {1}  Buy for: {2}", item.getName(), itemHolder.getAmount(), itemHolder.getAmount() * itemHolder.getPrice()));
                            marketItemListBtn.setOnClickListener(view -> {
                                if (player.getCoins() >= itemHolder.getPrice() * itemHolder.getAmount()) {
                                    player.setCoins(player.getCoins() - itemHolder.getPrice() * itemHolder.getAmount());
                                    player.addItemsToInventory(item, itemHolder.getAmount());
                                    dataSnapshot.getRef().removeValue();
                                    databaseReference.child("users").child(itemHolder.getOwnerName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                        @Override
                                        public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                            Player owner = snapshot1.child("player").getValue(Player.class);
                                            owner.addCoins(itemHolder.getPrice() * itemHolder.getAmount());
                                            owner.savePlayer();
                                        }

                                        @Override
                                        public void onCancelled(@NonNull DatabaseError error) {

                                        }
                                    });
                                } else {
                                    Toast.makeText(MarketPlace.this, "You don't have enough money", Toast.LENGTH_SHORT).show();
                                }
                            });
                            marketItemListHolder.addView(marketItemListBtn);
                        }
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    public void displayInventoryForSale(Map<Item, Integer> listToMapInventory) {
        for (Map.Entry<Item, Integer> entry : listToMapInventory.entrySet()) {
            if (!entry.getKey().equals(new ForceSaveInventoryList())) {
                Button inventoryItemListBtn = new Button(this);
                inventoryItemListBtn.setTextSize(20);
                inventoryItemListBtn.setTextColor(new GetRgbFromRarity().getRgbFromRarity(entry.getKey().getRarity()));
                inventoryItemListBtn.setText(MessageFormat.format("{0} x {1}", entry.getKey().getName(), entry.getValue()));
                inventoryItemListBtn.setOnClickListener(view -> {
                    final Integer[] amountToSell = {entry.getValue()};
                    Dialog dialog = new Dialog(MarketPlace.this);
                    dialog.setContentView(R.layout.overlay_add_item_marketplace);
                    Button confirmBtn = dialog.findViewById(R.id.confirmBtn);
                    Button plusBtn = dialog.findViewById(R.id.plusBtn);
                    Button minusBtn = dialog.findViewById(R.id.minusBtn);
                    Button itemSelectBtn = dialog.findViewById(R.id.itemSelectBtn);
                    EditText sellPriceField = dialog.findViewById(R.id.sellPriceField);
                    itemSelectBtn.setText(MessageFormat.format("{0} x {1}", entry.getKey().getName(), amountToSell[0]));

                    confirmBtn.setOnClickListener(view1 -> {
                        String priceString = sellPriceField.getText().toString();
                        if (!priceString.equals("")) {
                            Integer sellPrice = Integer.parseInt(priceString);
                            sendItemToMarketPlace(entry.getKey(), sellPrice, amountToSell[0]);
                            marketItemListHolder.removeView(inventoryItemListBtn);
                            dialog.dismiss();
                        } else {
                            Toast.makeText(MarketPlace.this, "Please set a valid price", Toast.LENGTH_SHORT).show();
                        }
                    });
                    plusBtn.setOnClickListener(view12 -> {
                        if (amountToSell[0] < entry.getValue()) {
                            amountToSell[0] += 1;
                            itemSelectBtn.setText(MessageFormat.format("{0} x {1}", entry.getKey().getName(), amountToSell[0]));
                        }
                    });
                    minusBtn.setOnClickListener(view13 -> {
                        if (amountToSell[0] > 1) {
                            amountToSell[0] -= 1;
                            itemSelectBtn.setText(MessageFormat.format("{0} x {1}", entry.getKey().getName(), amountToSell[0]));
                        }
                    });
                    itemSelectBtn.setOnClickListener(view14 -> dialog.dismiss());

                    dialog.show();
                });
                marketItemListHolder.addView(inventoryItemListBtn);
            }
        }
    }

    private void sendItemToMarketPlace(Item item, Integer sellPrice, Integer amountToSell) {
        databaseReference.child("market").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                DatabaseReference ref = databaseReference.child("market").push();
                ItemHolder itemHolder = new ItemHolder(item, amountToSell, sellPrice, player.getName());
                ref.setValue(itemHolder);
                player.removeItemFromInventory(item, amountToSell);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        new ActivityLauncher(this, Menu.class, player);
    }

}