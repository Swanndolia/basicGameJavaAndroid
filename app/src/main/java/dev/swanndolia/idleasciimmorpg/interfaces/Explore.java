package dev.swanndolia.idleasciimmorpg.interfaces;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;

public class Explore extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        Player player = (Player) bundle.getSerializable("player");

        setContentView(R.layout.activity_explore);

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
}
