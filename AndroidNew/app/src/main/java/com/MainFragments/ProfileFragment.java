package com.MainFragments;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.editprofile.EditProfileFragment;
import com.editprofile.MatchQuestionsFragment;
import com.models.SignupModel;
import com.models.UserImagesModel;
import com.models.UserModel;
import com.viewpagerindicator.PageIndicator;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import static com.albaniancircle.R.string.editprofile;


public class ProfileFragment extends BaseFragment implements
        View.OnClickListener{

    /*UI declaration*/

    private View mView;
    private ImageView /*userimage,*/sendmessage,matchdetails;
    private RelativeLayout sendmessagelayout;
    private RecyclerView userimagesgallery;
    private RelativeLayout matchCard,btn_edit_profile,btn_filter;
    private TextView heritage,city,age,height,religion,languages,txt_matchcard;
    private TextView agetext,heighttext,religiontext,zodiactext;
    private TextView smoke,drink,pet,/*oneword,movies,favsong,interest,*/aboutme;
    private TextView age_label,zodiac_label,location_label,heritage_label,religion_label,height_label,languages_label,about_label;
    private TextView username,premiummember;
    private ImageView btn_addtofav;
    private TextView headertext;

//    public FragmentDrawerProfile drawerFragment;
    public LinearLayout container_toolbar;

//    private DrawerLayout mDrawerLayout;
//    private ActionBarDrawerToggle mDrawerToggle;
    private ImageView rightmenubutton,onlinenow;
    private RelativeLayout back;

    ViewPager mIntroViewPager;
    PageIndicator indicator;
    FragmentPagerAdapter adapter;
    public static final String FRAGMENT_TAG = "PROFILE";

    /*Variables and members*/



     /* variables declaration*/
//     DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private AlbanianPreferances pref;
    private String profileVisitedID,CURRENTTABTAG;
    private SignupModel profilemodel;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        pref = new AlbanianPreferances(getActivity());
        profilemodel=new SignupModel();

//        options = new DisplayImageOptions.Builder()
////                .displayer(new RoundedBitmapDisplayer(200))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
////              .cacheOnDisc()
//                .build();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        if(mView!=null)
        {
            ViewGroup parent =(ViewGroup)mView.getParent();
            if(parent!=null)
                parent.removeView(mView);
        }
        try
        {
            mView = inflater.inflate(R.layout.fragment_profile, container, false);

            init(mView);
        }
        catch (Exception e)
        {

        }


        return mView;


    }

    private void init(View mView) {

        rightmenubutton=(ImageView)mView.findViewById(R.id.img_rightmenu);



        container_toolbar=(LinearLayout)mView.findViewById(R.id.container_toolbar_profile);
        premiummember=(TextView) mView.findViewById(R.id.tv_premiumuser);

        username=(TextView) mView.findViewById(R.id.tv_username);
        onlinenow=(ImageView) mView.findViewById(R.id.tv_username_online);
        btn_addtofav=(ImageView) mView.findViewById(R.id.btn_addtofav);

        mIntroViewPager = (ViewPager)mView.findViewById(R.id.user_allimages);


        userimagesgallery = (RecyclerView) mView.findViewById(R.id.gallery_ouser_images);
        userimagesgallery.setHasFixedSize(true);

        userimagesgallery
                .setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));


        age_label = (TextView) mView.findViewById(R.id.txt_age_label);
        zodiac_label = (TextView) mView.findViewById(R.id.txt_zodiac_label);
        location_label = (TextView) mView.findViewById(R.id.txt_location_label);
        heritage_label = (TextView) mView.findViewById(R.id.txt_heritage_label);
        religion_label = (TextView) mView.findViewById(R.id.txt_religion_label);
        height_label = (TextView) mView.findViewById(R.id.txt_height_label);
        languages_label = (TextView) mView.findViewById(R.id.txt_languages_label);
        about_label = (TextView) mView.findViewById(R.id.txt_about_label);

        headertext = (TextView) mView.findViewById(R.id.txt_headerlogo);
        back = (RelativeLayout) mView.findViewById(R.id.rl_back_header);
        back.setOnClickListener(this);

        matchdetails = (ImageView) mView.findViewById(R.id.btn_match_user);
        sendmessage = (ImageView) mView.findViewById(R.id.btn_send_message);
        sendmessagelayout = (RelativeLayout) mView.findViewById(R.id.rl_sendm);
        matchCard = (RelativeLayout) mView.findViewById(R.id.btn_matchcard);
        txt_matchcard = (TextView) mView.findViewById(R.id.txt_matchcard);
        btn_edit_profile = (RelativeLayout) mView.findViewById(R.id.btn_edit_profile);
        btn_filter = (RelativeLayout) mView.findViewById(R.id.btn_filter);

        city = (TextView) mView.findViewById(R.id.tv_locationtext);
        heritage = (TextView) mView.findViewById(R.id.tv_heritagetext);
        age = (TextView) mView.findViewById(R.id.tv_age_user);

        agetext= (TextView) mView.findViewById(R.id.tv_agetext);
        heighttext  = (TextView) mView.findViewById(R.id.tv_heighttext);
        religiontext = (TextView) mView.findViewById(R.id.tv_religiontext);
        zodiactext = (TextView) mView.findViewById(R.id.tv_zodiactext);

        height = (TextView) mView.findViewById(R.id.tv_height);
        religion = (TextView) mView.findViewById(R.id.tv_religion);
        languages = (TextView) mView.findViewById(R.id.tv_languages);
        smoke = (TextView) mView.findViewById(R.id.tv_smoke);
        drink = (TextView) mView.findViewById(R.id.tv_drink);
        pet = (TextView) mView.findViewById(R.id.tv_pet);

