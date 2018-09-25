package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 27/09/2015.
 */
public class MatchMaingModel implements Parcelable {


    private String MatchMakingId;
    private String MatchMakingQuestions;
    private String Percentage;
    private String MMAnswerID;
    private String MMAnswerUserID;
    private String UserId;
    private String UserName;
    private String UserImageUrl;
    private String UserFavQuote;
    private String UserCity;
    private String UserState;
    private String Age;
    private String UserSubscriptionStatus;




    public MatchMaingModel() {
        this.MatchMakingId = "";
        this.MatchMakingQuestions = "";
        this.Percentage = "";
        this.MMAnswerID = "";
        this.MMAnswerUserID = "";
        this.UserId = "";
        this.UserName = "";
        this.UserImageUrl = "";
        this.UserFavQuote = "";
        this.UserCity = "";
        this.UserState = "";
        this.Age = "";
        this.UserSubscriptionStatus = "";





    }

    public static final Parcelable.Creator<MatchMaingModel> CREATOR = new Parcelable.Creator<MatchMaingModel>() {


        @Override
        public MatchMaingModel[] newArray(int size) {
            return new MatchMaingModel[size];
        }

        @Override
        public MatchMaingModel createFromParcel(Parcel source) {
            return new MatchMaingModel(source);
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }




    public MatchMaingModel(Parcel source) {




        this.MatchMakingId  = source.readString();
        this.MatchMakingQuestions  = source.readString();
        this.Percentage  = source.readString();
        this.MMAnswerID  = source.readString();
        this.MMAnswerUserID  = source.readString();
        this.UserId  = source.readString();
        this.UserName  = source.readString();
        this.UserImageUrl  = source.readString();
        this.UserFavQuote  = source.readString();
        this.UserCity  = source.readString();
        this.UserState  = source.readString();
        this.Age  = source.readString();
        this.UserSubscriptionStatus  = source.readString();




    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {



        dest.writeString(this.MatchMakingId);;
        dest.writeString(this.MatchMakingQuestions );
        dest.writeString(this.Percentage );
        dest.writeString(this.MMAnswerID);
        dest.writeString(this.MMAnswerUserID );
        dest.writeString(this.UserId);
        dest.writeString(this.UserName);
        dest.writeString(this.UserImageUrl);
        dest.writeString(this.UserFavQuote);
        dest.writeString(this.UserCity);
        dest.writeString(this.UserState);
        dest.writeString(this.Age);
        dest.writeString(this.UserSubscriptionStatus);





    }


    public String getMatchMakingId() {
        return MatchMakingId;
    }

    public void setMatchMakingId(String matchMakingId) {
        MatchMakingId = matchMakingId;
    }

    public String getMatchMakingQuestions() {
        return MatchMakingQuestions;
    }

    public void setMatchMakingQuestions(String matchMakingQuestions) {
        MatchMakingQuestions = matchMakingQuestions;
    }

    public String getPercentage() {
        return Percentage;
    }

    public void setPercentage(String percentage) {
        Percentage = percentage;
    }

    public String getMMAnswerID() {
        return MMAnswerID;
    }

    public void setMMAnswerID(String MMAnswerID) {
        this.MMAnswerID = MMAnswerID;
    }

    public String getMMAnswerUserID() {
        return MMAnswerUserID;
    }

    public void setMMAnswerUserID(String MMAnswerUserID) {
        this.MMAnswerUserID = MMAnswerUserID;
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

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getUserSubscriptionStatus() {
        return UserSubscriptionStatus;
    }

    public void setUserSubscriptionStatus(String userSubscriptionStatus) {
        UserSubscriptionStatus = userSubscriptionStatus;
    }
}
