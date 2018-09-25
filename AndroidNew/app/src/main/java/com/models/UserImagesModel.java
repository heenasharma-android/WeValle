package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 27/09/2015.
 */
public class UserImagesModel implements Parcelable {

    public String ImageId;
    public String UserImageUrl;
    public String IsUserProfileImage;

    public UserImagesModel() {
        this.ImageId = "";
        this.UserImageUrl = "";
        this.IsUserProfileImage = "";






    }

    public static final Parcelable.Creator<UserImagesModel> CREATOR = new Parcelable.Creator<UserImagesModel>() {


        @Override
        public UserImagesModel[] newArray(int size) {
            return new UserImagesModel[size];
        }

        @Override
        public UserImagesModel createFromParcel(Parcel source) {
            return new UserImagesModel(source);
        }
    };

    @Override
    public int describeContents() {
        // TODO Auto-generated method stub
        return 0;
    }

    public UserImagesModel(Parcel source) {


        this.ImageId = source.readString();
        this.UserImageUrl = source.readString();
        this.IsUserProfileImage = source.readString();



    }

    @Override
    public void writeToParcel(Parcel dest, int arg1) {


        dest.writeString(this.ImageId);
        dest.writeString(this.UserImageUrl);
        dest.writeString(this.IsUserProfileImage);





    }

    public String getImageId() {
        return ImageId;
    }

    public void setImageId(String imageId) {
        ImageId = imageId;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public String getIsUserProfileImage() {
        return IsUserProfileImage;
    }

    public void setIsUserProfileImage(String isUserProfileImage) {
        IsUserProfileImage = isUserProfileImage;
    }
}