//        interest = (TextView) mView.findViewById(R.id.tv_interest);
        aboutme = (TextView) mView.findViewById(R.id.tv_aboutme);


        sendmessage.setOnClickListener(this);
        matchCard.setOnClickListener(this);
        btn_edit_profile.setOnClickListener(this);
        btn_filter.setOnClickListener(this);
        matchdetails.setOnClickListener(this);
        btn_addtofav.setOnClickListener(this);


        rightmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                if (profileVisitedID.equalsIgnoreCase(pref.getUserData().getUserId()))
                {
                    // user profile..

//                    showUserOptions();

                    Log.d("sumit","emailOptIn send= "+profilemodel.getEmailOptIn());

                    Bundle bundle=new Bundle();
                    bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);
                    bundle.putString(AlbanianConstants.EXTRA_EMAILOPTIN,profilemodel.getEmailOptIn());

                    mActivity.pushFragments(CURRENTTABTAG,
                            new SettingsFragment(), false, true, bundle);




                }
                else
                {
                    // other user profile..

                    toggleDrawer();
                }


            }
        });




    }

    private void getUserAnsweredOrNot(final boolean matchcardOtherUserOrNot, String profileVisitedID, final String userId) {

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "getUserAnsweredOrNot= "+response.toString());


                if (response.toString() != null) {

                    {

                        try {

                            JSONObject jObj = new JSONObject(response.toString());
                            String ErrorCode = jObj.optString("ErrorCode");
                            boolean Matchcard = jObj.optBoolean("Matchcard");


                            Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                             if(Matchcard && matchcardOtherUserOrNot)
                            {

                                matchdetails.setVisibility(View.VISIBLE);

                            }
                            else
                            {
                                matchdetails.setVisibility(View.GONE);
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
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

                params.put("api_name", "checkmatchcards");
                params.put("user_id", userId);
                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void showUserOptions() {

        android.app.AlertDialog.Builder builderSingle;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.HONEYCOMB)
        {
            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new android.app.AlertDialog.Builder(
                    getActivity());
        }


//      builderSingle.setTitle("Upload image");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
                android.R.layout.select_dialog_item);


//        arrayAdapter.add("Match card");
        arrayAdapter.add(getResources().getString(editprofile));
        arrayAdapter.add(getResources().getString(R.string.action_settings));





        builderSingle.setNegativeButton("Cancel",
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });

        builderSingle.setAdapter(arrayAdapter,
                new DialogInterface.OnClickListener() {

                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                        long strName = arrayAdapter.getItemId(which);


//                        if (strName == 0) {
//                            // comment report
//                            loadMatchCardFragment();
//
//
//
//                        }
                        if (strName == 0) {
                            // comment report

                            loadEditProfileFragment();



                        }
                        else if (strName == 1) {
                            // comment report

                            Bundle bundle=new Bundle();
                            bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,CURRENTTABTAG);

                            mActivity.pushFragments(CURRENTTABTAG,
                                    new SettingsFragment(), false, true, bundle);


                        }


                    }
                });

        builderSingle.show();

    }

