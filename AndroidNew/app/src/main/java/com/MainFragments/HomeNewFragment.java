package com.MainFragments;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.util.TypedValue;
import android.view.InflateException;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.adapter.FavoriteRVAdapter;
import com.adapter.RecyclerItemClickListener;
import com.adapter.ViewedYouRVAdapter;
import com.albaniancircle.AlbanianApplication;
import com.albaniancircle.AlbanianConstants;
import com.albaniancircle.AlbanianPreferances;
import com.albaniancircle.R;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.holders.ClickListener;
import com.holders.RecyclerTouchListener;
import com.models.HomePageModel;
import com.models.UserModel;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by Sumit on 30/08/2015.
 */
public class HomeNewFragment extends BaseFragment implements View.OnClickListener {

    /**********
     * Login Fragment UI Declaration
     */

    private View mView;


    private RelativeLayout upgrade;
    private RelativeLayout weValleLink,rl_favourites_header,rl_viewedyou_header;
    private ImageView profileicon;
    private TextView headertext;
    private TextView offertext;

     /* variables declaration*/

    private AlbanianPreferances pref;
    private AlbanianApplication mApp;


    private HomePageModel homePageModel;
    private RecyclerView homegallery;
    private RecyclerView events_list;
    private RecyclerView favourite_list;
    private RelativeLayout rl_fav_nouser,rl_viewed_nouser,rl_invite_btn;
//    private String mLatitude;
//    private String mLongitude;

//    DisplayImageOptions options;
//    protected ImageLoader imageLoader = ImageLoader.getInstance();
//    private ImageLoadingListener animateFirstListener = new AnimateFirstDisplayListener();

    private Handler mHandler;
    private boolean fragmentFlag;
    private int verticalItemPosition;



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Log.d("sumit", "oncreate home frag ");

        pref = new AlbanianPreferances(getActivity());

        homePageModel= new HomePageModel();


//        options = new DisplayImageOptions.Builder()
//                .displayer(new RoundedBitmapDisplayer(200))
////                .showStubImage(R.drawable.profile_male_btn)
////                .showImageForEmptyUri(R.drawable.profile_male_btn)
////                .showImageOnFail(R.drawable.profile_male_btn)
//                .cacheInMemory()
//                .cacheOnDisc()
//
//                .build();


        mHandler = new Handler() {
            public void handleMessage(Message msg) {
                if (msg.what == 1) {

                    setUserImage();
                }

            }
        };

    }


    @Override
    public void onResume() {
        super.onResume();

        // Tracking the screen view
        AlbanianApplication.getInstance().trackScreenView("Home");


        getActivity().registerReceiver(mMessageReceiver, new IntentFilter("changeimage"));
    }

    @Override
    public void onStart()
    {
        // TODO Auto-generated method stub
        super.onStart();

        pref.setCurrentScreen("ACTIVITYTAB");

        //mActivity.showCurrentTab();

        String[] mOffersArray =   getResources().getStringArray(R.array.offers_array);

        Random random = new Random();

        int maxIndex = mOffersArray.length;
        int generatedIndex = random.nextInt(maxIndex);

        offertext.setText(mOffersArray[generatedIndex]);


        AlbanianApplication.hideKeyBoard(getActivity(), getActivity());

        if (AlbanianApplication.isInternetOn(getActivity())) {


        } else {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }


    }

    private void getOnlineUsers() {

        AlbanianApplication.showProgressDialog(getActivity(), "", "Loading...");

        StringRequest sr = new StringRequest(Request.Method.POST,
                AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "getOnlineUsers= "+response);

                if (!mActivity.isFinishing()) {

//                    AlbanianApplication.hideProgressDialog(getActivity());

                }



                parseResponse(response.toString());

//

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                AlbanianApplication.hideProgressDialog(getActivity());

                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());


            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "profileviews");
                params.put("user_id", pref.getUserData().getUserId());
//                params.put("AppName", AlbanianConstants.AppName);


                return params;
            }

        };

        // Adding request to request queue
