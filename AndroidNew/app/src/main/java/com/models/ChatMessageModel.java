package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 30/11/2015.
 */
public class ChatMessageModel implements Parcelable {

    private String MsgId;
    private String MsgSender;
    private String MsgReceiver;
    private String MsgContent;
    private String MsgSentTime;
    private String MsgIsDeleted;
    private String MsgIsReaded;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.MsgId);
        dest.writeString(this.MsgSender);
        dest.writeString(this.MsgReceiver);
        dest.writeString(this.MsgContent);
        dest.writeString(this.MsgSentTime);
        dest.writeString(this.MsgIsDeleted);
        dest.writeString(this.MsgIsReaded);
    }

    public ChatMessageModel() {
    }

    protected ChatMessageModel(Parcel in) {
        this.MsgId = in.readString();
        this.MsgSender = in.readString();
        this.MsgReceiver = in.readString();
        this.MsgContent = in.readString();
        this.MsgSentTime = in.readString();
        this.MsgIsDeleted = in.readString();
        this.MsgIsReaded = in.readString();
    }

    public static final Parcelable.Creator<ChatMessageModel> CREATOR = new Parcelable.Creator<ChatMessageModel>() {
        public ChatMessageModel createFromParcel(Parcel source) {
            return new ChatMessageModel(source);
        }

        public ChatMessageModel[] newArray(int size) {
            return new ChatMessageModel[size];
        }
    };

    public String getMsgId() {
        return MsgId;
    }

    public void setMsgId(String msgId) {
        MsgId = msgId;
    }

    public String getMsgSender() {
        return MsgSender;
    }

    public void setMsgSender(String msgSender) {
        MsgSender = msgSender;
    }

    public String getMsgReceiver() {
        return MsgReceiver;
    }

    public void setMsgReceiver(String msgReceiver) {
        MsgReceiver = msgReceiver;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    public String getMsgSentTime() {
        return MsgSentTime;
    }

    public void setMsgSentTime(String msgSentTime) {
        MsgSentTime = msgSentTime;
    }

    public String getMsgIsDeleted() {
        return MsgIsDeleted;
    }

    public void setMsgIsDeleted(String msgIsDeleted) {
        MsgIsDeleted = msgIsDeleted;
    }

    public String getMsgIsReaded() {
        return MsgIsReaded;
    }

    public void setMsgIsReaded(String msgIsReaded) {
        MsgIsReaded = msgIsReaded;
    }
}