//    private void reportUser() {
//
//
//        Bundle bundle = new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, profileVisitedID);
//        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);
//
//
//        mActivity.pushFragments(CURRENTTABTAG,
//                new ReportUserFragment(), false, true, bundle);
//
//
//    }

    private void addToFavorite() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "addtofav= " + response.toString());

                parseFavResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Log.d("sumit","user_favorite_from "+pref.getUserData().getUserId());
                Log.d("sumit","user_favorite_to "+profileVisitedID);

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "insertuserfavourite");
                params.put("user_favorite_from", pref.getUserData().getUserId());
                params.put("user_favorite_to", profileVisitedID);
//                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void blockUnblockUser(final String blockstring) {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "blockUnblockUser= "+response.toString());

                parseBlockResponse(response.toString());

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
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

                params.put("api_name", blockstring);
                params.put("block_from", pref.getUserData().getUserId());
                params.put("block_to", profileVisitedID);
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

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);


                    }

                    else if(ErrorCode.equals("0") )
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
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

                            Toast.makeText(mActivity,"Added to favorites!",Toast.LENGTH_SHORT).show();

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

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
                                ErrorMessage, false);
                    }
                    else
                    {

                        String mTitle = getResources().getString(R.string.app_name);

                        AlbanianApplication.ShowAlert(getActivity(), mTitle,
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
            builderSingle = new android.app.AlertDialog.Builder(new ContextThemeWrapper(getActivity(), android.R.style.Theme_Holo_Light_Dialog));
        }
        else
        {
            builderSingle = new android.app.AlertDialog.Builder(
                    getActivity());
        }


//      builderSingle.setTitle("Upload image");
        final ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(
                getActivity(),
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

                            addToFavorite();

                        } else if (strName == 1) {
                            // comment report

                            //reportUser();

                        } else if (strName == 2) {
                            // comment report

                            if (profilemodel.getIsBlocked().equals("1")) {

                                blockUnblockUser("blockuser");

                            }
                            else
                            {
                                blockUnblockUser("unblockuser");
                            }


                        }


                    }
                });

        builderSingle.show();


    }


    @Override
    public void onResume() {
        super.onResume();

        AlbanianApplication.getInstance().trackScreenView("Profile");

        //mActivity.hideCurrentTab();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        //mActivity.hideCurrentTab();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        //mActivity.hideCurrentTab();
    }

    @Override
    public void onStart() {
        super.onStart();



        if (getArguments() != null)
        {

            profileVisitedID = getArguments().getString(
                    AlbanianConstants.EXTRA_PROFILEVISITEDID);

            CURRENTTABTAG = getArguments().getString(
                    AlbanianConstants.EXTRA_CURRENTTAB_TAG);


            if (profileVisitedID.equalsIgnoreCase(pref.getUserData().getUserId()))
            {

                sendmessagelayout.setVisibility(View.GONE);
                sendmessage.setVisibility(View.GONE);
                btn_addtofav.setVisibility(View.GONE);

                rightmenubutton.setVisibility(View.VISIBLE);

                btn_filter.setVisibility(View.VISIBLE);
                btn_edit_profile.setVisibility(View.VISIBLE);


                checkOtherUserMatchCard(pref.getUserData().getUserId(),pref.getUserData().getUserId());
            }
            else
            {
                sendmessagelayout.setVisibility(View.VISIBLE);
                sendmessage.setVisibility(View.VISIBLE);
                btn_addtofav.setVisibility(View.VISIBLE);
                matchCard.setVisibility(View.GONE);
                btn_filter.setVisibility(View.GONE);
                btn_edit_profile.setVisibility(View.GONE);

                rightmenubutton.setVisibility(View.VISIBLE);

                checkOtherUserMatchCard(profileVisitedID,pref.getUserData().getUserId());
            }

            if (profileVisitedID != null)
            {

                boolean flag=true;
                if (flag) {
                    flag = false;
                    getUserDetail(profileVisitedID);
                }



                if (!profileVisitedID.equals(pref.getUserData().getUserId())) {

                    insertUserViewedProfile(profileVisitedID);
                }


            }


        }



    }

    private void checkOtherUserMatchCard(final String profileVisitedID, final String userId) {

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "checkOtherUserMatchCard= "+response.toString());

                parsecheckOtherUserMatchCardResponse(response.toString(),profileVisitedID,userId);

