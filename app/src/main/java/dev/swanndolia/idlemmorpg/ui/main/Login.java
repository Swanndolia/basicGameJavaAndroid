package dev.swanndolia.idlemmorpg.ui.main;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;

public class Login extends AppCompatActivity {

    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    CheckBox stayLoginCheckBox;
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    Boolean isLogged = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Bundle bundle = this.getIntent().getExtras();
        if (bundle != null) {
            Boolean autologin = (Boolean) bundle.getSerializable("autologin");
            String usernameFromPref = (String) bundle.getSerializable("username");
            String passwordFromPref = (String) bundle.getSerializable("password");
            if (autologin) {
                loginFirebaseAndGetPlayer(usernameFromPref, passwordFromPref);
                if (isLogged) {
                    finish();
                }

            }

        }

        setContentView(R.layout.activity_login);

        EditText username = findViewById(R.id.username);
        EditText password = findViewById(R.id.password);
        Button loginBtn = findViewById(R.id.loginBtn);
        TextView registerNowBtn = findViewById(R.id.registerNowBtn);
        stayLoginCheckBox = findViewById(R.id.stayLoginCheckBox);

        loginBtn.setOnClickListener(v -> {
            String passwordTxt = password.getText().toString();
            String usernameTxt = username.getText().toString();

            if (usernameTxt.isEmpty() || passwordTxt.isEmpty()) {
                Toast.makeText(Login.this, "Please enter a valid email and password", Toast.LENGTH_SHORT).show();
            } else {
                loginFirebaseAndGetPlayer(passwordTxt, usernameTxt);
            }
        });

        registerNowBtn.setOnClickListener(v -> startActivity(new Intent(Login.this, Register.class)));
    }

    public void loginFirebaseAndGetPlayer(String passwordTxt, String usernameTxt) {
        databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (snapshot.hasChild(usernameTxt)) {
                    String getPassword = snapshot.child(usernameTxt).child("password").getValue(String.class);
                    if (passwordTxt.equals(getPassword)) {
                        Player player = snapshot.child(usernameTxt).child("player").getValue(Player.class);
                        Intent intent = new Intent(Login.this, Menu.class);
                        intent.putExtra("player", player);
                        if (stayLoginCheckBox.isChecked()) {
                            sharedPreferences = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("username", usernameTxt);
                            editor.putString("password", passwordTxt);
                            editor.putBoolean("stayLogin", true);
                            editor.apply();
                        }
                        startActivity(intent);
                        isLogged = true;
                        finish();
                    } else {
                        Toast.makeText(Login.this, "Password incorrect", Toast.LENGTH_SHORT).show();
                    }
                } else {
                    Toast.makeText(Login.this, "Email don't exist, create an account first", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(Login.this, "Error with database connexion", Toast.LENGTH_SHORT).show();
            }
        });
    }
}