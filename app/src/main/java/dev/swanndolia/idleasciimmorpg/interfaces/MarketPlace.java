package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.items.Item;
import dev.swanndolia.idleasciimmorpg.tools.player.ForceSaveInventoryList;
import dev.swanndolia.idleasciimmorpg.tools.player.ListToMapInventory;

public class MarketPlace extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;
    LinearLayout marketItemListHolder;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        setContentView(R.layout.activity_marketplace);
        databaseReference = FirebaseDatabase.getInstance().getReference();

        Button sellOnMarket = (Button) findViewById(R.id.sellItemOnMarket);
        Button buyOnMarket = (Button) findViewById(R.id.buyItemOnMarket);
        marketItemListHolder = (LinearLayout) findViewById(R.id.marketItemListHolder);
        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());

        fetchItemOnMarket();

        sellOnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marketItemListHolder.removeAllViews();
                sellOnMarket.setVisibility(View.GONE);
                buyOnMarket.setVisibility(View.VISIBLE);
                displayInventoryForSale(new ListToMapInventory().ListToMapInventory(player.getInventory()));
            }
        });
        buyOnMarket.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                marketItemListHolder.removeAllViews();
                buyOnMarket.setVisibility(View.GONE);
                sellOnMarket.setVisibility(View.VISIBLE);
                fetchItemOnMarket();
            }
        });
    }

    public void fetchItemOnMarket() {
        databaseReference.child("market").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                List<Item> itemList = new ArrayList<Item>();

                for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
                    Item item = dataSnapshot.getValue(Item.class);
                    itemList.add(item);
                }
                displayInventoryForSale(new ListToMapInventory().ListToMapInventory(itemList));
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void displayMarketToBuy(Map<Item, Integer> listToMapInventory) {
    }

    public void displayInventoryForSale(Map<Item, Integer> listToMapInventory) {
        for (Map.Entry<Item, Integer> entry : listToMapInventory.entrySet()) {
            if (!entry.getKey().equals(new ForceSaveInventoryList().ForceSaveInventoryList())) {
                Button itemListBtn = new Button(this);
                itemListBtn.setTextSize(20);
                itemListBtn.setText(entry.getKey().getName() + " x " + entry.getValue());
                itemListBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        
                    }
                });
                marketItemListHolder.addView(itemListBtn);
            }
        }
    }
}
