package dev.swanndolia.idlemmorpg.ui.main;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.UUID;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;
import dev.swanndolia.idlemmorpg.tools.services.DisconnectPlayerAfterTaskKill;

public class Register extends AppCompatActivity {
    DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText userName = findViewById(R.id.userName);
        final EditText email = findViewById(R.id.email);
        final EditText password = findViewById(R.id.password);
        final EditText conPassword = findViewById(R.id.conPassword);

        final CheckBox stayLoginCheckBox = findViewById(R.id.stayLoginCheckBox);
        final Button registerBtn = findViewById(R.id.registerBtn);
        final TextView loginNowBtn = findViewById(R.id.loginNowBtn);

        registerBtn.setOnClickListener(v -> {

            final String usernameTxt = userName.getText().toString();
            final String emailTxt = email.getText().toString().replace(".", ",");
            final String passwordTxt = password.getText().toString();
            final String conPasswordTxt = conPassword.getText().toString();

            if (emailTxt.isEmpty() || passwordTxt.isEmpty() || conPasswordTxt.isEmpty() || usernameTxt.isEmpty()) {
                Toast.makeText(Register.this, "Please fill all the fields", Toast.LENGTH_SHORT).show();
            } else if (!passwordTxt.equals(conPasswordTxt)) {
                Toast.makeText(Register.this, "Passwords are not matching", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {

                        if (snapshot.hasChild(usernameTxt)) {
                            Toast.makeText(Register.this, "This username has already been register", Toast.LENGTH_SHORT).show();
                        } else {
                            databaseReference.child("users").child(usernameTxt).child("username").setValue(usernameTxt);
                            databaseReference.child("users").child(usernameTxt).child("email").setValue(emailTxt);
                            databaseReference.child("users").child(usernameTxt).child("password").setValue(passwordTxt);

                            String sessionUUID = UUID.randomUUID().toString();
                            Player player = new Player(usernameTxt);
                            databaseReference.child("users").child(usernameTxt).child("online").setValue(true);

                            Intent disconnectPlayerService = new Intent(Register.this, DisconnectPlayerAfterTaskKill.class);
                            startService(disconnectPlayerService);

                            databaseReference.child("users").child(player.getName()).child("lastSessionID").setValue(sessionUUID);
                            sharedPreferences = getSharedPreferences("SESSION", Context.MODE_PRIVATE);
                            editor = sharedPreferences.edit();
                            editor.putString("id", sessionUUID);
                            editor.putString("username", usernameTxt);
                            editor.apply();

                            if (stayLoginCheckBox.isChecked()) {
                                sharedPreferences = getSharedPreferences("AUTO_LOGIN", Context.MODE_PRIVATE);
                                editor.putString("username", usernameTxt);
                                editor.putString("password", passwordTxt);
                                editor.putBoolean("stayLogin", true);
                                editor.apply();
                            }

                            Intent intent = new Intent(Register.this, Menu.class);
                            intent.putExtra("player", player);
                            intent.putExtra("tutorial", true);
                            startActivity(intent);
                            finish();
                        }
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                        Toast.makeText(Register.this, "Error with database connexion", Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });

        loginNowBtn.setOnClickListener(v -> startActivity(new Intent(Register.this, Login.class)));
    }

}