package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 13/09/2015.
 */
public class CountryModel implements Parcelable {

    private String country_id;
    private String country_name;
    private String state_id;
    private String state_name;


    public CountryModel() {
        this.country_id = "";
        this.country_name = "";
        this.state_id = "";
        this.state_name = "";


    }

    public static final Creator<CountryModel> CREATOR = new Creator<CountryModel>() {


        @Override
        public CountryModel[] newArray(int size) {
            return new CountryModel[size];
        }

        @Override
        public CountryModel createFromParcel(Parcel source) {
            return new CountryModel(source);
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public CountryModel(Parcel source) {


        this.country_id = source.readString();
        this.country_name = source.readString();
        this.state_id = source.readString();
        this.state_name = source.readString();


    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {


        dest.writeString(this.country_id);
        dest.writeString(this.country_name);
        dest.writeString(this.state_id);
        dest.writeString(this.state_name);


    }

    public String getCountry_id() {
        return country_id;
    }

    public void setCountry_id(String country_id) {
        this.country_id = country_id;
    }

    public String getCountry_name() {
        return country_name;
    }

    public void setCountry_name(String country_name) {
        this.country_name = country_name;
    }

    public String getState_id() {
        return state_id;
    }

    public void setState_id(String state_id) {
        this.state_id = state_id;
    }

    public String getState_name() {
        return state_name;
    }

    public void setState_name(String state_name) {
        this.state_name = state_name;
    }
}
