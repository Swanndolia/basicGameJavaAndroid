package dev.swanndolia.idlemmorpg.ui.main;

import android.os.Bundle;
import android.widget.ProgressBar;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;

public class Craft extends AppCompatActivity {
    Player player;
    ProgressBar expProgressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");
        makePlayerAlwaysUpdated();
        setContentView(R.layout.activity_craft);

        expProgressBar = (ProgressBar) findViewById(R.id.expProgressBar);
        expProgressBar.setProgress(player.getExp());
        expProgressBar.setMax(player.getNextLevelExp());
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