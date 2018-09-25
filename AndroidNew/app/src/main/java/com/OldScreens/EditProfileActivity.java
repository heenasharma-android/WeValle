package com.OldScreens;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.editprofile.SignUpEighthEditFragment;
import com.editprofile.SignUpFourthEditFragment;
import com.editprofile.SignUpNinthEditFragment;
import com.editprofile.SignUpSecondEditFragment;
import com.editprofile.SignUpSixthEditFragment;
import com.editprofile.SignUpThirdEditFragment;
import com.editprofile.SignUpThirteenthEditFragment;
import com.models.SignupModel;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;

public class EditProfileActivity extends FragmentActivity implements View.OnClickListener {

    AlbanianPreferances pref;
    SignupModel profilemodel;
    private TextView headertext;
    private RelativeLayout back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comman);
        ButterKnife.bind(this);
        pref = new AlbanianPreferances(this);
        profilemodel=new SignupModel();
        initViews();
        if (AlbanianApplication.isInternetOn(this)) {
            getUserDetail(pref.getUserData().getUserId());
        } else {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(this, mTitle,
                    getResources().getString(R.string.nointernet), false);
        }
    }

    private void initViews() {
        headertext = (TextView) findViewById(R.id.txt_headerlogo);
        back = (RelativeLayout) findViewById(R.id.rl_back_header);
        headertext.setText(getResources().getString(R.string.edit_profile));
        RelativeLayout tvHowTall = (RelativeLayout) findViewById(R.id.rl_how_tall);
        RelativeLayout tvYourReligion = (RelativeLayout) findViewById(R.id.rl_your_religion);
        RelativeLayout tvLanguageSpeak = (RelativeLayout) findViewById(R.id.rl_langauage_speak);
        RelativeLayout tvYourPets = (RelativeLayout) findViewById(R.id.rl_your_pets);
        RelativeLayout tvDrink = (RelativeLayout) findViewById(R.id.rl_drink);
        RelativeLayout tvSmoke = (RelativeLayout) findViewById(R.id.rl_smoke);
        RelativeLayout tvYourDescription = (RelativeLayout) findViewById(R.id.rl_your_desc);
        tvHowTall.setOnClickListener(this);
        tvYourReligion.setOnClickListener(this);
        tvLanguageSpeak.setOnClickListener(this);
        tvYourPets.setOnClickListener(this);
        tvDrink.setOnClickListener(this);
        tvSmoke.setOnClickListener(this);
        back.setOnClickListener(this);
        tvYourDescription.setOnClickListener(this);

    }

    public void getUserDetail(String userId){
        Log.d("sumit", "useris= " + pref.getUserData().getUserId());
        Log.d("sumit", "useris profileVisitedID= " + userId);

        AlbanianApplication.showProgressDialog(this, "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "profile= "+response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getApplicationContext());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
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


                params.put("api_name", "vieweingUserProfile");
                params.put("viewing_profile_id", pref.getUserData().getUserId());
                params.put("user_id", userId);
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);


    }

    private void parseResponse(String Result) {
        if (Result != null) {
            {
                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");

                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);


                        String UserId=jObj.optString("UserId");
                        profilemodel.setUserId(UserId);

                        String UserName=jObj.optString("UserName");
                        profilemodel.setUserName(UserName);

                        String UserEmail=jObj.optString("UserEmail");
                        profilemodel.setUserEmail(UserEmail);

                        String UserCountry=jObj.optString("UserCountry");
                        profilemodel.setUserCountry(UserCountry);

                        Log.d(AlbanianConstants.TAG,"edit prfileUserCountry = "+UserCountry);

                        String UserState=jObj.optString("UserState");
                        profilemodel.setUserState(UserState);

                        String UserCity=jObj.optString("UserCity");
                        profilemodel.setUserCity(UserCity);

                        String UserHearAboutUs=jObj.optString("UserHearAboutUs");
                        profilemodel.setUserHearAboutUs(UserHearAboutUs);

                        String UserZipCode=jObj.optString("UserZipCode");
                        profilemodel.setUserZipCode(UserZipCode);

                        String UserFullName=jObj.optString("UserFullName");
                        profilemodel.setUserFullName(UserFullName);

                        String UserGender=jObj.optString("UserGender");
                        profilemodel.setUserGender(UserGender);

                        String UserDOB=jObj.optString("UserDOB");
                        profilemodel.setUserDOB(UserDOB);

                        String UserRegDate=jObj.optString("UserRegDate");
                        profilemodel.setUserRegDate(UserRegDate);

                        String UserStatus=jObj.optString("UserStatus");
                        profilemodel.setUserStatus(UserStatus);

                        String UserReligion=jObj.optString("UserReligion");
                        profilemodel.setUserReligion(UserReligion);

                        String UserHairColor=jObj.optString("UserHairColor");
                        profilemodel.setUserHairColor(UserHairColor);

                        String UserEyeColor=jObj.optString("UserEyeColor");
                        profilemodel.setUserEyeColor(UserEyeColor);

                        String UserHeight=jObj.optString("UserHeight");
                        profilemodel.setUserHeight(UserHeight);

                        String UserBodyType=jObj.optString("UserBodyType");
                        profilemodel.setUserBodyType(UserBodyType);

                        String UserEducation=jObj.optString("UserEducation");
                        profilemodel.setUserEducation(UserEducation);

                        String UserOccupation=jObj.optString("UserOccupation");
                        profilemodel.setUserOccupation(UserOccupation);

                        String UserPoliticalViews=jObj.optString("UserPoliticalViews");
                        profilemodel.setUserPoliticalViews(UserPoliticalViews);

                        String UserOtherLanguages=jObj.optString("UserOtherLanguages");
                        profilemodel.setUserOtherLanguages(UserOtherLanguages);

                        String UserDescOneWord=jObj.optString("UserDescOneWord");
                        profilemodel.setUserDescOneWord(UserDescOneWord);

                        String UserPassion=jObj.optString("UserPassion");
                        profilemodel.setUserPassion(UserPassion);

                        String UserInfluencedBy=jObj.optString("UserInfluencedBy");
                        profilemodel.setUserInfluencedBy(UserInfluencedBy);

                        String UserPets=jObj.optString("UserPets");
                        profilemodel.setUserPets(UserPets);

                        String UserFreeSpendTime=jObj.optString("UserFreeSpendTime");
                        profilemodel.setUserFreeSpendTime(UserFreeSpendTime);

                        String UserCantLiveAbout=jObj.optString("UserCantLiveAbout");
                        profilemodel.setUserCantLiveAbout(UserCantLiveAbout);

                        String UserFavMovies=jObj.optString("UserFavMovies");
                        profilemodel.setUserFavMovies(UserFavMovies);

                        String UserAlbanianSongs=jObj.optString("UserAlbanianSongs");
                        profilemodel.setUserAlbanianSongs(UserAlbanianSongs);

                        String UserDrinks=jObj.optString("UserDrinks");
                        profilemodel.setUserDrinks(UserDrinks);

                        String UserSmokes=jObj.optString("UserSmokes");
                        profilemodel.setUserSmokes(UserSmokes);

                        String UserLookingFor=jObj.optString("UserLookingFor");
                        profilemodel.setUserLookingFor(UserLookingFor);

                        String UserMaritialStatus=jObj.optString("UserMaritialStatus");
                        profilemodel.setUserMaritialStatus(UserMaritialStatus);

                        String UserChildren=jObj.optString("UserChildren");
                        profilemodel.setUserChildren(UserChildren);

                        String UserDescAbtDating=jObj.optString("UserDescAbtDating");
                        profilemodel.setUserDescAbtDating(UserDescAbtDating);


                        String UserLongestRelationShip=jObj.optString("UserLongestRelationShip");
                        profilemodel.setUserLongestRelationShip(UserLongestRelationShip);

                        String UserDateWhoHasKids=jObj.optString("UserDateWhoHasKids");
                        profilemodel.setUserDateWhoHasKids(UserDateWhoHasKids);

                        String UserDateSmokeChoice=jObj.optString("UserDateSmokeChoice");
                        profilemodel.setUserDateSmokeChoice(UserDateSmokeChoice);

                        String UserFavQuote=jObj.optString("UserFavQuote");
                        profilemodel.setUserFavQuote(UserFavQuote);

                        String UserInterests=jObj.optString("UserInterests");
                        profilemodel.setUserInterests(UserInterests);

                        String UserDescription=jObj.optString("UserDescription");
                        profilemodel.setUserDescription(UserDescription);

                        String UserFirstDate=jObj.optString("UserFirstDate");
                        profilemodel.setUserFirstDate(UserFirstDate);

                        String UserLong=jObj.optString("UserLong");
                        profilemodel.setUserLong(UserLong);

                        String UserLat=jObj.optString("UserLat");
                        profilemodel.setUserLat(UserLat);

                        String UserDeviceTocken=jObj.optString("UserDeviceTocken");
                        profilemodel.setUserDeviceTocken(UserDeviceTocken);

                        String UserJabberId=jObj.optString("UserJabberId");
                        profilemodel.setUserJabberId(UserJabberId);

                        String UserPresence=jObj.optString("UserPresence");
                        profilemodel.setUserPresence(UserPresence);

                        String UserLastLogin=jObj.optString("UserLastLogin");
                        profilemodel.setUserLastLogin(UserLastLogin);

                        String UserMinAge=jObj.optString("UserMinAge");
                        profilemodel.setUserMinAge(UserMinAge);

                        String UserMaxAge=jObj.optString("UserMaxAge");
                        profilemodel.setUserMaxAge(UserMaxAge);

                        String UserDeactivationCause=jObj.optString("UserDeactivationCause");
                        profilemodel.setUserDeactivationCause(UserDeactivationCause);

                        String UserDeactivationText=jObj.optString("UserDeactivationText");
                        profilemodel.setUserDeactivationText(UserDeactivationText);

                        String UserSubscriptionStatus=jObj.optString("UserSubscriptionStatus");
                        profilemodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                        String UserInvisibleStatus=jObj.optString("UserInvisibleStatus");
                        profilemodel.setUserInvisibleStatus(UserInvisibleStatus);

                        String UserImage=jObj.optString("UserImage");
                        profilemodel.setUserImage(UserImage);

                        String Age=jObj.optString("Age");
                        profilemodel.setAge(Integer.parseInt(Age));

                        String Zodiac=jObj.optString("Zodiac");
                        profilemodel.setZodiac(Zodiac);

                        String SubscriptionStatus=jObj.optString("SubscriptionStatus");
                        profilemodel.setSubscriptionStatus(SubscriptionStatus);

                        String IsBlocked=jObj.optString("IsBlocked");
                        profilemodel.setIsBlocked(IsBlocked);

                        String BlockedMessage=jObj.optString("BlockedMessage");
                        profilemodel.setBlockedMessage(BlockedMessage);

                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(this, mTitle,
                                ErrorMessage, false);
                    }



                }
                catch (Exception e) {
                    // TODO Auto-generated catch block
//                        e.printStackTrace();
                    Log.d("sumit","profile exception= "+e);
                }

            }

        }

    }

