package com.models;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;

/**
 * Created by Sumit on 27/09/2015.
 */
public class SignupModel implements Parcelable {


    public String UserId;
    public String UserName;
    public String UserPassword;
    public String UserEmail;
    public String UserActivationCode;
    public String UserCountry;
    public String UserState;
    public String UserCity;
    public String UserHearAboutUs;
    public String UserZipCode;
    public String UserFullName;
    public String UserGender;
    public String UserDOB;
    public String UserRegDate;
    public String UserStatus;
//    private String UserInterestedIn;
public String UserReligion;
    public String UserHairColor;
    public String UserEyeColor;
    public String UserHeight;
    public String UserBodyType;
    public String UserEducation;
    public String UserOccupation;
    public String UserPoliticalViews;
    public String UserOtherLanguages;
    public String UserDescOneWord;
    public String UserPassion;
//    private String UserFriendsDesc;
public String UserInfluencedBy;
    public String UserPets;
    public String UserFreeSpendTime;
    public String UserCantLiveAbout;
    public String UserFavMovies;
    public String UserAlbanianSongs;
    public String UserDrinks;
    public String UserSmokes;
    public String UserLookingFor;
    public String UserMaritialStatus;
    public String UserChildren;
    public String UserDescAbtDating;
    public String UserLongestRelationShip;
    public String UserDateWhoHasKids;
    public String UserDateSmokeChoice;
    public String UserFavQuote;
    public String UserInterests;
    public String UserDescription;
    public String UserFirstDate;
    public String UserLong;
    public String UserLat;
    public String UserJabberId;
    public String UserDeviceTocken;

    public String getEmailOptIn() {
        return EmailOptIn;
    }

    public void setEmailOptIn(String emailOptIn) {
        EmailOptIn = emailOptIn;
    }

    public String EmailOptIn;
    public String UserPresence;
    public String UserLastLogin;
    public String UserMinAge;
    public String UserMaxAge;
    public String UserDeactivationCause;
    public String UserDeactivationText;
    public String UserSubscriptionStatus;
    public String UserInvisibleStatus;
    public String UserImage;
    public String UserImageUrl;
    public String UserHeritageInterestedIn;
    public int Age;
    public String Zodiac;
    public String SubscriptionStatus;
    public String IsBlocked;

    public String getIsFavourite() {
        return IsFavourite;
    }

    public void setIsFavourite(String isFavourite) {
        IsFavourite = isFavourite;
    }

    public String IsFavourite;
    public String BlockedMessage;
    public ArrayList<UserImagesModel> UserImages = new ArrayList<>();






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

    public String getUserActivationCode() {
        return UserActivationCode;
    }

    public void setUserActivationCode(String userActivationCode) {
        UserActivationCode = userActivationCode;
    }

    public String getUserHeritageInterestedIn() {
        return UserHeritageInterestedIn;
    }

