package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 15-Mar-18.
 */

public class UpgradeModel implements Parcelable{

    private String upgradeText;
//    private Drawable upgradeImage;


    public String getUpgradeText() {
        return upgradeText;
    }

    public void setUpgradeText(String upgradeText) {
        this.upgradeText = upgradeText;
    }

//    public Drawable getUpgradeImage() {
//        return upgradeImage;
//    }

//    public void setUpgradeImage(Drawable upgradeImage) {
//        this.upgradeImage = upgradeImage;
//    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.upgradeText);

        // Convert Drawable to Bitmap first:
//        Bitmap bitmap = (Bitmap)((BitmapDrawable) this.upgradeImage).getBitmap();

//        dest.writeParcelable(bitmap, flags);
    }

    public UpgradeModel() {
    }

    protected UpgradeModel(Parcel in) {
        this.upgradeText = in.readString();
//        this.upgradeImage = in.readParcelable(Drawable.class.getClassLoader());
    }

    public static final Creator<UpgradeModel> CREATOR = new Creator<UpgradeModel>() {
        @Override
        public UpgradeModel createFromParcel(Parcel source) {
            return new UpgradeModel(source);
        }

        @Override
        public UpgradeModel[] newArray(int size) {
            return new UpgradeModel[size];
        }
    };
}
