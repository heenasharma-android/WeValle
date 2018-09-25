package com.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Sumit on 04-Feb-18.
 */

public class FilterModel implements Parcelable{

    private String IWantTo;
    private String UserInterestedIn;
    private String UserMinAge;
    private String UserMaxAge;
    private String UserInvisibleStatus;
    private String UserHeritage;
    private ArrayList<Heritage> heritageModelArrayList;

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.IWantTo);
        dest.writeString(this.UserInterestedIn);
        dest.writeString(this.UserMinAge);
        dest.writeString(this.UserMaxAge);
        dest.writeString(this.UserInvisibleStatus);
        dest.writeString(this.UserHeritage);
        dest.writeTypedList(this.heritageModelArrayList);
    }

    public FilterModel() {
    }

    protected FilterModel(Parcel in) {
        this.IWantTo = in.readString();
        this.UserInterestedIn = in.readString();
        this.UserMinAge = in.readString();
        this.UserMaxAge = in.readString();
        this.UserInvisibleStatus = in.readString();
        this.UserHeritage = in.readString();
        this.heritageModelArrayList = in.createTypedArrayList(Heritage.CREATOR);
    }

    public static final Creator<FilterModel> CREATOR = new Creator<FilterModel>() {
        @Override
        public FilterModel createFromParcel(Parcel source) {
            return new FilterModel(source);
        }

        @Override
        public FilterModel[] newArray(int size) {
            return new FilterModel[size];
        }
    };

    public String getIWantTo() {
        return IWantTo;
    }

    public void setIWantTo(String IWantTo) {
        this.IWantTo = IWantTo;
    }

    public String getUserInterestedIn() {
        return UserInterestedIn;
    }

    public void setUserInterestedIn(String userInterestedIn) {
        UserInterestedIn = userInterestedIn;
    }

    public String getUserMinAge() {
        return UserMinAge;
    }

    public void setUserMinAge(String userMinAge) {
        UserMinAge = userMinAge;
    }

    public String getUserMaxAge() {
        return UserMaxAge;
    }

    public void setUserMaxAge(String userMaxAge) {
        UserMaxAge = userMaxAge;
    }

    public String getUserInvisibleStatus() {
        return UserInvisibleStatus;
    }

    public void setUserInvisibleStatus(String userInvisibleStatus) {
        UserInvisibleStatus = userInvisibleStatus;
    }

    public String getUserHeritage() {
        return UserHeritage;
    }

    public void setUserHeritage(String userHeritage) {
        UserHeritage = userHeritage;
    }

    public ArrayList<Heritage> getHeritageModelArrayList() {
        return heritageModelArrayList;
    }

    public void setHeritageModelArrayList(ArrayList<Heritage> heritageModelArrayList) {
        this.heritageModelArrayList = heritageModelArrayList;
    }
}