//                AlbanianApplication.hideProgressDialog(getActivity());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, " insertuserviews Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "checkmatchcards");
                params.put("user_id", profileVisitedID);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);

    }

    private void parsecheckOtherUserMatchCardResponse(String Result,
                                                      String profileVisitedID, String userId) {


        Log.d("sumit","match crd- "+Result);

        if (Result != null) {

            {

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
//                    String ErrorMessage = jObj.optString("ErrorMessage");
                    boolean Matchcard = jObj.optBoolean("Matchcard");
//                    String mMessage = jObj.getString("msg");


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

//                    if (Matchcard
//                            && userAnswered)
//
//                    {

                    if (profileVisitedID.equals(userId)) {

                        matchdetails.setVisibility(View.GONE);

                        if (Matchcard) {

//                            matchCard.setVisibility(View.GONE);
                            matchCard.setBackground(mActivity.getResources().getDrawable(R.drawable.button_bg_gray_border));
                            txt_matchcard.setTextColor(mActivity.getResources().getColor(R.color.black));
                        }
                        else
                        {
//                            matchCard.setVisibility(View.VISIBLE);
                            matchCard.setBackground(mActivity.getResources().getDrawable(R.drawable.button_bg_purple_solid));
                            txt_matchcard.setTextColor(mActivity.getResources().getColor(R.color.whiteprofile));
                        }

                    }
                    else {

                        getUserAnsweredOrNot(Matchcard,profileVisitedID,userId);
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

    private void insertUserViewedProfile(final String profileVisitedID) {

        Log.d("sumit","useris= "+pref.getUserData().getUserId());
        Log.d("sumit","useris profileVisitedID= "+profileVisitedID);

//        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "insertuserviews= "+response.toString());

                  parseInsertViewResponse(response.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, " insertuserviews Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "insertuserviews");
                params.put("UserViewsBy", pref.getUserData().getUserId());
                params.put("UserViewsTo", profileVisitedID);
                params.put("AppName", AlbanianConstants.AppName);

                return params;
            }

        };

        // Adding request to request queue

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    private void parseInsertViewResponse(String Result) {


    }


    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.btn_send_message:

                loadChatFragment();


                break;

            case R.id.btn_match_user:

                loadMatchCardViewFragment();

                break;

            case R.id.rl_back_header:

//                if (getFragmentManager().getBackStackEntryCount()<=0) {

                    getActivity().onBackPressed();
//                }
//                else
//                {
//                    getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);
//                }

                 break;

            case R.id.btn_matchcard:

                loadMatchCardFragment();
                break;

            case R.id.btn_edit_profile:

                loadEditProfileFragment();
                break;

            case R.id.btn_filter:

                loadFilterPage();
                break;

            case R.id.btn_addtofav:

                addToFavorite();

                break;

                default:
                    break;


        }
    }

    private void loadFilterPage() {

//        final FragmentManager fragmentManager = getFragmentManager();
//        final Fragment content = fragmentManager.findFragmentById(R.id.container_body);

        Log.d("sumit","filter loading= "+CURRENTTABTAG);

        Bundle bundle = new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_LOCALPAGENAME, extraFaq);
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                CURRENTTABTAG);


        mActivity.pushFragments(CURRENTTABTAG,
                new FilterFragment(), false, true,bundle);
    }

    private void loadMatchCardViewFragment() {


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, profilemodel.getUserId());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDIMAGE, profilemodel.getUserImage());
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);

        mActivity.pushFragments(CURRENTTABTAG,
                new MatchedCardDetailFragment(), false, true, bundle);

    }

    private void loadMatchCardFragment() {

        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);


        mActivity.pushFragments(CURRENTTABTAG,
                new MatchQuestionsFragment(), false, true, bundle);
    }

    private void loadChatFragment() {

        Log.d("sumit","sent mesg= "+profilemodel.getUserName());


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, profilemodel.getUserId());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDNAME, profilemodel.getUserName());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDIMAGE, profilemodel.getUserImage());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDAGE, String.valueOf(profilemodel.getAge()));
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDQUOTE, profilemodel.getUserFavQuote());
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDINTEREST, profilemodel.getUserInterests());
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG, CURRENTTABTAG);

        mActivity.pushFragments(CURRENTTABTAG,
                new ChatFragment(), false, true, bundle);
    }

    private void loadUserImagesFragment() {


        Bundle bundle = new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, profileVisitedID);
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                                            CURRENTTABTAG);


        mActivity.pushFragments(CURRENTTABTAG,
                new UserImagesFragment(), false, true, bundle);
    }



    private void loadEditProfileFragment() {


        Bundle bundle=new Bundle();
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                CURRENTTABTAG);

        mActivity.pushFragments(AlbanianConstants.TAB_1_TAG,
                new EditProfileFragment(), false, true, bundle);


    }


    private void getUserDetail(final String profileVisitedID)
    {

        Log.d("sumit","useris= "+pref.getUserData().getUserId());
        Log.d("sumit","useris profileVisitedID= "+profileVisitedID);

//        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response)

            {
                Log.d(AlbanianConstants.TAG, "profile= "+response.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());
                parseResponse(response.toString());


            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error)
            {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {


            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "vieweingUserProfile");
                params.put("viewing_profile_id", profileVisitedID);
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);

//                params.put("api_name", "userdetail");
//                params.put("ProfileVisitedId", pref.getUserData().getUserId());
//                params.put("user_id", profileVisitedID);
//                params.put("AppName", AlbanianConstants.AppName);

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

                        String EmailOptIn=jObj.optString("EmailOptIn");
                        profilemodel.setEmailOptIn(EmailOptIn);

                        JSONArray jsonArrayHeritage=jObj.getJSONArray("UserHeritage");

                        StringBuilder stringBuilder=new StringBuilder();

                        for (int i = 0; i < jsonArrayHeritage.length(); i++) {

                            JSONObject jsonObjectHeritage=jsonArrayHeritage.getJSONObject(i);

                            String heritage=jsonObjectHeritage.optString("Heritage");

                            stringBuilder.append(heritage);

                            if (i != jsonArrayHeritage.length()-1) {
                                stringBuilder.append(", ");
                            }


                        }

                        profilemodel.setUserHeritageInterestedIn(stringBuilder.toString());


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

                        String IsFavourite=jObj.optString("IsFavourite");
                        profilemodel.setIsFavourite(IsFavourite);

                        Log.d("sumit","IsFavourite-- "+IsFavourite);

                        String BlockedMessage=jObj.optString("BlockedMessage");
                        profilemodel.setBlockedMessage(BlockedMessage);


                        JSONArray jArray_response=jObj.getJSONArray("UserImages");

                        ArrayList<UserImagesModel> images=new ArrayList<>();

//                        UserImagesModel imagesmodeltpem=new UserImagesModel();

//                        String ImageId=jObj_images.optString("ImageId");
//                        imagesmodeltpem.setImageId("");

//                      String UserImageUrltemp=profilemodel.getUserImage();
//                      imagesmodeltpem.setUserImageUrl(UserImageUrltemp);
//                      images.add(imagesmodeltpem);


                        for (int i = 0; i < jArray_response.length(); i++) {

                            UserImagesModel imagesmodel=new UserImagesModel();

                            JSONObject jObj_images=jArray_response.getJSONObject(i);

                            // Job object

//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                            String ImageId=jObj_images.optString("ImageId");
                            imagesmodel.setImageId(ImageId);

                            String UserImageUrl=jObj_images.optString("UserImageUrl");
                            imagesmodel.setUserImageUrl(UserImageUrl);

                            ///////

                            images.add(imagesmodel);

                        }

                        profilemodel.setUserImages(images);

                        {

                            if (profileVisitedID.equals( pref.getUserData().getUserId())) {

                                UserModel userModel=pref.getUserData();
                                userModel.setUserImage(UserImage);
                                pref.setUserData(userModel);

                                Intent intent1 = new Intent("changeimage");
                                //put whatever data you want to send, if any
//                               intent1.putExtra("message", message);
                                //send broadcast
                                getActivity().sendBroadcast(intent1);
                            }


                        }


                        setProfileDetail(profilemodel);


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




    private void setProfileDetail(SignupModel profilemodel) {

        Log.d("sumit","profile user gallery= "+profilemodel.getUserImages());

        age_label.setVisibility(View.VISIBLE);
        zodiac_label.setVisibility(View.VISIBLE);
        location_label.setVisibility(View.VISIBLE);
        heritage_label.setVisibility(View.VISIBLE);
        religion_label.setVisibility(View.VISIBLE);
        height_label.setVisibility(View.VISIBLE);
        languages_label.setVisibility(View.VISIBLE);
        about_label.setVisibility(View.VISIBLE);

        if (profilemodel.getUserSubscriptionStatus().equals("0")) {
            premiummember.setVisibility(View.GONE);

//            usertypebox.setBackgroundColor(getResources().getColor(R.color.grey));
            username.setText(profilemodel.getUserName());

        }
        else
        {
            premiummember.setVisibility(View.VISIBLE);
//            usertypebox.setBackgroundColor(getResources().getColor(R.color.gold));
            username.setText(profilemodel.getUserName());
        }

        if (profilemodel.getUserPresence().equals("0"))
        {
            onlinenow.setVisibility(View.GONE);
        }
        else
        {
            onlinenow.setVisibility(View.VISIBLE);
        }

        if (profilemodel.getIsFavourite().equals("0"))
        {

            btn_addtofav.setImageResource(R.drawable.fav_unselected);
        }
        else
        {
            btn_addtofav.setImageResource(R.drawable.fav_selected);
        }



//        try {

            ArrayList<UserImagesModel> userImagesModels=new ArrayList<>();

            userImagesModels.clear();

//            UserImagesModel userImagesModel=new UserImagesModel();
//            userImagesModel.setIsUserProfileImage("1");
//            userImagesModel.setUserImageUrl(profilemodel.getUserImage());

//            userImagesModels.add(userImagesModel);

            for (int i = 0; i < profilemodel.getUserImages().size(); i++) {

                userImagesModels.add(profilemodel.getUserImages().get(i));
            }


            adapter = new DataAdapter(getChildFragmentManager(),userImagesModels);

//            adapter.notifyDataSetChanged();
            mIntroViewPager.setAdapter(adapter);



//        }
//        catch (Exception e)
//        {
//
//            Log.e("sumit","profi e= "+e);
//        }



        if (profilemodel.getUserImages() != null && profilemodel.getUserImages().size()>0) {

            userimagesgallery.setVisibility(View.VISIBLE);

        try
        {

                UserImagesGalleryAdapter adapter = new UserImagesGalleryAdapter(getActivity(), profilemodel.getUserImages());
                userimagesgallery.setAdapter(adapter);

                    userimagesgallery.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), new RecyclerItemClickListener.OnItemClickListener() {
                        @Override
                        public void onItemClick(View view, int position) {
                            // TODO Handle item click

                            Log.d("sumit","user images adapter clicked...");
                            loadUserImagesFragment();
                        }
                    })
            );



        }

            catch (Exception e)
            {

            }
        }
        else {

            userimagesgallery.setVisibility(View.GONE);
        }





        headertext.setText(profilemodel.getUserName());
