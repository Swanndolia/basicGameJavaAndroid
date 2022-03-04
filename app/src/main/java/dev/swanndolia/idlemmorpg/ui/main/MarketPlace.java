package dev.swanndolia.idlemmorpg.ui.main;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
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
import dev.swanndolia.idlemmorpg.tools.firebase.GetRgbFromRarity;
import dev.swanndolia.idlemmorpg.tools.market.ItemHolder;
import dev.swanndolia.idlemmorpg.tools.player.ForceSaveInventoryList;
import dev.swanndolia.idlemmorpg.tools.player.ListToMapInventory;

public class MarketPlace extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;
    LinearLayout marketItemListHolder;
    DatabaseReference databaseReference;
    Button sellOnMarket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        databaseReference = FirebaseDatabase.getInstance().getReference();
        setContentView(R.layout.activity_marketplace);
        sellOnMarket = (Button) findViewById(R.id.sellItemOnMarket);
        Button buyOnMarket = (Button) findViewById(R.id.buyItemOnMarket);

        marketItemListHolder = (LinearLayout) findViewById(R.id.marketItemListHolder);
        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());

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

                        Button marketItemListBtn = new Button(MarketPlace.this);
                        marketItemListBtn.setTextSize(20);
                        marketItemListBtn.setTextColor(new GetRgbFromRarity().GetRgbFromRarity(item.getRarity()));
                        marketItemListBtn.setText(MessageFormat.format("{0} x {1}  Buy for: {2}", item.getName(), itemHolder.getAmount(), itemHolder.getAmount() * itemHolder.getPrice()));
                        marketItemListBtn.setOnClickListener(view -> {
                            if (player.getCoins() >= itemHolder.getPrice() * itemHolder.getAmount()) {
                                player.setCoins(player.getCoins() - itemHolder.getPrice() * itemHolder.getAmount());
                                player.addInventory(item, itemHolder.getAmount());
                                dataSnapshot.getRef().removeValue();
                                databaseReference.child("users").child(itemHolder.getOwnerName()).addListenerForSingleValueEvent(new ValueEventListener() {
                                    @Override
                                    public void onDataChange(@NonNull DataSnapshot snapshot1) {
                                        Player owner = snapshot1.child("player").getValue(Player.class);
                                        owner.addCoins(itemHolder.getPrice() * itemHolder.getAmount());
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
                inventoryItemListBtn.setTextColor(new GetRgbFromRarity().GetRgbFromRarity(entry.getKey().getRarity()));
                inventoryItemListBtn.setText(MessageFormat.format("{0} x {1}", entry.getKey().getName(), entry.getValue()));
                inventoryItemListBtn.setOnClickListener(view -> {
                    final Integer[] amountToSell = {entry.getValue()};
                    Dialog dialog = new Dialog(MarketPlace.this);
                    dialog.setContentView(R.layout.overlay_add_item_marketplace);
                    Button confirmBtn = (Button) dialog.findViewById(R.id.confirmBtn);
                    Button plusBtn = (Button) dialog.findViewById(R.id.plusBtn);
                    Button minusBtn = (Button) dialog.findViewById(R.id.minusBtn);
                    Button itemSelectBtn = (Button) dialog.findViewById(R.id.itemSelectBtn);
                    EditText sellPriceField = (EditText) dialog.findViewById(R.id.sellPriceField);
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
                player.removeInventory(item, amountToSell);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(MarketPlace.this, Town.class);
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