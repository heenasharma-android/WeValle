package com.OldScreens;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.SigninSignup.ChatTab;
import com.SigninSignup.SettingActivity;
import com.adapter.RecyclerItemClickListener;
import com.adapter.UserImagesGalleryAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.models.SignupModel;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class OldProfile extends AppCompatActivity {

    ViewPager imageViewpager;
    AlbanianPreferances pref;
    SignupModel profilemodel;
    ImageView sendmessage;
    String userId;
    String UserId,UserName,UserImage;
    private RecyclerView userimagesgallery;
    private RelativeLayout matchCard, btn_edit_profile, btn_filter,sendmessagelayout,profilelayout;
    private TextView heritage, city, age, height, religion, languages, txt_matchcard;
    private TextView agetext, heighttext, religiontext, zodiactext;
    private TextView smoke, drink, pet, aboutme;
    private TextView age_label, zodiac_label, location_label, heritage_label, religion_label, height_label, languages_label, about_label;
    private TextView username, premiummember;
    private ImageView rightmenubutton, onlinenow;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_old_profile2);
        pref = new AlbanianPreferances(this);
        ImageView back=(ImageView)findViewById(R.id.img_back);
        ImageView img_rightmenu=(ImageView)findViewById(R.id.img_rightmenu);

        img_rightmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OldProfile.this, SettingActivity.class);
                startActivity(intent);
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        profilemodel = new SignupModel();
        userId="";
        userId = getIntent().getStringExtra("userId");
        imageViewpager = (ViewPager) findViewById(R.id.user_allimages);
        init();
        if (userId.equalsIgnoreCase(pref.getUserData().getUserId()))
        {
            sendmessagelayout.setVisibility(View.GONE);
            profilelayout.setVisibility(View.VISIBLE);
        }
        else {
            sendmessagelayout.setVisibility(View.VISIBLE);
            profilelayout.setVisibility(View.GONE);
        }
        if (userId == null || userId.equalsIgnoreCase("")) {
            getUserDetail(pref.getUserData().getUserId());
        } else {
            getUserDetail(userId);
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));
    }

    private void init() {
        premiummember = (TextView) findViewById(R.id.tv_premiumuser);
        username = (TextView) findViewById(R.id.tv_username);
        onlinenow = (ImageView) findViewById(R.id.tv_username_online);
        //btn_addtofav=(ImageView) findViewById(R.id.btn_addtofav);
        userimagesgallery = (RecyclerView) findViewById(R.id.gallery_ouser_images);
        userimagesgallery.setHasFixedSize(true);
        userimagesgallery.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false));
        age_label = (TextView) findViewById(R.id.txt_age_label);
        zodiac_label = (TextView) findViewById(R.id.txt_zodiac_label);
        location_label = (TextView) findViewById(R.id.txt_location_label);
        heritage_label = (TextView) findViewById(R.id.txt_heritage_label);
        religion_label = (TextView) findViewById(R.id.txt_religion_label);
        height_label = (TextView) findViewById(R.id.txt_height_label);
        languages_label = (TextView) findViewById(R.id.txt_languages_label);
        about_label = (TextView) findViewById(R.id.txt_about_label);
        // headertext = (TextView) findViewById(R.id.txt_headerlogo);
        //back = (RelativeLayout) findViewById(R.id.rl_back_header);
        // matchdetails = (ImageView) findViewById(R.id.btn_match_user);
        sendmessage = (ImageView) findViewById(R.id.btn_send_message);
        sendmessagelayout = (RelativeLayout) findViewById(R.id.rl_sendm);
        profilelayout = (RelativeLayout) findViewById(R.id.rl_profile);
        matchCard = (RelativeLayout) findViewById(R.id.btn_matchcard);
        txt_matchcard = (TextView) findViewById(R.id.txt_matchcard);
        btn_edit_profile = (RelativeLayout) findViewById(R.id.btn_edit_profile);
        btn_filter = (RelativeLayout) findViewById(R.id.btn_filter);
        btn_filter.setVisibility(View.GONE);
        matchCard.setVisibility(View.GONE);
        city = (TextView) findViewById(R.id.tv_locationtext);
        heritage = (TextView) findViewById(R.id.tv_heritagetext);
        age = (TextView) findViewById(R.id.tv_age_user);
        agetext = (TextView) findViewById(R.id.tv_agetext);
        heighttext = (TextView) findViewById(R.id.tv_heighttext);
        religiontext = (TextView) findViewById(R.id.tv_religiontext);
        zodiactext = (TextView) findViewById(R.id.tv_zodiactext);
        height = (TextView) findViewById(R.id.tv_height);
        religion = (TextView) findViewById(R.id.tv_religion);
        languages = (TextView) findViewById(R.id.tv_languages);
        smoke = (TextView) findViewById(R.id.tv_smoke);
        drink = (TextView) findViewById(R.id.tv_drink);
        pet = (TextView) findViewById(R.id.tv_pet);
        aboutme = (TextView) findViewById(R.id.tv_aboutme);
        btn_filter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),FilterActivity.class);
                startActivity(intent);
            }
        });
        btn_edit_profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(getApplicationContext(),EditProfileActivity.class);
                startActivity(intent);
            }
        });
        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(OldProfile.this,OldChatActivity.class);
                intent.putExtra("id",UserId);
                intent.putExtra("name",UserName);
                intent.putExtra("image",UserImage);
                startActivity(intent);

            }
        });
    }

    private void getUserDetail(final String profileVisitedID) {

        Log.d("sumit", "useris= " + pref.getUserData().getUserId());
        Log.d("sumit", "useris profileVisitedID= " + profileVisitedID);

        AlbanianApplication.showProgressDialog(this, "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "profile= " + response.toString());

                AlbanianApplication.hideProgressDialog(getApplicationContext());
                parseResponse(response.toString());


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getApplicationContext());

            }
        })

        {


            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "vieweingUserProfile");
                params.put("viewing_profile_id", profileVisitedID);
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseResponse(String Result) {

        Log.d(AlbanianConstants.TAG, "Profile Result" + Result);

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String errorCode = jObj.optString("ErrorCode");
                    String errorMessage = jObj.optString("ErrorMessage");


                    Log.d(AlbanianConstants.TAG, "" + errorCode);

                    if (errorCode.equalsIgnoreCase("1")) {

                        UserId = jObj.optString("UserId");
                        profilemodel.setUserId(UserId);

                        UserName = jObj.optString("UserName");
                        profilemodel.setUserName(UserName);

                        String UserEmail = jObj.optString("UserEmail");
                        profilemodel.setUserEmail(UserEmail);

                        String UserCountry = jObj.optString("UserCountry");
                        profilemodel.setUserCountry(UserCountry);

                        String UserState = jObj.optString("UserState");
                        profilemodel.setUserState(UserState);

                        String UserCity = jObj.optString("UserCity");
                        profilemodel.setUserCity(UserCity);

                        String UserHearAboutUs = jObj.optString("UserHearAboutUs");
                        profilemodel.setUserHearAboutUs(UserHearAboutUs);

                        String UserZipCode = jObj.optString("UserZipCode");
                        profilemodel.setUserZipCode(UserZipCode);

                        String UserFullName = jObj.optString("UserFullName");
                        profilemodel.setUserFullName(UserFullName);

                        String UserGender = jObj.optString("UserGender");
                        profilemodel.setUserGender(UserGender);

                        String UserDOB = jObj.optString("UserDOB");
                        profilemodel.setUserDOB(UserDOB);

                        String UserRegDate = jObj.optString("UserRegDate");
                        profilemodel.setUserRegDate(UserRegDate);

                        String UserStatus = jObj.optString("UserStatus");
                        profilemodel.setUserStatus(UserStatus);

                        String UserReligion = jObj.optString("UserReligion");
                        profilemodel.setUserReligion(UserReligion);

                        String UserHairColor = jObj.optString("UserHairColor");
                        profilemodel.setUserHairColor(UserHairColor);

                        String UserEyeColor = jObj.optString("UserEyeColor");
                        profilemodel.setUserEyeColor(UserEyeColor);

                        String UserHeight = jObj.optString("UserHeight");
                        profilemodel.setUserHeight(UserHeight);

                        String UserBodyType = jObj.optString("UserBodyType");
                        profilemodel.setUserBodyType(UserBodyType);

                        String UserEducation = jObj.optString("UserEducation");
                        profilemodel.setUserEducation(UserEducation);

                        String UserOccupation = jObj.optString("UserOccupation");
                        profilemodel.setUserOccupation(UserOccupation);

                        String UserPoliticalViews = jObj.optString("UserPoliticalViews");
                        profilemodel.setUserPoliticalViews(UserPoliticalViews);

                        String UserOtherLanguages = jObj.optString("UserOtherLanguages");
                        profilemodel.setUserOtherLanguages(UserOtherLanguages);

                        String UserDescOneWord = jObj.optString("UserDescOneWord");
                        profilemodel.setUserDescOneWord(UserDescOneWord);

                        String UserPassion = jObj.optString("UserPassion");
                        profilemodel.setUserPassion(UserPassion);

                        String UserInfluencedBy = jObj.optString("UserInfluencedBy");
                        profilemodel.setUserInfluencedBy(UserInfluencedBy);

                        String UserPets = jObj.optString("UserPets");
                        profilemodel.setUserPets(UserPets);

                        String UserFreeSpendTime = jObj.optString("UserFreeSpendTime");
                        profilemodel.setUserFreeSpendTime(UserFreeSpendTime);

                        String UserCantLiveAbout = jObj.optString("UserCantLiveAbout");
                        profilemodel.setUserCantLiveAbout(UserCantLiveAbout);

                        String UserFavMovies = jObj.optString("UserFavMovies");
                        profilemodel.setUserFavMovies(UserFavMovies);

                        String UserAlbanianSongs = jObj.optString("UserAlbanianSongs");
                        profilemodel.setUserAlbanianSongs(UserAlbanianSongs);

                        String UserDrinks = jObj.optString("UserDrinks");
                        profilemodel.setUserDrinks(UserDrinks);

                        String UserSmokes = jObj.optString("UserSmokes");
                        profilemodel.setUserSmokes(UserSmokes);

                        String UserLookingFor = jObj.optString("UserLookingFor");
                        profilemodel.setUserLookingFor(UserLookingFor);

                        String UserMaritialStatus = jObj.optString("UserMaritialStatus");
                        profilemodel.setUserMaritialStatus(UserMaritialStatus);

                        String UserChildren = jObj.optString("UserChildren");
                        profilemodel.setUserChildren(UserChildren);

                        String UserDescAbtDating = jObj.optString("UserDescAbtDating");
                        profilemodel.setUserDescAbtDating(UserDescAbtDating);


                        String UserLongestRelationShip = jObj.optString("UserLongestRelationShip");
                        profilemodel.setUserLongestRelationShip(UserLongestRelationShip);

                        String UserDateWhoHasKids = jObj.optString("UserDateWhoHasKids");
                        profilemodel.setUserDateWhoHasKids(UserDateWhoHasKids);

                        String UserDateSmokeChoice = jObj.optString("UserDateSmokeChoice");
                        profilemodel.setUserDateSmokeChoice(UserDateSmokeChoice);

                        String UserFavQuote = jObj.optString("UserFavQuote");
                        profilemodel.setUserFavQuote(UserFavQuote);

                        String UserInterests = jObj.optString("UserInterests");
                        profilemodel.setUserInterests(UserInterests);

                        String UserDescription = jObj.optString("UserDescription");
                        profilemodel.setUserDescription(UserDescription);

                        String UserFirstDate = jObj.optString("UserFirstDate");
                        profilemodel.setUserFirstDate(UserFirstDate);

                        String UserLong = jObj.optString("UserLong");
                        profilemodel.setUserLong(UserLong);

                        String UserLat = jObj.optString("UserLat");
                        profilemodel.setUserLat(UserLat);

                        String UserDeviceTocken = jObj.optString("UserDeviceTocken");
                        profilemodel.setUserDeviceTocken(UserDeviceTocken);

                        String UserJabberId = jObj.optString("UserJabberId");
                        profilemodel.setUserJabberId(UserJabberId);

                        String UserPresence = jObj.optString("UserPresence");
                        profilemodel.setUserPresence(UserPresence);

                        String UserLastLogin = jObj.optString("UserLastLogin");
                        profilemodel.setUserLastLogin(UserLastLogin);

                        String UserMinAge = jObj.optString("UserMinAge");
                        profilemodel.setUserMinAge(UserMinAge);

                        String UserMaxAge = jObj.optString("UserMaxAge");
                        profilemodel.setUserMaxAge(UserMaxAge);

                        String EmailOptIn = jObj.optString("EmailOptIn");
                        profilemodel.setEmailOptIn(EmailOptIn);

                        String UserDeactivationCause = jObj.optString("UserDeactivationCause");
                        profilemodel.setUserDeactivationCause(UserDeactivationCause);

                        String UserDeactivationText = jObj.optString("UserDeactivationText");
                        profilemodel.setUserDeactivationText(UserDeactivationText);

                        String UserSubscriptionStatus = jObj.optString("UserSubscriptionStatus");
                        profilemodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                        String UserInvisibleStatus = jObj.optString("UserInvisibleStatus");
                        profilemodel.setUserInvisibleStatus(UserInvisibleStatus);

                        UserImage = jObj.optString("UserImage");
                        profilemodel.setUserImage(UserImage);

                        String Age = jObj.optString("Age");
                        profilemodel.setAge(Integer.parseInt(Age));

                        String Zodiac = jObj.optString("Zodiac");
                        profilemodel.setZodiac(Zodiac);

                        String SubscriptionStatus = jObj.optString("SubscriptionStatus");
                        profilemodel.setSubscriptionStatus(SubscriptionStatus);

                        String IsBlocked = jObj.optString("IsBlocked");
                        profilemodel.setIsBlocked(IsBlocked);

                        String IsFavourite = jObj.optString("IsFavourite");
                        profilemodel.setIsFavourite(IsFavourite);

                        String BlockedMessage = jObj.optString("BlockedMessage");
                        profilemodel.setBlockedMessage(BlockedMessage);

                        JSONArray jsonArrayHeritage = jObj.getJSONArray("UserHeritage");

                        StringBuilder stringBuilder = new StringBuilder();

                        for (int i = 0; i < jsonArrayHeritage.length(); i++) {

                            JSONObject jsonObjectHeritage = jsonArrayHeritage.getJSONObject(i);

                            String heritage = jsonObjectHeritage.optString("Heritage");

                            stringBuilder.append(heritage);

                            if (i != jsonArrayHeritage.length() - 1) {
                                stringBuilder.append(", ");
                            }


                        }

                        profilemodel.setUserHeritageInterestedIn(stringBuilder.toString());


                        JSONArray jArray_response = jObj.getJSONArray("UserImages");

                        ArrayList<UserImagesModel> images = new ArrayList<>();

                        for (int i = 0; i < jArray_response.length(); i++) {

                            UserImagesModel imagesmodel = new UserImagesModel();

                            JSONObject jObj_images = jArray_response.getJSONObject(i);

                            String ImageId = jObj_images.optString("ImageId");
                            imagesmodel.setImageId(ImageId);

                            String UserImageUrl = jObj_images.optString("UserImageUrl");
                            imagesmodel.setUserImageUrl(UserImageUrl);

                            images.add(imagesmodel);

                        }

                        profilemodel.setUserImages(images);
                        if (UserPresence.equalsIgnoreCase("1")){
                            onlinenow.setVisibility(View.VISIBLE);
                        }
                        else {
                            onlinenow.setVisibility(View.GONE);
                        }
                        if (UserSubscriptionStatus.equalsIgnoreCase("1")){
                            premiummember.setVisibility(View.VISIBLE);
                        }
                        else {
                            premiummember.setVisibility(View.GONE);
                        }
                        username.setText(!UserName.equals("") ? (UserName) : getString(R.string.Unanswered));
                        agetext.setText(!Age.equals("") ? (Age) : getString(R.string.Unanswered));
                        heighttext.setText(!UserHeight.equals("") ? (UserHeight) : getString(R.string.Unanswered));
                        religiontext.setText(!UserReligion.equals("") ? (UserReligion) : getString(R.string.Unanswered));
                        zodiactext.setText(!Zodiac.equals("") ? (Zodiac) : getString(R.string.Unanswered));
                        height.setText(!UserHeight.equals("") ? (UserHeight) : getString(R.string.Unanswered));
                        religion.setText(!UserReligion.equals("") ? (UserReligion) : getString(R.string.Unanswered));
                        languages.setText(!UserOtherLanguages.equals("") ? (UserOtherLanguages) : getString(R.string.Unanswered));
                        smoke.setText(!UserSmokes.equals("") ? (UserSmokes) : getString(R.string.Unanswered));
                        drink.setText(!UserDrinks.equals("") ? (UserDrinks) : getString(R.string.Unanswered));
                        pet.setText(!UserPets.equals("") ? (UserPets) : getString(R.string.Unanswered));
                        aboutme.setText(!UserDescription.equals("") ? (UserDescription) : getString(R.string.Unanswered));
                        city.setText(!UserCity.equals("") ? (UserCity + ", " + UserCountry) : getString(R.string.Unanswered));
                        heritage.setText(!stringBuilder.toString().equals("") ? (stringBuilder.toString()) : getString(R.string.Unanswered));
                        setProfileDetail(profilemodel);
                    } else if (errorCode.equals("0")) {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(this, mTitle,
                                errorMessage, false);
                    }


                } catch (Exception e) {
                    // TODO Auto-generated catch block
                    Log.d("sumit", "profile exception= " + e);
                }

            }

        }

    }

    private void setProfileDetail(SignupModel profilemodel) {
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this, profilemodel.getUserImages());
        imageViewpager.setAdapter(customPagerAdapter);
        UserImagesGalleryAdapter adapter = new UserImagesGalleryAdapter(OldProfile.this, profilemodel.getUserImages());
        userimagesgallery.setAdapter(adapter);
        userimagesgallery.addOnItemTouchListener(
                new RecyclerItemClickListener(OldProfile.this, new RecyclerItemClickListener.OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        // TODO Handle item click
                        Log.d("sumit", "user images adapter clicked...");
                    }
                })
        );


        Log.d("sucess", "sucess" + profilemodel.getUserReligion());
    }


    class CustomPagerAdapter extends PagerAdapter {

        Context mContext;
        LayoutInflater mLayoutInflater;
        ArrayList<UserImagesModel> userImagesModels;

        public CustomPagerAdapter(Context context, ArrayList<UserImagesModel> userImagesModels) {
            this.mContext = context;
            this.userImagesModels = userImagesModels;
            mLayoutInflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        }

        @Override
        public int getCount() {
            return userImagesModels.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == ((LinearLayout) object);
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            View itemView = mLayoutInflater.inflate(R.layout.pager_item, container, false);

            ImageView imageView = (ImageView) itemView.findViewById(R.id.imageView);
            //imageView.setImageResource(mResources[position]);
            Picasso.with(getApplicationContext()).load(userImagesModels.get(position).getUserImageUrl()).fit().centerCrop().into(imageView);

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}
