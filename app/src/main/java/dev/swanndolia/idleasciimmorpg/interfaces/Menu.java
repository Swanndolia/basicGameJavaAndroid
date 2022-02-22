package dev.swanndolia.idleasciimmorpg.interfaces;

import androidx.annotation.ColorInt;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import android.app.Activity;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.MutableContextWrapper;
import android.content.SharedPreferences;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.transition.Explode;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.SeekBar;
import android.widget.TextView;

import com.google.android.material.slider.Slider;

import org.w3c.dom.Text;

import dev.swanndolia.idleasciimmorpg.R;
import dev.swanndolia.idleasciimmorpg.characters.Player;
import dev.swanndolia.idleasciimmorpg.tools.music.BackgroundMusicService;

public class Menu extends AppCompatActivity {

    AlertDialog dialog;
    Player player;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Bundle bundle = this.getIntent().getExtras();
        player = (Player) bundle.getSerializable("player");

        setContentView(R.layout.activity_menu);

        final Button inventoryBtn = findViewById(R.id.inventoryBtn);
        final Button exploreBtn = findViewById(R.id.exploreBtn);
        final ImageButton settingsBtn = findViewById(R.id.settingsBtn);

        inventoryBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Inventory.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
        });
        exploreBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Menu.this, Explore.class);
                intent.putExtra("player", player);
                startActivity(intent);
            }
        });
        settingsBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(Menu.this);
                builder.setTitle("Customize your settings here !");
                builder.setView(generateSettingsOverlay(builder));
                dialog = builder.create();
                dialog.show();
            }
        });
    }

    public LinearLayout generateSettingsOverlay(AlertDialog.Builder builder) {
        LinearLayout settingsOverlay = new LinearLayout(this);
        settingsOverlay.setOrientation(LinearLayout.VERTICAL);
        LinearLayout soundSettingsLayout = new LinearLayout(this);
        TextView soundTextView = new TextView(this);
        soundTextView.setText("Volume: ");
        SeekBar soundSeekBar = new SeekBar(this);
        soundSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onStopTrackingTouch(SeekBar arg0) {
            }

            @Override
            public void onStartTrackingTouch(SeekBar arg0) {

            }

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromTouch) {
                //send command to music service somehow
            }
        });
        soundSettingsLayout.addView(soundTextView);
        soundSettingsLayout.addView(soundSeekBar);
        settingsOverlay.addView(soundSettingsLayout);

        Button disconnectBtn = new Button(this);
        disconnectBtn.setText("DISCONNECT");
        disconnectBtn.setBackgroundColor(ContextCompat.getColor(this, com.google.android.material.R.color.design_default_color_error));
        disconnectBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences sharedPreferences = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();
                editor.putBoolean("stayLogin", false);
                editor.apply();
                dialog.dismiss();
                Intent intent = new Intent(Menu.this, Login.class);
                startActivity(intent);
                finish();
            }
        });
        TextView discordTextView = new TextView(this);
        discordTextView.setText("discord ic");
        TextView githubTextView = new TextView(this);
        githubTextView.setText("github là");
        TextView settings1TextView = new TextView(this);
        settings1TextView.setText("jsp encore ic");
        TextView settings2TextView = new TextView(this);
        settings2TextView.setText("jsp la nn plus");
        TextView settings3TextView = new TextView(this);
        settings3TextView.setText("et la nn plus");

        settingsOverlay.addView(discordTextView);
        settingsOverlay.addView(githubTextView);
        settingsOverlay.addView(settings1TextView);
        settingsOverlay.addView(settings2TextView);
        settingsOverlay.addView(settings3TextView);


        settingsOverlay.addView(disconnectBtn);
        return settingsOverlay;
    }

    @Override
    public void onBackPressed() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Are you sure you want to close the game ?");
        builder.setPositiveButton(android.R.string.ok,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                        System.exit(0);
                    }
                });
        builder.setNegativeButton(android.R.string.cancel,
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.dismiss();
                    }
                });
        AlertDialog dialog = builder.create();
        dialog.show();
    }
}