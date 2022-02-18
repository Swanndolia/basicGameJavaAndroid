package dev.swanndolia.idleasciimmorpg.interfaces;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Menu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        Player player = (Player) bundle.getSerializable("player");

        setContentView(R.layout.activity_menu);

        final Button inventoryBtn = findViewById(R.id.inventoryBtn);
        final Button exploreBtn = findViewById(R.id.exploreBtn);

        inventoryBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this,Inventory.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
        });
        exploreBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Explore.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
        });
    }

}// check how it work after clicking register creat player class on registyer and save it in firebase + get player class when clicking logging, display inventory with items on click ui + display items details on clik on it