//        AlbanianApplication.getInstance().addToRequestQueue(jsonObjReq,
//                tag_whoiviewed_obj);

        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }

    @Override
    public void onDestroy() {

        AlbanianApplication.hideProgressDialog(getActivity());
        super.onDestroy();
    }

    private void parseResponse(String Result) {


        ArrayList<ArrayList<UserModel>> homeUsers=new ArrayList<>();
//        homeUsers.clear();

        if (Result != null) {

            {

                Log.d("sumit", "getOnlineUsers= "+Result);

                try {

                    JSONObject jObj = new JSONObject(Result);
                    String ErrorCode = jObj.optString("ErrorCode");
                    String ErrorMessage = jObj.optString("ErrorMessage");
//                    String ProfileImage = jObj.optString("ProfileImage");
//                    String UserNationality = jObj.getString("UserNationality");
                    String SubscriptionStatus=jObj.optString("UserSubscriptionStatus");
                    String UnreadMessages=jObj.optString("UnreadMessages");

                    setCounters(pref.getUserSubscription());


                    Log.d(AlbanianConstants.TAG, "" + ErrorCode);

                    if (ErrorCode.equalsIgnoreCase("1")) {


                        String mTitle = getResources().getString(R.string.app_name);


//                        JSONArray jArray_NewUsers=jObj.getJSONArray("NewUsers");
//
//                        ArrayList<UserModel> NewUsers_arraylist
//                                = new ArrayList<>();
//
//                        if (jArray_NewUsers.length()==0) {
//
//                            UserModel usermodel=new UserModel();
//                            usermodel.setUsertypeHomePage("New");
//                            usermodel.setUserId("0");
//                            usermodel.setUserImageUrl("");
//
//                            NewUsers_arraylist.add(usermodel);
//                        }
//
//                        else {
//
//                            for (int i = 0; i < jArray_NewUsers.length(); i++) {
//
//                                UserModel usermodel = new UserModel();
//
//                                JSONObject jObj_UsersViewedMe = jArray_NewUsers.getJSONObject(i);
//
//                                // Job object
//
//                                usermodel.setUsertypeHomePage("New");
//
//                                String UserId = jObj_UsersViewedMe.optString("UserId");
//                                usermodel.setUserId(UserId);
//
//                                String UserName = jObj_UsersViewedMe.optString("UserName");
//                                usermodel.setUserName(UserName);
//
//                                String Age = jObj_UsersViewedMe.optString("Age");
//                                usermodel.setAge(Age);
//
//                                String UserImageUrl = jObj_UsersViewedMe.optString("UserImageUrl");
//                                usermodel.setUserImageUrl(UserImageUrl);
//
//                                String UserSubscriptionStatus = jObj_UsersViewedMe.optString("UserSubscriptionStatus");
//                                usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);
//
//                                String UserFavQuote = jObj_UsersViewedMe.optString("UserFavQuote");
//                                usermodel.setUserFavQuote(UserFavQuote);
//
//                                String UserCity = jObj_UsersViewedMe.optString("UserCity");
//                                usermodel.setUserCity(UserCity);
//
//                                String UserState = jObj_UsersViewedMe.optString("UserState");
//                                usermodel.setUserState(UserState);
//
//
//                                ///////
//
//                                NewUsers_arraylist.add(usermodel);
//
//                            }
//                        }
//
//                        homeUsers.add(NewUsers_arraylist);


                        JSONArray jArray_UserFavorites=jObj.getJSONArray("UserFavorites");

                        ArrayList<UserModel> UserFavorites_arraylist
                                = new ArrayList<>();

//                        if (jArray_UserFavorites.length()==0) {
//
//                            UserModel usermodel=new UserModel();
//                            usermodel.setUsertypeHomePage("Your Favourites");
//                            usermodel.setUserId("0");
//                            usermodel.setUserImageUrl("");
//
//                            UserFavorites_arraylist.add(usermodel);
//                        }
//
//                        else
                            {

                            for (int i = 0; i < jArray_UserFavorites.length(); i++) {

                                UserModel usermodel = new UserModel();

                                JSONObject jObj_UsersViewedMe = jArray_UserFavorites.getJSONObject(i);

                                // Job object

                                usermodel.setUsertypeHomePage("Your Favourites");

                                String UserId = jObj_UsersViewedMe.optString("UserId");
                                usermodel.setUserId(UserId);

                                String UserName = jObj_UsersViewedMe.optString("UserName");
                                usermodel.setUserName(UserName);

                                String Age = jObj_UsersViewedMe.optString("Age");
                                usermodel.setAge(Age);

                                String UserImageUrl = jObj_UsersViewedMe.optString("UserImageUrl");
                                usermodel.setUserImageUrl(UserImageUrl);

                                String UserSubscriptionStatus = jObj_UsersViewedMe.optString("UserSubscriptionStatus");
                                usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                                String UserFavQuote = jObj_UsersViewedMe.optString("UserFavQuote");
                                usermodel.setUserFavQuote(UserFavQuote);

                                String UserCity = jObj_UsersViewedMe.optString("UserCity");
                                usermodel.setUserCity(UserCity);

                                String UserState = jObj_UsersViewedMe.optString("UserState");
                                usermodel.setUserState(UserState);


                                ///////

                                UserFavorites_arraylist.add(usermodel);

                            }
                        }

                        homeUsers.add(UserFavorites_arraylist);


                        JSONArray jArray_UsersViewedMe=jObj.getJSONArray("UsersViewedMe");

                        ArrayList<UserModel> viewedme_arraylist
                                 = new ArrayList<>();

//                        if (jArray_UsersViewedMe.length()==0) {
//
//                            UserModel usermodel=new UserModel();
//                            usermodel.setUsertypeHomePage("Viewed you");
//                            usermodel.setUserId("0");
//
//                            usermodel.setUserImageUrl("");
//
//                            viewedme_arraylist.add(usermodel);
//                        }
//
//                        else
                            {

                            for (int i = 0; i < jArray_UsersViewedMe.length(); i++) {

                                UserModel usermodel = new UserModel();

                                JSONObject jObj_UsersViewedMe = jArray_UsersViewedMe.getJSONObject(i);

                                // Job object

//                            JSONObject jObj_job=jObj_Main.getJSONObject("Payment");

                                usermodel.setUsertypeHomePage("Viewed you");

                                String UserId = jObj_UsersViewedMe.optString("UserId");
                                usermodel.setUserId(UserId);

                                String UserName = jObj_UsersViewedMe.optString("UserName");
                                Log.d("sumit","userviewd me username= "+UserName);
                                usermodel.setUserName(UserName);

                                String Age = jObj_UsersViewedMe.optString("Age");
                                usermodel.setAge(Age);

                                String UserImageUrl = jObj_UsersViewedMe.optString("UserImageUrl");
                                usermodel.setUserImageUrl(UserImageUrl);

                                String UserSubscriptionStatus = jObj_UsersViewedMe.optString("UserSubscriptionStatus");
                                usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);

                                String UserFavQuote = jObj_UsersViewedMe.optString("UserFavQuote");
                                usermodel.setUserFavQuote(UserFavQuote);

                                String UserCity = jObj_UsersViewedMe.optString("UserCity");
                                usermodel.setUserCity(UserCity);

                                String UserLocation = jObj_UsersViewedMe.optString("UserLocation");
                                usermodel.setUserLocation(UserLocation);

                                String UserPresence = jObj_UsersViewedMe.optString("UserPresence");
                                usermodel.setUserPresence(UserPresence);

                                String UserState = jObj_UsersViewedMe.optString("UserState");
                                usermodel.setUserState(UserState);


                                ///////

                                viewedme_arraylist.add(usermodel);

                            }
                        }


                        homeUsers.add(viewedme_arraylist);





//                        JSONArray jArray_RandomUsers=jObj.getJSONArray("RandomUsers");
//
//                        ArrayList<UserModel> RandomUsers_arraylist
//                                = new ArrayList<>();
//
//                        if (jArray_RandomUsers.length()==0) {
//
//                            UserModel usermodel=new UserModel();
//                            usermodel.setUsertypeHomePage("Suggetions for you");
//                            usermodel.setUserId("0");
//                            usermodel.setUserImageUrl("");
//
//                            RandomUsers_arraylist.add(usermodel);
//                        }
//
//                        else {
//
//                            for (int i = 0; i < jArray_RandomUsers.length(); i++) {
//
//                                UserModel usermodel = new UserModel();
//
//                                JSONObject jObj_UsersViewedMe = jArray_RandomUsers.getJSONObject(i);
//
//                                // Job object
//
//                                usermodel.setUsertypeHomePage("Suggetions for you");
//
//                                String UserId = jObj_UsersViewedMe.optString("UserId");
//                                usermodel.setUserId(UserId);
//
//                                String UserName = jObj_UsersViewedMe.optString("UserName");
//                                usermodel.setUserName(UserName);
//
//                                String Age = jObj_UsersViewedMe.optString("Age");
//                                usermodel.setAge(Age);
//
//                                String UserImageUrl = jObj_UsersViewedMe.optString("UserImageUrl");
//                                usermodel.setUserImageUrl(UserImageUrl);
//
//                                String UserSubscriptionStatus = jObj_UsersViewedMe.optString("UserSubscriptionStatus");
//                                usermodel.setUserSubscriptionStatus(UserSubscriptionStatus);
//
//                                String UserFavQuote = jObj_UsersViewedMe.optString("UserFavQuote");
//                                usermodel.setUserFavQuote(UserFavQuote);
//
//                                String UserCity = jObj_UsersViewedMe.optString("UserCity");
//                                usermodel.setUserCity(UserCity);
//
//                                String UserState = jObj_UsersViewedMe.optString("UserState");
//                                usermodel.setUserState(UserState);
//
//
//                                ///////
//
//                                RandomUsers_arraylist.add(usermodel);
//
//                            }
//
//                        }
//
//                      homeUsers.add(RandomUsers_arraylist);


                        homePageModel.setHomeUsers(homeUsers);


//                        JSONArray jArray_Events=jObj.getJSONArray("Events");
//
//                        ArrayList<EventModel> Events_arraylist
//                                = new ArrayList<>();
//
////                        if (jArray_Events.length()==0) {
////
////                            EventModel eventModel=new EventModel();
////                            eventModel.setEventTitle("Events for you");
////                            eventModel.setEventId("0");
////                            eventModel.setEventImageUrl("");
////
////                            Events_arraylist.add(eventModel);
////                        }
////
////                        else
//                            {
//
//                            for (int i = 0; i < jArray_Events.length(); i++) {
//
//                                EventModel eventModel = new EventModel();
//
//                                JSONObject jObj_Event = jArray_Events.getJSONObject(i);
//
//                                // Job object
//
////                                eventModel.setUsertypeHomePage("Suggetions for you");
//
//                                String EventId = jObj_Event.optString("EventId");
//                                eventModel.setEventId(EventId);
//
//                                String EventTitle = jObj_Event.optString("EventTitle");
//                                eventModel.setEventTitle(EventTitle);
//
//                                String EventDescription = jObj_Event.optString("EventDescription");
//                                eventModel.setEventDescription(EventDescription);
//
//                                String EventContact = jObj_Event.optString("EventContact");
//                                eventModel.setEventContact(EventContact);
//
//                                String EventVenue = jObj_Event.optString("EventVenue");
//                                eventModel.setEventVenue(EventVenue);
//
//                                String EventLat = jObj_Event.optString("EventLat");
//                                eventModel.setEventLat(EventLat);
//
//                                String EventLong = jObj_Event.optString("EventLong");
//                                eventModel.setEventLong(EventLong);
//
//                                String EventHeritage = jObj_Event.optString("EventHeritage");
//                                eventModel.setEventHeritage(EventHeritage);
//
//                                String EventImageName = jObj_Event.optString("EventImageName");
//                                eventModel.setEventImageName(EventImageName);
//
//                                String EventDate = jObj_Event.optString("EventDate");
//                                eventModel.setEventDate(EventDate);
//
//                                String EventStatus = jObj_Event.optString("EventStatus");
//                                eventModel.setEventStatus(EventStatus);
//
//                                String EventImageUrl = jObj_Event.optString("EventImageUrl");
//                                eventModel.setEventImageUrl(EventImageUrl);
//
//
//
//
//                                ///////
//
//                                Events_arraylist.add(eventModel);
//
//                            }
//
//                        }
//                        homePageModel.setHomeEvents(Events_arraylist);

                        /////////////

                        setOnlineUserList(homePageModel);





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
                    Log.d("sumit","getOnlineUsers exception= "+e);
                }

            }

        }



    }

    private void setCounters(String userSubscriptionStatus) {



        Log.d("sumit","userSubscriptionStatus= "+userSubscriptionStatus);

        if (userSubscriptionStatus.equals("0")) {

//            upgrade_sidebar_main.setVisibility(View.VISIBLE);
            upgrade.setVisibility(View.VISIBLE);

        }
        else
        {
//            upgrade_sidebar_main.setVisibility(View.GONE);
            upgrade.setVisibility(View.GONE);
        }

//        mActivity.setMesageCount(newMsg);


    }

    private void setOnlineUserList(final HomePageModel homePageModel) {


        try {
            if (!mActivity.isFinishing()) {

                AlbanianApplication.hideProgressDialog(getActivity());

            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {


            rl_favourites_header.setVisibility(View.VISIBLE);
            rl_viewedyou_header.setVisibility(View.VISIBLE);

            UserClick userClick=new UserClick() {
                @Override
                public void onImageClick(int position) {

                    Log.d("sumit","onItemClick genious- "+ verticalItemPosition);
                    Log.d("sumit","onItemClick genious- "+ position);

                    loadProfile(homePageModel.getHomeUsers().get(verticalItemPosition).get(position).getUserId());
                }
            };

            homegallery.setHasFixedSize(true);

//            homegallery.addItemDecoration(
//                    new DividerItemDecoration(getActivity(), R.drawable.vertical_divider));

            LinearLayoutManager llm = new LinearLayoutManager(getActivity());
            homegallery.setLayoutManager(llm);
// nuggetsList is an ArrayList of Custom Objects, in this case  Nugget.class
            ViewedYouRVAdapter adapter = new ViewedYouRVAdapter();


            if (homePageModel.getHomeUsers().get(1) == null ||
                    homePageModel.getHomeUsers().get(1).size() ==0) {

                rl_viewed_nouser.setVisibility(View.VISIBLE);
                homegallery.setVisibility(View.GONE);
                rl_invite_btn.setVisibility(View.VISIBLE);
            }
            else
            {
                rl_viewed_nouser.setVisibility(View.GONE);
                homegallery.setVisibility(View.VISIBLE);
                rl_invite_btn.setVisibility(View.VISIBLE);

                homegallery.setAdapter(adapter);
            }



            adapter.setData(homePageModel.getHomeUsers().get(1));


            homegallery.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                    homegallery, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    // Values are passing to activity & to fragment as well

                    loadProfile(homePageModel.getHomeUsers().get(1).get(position).getUserId());

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));


//         OnlineUsersAdapter adapter = new OnlineUsersAdapter(getActivity(), onlineuser_arraylist);
//         homegallery.setAdapter(adapter);


//            events_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
//
//            events_list.setHasFixedSize(true);
//
//            RecyclerViewAdapter RecyclerViewHorizontalAdapter=
//                    new RecyclerViewAdapter(homePageModel.getHomeEvents());
//
//
//            events_list.setAdapter(RecyclerViewHorizontalAdapter);



            favourite_list.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));

            favourite_list.setHasFixedSize(true);


            FavoriteRVAdapter FavouriteAdapter=
                    new FavoriteRVAdapter();

            Log.d("sumit","homePageModel.getHomeUsers().get(0).size() "+
                    homePageModel.getHomeUsers().get(0).size());

            if (homePageModel.getHomeUsers().get(0) == null ||
                    homePageModel.getHomeUsers().get(0).size() ==0) {

                rl_fav_nouser.setVisibility(View.VISIBLE);
                favourite_list.setVisibility(View.GONE);
            }
            else
            {
                rl_fav_nouser.setVisibility(View.GONE);
                favourite_list.setVisibility(View.VISIBLE);

                favourite_list.setAdapter(FavouriteAdapter);
            }




            FavouriteAdapter.setData(homePageModel.getHomeUsers().get(0));

            favourite_list.addOnItemTouchListener(new RecyclerTouchListener(getActivity(),
                    homegallery, new ClickListener() {
                @Override
                public void onClick(View view, final int position) {
                    //Values are passing to activity & to fragment as well

                    loadProfile(homePageModel.getHomeUsers().get(0).get(position).getUserId());

                }

                @Override
                public void onLongClick(View view, int position) {

                }
            }));



            final RecyclerItemClickListener.OnItemClickListener listener=new RecyclerItemClickListener.
                    OnItemClickListener() {
                @Override
                public void onItemClick(View view, int position) {

//                    loadProfile(onlineuser_arraylist.get(position).getUserId());

                    verticalItemPosition=position;


                }
            };


            homegallery.addOnItemTouchListener(
                    new RecyclerItemClickListener(getActivity(), listener) );

            adapter.notifyDataSetChanged();

        }
        catch (Exception e)
        {
            e.printStackTrace();
        }

    }



    public interface UserClick
    {
        void onImageClick(int position);
    }




    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        Log.d("sumit","oncreateview view home frag");

        fragmentFlag=true;

        if (mView != null) {
            ViewGroup parent = (ViewGroup) mView.getParent();
            if (parent != null)
                parent.removeView(mView);
        }
        try {
            mView = inflater.inflate(R.layout.homenew, container, false);
        } catch (InflateException e) {
        /* map is already there, just return view as it is */
        }



        init();

        if (AlbanianApplication.isInternetOn(getActivity())) {

            Log.d("sumit","online users==0 ");

            getOnlineUsers();

            updateTokenOnServer();

        } else {
            String mTitle = getResources().getString(R.string.app_name);

            AlbanianApplication.ShowAlert(getActivity(), mTitle,
                    getResources().getString(R.string.nointernet), false);
        }

        return mView;
    }

    private void updateTokenOnServer() {


        String deviceToken=AlbanianApplication.getReg_deviceID();

        if (deviceToken == null|| deviceToken.length()<=0) {

            AlbanianApplication.registerDevice(getActivity(),getActivity());
        }


        if (deviceToken == null || deviceToken.length()<=0) {

            deviceToken="";
        }


        final String finalDeviceToken = deviceToken;

        StringRequest sr = new StringRequest(Request.Method.POST, AlbanianConstants.base_url , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d(AlbanianConstants.TAG, "updateTokenOnServer= "+response);

//                parseResponse(response.toString());


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                VolleyLog.d(AlbanianConstants.TAG, "Error: " + error.getMessage());
                Log.d(AlbanianConstants.TAG, "" + error.getMessage() + "," + error.toString());

//                AlbanianApplication.hideProgressDialog(getActivity());

            }
        })

        {



            @Override
            protected Map<String, String> getParams()
            {
                Log.d("sumit","device tocken==== "+finalDeviceToken);

                Map<String, String> params = new HashMap<String, String>();

                params.put("api_name", "getusertoken");
                params.put("user_id", pref.getUserData().getUserId());
                params.put("AppName", AlbanianConstants.AppName);
//                params.put("deviceId", "2");
                params.put("user_device_type", "2");
                params.put("user_device_token", (finalDeviceToken==null||finalDeviceToken.length()<=0)?"":finalDeviceToken);

                return params;
            }

        };


        AlbanianApplication.getInstance().addToRequestQueue(sr);
    }


    private void init() {
        initViews();
        initListners();
    }

    private void initListners() {

        upgrade.setOnClickListener(this);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
    }

    private void initViews() {


        rl_favourites_header = (RelativeLayout)mView.findViewById(R.id.rl_favourites_header);
        rl_viewedyou_header = (RelativeLayout)mView.findViewById(R.id.rl_viewedyou_header);
        weValleLink = (RelativeLayout)mView.findViewById(R.id.rl_wevalle);
        weValleLink.setVisibility(View.VISIBLE);
        headertext = (TextView)mView.findViewById(R.id.txt_headerlogo);
        headertext.setText(getString(R.string.WeValle));
        headertext.setTextSize(TypedValue.COMPLEX_UNIT_PX,
                getResources().getDimension(R.dimen.headertextsize));
        headertext.setVisibility(View.VISIBLE);

        profileicon= (ImageView) mView.findViewById(R.id.img_profile_header);

        profileicon.setVisibility(View.GONE);

        profileicon.setOnClickListener(this);

        events_list = (RecyclerView) mView.findViewById(R.id.vertical_events_list);
        events_list.setHasFixedSize(true);

        rl_fav_nouser = (RelativeLayout) mView.findViewById(R.id.rl_fav_nouser);
        rl_viewed_nouser = (RelativeLayout) mView.findViewById(R.id.rl_viewed_nouser);
        rl_invite_btn = (RelativeLayout) mView.findViewById(R.id.rl_invite_btn);

        favourite_list = (RecyclerView) mView.findViewById(R.id.horizontal_fav_users_list);
        favourite_list.setHasFixedSize(true);

        homegallery = (RecyclerView) mView.findViewById(R.id.vertical_users_list);
        homegallery.setHasFixedSize(true);



        upgrade=(RelativeLayout)mView.findViewById(R.id.ll_upgrade_to_premium);

        offertext=(TextView)mView.findViewById(R.id.txt_offertext);


//        weValleLink.setOnClickListener(this);

        setUserImage();


        ImageView leftmenubutton=(ImageView)mView.findViewById(R.id.img_menu);

        leftmenubutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                toggleDrawer();

                FragmentManager mFragmentManager = getFragmentManager();
                FragmentTransaction mFragmentTransaction = mFragmentManager
                        .beginTransaction();
                SettingsFragment mFragment = new SettingsFragment();

                mFragmentTransaction.replace(R.id.container_body, mFragment);
                mFragmentTransaction.addToBackStack(null);
                mFragmentTransaction.commit();

            }
        });


        rl_invite_btn.setOnClickListener(this);

        Log.d("sumit","profile icon-== "+pref.getUserData().getUserGender());

