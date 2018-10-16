package com.models;

public class ViewData {
    String uId;
    String uName;
    String uFullName;
    String uPresence;

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

    public String getuLocation() {
        return uLocation;
    }

    public void setuLocation(String uLocation) {
        this.uLocation = uLocation;
    }

    public String getuAge() {
        return uAge;
    }

    public void setuAge(String uAge) {
        this.uAge = uAge;
    }

    public String getuImageUrl() {
        return uImageUrl;
    }

    public void setuImageUrl(String uImageUrl) {
        this.uImageUrl = uImageUrl;
    }

    public String getuFavQuote() {
        return uFavQuote;
    }

    public void setuFavQuote(String uFavQuote) {
        this.uFavQuote = uFavQuote;
    }

    String uSubscriptionStatus;
    String uLocation;
    String uAge;
    String uImageUrl;
    String uFavQuote;
}
