package dev.swanndolia.idlemmorpg.ui.overlays;

import android.app.Dialog;
import android.content.Context;
import android.os.Build;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.text.MessageFormat;

import dev.swanndolia.idlemmorpg.R;
import dev.swanndolia.idlemmorpg.characters.Player;

import dev.swanndolia.idlemmorpg.tools.chat.MessageHolder;

public class ChatOverlay {
    LinearLayout messagesHolder;

    public void ChatOverlay(Context context, Player player) {
        Dialog dialog = new Dialog(context);
        dialog.setContentView(R.layout.chat_overlay);
        messagesHolder = dialog.findViewById(R.id.messagesHolder);
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        EditText messageBox = dialog.findViewById(R.id.messageBox);
        Button sendBtn = dialog.findViewById(R.id.sendMessageBtn);
        databaseReference.child("chat").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                updateChat(context, player, snapshot);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
            }
        });

        sendBtn.setOnClickListener(v -> {
            String messageToSend = messageBox.getText().toString();
            messageBox.setText("");
            if (messageToSend.isEmpty()) {
                Toast.makeText(context, "Please send a valid message", Toast.LENGTH_SHORT).show();
            } else {
                databaseReference.child("chat").addListenerForSingleValueEvent(new ValueEventListener() {
                    @RequiresApi(api = Build.VERSION_CODES.O)
                    @Override
                    public void onDataChange(@NonNull DataSnapshot snapshot) {
                        MessageHolder messageHolder = new MessageHolder(messageToSend, player.getName());
                        DatabaseReference ref = databaseReference.child("chat").push();
                        ref.setValue(messageHolder);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError error) {
                    }
                });

            }
        });


        Window window = dialog.getWindow();
        window.setLayout(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        dialog.show();
    }

    public void updateChat(Context context, Player player, DataSnapshot snapshot) {
        messagesHolder.removeAllViews();
        for (DataSnapshot dataSnapshot : snapshot.getChildren()) {
            MessageHolder messageHolder = dataSnapshot.getValue(MessageHolder.class);
            String message = messageHolder.getMessage();
            String sender = messageHolder.getSender();
            String date = messageHolder.getDate();

            TextView chatMessage = new TextView(context);
            if (messageHolder.getSender().equals(player.getName())) {
                chatMessage.setTextAlignment(View.TEXT_ALIGNMENT_TEXT_END);
            }
            chatMessage.setTextSize(18);
            chatMessage.setText(MessageFormat.format("{0} {1}: {2}", date, sender, message));
            messagesHolder.addView(chatMessage);
        }
    }
}
