package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 23/09/2015.
 */
public class RecievedMessageModel implements Parcelable{

    private String UserId;
    private String UserName;
    private String UserDOB;
    private String UserInterests;
    private String UserFavQuote;
    private String UserPresence;
    private String MessageSentTime;
    private String UserSubscriptionStatus;
    private String MsgIsReaded;
    private String UnreadMessagesCount;
    private String UserImageUrl;
    private String Age;
    private String MsgContent;
    private String city;
    private String country;


    public String getUnreadMessagesCount() {
        return UnreadMessagesCount;
    }

    public void setUnreadMessagesCount(String unreadMessagesCount) {
        UnreadMessagesCount = unreadMessagesCount;
    }

    public RecievedMessageModel() {

        this.UserId = "";
        this.UserName = "";
        this.UserDOB = "";
        this.UserInterests = "";
        this.UserFavQuote = "";
        this.UserPresence = "";
        this.MessageSentTime = "";
        this.UserSubscriptionStatus = "";
        this.MsgIsReaded = "";
        this.UnreadMessagesCount = "";
        this.UserImageUrl = "";
        this.Age = "";
        this.MsgContent = "";
        this.city = "";
        this.country = "";




    }


    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public String getUserDOB() {
        return UserDOB;
    }

    public void setUserDOB(String userDOB) {
        UserDOB = userDOB;
    }

    public String getUserInterests() {
        return UserInterests;
    }

    public void setUserInterests(String userInterests) {
        UserInterests = userInterests;
    }

    public String getUserFavQuote() {
        return UserFavQuote;
    }

    public void setUserFavQuote(String userFavQuote) {
        UserFavQuote = userFavQuote;
    }

    public String getUserPresence() {
        return UserPresence;
    }

    public void setUserPresence(String userPresence) {
        UserPresence = userPresence;
    }

    public String getMessageSentTime() {
        return MessageSentTime;
    }

    public void setMessageSentTime(String messageSentTime) {
        MessageSentTime = messageSentTime;
    }

    public String getUserSubscriptionStatus() {
        return UserSubscriptionStatus;
    }

    public void setUserSubscriptionStatus(String userSubscriptionStatus) {
        UserSubscriptionStatus = userSubscriptionStatus;
    }

    public String getMsgIsReaded() {
        return MsgIsReaded;
    }

    public void setMsgIsReaded(String msgIsReaded) {
        MsgIsReaded = msgIsReaded;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getMsgContent() {
        return MsgContent;
    }

    public void setMsgContent(String msgContent) {
        MsgContent = msgContent;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserId);
        dest.writeString(this.UserName);
        dest.writeString(this.UserDOB);
        dest.writeString(this.UserInterests);
        dest.writeString(this.UserFavQuote);
        dest.writeString(this.UserPresence);
        dest.writeString(this.MessageSentTime);
        dest.writeString(this.UserSubscriptionStatus);
        dest.writeString(this.MsgIsReaded);
        dest.writeString(this.UnreadMessagesCount);
        dest.writeString(this.UserImageUrl);
        dest.writeString(this.Age);
        dest.writeString(this.MsgContent);
        dest.writeString(this.city);
        dest.writeString(this.country);
    }

    protected RecievedMessageModel(Parcel in) {
        this.UserId = in.readString();
        this.UserName = in.readString();
        this.UserDOB = in.readString();
        this.UserInterests = in.readString();
        this.UserFavQuote = in.readString();
        this.UserPresence = in.readString();
        this.MessageSentTime = in.readString();
        this.UserSubscriptionStatus = in.readString();
        this.MsgIsReaded = in.readString();
        this.UnreadMessagesCount = in.readString();
        this.UserImageUrl = in.readString();
        this.Age = in.readString();
        this.MsgContent = in.readString();
        this.city = in.readString();
        this.country = in.readString();
    }

    public static final Creator<RecievedMessageModel> CREATOR = new Creator<RecievedMessageModel>() {
        @Override
        public RecievedMessageModel createFromParcel(Parcel source) {
            return new RecievedMessageModel(source);
        }

        @Override
        public RecievedMessageModel[] newArray(int size) {
            return new RecievedMessageModel[size];
        }
    };
}
