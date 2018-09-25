package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 04-Feb-18.
 */

public class HeritageModel implements Parcelable {

    private String heritageId;
    private String heritageName;
    private String Flag;
    private boolean isInterested;
//    private boolean isSelected;

    public HeritageModel()
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

    protected HeritageModel(Parcel in) {
        this.heritageId = in.readString();
        this.heritageName = in.readString();
        this.Flag = in.readString();
        this.isInterested = in.readByte() != 0;
    }

    public static final Creator<HeritageModel> CREATOR = new Creator<HeritageModel>() {
        @Override
        public HeritageModel createFromParcel(Parcel source) {
            return new HeritageModel(source);
        }

        @Override
        public HeritageModel[] newArray(int size) {
            return new HeritageModel[size];
        }
    };
}
