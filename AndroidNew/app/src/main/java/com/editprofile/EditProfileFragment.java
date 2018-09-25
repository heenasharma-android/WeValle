package com.editprofile;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.MainFragments.BaseFragment;
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

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Sumit on 9/23/2015.
 */
public class EditProfileFragment extends BaseFragment implements View.OnClickListener {

    private View mView;
    private TextView headertext;
    private RelativeLayout back;
//    private RelativeLayout tvLocation;
//    private TextView txtLocation;


    /*VAriables */

    private SignupModel profilemodel;
    private AlbanianPreferances pref;
    private String CURRENTTABTAG;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
        profilemodel=new SignupModel();

    }

    @Override
    public void onResume() {
        super.onResume();


        AlbanianApplication.getInstance().trackScreenView("Edit Profile");

        //mActivity.hideCurrentTab();
    }

    @Override
    public void onStart() {
        super.onStart();

        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        if (getArguments() != null)
        {
            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);
        }



        if (AlbanianApplication.isInternetOn(getActivity())) {


        } else {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }


    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        mView = inflater.inflate(R.layout.edit_profile_fragment, container, false);
        init();

        if (AlbanianApplication.isInternetOn(getActivity())) {


            getUserDetail(pref.getUserData().getUserId());

        } else {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }

        return mView;
    }

    private void init() {
        initViews();

    }




    private void initViews() {

        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);

        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);
        headertext.setText(mActivity.getString(R.string.edit_profile));


//        tvLocation = (RelativeLayout) mView.findViewById(R.id.rl_location);
//        txtLocation = (TextView) mView.findViewById(R.id.txt_location);
        RelativeLayout tvHowTall = (RelativeLayout) mView.findViewById(R.id.rl_how_tall);
        RelativeLayout tvYourReligion = (RelativeLayout) mView.findViewById(R.id.rl_your_religion);
        RelativeLayout tvLanguageSpeak = (RelativeLayout) mView.findViewById(R.id.rl_langauage_speak);
        RelativeLayout tvYourPets = (RelativeLayout) mView.findViewById(R.id.rl_your_pets);
//        RelativeLayout tvWatchedMovies = (RelativeLayout) mView.findViewById(R.id.rl_kindsofmovie);
        RelativeLayout tvDrink = (RelativeLayout) mView.findViewById(R.id.rl_drink);
        RelativeLayout tvSmoke = (RelativeLayout) mView.findViewById(R.id.rl_smoke);
//        RelativeLayout tvInterests = (RelativeLayout) mView.findViewById(R.id.rl_interests);
//        RelativeLayout tvFavoriteQuote = (RelativeLayout) mView.findViewById(R.id.rl_favorite_quote);
//        RelativeLayout tvAlbanianSong = (RelativeLayout) mView.findViewById(R.id.rl_your_albanian_song);
        RelativeLayout tvYourDescription = (RelativeLayout) mView.findViewById(R.id.rl_your_desc);


//        tvLocation.setOnClickListener(this);
        tvHowTall.setOnClickListener(this);
        tvYourReligion.setOnClickListener(this);
        tvLanguageSpeak.setOnClickListener(this);
        tvYourPets.setOnClickListener(this);
//        tvWatchedMovies.setOnClickListener(this);
        tvDrink.setOnClickListener(this);
        tvSmoke.setOnClickListener(this);
//        tvInterests.setOnClickListener(this);
//        tvFavoriteQuote.setOnClickListener(this);
//        tvAlbanianSong.setOnClickListener(this);
        tvYourDescription.setOnClickListener(this);

    }



    @Override
    public void onClick(View v) {
        switch (v.getId()) {

//            case R.id.rl_location:
//                Fragment locationFragment = new LocationFragment();
//                addFragment(locationFragment,"","");
//
//                break;
            case R.id.rl_how_tall:
                Fragment signUpSecondFragment = new SignUpSecondEditFragment();

                addFragment(signUpSecondFragment, AlbanianConstants.EXTRA_HEIGHT
                        ,profilemodel.getUserHeight());

                break;
            case R.id.rl_your_religion:
                Fragment signUpThirdFragment = new SignUpThirdEditFragment();
                addFragment(signUpThirdFragment,AlbanianConstants.EXTRA_RELIGION,
                        profilemodel.getUserReligion());



                break;
            case R.id.rl_langauage_speak:

                Fragment signUpFourthFragment = new SignUpFourthEditFragment();
                addFragment(signUpFourthFragment,AlbanianConstants.EXTRA_OTHERLANGUAGES,
                        profilemodel.getUserOtherLanguages());

                break;
            case R.id.rl_your_pets:
                Fragment signUpSixthFragment = new SignUpSixthEditFragment();
                addFragment(signUpSixthFragment,AlbanianConstants.EXTRA_PETS,
                        profilemodel.getUserPets());

                break;


            case R.id.rl_drink:
                Fragment signUpEighthFragment = new SignUpEighthEditFragment();
                addFragment(signUpEighthFragment,AlbanianConstants.EXTRA_DRINKS,
                        profilemodel.getUserDrinks());

                break;
            case R.id.rl_smoke:
                Fragment signUpNinthFragment = new SignUpNinthEditFragment();
                addFragment(signUpNinthFragment,AlbanianConstants.EXTRA_SMOKES,
                        profilemodel.getUserSmokes());

                break;
//            case R.id.rl_interests:
//                Fragment signUpTenthFragment = new SignUpInterestEditFragment();
//                addFragment(signUpTenthFragment,AlbanianConstants.EXTRA_INTEREST,
//                        profilemodel.getUserInterests());
//
//                break;

            case R.id.rl_your_desc:
                Fragment signUpThirteenthFragment = new SignUpThirteenthEditFragment();
                addFragment(signUpThirteenthFragment,AlbanianConstants.EXTRA_DESC,
                        profilemodel.getUserDescription());
                break;

            case R.id.rl_back_header:

                getActivity().onBackPressed();

                break;
        }
    }





    private void addFragment(Fragment fragment,String key,String value) {
        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        Bundle bundle = new Bundle();
        bundle.putString(key,value);
        fragment.setArguments(bundle);
        transaction.replace(R.id.container_body, fragment);
        transaction.addToBackStack(null);
        transaction.commit();


    }


    private void getUserDetail(final String userId) {

        Log.d("sumit", "useris= " + pref.getUserData().getUserId());
        Log.d("sumit", "useris profileVisitedID= " + userId);

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "profile= "+response.toString());

                parseResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

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

//                params.put("api_name", "userdetail");
//                params.put("ProfileVisitedId", pref.getUserData().getUserId());
//                params.put("user_id", userId);
//                params.put("AppName", AlbanianConstants.AppName);


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
//                    String mMessage = jObj.getString("msg");


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

//                        String UserInterestedIn=jObj.optString("UserInterestedIn");
//                        profilemodel.setUserInterestedIn(UserInterestedIn);

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

//                        String UserFriendsDesc=jObj.optString("UserFriendsDesc");
//                        profilemodel.setUserFriendsDesc(UserFriendsDesc);

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


//                      txtLocation.setText((profilemodel.getUserCity()
//                                            +", "+profilemodel.getUserCountry()).trim().replace(" ,",",").replaceAll("\\s+(?=[),])", ""));

                        /////////////


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
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

}
