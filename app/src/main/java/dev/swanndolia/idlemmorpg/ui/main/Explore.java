package dev.swanndolia.idlemmorpg.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;

public class Explore extends AppCompatActivity {
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        setContentView(R.layout.activity_explore);

        final Button testAreaButton = findViewById(R.id.testAreaButton);
        testAreaButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this, Fight.class);
                intent.putExtra("player", player);
                intent.putExtra("location", "testArea");
                startActivity(intent);
                finish();
            }
        });
        final Button fieldsBtn = findViewById(R.id.fieldsBtn);
        fieldsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Explore.this, Fight.class);
                intent.putExtra("player", player);
                intent.putExtra("location", "fields");
                startActivity(intent);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(Explore.this, Menu.class);
        intent.putExtra("player", player);
        startActivity(intent);
        finish();
    }


}