//    private void addFragment(Fragment fragment,String key,String value) {
//        FragmentManager fragmentManager = getSupportFragmentManager();
//        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//        Bundle bundle = new Bundle();
//        bundle.putString(key,value);
//        fragment.setArguments(bundle);
//        fragmentTransaction.replace(R.id.container_body, fragment);
//        fragmentTransaction.addToBackStack(null);
//        fragmentTransaction.commit();
//
//
//    }

    public void replaceFragments(Class fragmentClass,String key,String value) {
        Fragment fragment = null;
        try {
            fragment = (Fragment) fragmentClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        // Insert the fragment by replacing any existing fragment
        FragmentManager fragmentManager = getSupportFragmentManager();
        Bundle bundle = new Bundle();
        bundle.putString(key,value);
        fragment.setArguments(bundle);
        fragmentManager.beginTransaction().replace(R.id.container_body, fragment)
                .commit();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.rl_how_tall:
                Fragment signUpSecondFragment = new SignUpSecondEditFragment();
               // replaceFragments(SignUpSecondEditFragment.class, AlbanianConstants.EXTRA_HEIGHT,profilemodel.getUserHeight());
//                addFragment(signUpSecondFragment, AlbanianConstants.EXTRA_HEIGHT
//                        ,profilemodel.getUserHeight());
                SignUpSecondEditFragment fragment = new SignUpSecondEditFragment();
                Bundle arguments = new Bundle();
                arguments.putString(AlbanianConstants.EXTRA_HEIGHT ,profilemodel.getUserHeight());
                fragment.setArguments(arguments);
                getSupportFragmentManager().beginTransaction().replace(R.id.container_body, fragment).commit();
                break;
            case R.id.rl_your_religion:
                Fragment signUpThirdFragment = new SignUpThirdEditFragment();
                replaceFragments(SignUpThirdEditFragment.class,AlbanianConstants.EXTRA_RELIGION, profilemodel.getUserReligion());
//                addFragment(signUpThirdFragment,AlbanianConstants.EXTRA_RELIGION,
//                        profilemodel.getUserReligion());



                break;
            case R.id.rl_langauage_speak:
//                addFragment(signUpFourthFragment,AlbanianConstants.EXTRA_OTHERLANGUAGES,
//                        profilemodel.getUserOtherLanguages());

                Fragment signUpFourthFragment = new SignUpFourthEditFragment();
                replaceFragments(SignUpFourthEditFragment.class,AlbanianConstants.EXTRA_OTHERLANGUAGES,
                        profilemodel.getUserOtherLanguages());

                break;
            case R.id.rl_your_pets:
                Fragment signUpSixthFragment = new SignUpSixthEditFragment();
                replaceFragments(SignUpSixthEditFragment.class,AlbanianConstants.EXTRA_PETS, profilemodel.getUserPets());
//                addFragment(signUpSixthFragment,AlbanianConstants.EXTRA_PETS,
//                        profilemodel.getUserPets());

                break;


            case R.id.rl_drink:
                Toast.makeText(getApplicationContext(),"0",Toast.LENGTH_LONG).show();
                Fragment signUpEighthFragment = new SignUpEighthEditFragment();
                replaceFragments(SignUpEighthEditFragment.class,AlbanianConstants.EXTRA_DRINKS, profilemodel.getUserDrinks());
//                addFragment(signUpEighthFragment,AlbanianConstants.EXTRA_DRINKS,
//                        profilemodel.getUserDrinks());

                break;
            case R.id.rl_smoke:
                Fragment signUpNinthFragment = new SignUpNinthEditFragment();
                replaceFragments(SignUpNinthEditFragment.class,AlbanianConstants.EXTRA_SMOKES, profilemodel.getUserSmokes());

//                addFragment(signUpNinthFragment,AlbanianConstants.EXTRA_SMOKES,
//                        profilemodel.getUserSmokes());

                break;


            case R.id.rl_your_desc:
                Fragment signUpThirteenthFragment = new SignUpThirteenthEditFragment();
                replaceFragments(SignUpThirteenthEditFragment.class,AlbanianConstants.EXTRA_DESC, profilemodel.getUserDescription());
//                addFragment(signUpThirteenthFragment,AlbanianConstans.EXTRA_DESC,
////                        profilemodel.getUserDescription());
                break;

            case R.id.rl_back_header:

                onBackPressed();

                break;
        }
    }
}