//        quote.setText(!profilemodel.getUserFavQuote().equals("")?profilemodel.getUserFavQuote():getString(R.string.Unanswered));
        city.setText(!profilemodel.getUserCity().equals("")?(profilemodel.getUserCity()+", "
                +profilemodel.getUserCountry()):getString(R.string.Unanswered));

        heritage.setText(!profilemodel.getUserHeritageInterestedIn().equals("")?(profilemodel.getUserHeritageInterestedIn()
                ):getString(R.string.Unanswered));
//        age.setText(String.valueOf(profilemodel.getAge()));

        Log.d("sumit","profile.getUserHeight() 0= "+profilemodel.getUserHeight());


//        religion.setText(profilemodel.getUserReligion());
        languages.setText(!profilemodel.getUserOtherLanguages().equals("")?decode(profilemodel.getUserOtherLanguages()):getString(R.string.Unanswered));
        smoke.setText(!profilemodel.getUserSmokes().equals("")?profilemodel.getUserSmokes():getString(R.string.Unanswered));
        drink.setText(!profilemodel.getUserDrinks().equals("")?decode(profilemodel.getUserDrinks()):getString(R.string.Unanswered));
        pet.setText(!profilemodel.getUserPets().equals("")?decode(profilemodel.getUserPets()):getString(R.string.Unanswered));
