package com.models;

import android.os.Parcel;
import android.os.Parcelable;

public class Heritage implements Parcelable {

    private String heritageId;
    private String heritageName;
    private String Flag;
    private boolean isInterested;
//    private boolean isSelected;

    public Heritage()
    {

    }

    public String getHeritageId() {
        return heritageId;
    }

    public void setHeritageId(String heritageId) {
        this.heritageId = heritageId;
    }

    public String getHeritageName() {
        return heritageName;
    }

    public void setHeritageName(String heritageName) {
        this.heritageName = heritageName;
    }

    public String getFlag() {
        return Flag;
    }

    public void setFlag(String flag) {
        Flag = flag;
    }

    public boolean isInterested() {
        return isInterested;
    }

    public void setInterested(boolean interested) {
        isInterested = interested;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.heritageId);
        dest.writeString(this.heritageName);
        dest.writeString(this.Flag);
        dest.writeByte(this.isInterested ? (byte) 1 : (byte) 0);
    }

    protected Heritage(Parcel in) {
        this.heritageId = in.readString();
        this.heritageName = in.readString();
        this.Flag = in.readString();
        this.isInterested = in.readByte() != 0;
    }

    public static final Creator<Heritage> CREATOR = new Creator<Heritage>() {
        @Override
        public Heritage createFromParcel(Parcel source) {
            return new Heritage(source);
        }

        @Override
        public Heritage[] newArray(int size) {
            return new Heritage[size];
        }
    };
}
