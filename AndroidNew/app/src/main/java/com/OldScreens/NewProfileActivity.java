package com.OldScreens;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.MainFragments.ReportUserFragment;
import com.MainFragments.SlideActivity;
import com.SigninSignup.SettingActivity;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.FragmentChangeListener;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.editprofile.PasswordFragment;
import com.models.SignupModel;
import com.models.UserImagesModel;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class NewProfileActivity  extends AppCompatActivity {

    ViewPager imageViewpager;
    AlbanianPreferances pref;
    SignupModel profilemodel;
    FloatingActionButton sendmessage,editProfile;
    String userId;
    String UserId,UserName,UserImage;
    ImageView btn_addtofav;
    private TextView tvName, tvHeritage,tvAbout,tvLanguage,tvLocation,tvProfession,tvAge;


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_profile);
        pref = new AlbanianPreferances(this);
        ImageView back=(ImageView)findViewById(R.id.img_back);
        userId = getIntent().getStringExtra("userId");
        ImageView img_rightmenu=(ImageView)findViewById(R.id.img_rightmenu);
        btn_addtofav=(ImageView) findViewById(R.id.btn_addtofav);

        img_rightmenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (userId.equalsIgnoreCase(pref.getUserData().getUserId())) {
                    Intent intent = new Intent(NewProfileActivity.this, SettingActivity.class);
                    startActivity(intent);
                }
                else {
                    toggleDrawer();
                }
            }
        });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });


        imageViewpager = (ViewPager) findViewById(R.id.user_allimages);
        init();
        if (userId == null || userId.equalsIgnoreCase("")) {
            getUserDetail(pref.getUserData().getUserId());

        } else {
            if (userId.equalsIgnoreCase(pref.getUserData().getUserId())){
                editProfile.setVisibility(View.VISIBLE);
                sendmessage.setVisibility(View.GONE);
            }
            else {
                editProfile.setVisibility(View.GONE);
                sendmessage.setVisibility(View.VISIBLE);
            }
            getUserDetail(userId);
        }

        Window window = this.getWindow();
        window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        window.setStatusBarColor(ContextCompat.getColor(this, R.color.purp));


    }

    private void addToFavorite() {

        AlbanianApplication.showProgressDialog(getApplicationContext(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "addtofav= " + response.toString());

                parseFavResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getApplicationContext());

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
            protected Map<String, String> getParams()
            {
                Log.d("sumit","user_favorite_from "+pref.getUserData().getUserId());
             //   Log.d("sumit","user_favorite_to "+profileVisitedID);

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "insertuserfavourite");
                params.put("user_favorite_from", pref.getUserData().getUserId());
                params.put("user_favorite_to", userId);
//                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void blockUnblockUser(final String blockstring) {

        AlbanianApplication.showProgressDialog(getApplicationContext(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "blockUnblockUser= "+response.toString());

                parseBlockResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getApplicationContext());

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
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", blockstring);
                params.put("block_from", pref.getUserData().getUserId());
                params.put("block_to", userId);
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseBlockResponse(String Result) {


        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getApplicationContext(), mTitle,
                                ErrorMessage, false);


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getApplicationContext(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getApplicationContext(), mTitle,
                                ErrorMessage, false);
                    }


                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","block user exception= "+e);
                }

            }

        }

    }

    private void parseFavResponse(String Result) {


        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {
                        String IsFavorite = jObj.optString("IsFavorite");

                        if (IsFavorite.equals("1")) {

                            Toast.makeText(getApplicationContext(),"Added to favorites!",Toast.LENGTH_SHORT).show();

                            btn_addtofav.setImageResource(R.drawable.fav_selected);

                        }
                        else
                        {
                            btn_addtofav.setImageResource(R.drawable.fav_unselected);
                        }


//                        String mTitle = getResources().getString(R.string.app_name);

//                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
//                                ErrorMessage, false);




                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getApplicationContext(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getApplicationContext(), mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","block user exception= "+e);
                }

            }

        }

    }

    private void toggleDrawer() {



        android.app.AlertDialog.Builder builderSingle;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(NewProfileActivity.this, android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new android.app.AlertDialog.Builder(
                    NewProfileActivity.this);
        }


//      builderSingle.setTitle("Upload image");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                NewProfileActivity.this,
                android.R.layout.select_dialog_item);


        arrayAdapter.add(getResources().getString(R.string.addtofavorites));
        arrayAdapter.add(getResources().getString(R.string.reportuser));

        if (profilemodel.getIsBlocked().equals("1")) {

            arrayAdapter.add(getResources().getString(R.string.blockuser));

        }
        else
        {
            arrayAdapter.add(getResources().getString(R.string.unblockuser));
        }




        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        long strName = arrayAdapter.getItemId(which);


                        if (strName == 0) {
                            // comment report

                           // addToFavorite();

                        } else if (strName == 1) {
                            // comment report

                            //reportUser();

                        } else if (strName == 2) {
                            // comment report

                            if (profilemodel.getIsBlocked().equals("1")) {

                                //blockUnblockUser("blockuser");

                            }
                            else
                            {
                               // blockUnblockUser("unblockuser");
                            }


                        }


                    }
                });

        builderSingle.show();


    }

    private void reportUser() {


//        Bundle bundle = new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, profileVisitedID);
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);
//
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new ReportUserFragment(), false, true, bundle);
        Fragment fr = new ReportUserFragment();
        FragmentChangeListener fc = (FragmentChangeListener) getApplicationContext();
        fc.replaceFragment(fr, "Password");


    }

    private void init() {
        tvName = (TextView) findViewById(R.id.tv_name);
        sendmessage = (FloatingActionButton) findViewById(R.id.btn_send_message);
        editProfile = (FloatingActionButton) findViewById(R.id.btn_edit_profile);
        tvHeritage = (TextView) findViewById(R.id.tv_heritage);
        tvAbout = (TextView) findViewById(R.id.tv_about);
        tvLanguage = (TextView) findViewById(R.id.tv_language);
        tvLocation= (TextView) findViewById(R.id.tv_location);
        tvProfession = (TextView) findViewById(R.id.tv_profession);
        tvAge = (TextView) findViewById(R.id.tv_age);

        sendmessage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewProfileActivity.this,OldChatActivity.class);
                intent.putExtra("id",UserId);
                intent.putExtra("name",UserName);
                intent.putExtra("image",UserImage);
                startActivity(intent);

            }
        });

        editProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(NewProfileActivity.this,EditProfile.class);
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
                    profilemodel = new SignupModel();


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

                        Log.d("profilemodel", "profilemodel" + images);

                        profilemodel.setUserImages(images);
                        tvName.setText(!UserName.equals("") ? (UserName+",") : getString(R.string.Unanswered));
                        tvLanguage.setText(!UserOtherLanguages.equals("") ? (UserOtherLanguages) : getString(R.string.Unanswered));
                        tvAbout.setText(!UserDescription.equals("") ? (UserDescription) : getString(R.string.Unanswered));
                        tvLocation.setText(!UserCity.equals("") ? (UserCity + ", " + UserCountry) : getString(R.string.Unanswered));
                        tvHeritage.setText(!stringBuilder.toString().equals("") ? (stringBuilder.toString()) : getString(R.string.Unanswered));
                        tvAge.setText(!Age.equals("") ? (Age) : getString(R.string.Unanswered));
                        tvProfession.setText(!UserPassion.equals("") ? (UserPassion) : getString(R.string.Unanswered));
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
        Log.d("sucess", "sucess" + profilemodel.getUserImages());
        CustomPagerAdapter customPagerAdapter = new CustomPagerAdapter(this, profilemodel.getUserImages());
        imageViewpager.setAdapter(customPagerAdapter);
      ///  Log.d("sucess", "sucess" + profilemodel.getUserImages());
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
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent=new Intent(mContext, SlideActivity.class);
                    intent.putParcelableArrayListExtra("images",userImagesModels);
                    mContext.startActivity(intent);
                }
            });

            container.addView(itemView);

            return itemView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((LinearLayout) object);
        }
    }


}