//        oneword.setText(!profilemodel.getUserDescOneWord().equals("")?decode(profilemodel.getUserDescOneWord()):getString(R.string.Unanswered));
//        movies.setText((!profilemodel.getUserFavMovies().equals("")?decode(profilemodel.getUserFavMovies()):getString(R.string.Unanswered)));
//        favsong.setText(!profilemodel.getUserAlbanianSongs().equals("")?decode(profilemodel.getUserAlbanianSongs()):getString(R.string.Unanswered));
//        interest.setText(!profilemodel.getUserInterests().equals("")?decode(profilemodel.getUserInterests()):getString(R.string.Unanswered));
        aboutme.setText(!profilemodel.getUserDescription().equals("")?decode(profilemodel.getUserDescription()):getString(R.string.Unanswered));


//        if (quote.getText().toString().equals(getString(R.string.Unanswered))) {
//            quote.setTextColor(getResources().getColor(R.color.unanswercolor));
//        }
//        else {
//            quote.setTextColor(getResources().getColor(R.color.answercolor));
//        }

        if (languages.getText().toString().equals(getString(R.string.Unanswered))) {
            languages.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            languages.setTextColor(getResources().getColor(R.color.answercolor));
        }

        if (smoke.getText().toString().equals(getString(R.string.Unanswered))) {
            smoke.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            smoke.setTextColor(getResources().getColor(R.color.answercolor));
        }



        if (drink.getText().toString().equals(getString(R.string.Unanswered))) {
            drink.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            drink.setTextColor(getResources().getColor(R.color.answercolor));
        }



        if (pet.getText().toString().equals(getString(R.string.Unanswered))) {
            pet.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            pet.setTextColor(getResources().getColor(R.color.answercolor));
        }