//        if (pref.getUserData().getUserGender().equalsIgnoreCase("f"))
        {


//            try {
//                imageLoader
//                        .displayImage(pref.getUserData().getUserImage(),
//                                profileicon, options, animateFirstListener);
//            } catch (Exception e) {
//                e.printStackTrace();
//            }

//            profileicon.setImageDrawable(getResources().getDrawable(R.drawable.profile_female));
        }
//        else
//        {
//            profileicon.setImageDrawable(getResources().getDrawable(R.drawable.profile_male));
//        }


    }




    private void setUserImage() {

//        username.setText(pref.getUserData().getUserName());
//        imageLoader.displayImage(pref.getUserData().getUserImage(), userimage, options, animateFirstListener);

    }



    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.rl_invite_btn:

                Uri imageUri = Uri.parse("android.resource://" + getActivity().getPackageName()
                        + "/drawable/" + "wevalletextlogo");
                Intent shareIntent = new Intent();
                shareIntent.setAction(Intent.ACTION_SEND);
                shareIntent.putExtra(Intent.EXTRA_TEXT, "I'm using an app called WeValle to meet people with my heritage." +
                                                        " Get it at www.wevalle.com");
                shareIntent.putExtra(Intent.EXTRA_STREAM, imageUri);
                shareIntent.setType("image/jpeg");
                shareIntent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                startActivity(Intent.createChooser(shareIntent, "send"));

                break;
            case R.id.rl_favorites:
                Fragment favoriteFragment = new FavoritesFragment();
                addFragment(favoriteFragment);

                break;


            case R.id.img_profile_header:

                AlbanianApplication.getInstance().trackEvent("ProfilePage", "ProfilePage", "ProfilePage");

                loadProfile(pref.getUserData().getUserId());

                break;

            case R.id.rl_wevalle:


                loadFilterPage();


                break;



            case R.id.ll_upgrade_to_premium:


                Bundle bundle=new Bundle();
                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                        AlbanianConstants.TAB_2_TAG);

