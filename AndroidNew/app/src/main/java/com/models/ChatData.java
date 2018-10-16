package com.models;

public class ChatData {


    public String getuPresence() {
        return uPresence;
    }

    public void setuPresence(String uPresence) {
        this.uPresence = uPresence;
    }

    public String getuName() {
        return uName;
    }

    public void setuName(String uName) {
        this.uName = uName;
    }

    public String getuId() {
        return uId;

    }

    public void setuId(String uId) {
        this.uId = uId;
    }



    public String getuFullName() {
        return uFullName;
    }

    public void setuFullName(String uFullName) {
        this.uFullName = uFullName;
    }

    public String getuSubscriptionStatus() {
        return uSubscriptionStatus;
    }

    public void setuSubscriptionStatus(String uSubscriptionStatus) {
        this.uSubscriptionStatus = uSubscriptionStatus;
    }



    String uId;
    String uName;

    public String getuDOB() {
        return uDOB;
    }

    public void setuDOB(String uDOB) {
        this.uDOB = uDOB;
    }

    public String getMssageSentTime() {
        return mssageSentTime;
    }

    public void setMssageSentTime(String mssageSentTime) {
        this.mssageSentTime = mssageSentTime;
    }

    public String getMsgSentTime() {
        return msgSentTime;
    }

    public void setMsgSentTime(String msgSentTime) {
        this.msgSentTime = msgSentTime;
    }

    public String getUnreadMessagesCount() {
        return unreadMessagesCount;
    }

    public void setUnreadMessagesCount(String unreadMessagesCount) {
        this.unreadMessagesCount = unreadMessagesCount;
    }

    public String getuImageUrl() {
        return uImageUrl;
    }

    public void setuImageUrl(String uImageUrl) {
        this.uImageUrl = uImageUrl;
    }

    public int getuAge() {
        return uAge;
    }

    public void setuAge(int uAge) {
        this.uAge = uAge;
    }

    String uFullName;
    String uDOB;
    String uPresence;
    String mssageSentTime;
    String msgSentTime;
    String uSubscriptionStatus;
    String unreadMessagesCount;
    String uImageUrl;
    int uAge;


}