//        if (oneword.getText().toString().equals(getString(R.string.Unanswered))) {
//            oneword.setTextColor(getResources().getColor(R.color.unanswercolor));
//        }
//        else {
//            oneword.setTextColor(getResources().getColor(R.color.answercolor));
//        }


//        if (interest.getText().toString().equals(getString(R.string.Unanswered))) {
//            interest.setTextColor(getResources().getColor(R.color.unanswercolor));
//        }
//        else {
//            interest.setTextColor(getResources().getColor(R.color.answercolor));
//        }

        if (aboutme.getText().toString().equals(getString(R.string.Unanswered))) {
            aboutme.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            aboutme.setTextColor(getResources().getColor(R.color.answercolor));
        }

        Log.d("sumit","resourcen= "+profilemodel.getUserHeight());
        Log.d("sumit","resourcen= "+profilemodel.getUserReligion());
        Log.d("sumit","resourcen= "+profilemodel.getZodiac());

        agetext.setText(String.valueOf(profilemodel.getAge()));
//        heighttext.setText(Html.fromHtml(String.valueOf(profilemodel.getUserHeight())));
//        religiontext.setText(String.valueOf(profilemodel.getUserReligion()));

        heighttext.setText((!profilemodel.getUserHeight().
                equals("")?decode(profilemodel.getUserHeight()):
                getString(R.string.Unanswered)));
        Log.d("sumit","profile.getUserHeight()1= "+profilemodel.getUserHeight());

