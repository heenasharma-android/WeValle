package com.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Sumit on 01-Aug-17.
 */

public class HomeUsers implements Parcelable {

    private ArrayList<UserModel> UsersViewedMe;
    private ArrayList<UserModel> UserFavorites;
    private ArrayList<UserModel> NewUsers;
    private ArrayList<UserModel> RandomUsers;


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeTypedList(this.UsersViewedMe);
        dest.writeTypedList(this.UserFavorites);
        dest.writeTypedList(this.NewUsers);
        dest.writeTypedList(this.RandomUsers);
    }

    public HomeUsers() {
    }

    protected HomeUsers(Parcel in) {
        this.UsersViewedMe = in.createTypedArrayList(UserModel.CREATOR);
        this.UserFavorites = in.createTypedArrayList(UserModel.CREATOR);
        this.NewUsers = in.createTypedArrayList(UserModel.CREATOR);
        this.RandomUsers = in.createTypedArrayList(UserModel.CREATOR);
    }

    public static final Parcelable.Creator<HomeUsers> CREATOR = new Parcelable.Creator<HomeUsers>() {
        @Override
        public HomeUsers createFromParcel(Parcel source) {
            return new HomeUsers(source);
        }

        @Override
        public HomeUsers[] newArray(int size) {
            return new HomeUsers[size];
        }
    };

    public ArrayList<UserModel> getUsersViewedMe() {
        return UsersViewedMe;
    }

    public void setUsersViewedMe(ArrayList<UserModel> usersViewedMe) {
        UsersViewedMe = usersViewedMe;
    }

    public ArrayList<UserModel> getUserFavorites() {
        return UserFavorites;
    }

    public void setUserFavorites(ArrayList<UserModel> userFavorites) {
        UserFavorites = userFavorites;
    }

    public ArrayList<UserModel> getNewUsers() {
        return NewUsers;
    }

    public void setNewUsers(ArrayList<UserModel> newUsers) {
        NewUsers = newUsers;
    }

    public ArrayList<UserModel> getRandomUsers() {
        return RandomUsers;
    }

    public void setRandomUsers(ArrayList<UserModel> randomUsers) {
        RandomUsers = randomUsers;
    }
}
