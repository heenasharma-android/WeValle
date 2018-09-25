package com.models;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Sumit on 13/09/2015.
 */
public class UserModel<T> implements Parcelable {

    private String UserId;
    private String UserName;
    private String ImageFileName;
    private String Age;
    private String UserImageUrl;
    private String UserFavQuote;
    private String UserPassword;
    private String UserEmail;
    private String UserFullName;
    private String UserCountry;
    private String UserState;
    private String UserCity;
    private String UserZipCode;
    private String UserHearAboutUs;
    private String UserGender;
    private String UserDOB;
    private String UserInterestedIn;
    private String UserReligion;
    private String UserHairColor;
    private String UserEyeColor;
    private String UserHeight;
    private String UserBodyType;
    private String UserEducation;
    private String UserOccupation;
    private String UserPoliticalViews;
    private String UserOtherLanguages;
    private String UserDescOneWord;
    private String UserPassion;
    private String UserDescription;
    private String UserInfluencedBy;
    private String UserPets;
    private String UserFreeSpendTime;
    private String UserCantLiveAbout;
    private String UserFavMovies;
    private String UserAlbanianSongs;
    private String UserDrinks ;
    private String UserSmokes;
    private String UserStatus;
    private String UserImage;
    private String UserMinAge;
    private String UserMaxAge;
    private String UserSubscriptionStatus;
    private String UserHeritage;
    private String UserInvisibleStatus;
    private String UserPresence;
    private String RowDistance;
    private String Unit;
    private String MilesDistance;
    private String usertypeHomePage;
    private String userlatitude;

    public String getUserHeritage() {
        return UserHeritage;
    }

    public void setUserHeritage(String userHeritage) {
        UserHeritage = userHeritage;
    }

    public String getUserLocation() {
        return UserLocation;
    }

    public void setUserLocation(String userLocation) {
        UserLocation = userLocation;
    }

    private String UserLocation;

    public String getUserlatitude() {
        return userlatitude;
    }

    public void setUserlatitude(String userlatitude) {
        this.userlatitude = userlatitude;
    }

    public String getUserlogitude() {
        return userlogitude;
    }

    public void setUserlogitude(String userlogitude) {
        this.userlogitude = userlogitude;
    }

    private String userlogitude;


    public String getImageFileName() {
        return ImageFileName;
    }

    public void setImageFileName(String imageFileName) {
        ImageFileName = imageFileName;
    }

