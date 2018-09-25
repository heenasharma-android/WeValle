package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 21/09/2015.
 */
public class WhoIViewedModel implements Parcelable {

    private String UserId;
    private String UserName;
    private String ImageFileName;
    private String UserJabberId;
    private String UserPassword;
    private String UserStatus;
    private String UserSubscriptionStatus;
    private String Age;
    private String UserImageUrl;
    private String UserFavQuote;
    private String UserCity;
    private String UserCountry;
    private String UserState;
    private String UserPresence;
    private String BlockTo;


    public WhoIViewedModel() {
        this.UserId = "";
        this.UserName = "";
        this.ImageFileName = "";

        this.UserJabberId = "";
        this.UserPassword = "";
        this.UserStatus = "";
        this.UserSubscriptionStatus = "";
        this.Age = "";
        this.UserImageUrl = "";
        this.UserFavQuote = "";
        this.UserCity = "";
        this.UserCountry = "";
        this.UserState = "";
        this.UserPresence = "";
        this.BlockTo = "";



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

    public String getImageFileName() {
        return ImageFileName;
    }

    public void setImageFileName(String imageFileName) {
        ImageFileName = imageFileName;
    }

    public String getUserJabberId() {
        return UserJabberId;
    }

    public void setUserJabberId(String userJabberId) {
        UserJabberId = userJabberId;
    }

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserSubscriptionStatus() {
        return UserSubscriptionStatus;
    }

    public void setUserSubscriptionStatus(String userSubscriptionStatus) {
        UserSubscriptionStatus = userSubscriptionStatus;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public String getUserFavQuote() {
        return UserFavQuote;
    }

    public void setUserFavQuote(String userFavQuote) {
        UserFavQuote = userFavQuote;
    }

    public String getUserCity() {
        return UserCity;
    }

    public void setUserCity(String userCity) {
        UserCity = userCity;
    }

    public String getUserState() {
        return UserState;
    }

    public void setUserState(String userState) {
        UserState = userState;
    }

    public String getUserPresence() {
        return UserPresence;
    }

    public void setUserPresence(String userPresence) {
        UserPresence = userPresence;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public String getBlockTo() {
        return BlockTo;
    }

    public void setBlockTo(String blockTo) {
        BlockTo = blockTo;
    }

    public String getUserCountry() {
        return UserCountry;
    }

    public void setUserCountry(String userCountry) {
        UserCountry = userCountry;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.UserId);
        dest.writeString(this.UserName);
        dest.writeString(this.ImageFileName);
        dest.writeString(this.UserJabberId);
        dest.writeString(this.UserPassword);
        dest.writeString(this.UserStatus);
        dest.writeString(this.UserSubscriptionStatus);
        dest.writeString(this.Age);
        dest.writeString(this.UserImageUrl);
        dest.writeString(this.UserFavQuote);
        dest.writeString(this.UserCity);
        dest.writeString(this.UserCountry);
        dest.writeString(this.UserState);
        dest.writeString(this.UserPresence);
        dest.writeString(this.BlockTo);
    }

    protected WhoIViewedModel(Parcel in) {
        this.UserId = in.readString();
        this.UserName = in.readString();
        this.ImageFileName = in.readString();
        this.UserJabberId = in.readString();
        this.UserPassword = in.readString();
        this.UserStatus = in.readString();
        this.UserSubscriptionStatus = in.readString();
        this.Age = in.readString();
        this.UserImageUrl = in.readString();
        this.UserFavQuote = in.readString();
        this.UserCity = in.readString();
        this.UserCountry = in.readString();
        this.UserState = in.readString();
        this.UserPresence = in.readString();
        this.BlockTo = in.readString();
    }

    public static final Creator<WhoIViewedModel> CREATOR = new Creator<WhoIViewedModel>() {
        @Override
        public WhoIViewedModel createFromParcel(Parcel source) {
            return new WhoIViewedModel(source);
        }

        @Override
        public WhoIViewedModel[] newArray(int size) {
            return new WhoIViewedModel[size];
        }
    };
}
