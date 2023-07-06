package dev.swanndolia.idlemmorpg.tools.services;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;

import androidx.annotation.Nullable;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class DisconnectPlayerAfterTaskKill extends Service {

    DatabaseReference usersDatabase;


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return Service.START_STICKY;
    }

    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public void onTaskRemoved(Intent intent) {
        String username = getSharedPreferences("SESSION", Context.MODE_PRIVATE).getString("username", "");
        usersDatabase = FirebaseDatabase.getInstance().getReference().child("users");
        usersDatabase.child(username).child("online").setValue(false);
        stopSelf();
    }
}
