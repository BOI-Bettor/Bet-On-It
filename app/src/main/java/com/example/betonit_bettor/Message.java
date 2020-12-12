package com.example.betonit_bettor;

import com.parse.ParseClassName;
import com.parse.ParseObject;

@ParseClassName("Message")
public class Message extends ParseObject {
    public static final String KEY_MESSAGE_BODY = "message_Body";
    public static final String KEY_MESSAGE_STATUS = "message_Status";
    public static final String KEY_MESSAGE_SENDER = "message_Sender";
    public static final String KEY_MESSAGE_RECEIVER = "message_Receiver";

    public String getKeyMessageBody() {
        return getString(KEY_MESSAGE_BODY);
    }

    public String getKeyMessageStatus() {
        return getString(KEY_MESSAGE_STATUS);
    }

    public String getKeyMessageSender() {
        return getString(KEY_MESSAGE_SENDER);
    }

    public String getKeyMessageReceiver() {
        return getString(KEY_MESSAGE_RECEIVER);
    }

    public void setKeyMessageBody(String message_Body) {
        put(KEY_MESSAGE_BODY, message_Body);
    }

    public void setKeyMessageStatus(String message_Status) {
        put(KEY_MESSAGE_STATUS, message_Status);
    }

    public void setKeyMessageSender(String message_Sender) {
        put(KEY_MESSAGE_SENDER, message_Sender);
    }

    public void setKeyMessageReceiver(String message_Receiver) {
        put(KEY_MESSAGE_RECEIVER, message_Receiver);
    }
}