//        height.setText(Html.fromHtml(profilemodel.getUserHeight()));
        religiontext.setText((!profilemodel.getUserReligion().
                equals("")?decode(profilemodel.getUserReligion()):
                getString(R.string.Unanswered)));

        if (heighttext.getText().toString().equals(getString(R.string.Unanswered))) {
            heighttext.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            heighttext.setTextColor(getResources().getColor(R.color.answercolor));
        }

        if (religiontext.getText().toString().equals(getString(R.string.Unanswered))) {
            religiontext.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            religiontext.setTextColor(getResources().getColor(R.color.answercolor));
        }

        if (religiontext.getText().toString().equals(getString(R.string.Unanswered))) {
            religiontext.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            religiontext.setTextColor(getResources().getColor(R.color.answercolor));
        }

        if (city.getText().toString().equals(getString(R.string.Unanswered))) {
            city.setTextColor(getResources().getColor(R.color.unanswercolor));
        }
        else {
            city.setTextColor(getResources().getColor(R.color.answercolor));
        }

        zodiactext.setText(String.valueOf(profilemodel.getZodiac()));

        Log.d("sumit","resourcen new= "+profilemodel.getZodiac());

    }



    class DataAdapter extends FragmentPagerAdapter {

        protected ArrayList<UserImagesModel> result;

        public DataAdapter(FragmentManager fm, ArrayList<UserImagesModel> result) {
            super(fm);

            this.result = result;

        }

        @Override
        public Fragment getItem(int position) {

            ProfileImagesFragment fragment = new ProfileImagesFragment();

            Log.d("sumit", "usermodels ProfileImagesFragment ");

            if(result != null) {

                {

                    Log.d("sumit", "usermodels pimage to send is= " + CURRENTTABTAG);

                    Bundle b = new Bundle();
                    b.putParcelable("imagestring", result.get(position));
                    b.putParcelableArrayList("imagestringlist", result);
                    b.putString(AlbanianConstants.EXTRA_USERID, profileVisitedID);
                    b.putString("position", String.valueOf(position));
                    b.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                            CURRENTTABTAG);
                    fragment.setArguments(b);

                }

                return fragment;
            } else {
                return new Fragment();
            }

        }


        @Override
        public int getCount() {

            return result.size();

        }

    }



    private String decode(final String in)
    {
        String working = in;
        int index;
        index = working.indexOf("\\u");
        while(index > -1)
        {
            int length = working.length();
            if(index > (length-6))break;
            int numStart = index + 2;
            int numFinish = numStart + 4;
            String substring = working.substring(numStart, numFinish);
            int number = Integer.parseInt(substring,16);
            String stringStart = working.substring(0, index);
            String stringEnd   = working.substring(numFinish);
            working = stringStart + ((char)number) + stringEnd;
            index = working.indexOf("\\u");
        }
        return working;
    }


    public class CustomLayoutManager extends LinearLayoutManager {
        private int mParentWidth;
        private int mItemWidth;

        public CustomLayoutManager(Context context, int parentWidth, int itemWidth) {
            super(context);
            mParentWidth = parentWidth;
            mItemWidth = itemWidth;
        }

        @Override
        public int getPaddingLeft() {
            return Math.round(mParentWidth / 2f - mItemWidth / 2f);
        }

        @Override
        public int getPaddingRight() {
            return getPaddingLeft();
        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