    public UserModel() {
        this.UserId = "";
        this.UserName = "";
        this.Age = "";
        this.UserImageUrl = "";
        this.UserFavQuote = "";
        this.UserPassword = "";
        this.UserEmail = "";

        this.UserFullName = "";
        this.UserCountry = "";
        this.UserState = "";
        this.UserCity = "";
        this.UserZipCode = "";
        this.UserHearAboutUs = "";
        this.UserGender = "";
        this.UserDOB = "";
        this.UserInterestedIn = "";
        this.UserReligion = "";
        this.UserHairColor = "";
        this.UserEyeColor = "";
        this.UserHeight = "";
        this.UserBodyType = "";
        this.UserEducation = "";
        this.UserOccupation = "";
        this.UserPoliticalViews = "";
        this.UserOtherLanguages = "";
        this.UserDescOneWord = "";
        this.UserPassion = "";
        this.UserDescription = "";
        this.UserInfluencedBy = "";
        this.UserPets = "";
        this.UserFreeSpendTime = "";
        this.UserCantLiveAbout = "";
        this.UserFavMovies = "";
        this.UserAlbanianSongs = "";
        this.UserDrinks = "";
        this.UserSmokes = "";
        this.UserStatus = "";
        this.UserImage = "";
        this.UserMinAge = "";
        this.UserMaxAge = "";
        this.UserSubscriptionStatus = "";
        this.UserInvisibleStatus = "";
        this.UserPresence = "";
        this.RowDistance = "";
        this.Unit = "";
        this.MilesDistance = "";
        this.usertypeHomePage = "";





    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
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

    public String getUserPassword() {
        return UserPassword;
    }

    public void setUserPassword(String userPassword) {
        UserPassword = userPassword;
    }

    public String getUserEmail() {
        return UserEmail;
    }

    public void setUserEmail(String userEmail) {
        UserEmail = userEmail;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
    }

    public String getUserCountry() {
        return UserCountry;
    }

    public void setUserCountry(String userCountry) {
        UserCountry = userCountry;
    }

    public String getUserState() {
        return UserState;
    }

    public void setUserState(String userState) {
        UserState = userState;
    }

    public String getUserCity() {
        return UserCity;
    }

    public void setUserCity(String userCity) {
        UserCity = userCity;
    }

    public String getUserZipCode() {
        return UserZipCode;
    }

    public void setUserZipCode(String userZipCode) {
        UserZipCode = userZipCode;
    }

    public String getUserHearAboutUs() {
        return UserHearAboutUs;
    }

    public void setUserHearAboutUs(String userHearAboutUs) {
        UserHearAboutUs = userHearAboutUs;
    }

    public String getUserGender() {
        return UserGender;
    }

    public void setUserGender(String userGender) {
        UserGender = userGender;
    }

    public String getUserDOB() {
        return UserDOB;
    }

    public void setUserDOB(String userDOB) {
        UserDOB = userDOB;
    }

    public String getUserInterestedIn() {
        return UserInterestedIn;
    }

    public void setUserInterestedIn(String userInterestedIn) {
        UserInterestedIn = userInterestedIn;
    }

    public String getUserReligion() {
        return UserReligion;
    }

    public void setUserReligion(String userReligion) {
        UserReligion = userReligion;
    }

    public String getUserHairColor() {
        return UserHairColor;
    }

    public void setUserHairColor(String userHairColor) {
        UserHairColor = userHairColor;
    }

    public String getUserEyeColor() {
        return UserEyeColor;
    }

    public void setUserEyeColor(String userEyeColor) {
        UserEyeColor = userEyeColor;
    }

    public String getUserHeight() {
        return UserHeight;
    }

    public void setUserHeight(String userHeight) {
        UserHeight = userHeight;
    }

    public String getUserBodyType() {
        return UserBodyType;
    }

    public void setUserBodyType(String userBodyType) {
        UserBodyType = userBodyType;
    }

    public String getUserEducation() {
        return UserEducation;
    }

    public void setUserEducation(String userEducation) {
        UserEducation = userEducation;
    }

    public String getUserOccupation() {
        return UserOccupation;
    }

    public void setUserOccupation(String userOccupation) {
        UserOccupation = userOccupation;
    }

    public String getUserPoliticalViews() {
        return UserPoliticalViews;
    }

    public void setUserPoliticalViews(String userPoliticalViews) {
        UserPoliticalViews = userPoliticalViews;
    }

    public String getUserOtherLanguages() {
        return UserOtherLanguages;
    }

    public void setUserOtherLanguages(String userOtherLanguages) {
        UserOtherLanguages = userOtherLanguages;
    }

    public String getUserDescOneWord() {
        return UserDescOneWord;
    }

    public void setUserDescOneWord(String userDescOneWord) {
        UserDescOneWord = userDescOneWord;
    }

    public String getUserPassion() {
        return UserPassion;
    }

    public void setUserPassion(String userPassion) {
        UserPassion = userPassion;
    }


    public String getUserDescription() {
        return UserDescription;
    }

    public void setUserDescription(String userDescription) {
        UserDescription = userDescription;
    }

    public String getUserInfluencedBy() {
        return UserInfluencedBy;
    }

    public void setUserInfluencedBy(String userInfluencedBy) {
        UserInfluencedBy = userInfluencedBy;
    }

    public String getUserPets() {
        return UserPets;
    }

    public void setUserPets(String userPets) {
        UserPets = userPets;
    }

    public String getUserFreeSpendTime() {
        return UserFreeSpendTime;
    }

    public void setUserFreeSpendTime(String userFreeSpendTime) {
        UserFreeSpendTime = userFreeSpendTime;
    }

    public String getUserCantLiveAbout() {
        return UserCantLiveAbout;
    }

    public void setUserCantLiveAbout(String userCantLiveAbout) {
        UserCantLiveAbout = userCantLiveAbout;
    }

    public String getUserFavMovies() {
        return UserFavMovies;
    }

    public void setUserFavMovies(String userFavMovies) {
        UserFavMovies = userFavMovies;
    }

    public String getUserAlbanianSongs() {
        return UserAlbanianSongs;
    }

    public void setUserAlbanianSongs(String userAlbanianSongs) {
        UserAlbanianSongs = userAlbanianSongs;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
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

    public String getUserSubscriptionStatus() {
        return UserSubscriptionStatus;
    }

    public void setUserSubscriptionStatus(String userSubscriptionStatus) {
        UserSubscriptionStatus = userSubscriptionStatus;
    }

    public String getUserInvisibleStatus() {
        return UserInvisibleStatus;
    }

    public void setUserInvisibleStatus(String userInvisibleStatus) {
        UserInvisibleStatus = userInvisibleStatus;
    }

    public String getAge() {
        return Age;
    }

    public void setAge(String age) {
        Age = age;
    }

    public String getUserFavQuote() {
        return UserFavQuote;
    }

    public void setUserFavQuote(String userFavQuote) {
        UserFavQuote = userFavQuote;
    }

    public String getUserDrinks() {
        return UserDrinks;
    }

    public void setUserDrinks(String userDrinks) {
        UserDrinks = userDrinks;
    }

    public String getUserSmokes() {
        return UserSmokes;
    }

    public void setUserSmokes(String userSmokes) {
        UserSmokes = userSmokes;
    }

    public String getUserPresence() {
        return UserPresence;
    }

    public void setUserPresence(String userPresence) {
        UserPresence = userPresence;
    }

    public String getRowDistance() {
        return RowDistance;
    }

    public void setRowDistance(String rowDistance) {
        RowDistance = rowDistance;
    }

    public String getUnit() {
        return Unit;
    }

    public void setUnit(String unit) {
        Unit = unit;
    }

    public String getMilesDistance() {
        return MilesDistance;
    }

    public void setMilesDistance(String milesDistance) {
        MilesDistance = milesDistance;
    }

    public String getUsertypeHomePage() {
        return usertypeHomePage;
    }

    public void setUsertypeHomePage(String usertypeHomePage) {
        this.usertypeHomePage = usertypeHomePage;
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
        dest.writeString(this.Age);
        dest.writeString(this.UserImageUrl);
        dest.writeString(this.UserFavQuote);
        dest.writeString(this.UserPassword);
        dest.writeString(this.UserEmail);
        dest.writeString(this.UserFullName);
        dest.writeString(this.UserCountry);
        dest.writeString(this.UserState);
        dest.writeString(this.UserCity);
        dest.writeString(this.UserZipCode);
        dest.writeString(this.UserHearAboutUs);
        dest.writeString(this.UserGender);
        dest.writeString(this.UserDOB);
        dest.writeString(this.UserInterestedIn);
        dest.writeString(this.UserReligion);
        dest.writeString(this.UserHairColor);
        dest.writeString(this.UserEyeColor);
        dest.writeString(this.UserHeight);
        dest.writeString(this.UserBodyType);
        dest.writeString(this.UserEducation);
        dest.writeString(this.UserOccupation);
        dest.writeString(this.UserPoliticalViews);
        dest.writeString(this.UserOtherLanguages);
        dest.writeString(this.UserDescOneWord);
        dest.writeString(this.UserPassion);
        dest.writeString(this.UserDescription);
        dest.writeString(this.UserInfluencedBy);
        dest.writeString(this.UserPets);
        dest.writeString(this.UserFreeSpendTime);
        dest.writeString(this.UserCantLiveAbout);
        dest.writeString(this.UserFavMovies);
        dest.writeString(this.UserAlbanianSongs);
        dest.writeString(this.UserDrinks);
        dest.writeString(this.UserSmokes);
        dest.writeString(this.UserStatus);
        dest.writeString(this.UserImage);
        dest.writeString(this.UserMinAge);
        dest.writeString(this.UserMaxAge);
        dest.writeString(this.UserSubscriptionStatus);
        dest.writeString(this.UserHeritage);
        dest.writeString(this.UserInvisibleStatus);
        dest.writeString(this.UserPresence);
        dest.writeString(this.RowDistance);
        dest.writeString(this.Unit);
        dest.writeString(this.MilesDistance);
        dest.writeString(this.usertypeHomePage);
        dest.writeString(this.userlatitude);
        dest.writeString(this.UserLocation);
        dest.writeString(this.userlogitude);
    }

    protected UserModel(Parcel in) {
        this.UserId = in.readString();
        this.UserName = in.readString();
        this.ImageFileName = in.readString();
        this.Age = in.readString();
        this.UserImageUrl = in.readString();
        this.UserFavQuote = in.readString();
        this.UserPassword = in.readString();
        this.UserEmail = in.readString();
        this.UserFullName = in.readString();
        this.UserCountry = in.readString();
        this.UserState = in.readString();
        this.UserCity = in.readString();
        this.UserZipCode = in.readString();
        this.UserHearAboutUs = in.readString();
        this.UserGender = in.readString();
        this.UserDOB = in.readString();
        this.UserInterestedIn = in.readString();
        this.UserReligion = in.readString();
        this.UserHairColor = in.readString();
        this.UserEyeColor = in.readString();
        this.UserHeight = in.readString();
        this.UserBodyType = in.readString();
        this.UserEducation = in.readString();
        this.UserOccupation = in.readString();
        this.UserPoliticalViews = in.readString();
        this.UserOtherLanguages = in.readString();
        this.UserDescOneWord = in.readString();
        this.UserPassion = in.readString();
        this.UserDescription = in.readString();
        this.UserInfluencedBy = in.readString();
        this.UserPets = in.readString();
        this.UserFreeSpendTime = in.readString();
        this.UserCantLiveAbout = in.readString();
        this.UserFavMovies = in.readString();
        this.UserAlbanianSongs = in.readString();
        this.UserDrinks = in.readString();
        this.UserSmokes = in.readString();
        this.UserStatus = in.readString();
        this.UserImage = in.readString();
        this.UserMinAge = in.readString();
        this.UserMaxAge = in.readString();
        this.UserSubscriptionStatus = in.readString();
        this.UserHeritage = in.readString();
        this.UserInvisibleStatus = in.readString();
        this.UserPresence = in.readString();
        this.RowDistance = in.readString();
        this.Unit = in.readString();
        this.MilesDistance = in.readString();
        this.usertypeHomePage = in.readString();
        this.userlatitude = in.readString();
        this.UserLocation = in.readString();
        this.userlogitude = in.readString();
    }

    public static final Creator<UserModel> CREATOR = new Creator<UserModel>() {
        @Override
        public UserModel createFromParcel(Parcel source) {
            return new UserModel(source);
        }

        @Override
        public UserModel[] newArray(int size) {
            return new UserModel[size];
        }
    };
}
