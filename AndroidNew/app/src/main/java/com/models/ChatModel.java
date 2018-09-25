package com.models;

public class ChatModel {
    public String message;
    public String id;
    public  String mSender;
    public  String mReceiver;

    public ChatModel() {
    }

    public ChatModel(String message, String id, String mSender , String mReceiver) {
        this.message = message;
        this.id = id;
        this.mSender = mSender;
        this.mReceiver = mReceiver;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getMSender() {
        return mSender;
    }

    public void setMSender(String mSender) {
        this.mSender = mSender;
    }

    public String getMReceiver() {
        return mReceiver;
    }

    public void setMReceiver(String mReceiver) {
        this.mReceiver = mReceiver;
    }
}