//                mActivity.pushFragments(AlbanianConstants.TAB_2_TAG,
//                        new UpgradeFragment(), false, true, bundle);


                break;

            default:
                break;
        }


    }

    private void loadFilterPage() {

        final FragmentManager fragmentManager = getFragmentManager();
        final Fragment content = fragmentManager.findFragmentById(R.id.container_body);


        Bundle bundle = new Bundle();
//        bundle.putString(AlbanianConstants.EXTRA_LOCALPAGENAME, extraFaq);
        bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                AlbanianConstants.TAB_2_TAG);


//        mActivity.pushFragments(AlbanianConstants.TAB_2_TAG,
//                new FilterFragment(), false, true,bundle);
    }

    private void loadProfile(String userId) {



        if (fragmentFlag ) {

            fragmentFlag=false;

            final FragmentManager fragmentManager = getFragmentManager();
            final Fragment content = fragmentManager.findFragmentById(R.id.container_body);
            if (content == null || !(content instanceof ProfileFragment))
            {

                Log.d("sumit","profile fragment loading now............");

//                final FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
//                final ProfileFragment myFragment = new ProfileFragment();

                Bundle bundle = new Bundle();
                bundle.putString(AlbanianConstants.EXTRA_PROFILEVISITEDID, userId);
                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                        AlbanianConstants.TAB_2_TAG);
//                myFragment.setArguments(bundle);

//                fragmentTransaction.replace(R.id.container_body, myFragment);
//                fragmentTransaction.addToBackStack(null);
//                fragmentTransaction.commit();


//                mActivity.pushFragments(AlbanianConstants.TAB_2_TAG,
//                        new ProfileFragment(), false, true,bundle);



            }
        }

    }


    private void loadLocalPage(String extraFaq) {


            final FragmentManager fragmentManager = getFragmentManager();
            final Fragment content = fragmentManager.findFragmentById(R.id.container_body);


                Bundle bundle = new Bundle();
                bundle.putString(AlbanianConstants.EXTRA_LOCALPAGENAME, extraFaq);
                bundle.putString(AlbanianConstants.EXTRA_CURRENTTAB_TAG,
                        AlbanianConstants.TAB_2_TAG);


//                mActivity.pushFragments(AlbanianConstants.TAB_2_TAG,
//                        new FaqFragment(), false, true,bundle);



    }

    private void addFragment(Fragment fragment) {
        getFragmentManager().popBackStack(null,FragmentManager.POP_BACK_STACK_INCLUSIVE);

        FragmentTransaction transaction = getFragmentManager().beginTransaction();
        transaction.replace(R.id.container_body, fragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }




    @Override
    public void onPause() {
        // TODO Auto-generated method stub
        super.onPause();

        getActivity().unregisterReceiver(mMessageReceiver);
    }


    //This is the handler that will manager to process the broadcast intent
    private BroadcastReceiver mMessageReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {

            // Extract data included in the Intent
            String message = intent.getStringExtra("message");

            Log.d("sumit", " broadcast on home message= " + message);

            Message msg = new Message();
            msg.what = 1;
            mHandler.sendMessage(msg);

        }
    };
}