    public void setUserHeritageInterestedIn(String userHeritageInterestedIn) {
        UserHeritageInterestedIn = userHeritageInterestedIn;
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

    public String getUserHearAboutUs() {
        return UserHearAboutUs;
    }

    public void setUserHearAboutUs(String userHearAboutUs) {
        UserHearAboutUs = userHearAboutUs;
    }

    public String getUserZipCode() {
        return UserZipCode;
    }

    public void setUserZipCode(String userZipCode) {
        UserZipCode = userZipCode;
    }

    public String getUserFullName() {
        return UserFullName;
    }

    public void setUserFullName(String userFullName) {
        UserFullName = userFullName;
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

    public String getUserRegDate() {
        return UserRegDate;
    }

    public void setUserRegDate(String userRegDate) {
        UserRegDate = userRegDate;
    }

    public String getUserStatus() {
        return UserStatus;
    }

    public void setUserStatus(String userStatus) {
        UserStatus = userStatus;
    }

//    public String getUserInterestedIn() {
//        return UserInterestedIn;
//    }
//
//    public void setUserInterestedIn(String userInterestedIn) {
//        UserInterestedIn = userInterestedIn;
//    }

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

//    public String getUserFriendsDesc() {
//        return UserFriendsDesc;
//    }
//
//    public void setUserFriendsDesc(String userFriendsDesc) {
//        UserFriendsDesc = userFriendsDesc;
//    }

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

    public String getUserLookingFor() {
        return UserLookingFor;
    }

    public void setUserLookingFor(String userLookingFor) {
        UserLookingFor = userLookingFor;
    }

    public String getUserMaritialStatus() {
        return UserMaritialStatus;
    }

    public void setUserMaritialStatus(String userMaritialStatus) {
        UserMaritialStatus = userMaritialStatus;
    }

    public String getUserChildren() {
        return UserChildren;
    }

    public void setUserChildren(String userChildren) {
        UserChildren = userChildren;
    }

    public String getUserDescAbtDating() {
        return UserDescAbtDating;
    }

    public void setUserDescAbtDating(String userDescAbtDating) {
        UserDescAbtDating = userDescAbtDating;
    }

    public String getUserLongestRelationShip() {
        return UserLongestRelationShip;
    }

    public void setUserLongestRelationShip(String userLongestRelationShip) {
        UserLongestRelationShip = userLongestRelationShip;
    }

    public String getUserDateWhoHasKids() {
        return UserDateWhoHasKids;
    }

    public void setUserDateWhoHasKids(String userDateWhoHasKids) {
        UserDateWhoHasKids = userDateWhoHasKids;
    }

    public String getUserDateSmokeChoice() {
        return UserDateSmokeChoice;
    }

    public void setUserDateSmokeChoice(String userDateSmokeChoice) {
        UserDateSmokeChoice = userDateSmokeChoice;
    }

    public String getUserFavQuote() {
        return UserFavQuote;
    }

    public void setUserFavQuote(String userFavQuote) {
        UserFavQuote = userFavQuote;
    }

    public String getUserInterests() {
        return UserInterests;
    }

    public void setUserInterests(String userInterests) {
        UserInterests = userInterests;
    }

    public String getUserDescription() {
        return UserDescription;
    }

    public void setUserDescription(String userDescription) {
        UserDescription = userDescription;
    }

    public String getUserFirstDate() {
        return UserFirstDate;
    }

    public void setUserFirstDate(String userFirstDate) {
        UserFirstDate = userFirstDate;
    }

    public String getUserLong() {
        return UserLong;
    }

    public void setUserLong(String userLong) {
        UserLong = userLong;
    }

    public String getUserLat() {
        return UserLat;
    }

    public void setUserLat(String userLat) {
        UserLat = userLat;
    }

    public String getUserJabberId() {
        return UserJabberId;
    }

    public void setUserJabberId(String userJabberId) {
        UserJabberId = userJabberId;
    }

    public String getUserDeviceTocken() {
        return UserDeviceTocken;
    }

    public void setUserDeviceTocken(String userDeviceTocken) {
        UserDeviceTocken = userDeviceTocken;
    }

//    public String getUserProfileImage() {
//        return UserProfileImage;
//    }
//
//    public void setUserProfileImage(String userProfileImage) {
//        UserProfileImage = userProfileImage;
//    }

    public String getUserPresence() {
        return UserPresence;
    }

    public void setUserPresence(String userPresence) {
        UserPresence = userPresence;
    }

    public String getUserLastLogin() {
        return UserLastLogin;
    }

    public void setUserLastLogin(String userLastLogin) {
        UserLastLogin = userLastLogin;
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

    public String getUserDeactivationCause() {
        return UserDeactivationCause;
    }

    public void setUserDeactivationCause(String userDeactivationCause) {
        UserDeactivationCause = userDeactivationCause;
    }

    public String getUserDeactivationText() {
        return UserDeactivationText;
    }

    public void setUserDeactivationText(String userDeactivationText) {
        UserDeactivationText = userDeactivationText;
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

    public String getUserImage() {
        return UserImage;
    }

    public void setUserImage(String userImage) {
        UserImage = userImage;
    }

    public String getUserImageUrl() {
        return UserImageUrl;
    }

    public void setUserImageUrl(String userImageUrl) {
        UserImageUrl = userImageUrl;
    }

    public Integer getAge() {
        return Age;
    }

    public void setAge(int age) {
        Age = age;
    }

    public String getZodiac() {
        return Zodiac;
    }

    public void setZodiac(String zodiac) {
        Zodiac = zodiac;
    }

    public String getSubscriptionStatus() {
        return SubscriptionStatus;
    }

    public void setSubscriptionStatus(String subscriptionStatus) {
        SubscriptionStatus = subscriptionStatus;
    }

    public String getIsBlocked() {
        return IsBlocked;
    }

    public void setIsBlocked(String isBlocked) {
        IsBlocked = isBlocked;
    }

    public String getBlockedMessage() {
        return BlockedMessage;
    }

    public void setBlockedMessage(String blockedMessage) {
        BlockedMessage = blockedMessage;
    }

    public ArrayList<UserImagesModel> getUserImages() {
        return UserImages;
    }

    public void setUserImages(ArrayList<UserImagesModel> userImages) {
        UserImages = userImages;
    }

    public SignupModel() {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(UserId);
        dest.writeString(UserName);
        dest.writeString(UserPassword);
        dest.writeString(UserEmail);
        dest.writeString(UserActivationCode);
        dest.writeString(UserCountry);
        dest.writeString(UserState);
        dest.writeString(UserCity);
        dest.writeString(UserHearAboutUs);
        dest.writeString(UserZipCode);
        dest.writeString(UserFullName);
        dest.writeString(UserGender);
        dest.writeString(UserDOB);
        dest.writeString(UserRegDate);
        dest.writeString(UserStatus);
        dest.writeString(UserReligion);
        dest.writeString(UserHairColor);
        dest.writeString(UserEyeColor);
        dest.writeString(UserHeight);
        dest.writeString(UserBodyType);
        dest.writeString(UserEducation);
        dest.writeString(UserOccupation);
        dest.writeString(UserPoliticalViews);
        dest.writeString(UserOtherLanguages);
        dest.writeString(UserDescOneWord);
        dest.writeString(UserPassion);
        dest.writeString(UserInfluencedBy);
        dest.writeString(UserPets);
        dest.writeString(UserFreeSpendTime);
        dest.writeString(UserCantLiveAbout);
        dest.writeString(UserFavMovies);
        dest.writeString(UserAlbanianSongs);
        dest.writeString(UserDrinks);
        dest.writeString(UserSmokes);
        dest.writeString(UserLookingFor);
        dest.writeString(UserMaritialStatus);
        dest.writeString(UserChildren);
        dest.writeString(UserDescAbtDating);
        dest.writeString(UserLongestRelationShip);
        dest.writeString(UserDateWhoHasKids);
        dest.writeString(UserDateSmokeChoice);
        dest.writeString(UserFavQuote);
        dest.writeString(UserInterests);
        dest.writeString(UserDescription);
        dest.writeString(UserFirstDate);
        dest.writeString(UserLong);
        dest.writeString(UserLat);
        dest.writeString(UserJabberId);
        dest.writeString(UserDeviceTocken);
        dest.writeString(EmailOptIn);
        dest.writeString(UserPresence);
        dest.writeString(UserLastLogin);
        dest.writeString(UserMinAge);
        dest.writeString(UserMaxAge);
        dest.writeString(UserDeactivationCause);
        dest.writeString(UserDeactivationText);
        dest.writeString(UserSubscriptionStatus);
        dest.writeString(UserInvisibleStatus);
        dest.writeString(UserImage);
        dest.writeString(UserImageUrl);
        dest.writeString(UserHeritageInterestedIn);
        dest.writeInt(Age);
        dest.writeString(Zodiac);
        dest.writeString(SubscriptionStatus);
        dest.writeString(IsBlocked);
        dest.writeString(IsFavourite);
        dest.writeString(BlockedMessage);
        dest.writeTypedList(UserImages);
    }

    protected SignupModel(Parcel in) {
        UserId = in.readString();
        UserName = in.readString();
        UserPassword = in.readString();
        UserEmail = in.readString();
        UserActivationCode = in.readString();
        UserCountry = in.readString();
        UserState = in.readString();
        UserCity = in.readString();
        UserHearAboutUs = in.readString();
        UserZipCode = in.readString();
        UserFullName = in.readString();
        UserGender = in.readString();
        UserDOB = in.readString();
        UserRegDate = in.readString();
        UserStatus = in.readString();
        UserReligion = in.readString();
        UserHairColor = in.readString();
        UserEyeColor = in.readString();
        UserHeight = in.readString();
        UserBodyType = in.readString();
        UserEducation = in.readString();
        UserOccupation = in.readString();
        UserPoliticalViews = in.readString();
        UserDescOneWord = in.readString();
        UserPassion = in.readString();
        UserInfluencedBy = in.readString();
        UserPets = in.readString();
        UserFreeSpendTime = in.readString();
        UserCantLiveAbout = in.readString();
        UserFavMovies = in.readString();
        UserAlbanianSongs = in.readString();
        UserDrinks = in.readString();
        UserSmokes = in.readString();
        UserLookingFor = in.readString();
        UserMaritialStatus = in.readString();
        UserChildren = in.readString();
        UserDescAbtDating = in.readString();
        UserLongestRelationShip = in.readString();
        UserDateWhoHasKids = in.readString();
        UserDateSmokeChoice = in.readString();
        UserFavQuote = in.readString();
        UserInterests = in.readString();
        UserDescription = in.readString();
        UserFirstDate = in.readString();
        UserLong = in.readString();
        UserLat = in.readString();
        UserJabberId = in.readString();
        UserDeviceTocken = in.readString();
        EmailOptIn = in.readString();
        UserPresence = in.readString();
        UserLastLogin = in.readString();
        UserMinAge = in.readString();
        UserMaxAge = in.readString();
        UserDeactivationCause = in.readString();
        UserDeactivationText = in.readString();
        UserSubscriptionStatus = in.readString();
        UserInvisibleStatus = in.readString();
        UserImage = in.readString();
        UserImageUrl = in.readString();
        UserHeritageInterestedIn = in.readString();
        Age = in.readInt();
        Zodiac = in.readString();
        SubscriptionStatus = in.readString();
        IsBlocked = in.readString();
        IsFavourite = in.readString();
        BlockedMessage = in.readString();
        UserImages = in.createTypedArrayList(UserImagesModel.CREATOR);
    }

    public static final Creator<SignupModel> CREATOR = new Creator<SignupModel>() {
        @Override
        public SignupModel createFromParcel(Parcel source) {
            return new SignupModel(source);
        }

        @Override
        public SignupModel[] newArray(int size) {
            return new SignupModel[size];
        }
    };
}
