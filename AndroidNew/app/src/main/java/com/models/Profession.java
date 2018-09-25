package com.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Profession implements Parcelable{
    public Profession(String id, String profession, boolean isSelected) {
        this.id = id;
        this.profession = profession;
        this.isSelected = isSelected;
    }

    String id;
    String profession;
    boolean isSelected;

    protected Profession(Parcel in) {
        id = in.readString();
        profession = in.readString();
        isSelected = in.readByte() != 0;
    }

    public static final Creator<Profession> CREATOR = new Creator<Profession>() {
        @Override
        public Profession createFromParcel(Parcel in) {
            return new Profession(in);
        }

        @Override
        public Profession[] newArray(int size) {
            return new Profession[size];
        }
    };

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getProfession() {
        return profession;
    }

    public void setProfession(String profession) {
        this.profession = profession;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel parcel, int i) {
        parcel.writeString(id);
        parcel.writeString(profession);
        parcel.writeByte((byte) (isSelected ? 1 : 0));
    }
}
