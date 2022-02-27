package dev.swanndolia.idlemmorpg.tools.chat;


import android.os.Build;

import androidx.annotation.RequiresApi;

import java.text.MessageFormat;
import java.time.LocalDateTime;

public class MessageHolder {
    String sender;
    String message;
    String date;

    @RequiresApi(api = Build.VERSION_CODES.O)
    public MessageHolder(String messageToSend, String sender) {
        this.date = MessageFormat.format("{0}:{1}", String.valueOf(LocalDateTime.now().getHour()), String.valueOf(LocalDateTime.now().getMinute()));
        this.message = messageToSend;
        this.sender = sender;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

}
